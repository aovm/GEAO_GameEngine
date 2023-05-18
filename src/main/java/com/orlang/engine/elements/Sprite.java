package com.orlang.engine.elements;

import com.orlang.engine.utils.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture;
    private Vector2f[] texCoords;
    private int width, height;

    public Sprite(Texture texture, int width, int height) {
        this(texture, new Vector2f[] {
                new Vector2f(1, 1), // Top right.
                new Vector2f(1, 0), // Bottom right.
                new Vector2f(0, 0), // Bottom left.
                new Vector2f(0, 1)  // Top left.
        }, width, height);
    }

    public Sprite(Texture texture, Vector2f[] texCoords, int width, int height) {
        this.texture = texture;
        this.texCoords = texCoords;
        this.width = width;
        this.height = height;
    }

    // Texture
    public Texture getTexture() { return this.texture; }
    public void setTexture(Texture tex) {
        this.texture = tex;
    }

    // Texture Coordinates.
    public Vector2f[] getTexCoords() { return texCoords; }
    public void changeTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

}
