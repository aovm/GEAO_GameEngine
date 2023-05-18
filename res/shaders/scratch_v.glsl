#version 330 core

layout (location = 0) in vec2 vPos;
layout (location = 1) in vec2 vUV;

uniform mat4 view;

out vec2 fUV;

void main() {
    fUV = vUV;
    gl_Position = view * vec4(vVertices.x, vVertices.y, 0.f, 1.f);
}