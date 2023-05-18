package com.orlang.examples;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;

import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ExampleRender {

    private static float[] vertices = {
            // VERTEX DATA      // COLOR RGBA
            0.5f, 0.5f,         0.0f, 1.f, 0.0f,
            -0.5f, -0.5f,         1.f, 0f, 0f,
            0.5f, -0.5f,         0.0f, 0.0f, 1.f,
            -0.5f, 0.5f,         1.f, 1.f, 0f
    };

    private static int vaoID;
    private static int vboID;
    private static Shader shaderProgram;

    public static void init() {
        shaderProgram = Resources.lazyShader("res/shaders/example_v.glsl",
                                            "res/shaders/example_f.glsl");
        // Generates and binds the VAO.
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Generates and binds the Array Buffer.
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] elementIndices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementIndices, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 5 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public static void render() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shaderProgram.attach();

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shaderProgram.detach();
    }

    private static int[] generateIndices() {
        // 6 indices per quad texture.
        int[] elements = new int[6];
        for (int i = 0; i < 1; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private static void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1         7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset;
        elements[offsetArrayIndex + 1] = offset + 1;
        elements[offsetArrayIndex + 2] = offset + 2;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 1;
        elements[offsetArrayIndex + 4] = offset;
        elements[offsetArrayIndex + 5] = offset + 3;
    }

}
