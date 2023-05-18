#version 330 core

in vec2 fUV;
out vec4 color;

uniform sampler2D texture1;
uniform sampler2D texture2;

void main() {
    color = mix(texture(texture1, fUV),
                texture(texture2, fUV), 0.75f);
}