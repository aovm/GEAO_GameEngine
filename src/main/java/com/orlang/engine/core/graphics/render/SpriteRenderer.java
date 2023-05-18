package com.orlang.engine.core.graphics.render;

import com.orlang.engine.components.SpriteComponent;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.utils.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/*
* SpriteRenderer? LightRenderer? SkyBoxRenderer? etc...
* */
public class SpriteRenderer {

    private Matrix4f model;
    private Shader shader;
    private int quadVao;

    public SpriteRenderer(Shader shader) {
        this.shader = shader;
        this.model = new Matrix4f();
        init();
    }

    public void drawSprite(Texture texture, Vector2f position, Vector2f size, float rotation, Vector3f color) {
        shader.attach();

        model.identity();
        model.translate(position.x, position.y, 0.0f);
        // Rotation.
        model.translate(0.5f * size.x, 0.5f * size.y, 0.0f);
        model.rotate((float) (rotation * (Math.PI / 180.0f)), 0.0f, 0.0f, 1.0f);
        model.translate(-0.5f * size.x, -0.5f * size.y, 0.0f);

        model.scale(size.x, size.y, 1.0f);

        shader.uploadUniformMatrix4f("model", model);
        shader.uploadVector3Float("spriteColor", color);

        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        glBindVertexArray(quadVao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void drawSprite(SpriteComponent component, float rotation) {
        Vector4f color = component.getColor();
        drawSprite(component.getSpriteImage().getTexture(), component.getPosition(),
                component.getScale(), rotation, new Vector3f(color.x, color.y, color.z));
    }

    private void init() {
        int vbo;
        float vertices[] = {
            // pos          // tex
            0.0f, 1.0f,     0.0f, 1.0f, // Left-top
            1.0f, 0.0f,     1.0f, 0.0f, // Right-bottom
            0.0f, 0.0f,     0.0f, 0.0f, // Left-bottom
            1.0f, 1.0f,     1.0f, 1.0f, // Right-top
        };

        quadVao = glGenVertexArrays();
        glBindVertexArray(quadVao);
        vbo = glGenBuffers();

        int[] elements = {
            0, 2, 1,
            0, 1, 3
        };

        int ebo;
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_STATIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        glBindVertexArray(quadVao);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
