#version 330 core
layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 uv;
layout (location = 2) in mat4 model;

uniform mat4 cameraPerspective;

out vec4 fColor;
out vec2 fUV;

void main() {
    fUV = uv;
    fColor = color;
    gl_Position = cameraPerspective * model * vec4(pos, 1.0f);
}
