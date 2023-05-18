package com.orlang.engine.core.graphics.render;

import com.orlang.engine.Resources;
import com.orlang.engine.components.SpriteComponent;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.utils.Texture;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteBatchRenderer {

    // vec2 pos, vec2 texCoords per vertex, float texID.
    // 4 vertices per quad.
    // 4 * 4 * maxCapacity


    private float[] vertices;

    private final int MAX_CAPACITY;
    private final int VERTEX_SIZE = 5;

    private int vaoID;
    private int vboID;


    private int[] texID = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

    private Shader spriteShader;
    private List<Texture> textures;
    private List<SpriteComponent> sprites;
    private boolean reloadVertices;

    public SpriteBatchRenderer(final int maxCapacity) {
        this.MAX_CAPACITY = maxCapacity;
        this.vertices = new float[4 * VERTEX_SIZE * MAX_CAPACITY];
        this.spriteShader = Resources.lazyShader("res/game/shaders/sprite_f.glsl", "res/game/shaders/sprite_v.glsl");

        this.textures = new ArrayList<>(16 + 1);
        this.sprites = new ArrayList<>(MAX_CAPACITY + 1);
        this.reloadVertices = true;
    }

    public void init() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT,false,
                VERTEX_SIZE * Float.BYTES,0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false,
                VERTEX_SIZE * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 1, GL_FLOAT, false,
                VERTEX_SIZE * Float.BYTES, 4 * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    public void addSprite(SpriteComponent sprite) {
        if (sprites.contains(sprite)) return;
        if (sprites.size() >= MAX_CAPACITY) return;

        if (textures.size() >= 15 && !containsTexture(sprite.getSpriteImage().getTexture())) return;

        if (!containsTexture(sprite.getSpriteImage().getTexture())) {
            textures.add(sprite.getSpriteImage().getTexture());
        }
        sprites.add(sprite);

        // TODO: recalculate vertices?? or set recalculation flag?
    }

    public void removeSprite(SpriteComponent sprite) {
        sprites.remove(sprite);
        if (!isTextureUsed(sprite.getSpriteImage().getTexture())) {
            textures.remove(sprite.getSpriteImage().getTexture());
        }

        // TODO: recalculate vertices?? or set recalculation flag?
    }

    public void render() {

        if (reloadVertices) {

        }

        glBindVertexArray(vaoID);

    }

    public boolean hasTextureRoom() { return textures.size() < 16; }

    public int sizeSprites() { return sprites.size(); }
    public int sizeTextures() { return textures.size(); }

    private int[] generateIndices() {
        int[] indices = new int[MAX_CAPACITY * 6];
        int offset = 0;
        for (int i = 0; i < MAX_CAPACITY; i++) {
            indices[offset] = i * 4;
            indices[offset + 1] = i * 4 + 2;
            indices[offset + 2] = i * 4 + 1;
            indices[offset + 3] = i * 4;
            indices[offset + 4] = i * 4 + 1;
            indices[offset + 5] = i * 4 + 3;
            offset += 6;
        }
        return indices;
    }

    private boolean containsTexture(Texture texture) {
        return textures.contains(texture);
    }

    private boolean isTextureUsed(Texture texture) {
        boolean usedFlag = false;
        for (int i = 0; i < sprites.size(); i++) {
            SpriteComponent spr = sprites.get(i);
            if (texture.equals( spr.getSpriteImage().getTexture() )) {
                usedFlag = true;
                break;
            }
        }
        return usedFlag;
    }

}
