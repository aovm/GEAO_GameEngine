#version 330 core

layout (location = 0) in vec3 vPos;
layout (location = 1) in vec3 vColor;

uniform mat4 perspective;

out vec3 fColor;

void main() {
    fColor = vColor;
    gl_Position = perspective * vec4(vPos, 1.f);
}
