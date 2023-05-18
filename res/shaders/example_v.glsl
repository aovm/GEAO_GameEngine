#version 330 core

layout (location = 0) in vec2 vVertices;
layout (location = 1) in vec3 vColor;

out vec3 fColor;

void main() {
    fColor = vColor;
    gl_Position = vec4(vVertices.x, vVertices.y, 1.f, 1.f);
}