package pzi.md_je.rendering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pzi.md_je.MD;
import pzi.md_je.loading.ResourcesHelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Parses GeckoLib animation JSON files (format version 1.8.0 / geckolib_format_version 2)
 * into a structure of keyframes that can be used with Forge 1.12.2's manual animation system.
 */
public class AnimLoader 
{

    /**
     * Interpolation type used between keyframes.
     */
    public enum Interpolation {
        LINEAR,
        CATMULLROM
    }

    /**
     * A single keyframe: time (in seconds) and a 3‑component vector value.
     */
    public static class Keyframe {
        public float time;
        public float[] vector = new float[3];  // [x, y, z]
        public Interpolation interpolation = Interpolation.LINEAR;

        @Override
        public String toString() {
            return String.format("Keyframe{time=%.4f, vector=[%.4f, %.4f, %.4f], interp=%s}",
                    time, vector[0], vector[1], vector[2], interpolation);
        }
    }

    /**
     * Animation data for a single bone: separate lists of rotation and position keyframes.
     */
    public static class BoneAnimation {
        public final List<Keyframe> rotationKeyframes = new ArrayList<>();
        public final List<Keyframe> positionKeyframes = new ArrayList<>();
        public final List<Keyframe> scaleKeyframes = new ArrayList<>();

        @Override
        public String toString() {
            return "BoneAnimation{" +
                    "rotationKeyframes=" + rotationKeyframes +
                    ", positionKeyframes=" + positionKeyframes +
                    ", scaleKeyframes=" + scaleKeyframes +
                    '}';
        }
    }

    /**
     * Complete animation data: length, loop flag, and per‑bone animations.
     */
    public static class AnimationData {
        public float length;
        public boolean loop;
        public final Map<String, BoneAnimation> bones = new HashMap<>();

        @Override
        public String toString() {
            return "AnimationData{" +
                    "length=" + length +
                    ", loop=" + loop +
                    ", bones=" + bones.keySet() +
                    '}';
        }
    }

    /**
     * Container for all animations found in the file.
     */
    public static class AnimationContainer {
        public final Map<String, AnimationData> animations = new HashMap<>();
    }

    public static AnimationContainer parseAt(String name) throws IOException 
    {
        String path = ResourcesHelper.GetAnimationPath(name); // e.g. "assets/mymod/animations/foo.json"
        try (InputStream in = AnimLoader.class.getResourceAsStream("/" + path)) {
            if (in == null) {
                throw new IOException("Animation resource not found: " + path);
            }
            return parse(in);
        }
    }

    public static AnimationContainer parse(InputStream in) throws IOException 
    {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonObject root = new JsonParser().parse(reader).getAsJsonObject();
            return parseRoot(root);
        }
    }

     private static AnimationContainer parseRoot(JsonObject root) {
        AnimationContainer container = new AnimationContainer();
        JsonObject animationsObj = root.getAsJsonObject("animations");
        if (animationsObj == null) return container;

        for (Map.Entry<String, JsonElement> entry : animationsObj.entrySet()) {
            String name = entry.getKey();
            MD.logger.info("anim name: "+name);
            JsonObject animObj = entry.getValue().getAsJsonObject();
            AnimationData data = parseAnimation(animObj);
            container.animations.put(name, data);
        }
        return container;
    }

    private static AnimationData parseAnimation(JsonObject animObj) {
        AnimationData data = new AnimationData();
        data.length = animObj.get("animation_length").getAsFloat();
        JsonElement loop = animObj.get("loop");
        if (loop == null) data.loop = false;
        else data.loop = loop.getAsBoolean();

        JsonObject bonesObj = animObj.getAsJsonObject("bones");
        if (bonesObj != null) {
            for (Map.Entry<String, JsonElement> boneEntry : bonesObj.entrySet()) {
                String boneName = boneEntry.getKey();
                JsonObject boneDataObj = boneEntry.getValue().getAsJsonObject();
                BoneAnimation boneAnim = parseBone(boneDataObj);
                data.bones.put(boneName, boneAnim);
            }
        }
        return data;
    }

    private static BoneAnimation parseBone(JsonObject boneDataObj) {
        BoneAnimation boneAnim = new BoneAnimation();

        // ---- Rotation ----
        JsonElement rotElem = boneDataObj.get("rotation");
        if (rotElem != null && rotElem.isJsonObject()) {
            JsonObject rotObj = rotElem.getAsJsonObject();
            // Check if this is a constant value (direct "vector" key)
            if (rotObj.has("vector") && rotObj.get("vector").isJsonArray()) {
                Keyframe kf = new Keyframe();
                kf.time = 0f;
                JsonArray vec = rotObj.getAsJsonArray("vector");
                kf.vector[0] = vec.get(0).getAsFloat();
                kf.vector[1] = vec.get(1).getAsFloat();
                kf.vector[2] = vec.get(2).getAsFloat();
                // No lerp_mode – default linear
                boneAnim.rotationKeyframes.add(kf);
            } else {
                // It's a map of time -> keyframe
                for (Map.Entry<String, JsonElement> rotEntry : rotObj.entrySet()) {
                    try {
                        float time = Float.parseFloat(rotEntry.getKey());
                        JsonObject keyObj = rotEntry.getValue().getAsJsonObject();
                        Keyframe kf = parseKeyframe(time, keyObj);
                        boneAnim.rotationKeyframes.add(kf);
                    } catch (NumberFormatException e) {
                        // Silently skip non‑numeric keys (should not happen if we handled "vector" above)
                    }
                }
                boneAnim.rotationKeyframes.sort(Comparator.comparingDouble(k -> k.time));
            }
        }

        // ---- Position ----
        JsonElement posElem = boneDataObj.get("position");
        if (posElem != null && posElem.isJsonObject()) {
            JsonObject posObj = posElem.getAsJsonObject();
            if (posObj.has("vector") && posObj.get("vector").isJsonArray()) {
                Keyframe kf = new Keyframe();
                kf.time = 0f;
                JsonArray vec = posObj.getAsJsonArray("vector");
                kf.vector[0] = vec.get(0).getAsFloat();
                kf.vector[1] = vec.get(1).getAsFloat();
                kf.vector[2] = vec.get(2).getAsFloat();
                boneAnim.positionKeyframes.add(kf);
            } else {
                for (Map.Entry<String, JsonElement> posEntry : posObj.entrySet()) {
                    try {
                        float time = Float.parseFloat(posEntry.getKey());
                        JsonObject keyObj = posEntry.getValue().getAsJsonObject();
                        Keyframe kf = parseKeyframe(time, keyObj);
                        boneAnim.positionKeyframes.add(kf);
                    } catch (NumberFormatException e) {
                        // Skip non‑numeric keys
                    }
                }
                boneAnim.positionKeyframes.sort(Comparator.comparingDouble(k -> k.time));
            }
        }

        // ---- Scale ----
        JsonElement scaleElem = boneDataObj.get("scale");
        if (scaleElem!= null && scaleElem.isJsonObject()) {
            JsonObject scaleObj = scaleElem.getAsJsonObject();
            if (scaleObj.has("vector") && scaleObj.get("vector").isJsonArray()) {
                Keyframe kf = new Keyframe();
                kf.time = 0f;
                JsonArray vec = scaleObj.getAsJsonArray("vector");
                kf.vector[0] = vec.get(0).getAsFloat();
                kf.vector[1] = vec.get(1).getAsFloat();
                kf.vector[2] = vec.get(2).getAsFloat();
                boneAnim.scaleKeyframes.add(kf);
            } else {
                for (Map.Entry<String, JsonElement> scaleEntry : scaleObj.entrySet()) {
                    try {
                        float time = Float.parseFloat(scaleEntry.getKey());
                        JsonObject keyObj = scaleEntry.getValue().getAsJsonObject();
                        Keyframe kf = parseKeyframe(time, keyObj);
                        boneAnim.scaleKeyframes.add(kf);
                    } catch (NumberFormatException e) {
                        // Skip non‑numeric keys
                    }
                }
                boneAnim.scaleKeyframes.sort(Comparator.comparingDouble(k -> k.time));
            }
        }
        return boneAnim;
    }

    private static Keyframe parseKeyframe(float time, JsonObject keyObj) {
        Keyframe kf = new Keyframe();
        kf.time = time;

        JsonArray vectorArr = keyObj.getAsJsonArray("vector");
        if (vectorArr != null && vectorArr.size() == 3) {
            kf.vector[0] = vectorArr.get(0).getAsFloat();
            kf.vector[1] = vectorArr.get(1).getAsFloat();
            kf.vector[2] = vectorArr.get(2).getAsFloat();
        }

        JsonElement lerpElem = keyObj.get("lerp_mode");
        if (lerpElem != null) {
            String mode = lerpElem.getAsString();
            if ("catmullrom".equalsIgnoreCase(mode)) {
                kf.interpolation = Interpolation.CATMULLROM;
            } else {
                kf.interpolation = Interpolation.LINEAR;
            }
        }
        return kf;
    }

    public static void test(String filename) 
    {


        try {
            AnimationContainer container = parseAt(filename);

            // Log using your mod's logger – make sure MD.logger is static and accessible.
            MD.logger.info("Loaded animation file: " + filename);

            for (Map.Entry<String, AnimationData> animEntry : container.animations.entrySet()) {
                String animName = animEntry.getKey();
                AnimationData data = animEntry.getValue();
                MD.logger.info("Animation: " + animName);
                MD.logger.info("  Length: " + data.length + ", Loop: " + data.loop);
                for (Map.Entry<String, BoneAnimation> boneEntry : data.bones.entrySet()) {
                    String boneName = boneEntry.getKey();
                    BoneAnimation boneAnim = boneEntry.getValue();
                    MD.logger.info("  Bone: " + boneName);
                    if (!boneAnim.rotationKeyframes.isEmpty()) {
                        MD.logger.info("    Rotation keyframes:");
                        for (Keyframe kf : boneAnim.rotationKeyframes) {
                            MD.logger.info("      " + kf);
                        }
                    }
                    if (!boneAnim.positionKeyframes.isEmpty()) {
                        MD.logger.info("    Position keyframes:");
                        for (Keyframe kf : boneAnim.positionKeyframes) {
                            MD.logger.info("      " + kf);
                        }
                    }
                }
            }

        } catch (IOException e) {
            MD.logger.error("Failed to parse animation file: " + filename, e);
        }
    }
}