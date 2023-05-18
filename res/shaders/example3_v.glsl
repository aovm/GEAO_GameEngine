#version 330 core

layout (location = 0) in vec2 vPos;
layout (location = 1) in vec3 vColor;
layout (location = 2) in vec2 vUV;

uniform mat4 transform;

out vec3 fColor;
out vec2 fUV;

void main() {
    fColor = vColor;
    fUV = vUV;
    gl_Position = transform * vec4(vPos, 0.f, 1.f);
}