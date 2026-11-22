#version 120

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;

vec3 emissionAt(vec2 uv) {
    vec4 source = texture2D(DiffuseSampler, uv);
    return source.rgb * source.a;
}

void main() {
    vec2 uv = gl_TexCoord[0].st;
    vec2 offset = 0.5 / InSize;
    vec3 color = emissionAt(uv + vec2(-offset.x, -offset.y));
    color += emissionAt(uv + vec2(offset.x, -offset.y));
    color += emissionAt(uv + vec2(-offset.x, offset.y));
    color += emissionAt(uv + vec2(offset.x, offset.y));
    gl_FragColor = vec4(color * 0.25, 1.0);
}
