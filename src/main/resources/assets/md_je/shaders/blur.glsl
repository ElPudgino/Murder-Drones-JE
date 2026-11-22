#version 120

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform vec2 BlurDir;

// Normalized Gaussian, sigma 3, radius 6. Input is already alpha-premultiplied.
const float weight[13] = float[] (
    0.018544022, 0.034166942, 0.056331764, 0.083108539,
    0.109719294, 0.129618031, 0.137022816, 0.129618031,
    0.109719294, 0.083108539, 0.056331764, 0.034166942,
    0.018544022
);

void main() {
    vec2 uv = gl_TexCoord[0].st;
    vec2 stepSize = BlurDir / InSize;
    vec3 blurred = vec3(0.0);
    for (int i = -6; i <= 6; i++) {
        blurred += texture2D(DiffuseSampler, uv + stepSize * float(i)).rgb * weight[i + 6];
    }
    gl_FragColor = vec4(blurred, 1.0);
}
