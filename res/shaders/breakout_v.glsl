#version 330 core
layout (location = 0) in vec4 vertex; // <vec2 pos, vec2 texCoords>

out vec2 fTexCoords;

uniform mat4 model;
uniform mat4 projection;

void main() {
    fTexCoords = vertex.zw;
    gl_Position = projection * model * vec4(vertex.xy, 0.0f, 1.0f);
}
