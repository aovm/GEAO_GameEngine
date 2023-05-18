package com.orlang.examples;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.core.input.KeyListener;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ExampleRender2 {

    /*private static float[] vertices = {
      // 3D pos             color RGB
         -0.66f, 0.66f, -1.3f,     1.f, 1.f, 1.f, // Top front left
         0.66f, 0.66f,  -1.3f,     1.f, 1.f, 1.f, // Top front right
         -0.66f, 0.66f, -1.5f,     1.f, 1.f, 1.f, // Top back left
         0.66f, 0.66f, -1.5f,      1.f, 1.f, 1.f, // Top back right

         -0.66f, 0.66f, -0.5f,     1.f, 1.f, 1.f, // Top front left
         -0.66f, -0.66f, -0.5f,    1.f, 1.f, 1.f, // Bottom front left

         -0.66f, -0.66f, -0.5f,    1.f, 1.f, 1.f, // Bottom front left
         0.66f, -0.66f, -0.5f,     1.f, 1.f, 1.f, // Bottom front right
         -0.66f, -0.66f, -1.5f,    1.f, 1.f, 1.f, // Bottom back left
         0.66f, -0.66f, -1.5f,    1.f, 1.f, 1.f  // Bottom back right

    };*/

    private static float[] vertices = new float[48];

    private static int[] elementArray = {
        3, 2, 1, // BACK
        0, 1, 2,
        7, 6, 3, // BOTTOM
        2, 3, 6,
        5, 4, 1, // TOP
        0, 1, 4,
        6, 2, 4, // RIGHT
        0, 4, 2,
        7, 5, 3, // LEFT
        1, 3, 5,
        7, 6, 5, // FRONT
        4, 5, 6
    };

    private static float[] colors = {
        1.f, 0.f, 0.f,
        0.f, 1.f, 0.f,
        1.f, 1.f, 1.f,
        1.f, 1.f, 0.f,
        0.f, 1.f, 1.f,
        0.f, 0.f, 1.f,
        0.5f, 0.5f, 0.2f,
        1.f, 0.8f, 0.2f
    };

    private static float x = -0.2f;
    private static float y = -0.5f;
    private static float z = -2.5f;

    private static boolean wireframe = false;
    private static boolean qLock = false;

    private static int vaoID;
    private static int vboID;
    private static int eboID;
    private static Shader shaderProgram;
    private static Matrix4f perspective = new Matrix4f().perspective(100.f, 16.f/9, 0.1f,3.f);

    public static float totalTime = 0;
    public static Vector3f color = new Vector3f(1.f, 1.f, 1.f);
    public static int rotation = 0;

    public static void init() {
        updateVertices(0.01f);

        glEnable(GL_DEPTH_TEST);

        shaderProgram = Resources.lazyShader("res/shaders/example2_v.glsl",
                                                "res/shaders/example2_f.glsl");

        // Generate the VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Generate the VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArray, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glDisable(GL_DEPTH_TEST);
    }

    public static void updateVertices(float dt) {
        totalTime += dt;

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_W)) {
            y -= 0.01f * dt * 144;
        } else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_S)) {
            y += 0.01f * dt * 144;
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_D)) {
            x += 0.01f * dt * 144;
        } else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_F)) {
            x -= 0.01f * dt * 144;
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_T)) {
            z -= 0.01f * dt * 144;
        } else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_G)) {
            z += 0.01f * dt * 144;
        }

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_Q)) {
            if (qLock) return;
            qLock = true;

            if (!wireframe) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                wireframe = true;
            } else {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                wireframe = false;
            }

        } else {
            qLock = false;
        }

        createCube(x, y, z,0.3f, color, rotation);
    }

    private static void createCube(float xBLF, float yBLF, float zBLF, float length, Vector3f color, float rotation) {
        int offset = 0;
        int cOffset = 0;
        for (int z = 0; z < 2; z++) {
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 2; x++) {
                    vertices[offset] = xBLF + x * length;
                    vertices[offset + 1] = yBLF + y * length;
                    vertices[offset + 2] = zBLF + z * length;
                    vertices[offset + 3] = colors[cOffset];
                    vertices[offset + 4] = colors[cOffset + 1];
                    vertices[offset + 5] = colors[cOffset + 2];
                    offset += 6;
                    cOffset += 3;
                }
            }
        }
    }

    public static void render() {
        glEnable(GL_DEPTH_TEST);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shaderProgram.attach();
        shaderProgram.uploadUniformMatrix4f("perspective", perspective);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glLineWidth(5.5f);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shaderProgram.detach();

        glClear(GL_DEPTH_BUFFER_BIT);
        glLineWidth(1.0f);
        glDisable(GL_DEPTH_TEST);
    }

}
