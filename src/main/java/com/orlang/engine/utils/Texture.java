package com.orlang.engine.utils;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private String imagePath;
    private int texID;

    private int width;
    private int height;

    public Texture() {
        this.imagePath = null;
        this.width = -1;
        this.height = -1;
    }

    public void load(String filepath) {
        this.imagePath = filepath;

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        // Repeat image on both directions.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching, pixelating.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(false);

        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB,
                        width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,
                        width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false: "Error: Unknown number of channels '" + filepath + "'";
            }
        } else {
            assert false : "Error: Could not load image " + filepath;
        }

        stbi_image_free(image);
    }

    public void bind() { glBindTexture(GL_TEXTURE_2D, texID); }
    public void unbind() { glBindTexture(GL_TEXTURE_2D, 0); }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public String getImagePath() { return this.imagePath; }
    public int getTexID() { return this.texID; }

}
