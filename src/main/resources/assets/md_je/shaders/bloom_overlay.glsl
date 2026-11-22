#version 120

uniform sampler2D BloomSampler;
uniform sampler2D EmissionSampler;
uniform sampler2D BackgroundSampler;
uniform float intensive;
uniform float base;
uniform float threshold_up;
uniform float threshold_down;

void main() {
    vec2 uv = gl_TexCoord[0].st;

    vec3 bloom = (texture2D(BloomSampler, uv).rgb + texture2D(EmissionSampler, uv).rgb) * intensive;
    vec3 background = texture2D(BackgroundSampler, uv).rgb;

    float mx = max(background.b, max(background.r, background.g));
    float mn = min(background.b, min(background.r, background.g));
    vec4 clr1 = vec4(background + bloom * ((1. - (mx + mn) / 2.) * (threshold_up - threshold_down) + threshold_down + base), 1.);
    
    float bmax = max(bloom.b, max(bloom.r, bloom.g));
    float ratio = 0.0;
    if (bmax > 1.001) 
    {
        ratio = min((bmax - 1.0) / 0.5, 1.0);
        bloom = bmax * bloom;
    }

    gl_FragColor =clr1;
}