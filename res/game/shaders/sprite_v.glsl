#version 330 core
layout (location = 1) in vec2 position;
layout (location = 2) in vec2 texCoords;
layout (location = 3) in float texID;

uniform mat4 model;
uniform mat4 projection;

out vec2 fTexCoords;
out float fTexID;

void main() {
    fTexID = texID;
    fTexCoords = texCoords;
    gl_Position = projection * model * vec4(position.xy, 0.0f, 1.0f);
}
