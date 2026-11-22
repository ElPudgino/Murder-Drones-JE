package pzi.md_je.loading;

import net.minecraft.util.ResourceLocation;
import pzi.md_je.MD;

public class ResourcesHelper {

    public static String GetShadersPath()
    {
        return "/assets/"+MD.MODID+"/shaders/";
    }   

    public static String GetModelsPath()
    {
        return "obj_models/";
    }

    public static String GetShaderTexturesPath()
    {
        return "textures/shaders/";
    }
    public static String GetAnimationPath(String animName)
    {
        return "assets/"+MD.MODID+"/animations/"+animName+".animation.json";
    }

    public static ResourceLocation GetModelLocation(String name)
    {
        return GetLocation(GetModelsPath()+name);
    }

    public static ResourceLocation GetShaderTextureLocation(String name)
    {
        return GetLocation(GetShaderTexturesPath()+name);
    }

    public static ResourceLocation GetLocation(String path)
    {
        return new ResourceLocation(MD.MODID, path);
    }

    public static ResourceLocation GetGuiTexture(String name)
    {
        return GetLocation("textures/gui/"+name);
    }
    public static ResourceLocation GetEntityTexture(String name)
    {
        return GetLocation("textures/entities/"+name);
    }

    public static ResourceLocation GetTextureLocation(String path)
    {
        return new ResourceLocation(MD.MODID, "textures/"+path);
    }
    
}
