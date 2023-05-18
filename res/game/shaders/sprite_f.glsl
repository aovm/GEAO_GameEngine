#version 330 core
in vec2 fTexCoords;
in float fTexID;

uniform sampler2D textures[15];
uniform vec3 spriteColor;

out vec4 color;

void main() {
    int texID = int(fTexID);
    color = vec4(spriteColor, 1.0f) * texture(textures[texID], fTexCoords);
}
