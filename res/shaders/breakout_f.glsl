#version 330 core

in vec2 fTexCoords;
out vec4 color;

uniform sampler2D texture0;
uniform vec3 spriteColor;

void main() {
    color = vec4(spriteColor, 1.0f) * texture(texture0, fTexCoords);
}
