#version 330 core

layout (location = 0) in vec3 vPos;
layout (location = 1) in vec2 vUV;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

out vec2 fUV;

void main() {
    fUV = vUV;
    gl_Position = projection * view * model * vec4(vPos, 1.f);
}