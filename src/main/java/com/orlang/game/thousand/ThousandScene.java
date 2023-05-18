package com.orlang.game.thousand;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.scene.Scene;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;


public class ThousandScene extends Scene {

    // vec3 pos = 3
    // vec2 uv = 2
    // mat4 modelMatrix (4 * vec4) = 16
    // Total per vertex = 21

    private int megaCubeRadius = 10;
    private int CUBE_AMOUNT = megaCubeRadius * megaCubeRadius * megaCubeRadius;

    private int VERTEX_SIZE = 21;
    private int VERTEX_SIZE_BYTES = 21 * Float.BYTES;

    private float[] vertices;
    private static float[] singleCubeVertices = {
            // 3D position,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
    };
    private int mainVaoID;
    private int vboID;
    private Matrix4f cameraPerspective;
    private Shader asteroides;

    public ThousandScene() {
        asteroides = Resources.lazyShader("res/shaders/thousand_v.glsl", "res/shaders/thousand_f.glsl");


        this.vertices = new float[CUBE_AMOUNT * VERTEX_SIZE * 36];

        mainVaoID = glGenVertexArrays();
        glBindVertexArray(mainVaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // View matrix
        cameraPerspective = new Matrix4f();
        Vector3f cameraPos = new Vector3f(0.0f, 0.0f, 3.0f);
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        cameraPerspective.lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp);

        // Projection matrix
        cameraPerspective.perspective((float) (Math.PI / 3.f), 16.f/9.f, -1.0f, 1000.f);
    }

    private void updateVertices() {
        for (int i = 0; i < CUBE_AMOUNT; i++ ) {

        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(float dt) {
        glEnable(GL_DEPTH_TEST);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        glBindVertexArray(mainVaoID);
        asteroides.attach();



        asteroides.detach();
        glBindVertexArray(0);
    }
}
