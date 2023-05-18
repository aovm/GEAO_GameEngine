package com.orlang.examples;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.core.input.KeyListener;
import com.orlang.engine.core.input.MouseListener;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class ExampleRender4 {

    private static float[] vertices = {
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

    private static Vector3f[] cubePositions = {
            new Vector3f(0.0f, 0.0f, 0.0f),
            new Vector3f(2.0f, 5.0f, -15.0f),
            new Vector3f(-1.5f, -2.2f, -2.5f),

            new Vector3f(-3.8f, -2.0f, -12.3f),
            new Vector3f(2.4f, -0.4f, -3.5f),
            new Vector3f(-1.7f, 3.0f, -7.5f),

            new Vector3f(1.3f, -2.0f, -2.5f),
            new Vector3f(1.5f, 2.0f, -2.5f),
            new Vector3f(1.5f, 0.2f, -1.5f),
            new Vector3f(-1.3f, 1.0f, -1.5f),
    };

    private static int tex0ID;
    private static int tex1ID;

    private static int vaoID;
    private static int vboID;
    private static Shader shaderProgram;

    private static Vector3f cameraDirection = new Vector3f();
    private static double yaw = -90.0f;
    private static double pitch = 0.0f;
    private static double roll = 0.0f;
    private static double lastX = 400;
    private static double lastY = 300;
    private static boolean firstMouse = true;
    private static double zoom = 25.0f;

    public static void init(long window) {
        glEnable(GL_DEPTH_TEST);
        shaderProgram = Resources.lazyShader("res/shaders/example4_v.glsl",
                                                            "res/shaders/example4_f.glsl");

        model = new Matrix4f();
        model.rotate((float) toRadians(50.0), new Vector3f(0.5f, 1.0f, 0.0f));
        view = new Matrix4f();
        proj = new Matrix4f();
        proj.perspective((float) toRadians(45.0), 16.f/9.f, 0.1f, 100.f);

        tex0ID = loadImage("res/images/sus.png");
        tex1ID = loadImage("res/images/structure_blocks/oak_planks.png");

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glDisable(GL_DEPTH_TEST);
    }

    private static int loadImage(String path) {

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer imageBytes;
        stbi_set_flip_vertically_on_load(true);
        imageBytes = stbi_load(path, width, height, channels, 0);

        if (imageBytes == null) {
            throw new IllegalStateException("ERROR: Image could not be loaded: " + path);
        }

        int texturedID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texturedID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (channels.get(0) == 4) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, imageBytes);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                    0, GL_RGB, GL_UNSIGNED_BYTE, imageBytes);
        }

        stbi_image_free(imageBytes);

        return texturedID;
    }

    public static double toRadians(double degrees) { return degrees * (Math.PI / 180.f); }

    private static Matrix4f model;
    private static Matrix4f view;
    private static Matrix4f proj;
    private static float time = 0.0f;

    private static Vector3f cameraPos = new Vector3f(0.0f, 0.0f, 3.0f);
    private static Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    private static Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    public static void mouseInput() {
        double xpos = MouseListener.getCurrentX();
        double ypos = MouseListener.getCurrentY();

        if (firstMouse) {
            lastX = xpos;
            lastY = ypos;
            firstMouse = false;
        }

        double xOffset = xpos - lastX;
        double yOffset = lastY - ypos;
        lastX = xpos;
        lastY = ypos;

        double sensitivity = 0.1;
        xOffset *= sensitivity;
        yOffset *= sensitivity;

        yaw += xOffset;
        pitch += yOffset;

        if (pitch > 89.0) { pitch = 89.0; }
        if (pitch < -89.0) { pitch = -89.0; }

        cameraDirection.x = (float) (Math.cos(toRadians(yaw)) * Math.cos(toRadians(pitch)));
        cameraDirection.y = (float) (Math.sin(toRadians(pitch)));
        cameraDirection.z = (float) (Math.sin(toRadians(yaw)) * Math.cos(toRadians(pitch)));
        cameraDirection.normalize(cameraFront);
    }

    public static void scrollUpdate() {
        zoom -= MouseListener.getScrollY();
        if (zoom < 1.0) {
            zoom = 1.0;
        }
        if (zoom > 45.0) {
            zoom = 45.0;
        }
    }

    public static void loop(float dt) {
        //
        // UPDATE STAGE
        //
        time += dt;

        mouseInput();
        scrollUpdate();

        float interval = 4.f;
        float angle = (float) (180 * (Math.sin(time * Math.PI / interval) + 1));

        float generalSpeed = 0.1f * dt * 60;
        Vector3f cameraSpeed = new Vector3f(generalSpeed);
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            cameraPos.add(new Vector3f(cameraSpeed).mul(cameraFront));
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            cameraPos.sub(new Vector3f(cameraSpeed).mul(cameraFront));
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            cameraPos.sub(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed));
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            cameraPos.add(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed));
        }

        //
        // RENDER STAGE
        //

        glEnable(GL_DEPTH_TEST);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tex0ID);

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, tex1ID);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shaderProgram.attach();

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        shaderProgram.uploadUniformInt("texture1", 0);
        shaderProgram.uploadUniformInt("texture2", 1);

        view.identity();
        view.lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp);
        shaderProgram.uploadUniformMatrix4f("view", view);
        proj.identity();
        proj.perspective((float) toRadians(zoom), 16.f/9.f, 0.1f, 100.f);
        shaderProgram.uploadUniformMatrix4f("projection", proj);

        for (int i = 0; i < 10; i++) {
            model.identity();
            model.translate(cubePositions[i]);
            if (i == 0 || (i % 3 == 0)) {
                model.rotate((float) toRadians(angle - ((i * i) % 100)), new Vector3f(1.0f, 0.3f, 0.5f));
            } else {
                model.rotate((float) toRadians(20.f * i), new Vector3f(1.0f, 0.3f, 0.5f));
            }
            shaderProgram.uploadUniformMatrix4f("model", model);
            glDrawArrays(GL_TRIANGLES, 0, vertices.length / 5);
        }

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        shaderProgram.detach();
        glBindVertexArray(0);

        glDisable(GL_DEPTH_TEST);
    }

}
