package com.orlang.examples;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.core.input.KeyListener;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.stb.STBImage.*;

public class ExampleRender3 {

    private static float[] vertices = {
            // 2D pos,           RGB                 UVx, UVy
            -0.5f, -0.5f, 1.f, 1.f, 1.f, 0.0f, 0.0f, // Bottom left.
            0.5f, 0.5f, 1.f, 1.f, 1.f, 1.0f, 1.0f, // Top right.
            -0.5f, 0.5f, 1.f, 1.f, 1.f, 0.0f, 1.0f, // Top left.
            0.5f, -0.5f, 1.f, 1.f, 1.f, 1.0f, 0.0f // Bottom right.
    };

    private static int[] elementArray = {
            2, 3, 1,
            0, 3, 2,
    };

    private static int texID;
    private static int tex2ID;
    private static int vaoID;
    private static int vboID;
    private static Shader shaderProgram;

   public static void init() {
       shaderProgram = Resources.lazyShader("res/shaders/example3_v.glsl",
                                                        "res/shaders/example3_f.glsl");

       texID = loadImage("res/images/mario.png");
       tex2ID = loadImage("res/images/sus.png");

       vaoID = glGenVertexArrays();
       glBindVertexArray(vaoID);

       vboID = glGenBuffers();
       glBindBuffer(GL_ARRAY_BUFFER, vboID);
       glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

       int eboID = glGenBuffers();
       glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
       glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementArray, GL_DYNAMIC_DRAW);

       glVertexAttribPointer(0, 2, GL_FLOAT, false, 7 * Float.BYTES, 0);
       glEnableVertexAttribArray(0);

       glVertexAttribPointer(1, 3, GL_FLOAT, false, 7 * Float.BYTES, 2 * Float.BYTES);
       glEnableVertexAttribArray(1);

       glVertexAttribPointer(2, 2, GL_FLOAT, false, 7 * Float.BYTES, 5 * Float.BYTES);
       glEnableVertexAttribArray(2);

       glBindVertexArray(0);
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

   public static float toRadians(float degrees) {
       return (float) (degrees * (Math.PI / 180.f));
   }

   public static float mixCounter = 0.0f;
   public static float mixValue = 1.0f;
   public static boolean doMix = true;
   public static boolean mixLock = false;

   public static float rotationCounter = 0.0f;
   public static float rotationDegrees = 0.0f;
   public static boolean doRotate = false;
   public static boolean rotationLock = false;

   public static void update(float dt) {
       if (doMix) {
            mixCounter += dt;
            float mixInterval = 1.f; // Intervalo de segundos
            mixValue = (float) (Math.sin(mixCounter * Math.PI / mixInterval) + 1) / 2.f;
       }
       if (doRotate) {
            rotationCounter += dt;
            float rotationInterval = 3.f;
            rotationDegrees = (float) -((int)(rotationCounter * 360 / rotationInterval) % 361);
       }

       if (KeyListener.isKeyPressed(GLFW_KEY_T)) {
           mixValue = 1.0f;
           mixCounter = 0.0f;
       }
       if (KeyListener.isKeyPressed(GLFW_KEY_Y)) {
           rotationDegrees = 0.0f;
           rotationCounter = 0.0f;
       }
       if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
           if (!mixLock) {
               mixLock = true;
               doMix = !doMix;
           }
       } else {
           mixLock = false;
       }
       if (KeyListener.isKeyPressed(GLFW_KEY_R)) {
           if (!rotationLock) {
               rotationLock = true;
               doRotate = !doRotate;
           }
       } else {
           rotationLock = false;
       }
   }

   public static void render(float dt) {
       update(dt);

       Matrix4f transform = new Matrix4f();
       transform.scale(1.f);
       transform.rotate(toRadians(rotationDegrees), new Vector3f(0.f, 0.f, 1.f));

       glActiveTexture(GL_TEXTURE0);
       glBindTexture(GL_TEXTURE_2D, texID);

       glActiveTexture(GL_TEXTURE1);
       glBindTexture(GL_TEXTURE_2D, tex2ID);

       glBindBuffer(GL_ARRAY_BUFFER, vboID);
       glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

       shaderProgram.attach();

       shaderProgram.uploadUniformMatrix4f("transform", transform);
       shaderProgram.uploadUniformInt("texture1", 0);
       shaderProgram.uploadUniformInt("texture2", 1);
       shaderProgram.uploadUniformFloat("index", mixValue);

       glBindVertexArray(vaoID);
       glEnableVertexAttribArray(0);
       glEnableVertexAttribArray(1);
       glEnableVertexAttribArray(2);

       glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

       glDisableVertexAttribArray(0);
       glDisableVertexAttribArray(1);
       glDisableVertexAttribArray(2);
       glBindVertexArray(0);

       shaderProgram.detach();
   }

}
