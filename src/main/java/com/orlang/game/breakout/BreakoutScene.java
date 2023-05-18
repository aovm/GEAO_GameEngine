package com.orlang.game.breakout;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.core.graphics.render.SpriteRenderer;
import com.orlang.engine.scene.Scene;
import com.orlang.engine.utils.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class BreakoutScene extends Scene {

    private Shader shader;
    private Matrix4f projection;
    private SpriteRenderer renderer;
    private Texture texture;

    public BreakoutScene() {
        projection = new Matrix4f().ortho(0.0f, 800, 600.0f, 0.0f, -1.0f, 1.0f);
    }

    @Override
    public void init() {
        super.init();
        shader = Resources.lazyShader("res/shaders/breakout_v.glsl", "res/shaders/breakout_f.glsl");
        shader.uploadUniformInt("texture0", 0);
        shader.uploadUniformMatrix4f("projection", projection);
        renderer = new SpriteRenderer(shader);
        texture = Resources.lazyTexture("res/images/awesomeface.png");
    }

    @Override
    public void update(float dt) {
    }

    private float totalTime = 0.0f;
    private Vector2f position = new Vector2f(200.f, 200.0f);
    private Vector2f size = new Vector2f(100.f, 100.f);
    private float rotation = 0.f;
    private Vector3f color = new Vector3f(0.0f, 1.0f, 0.0f);

    @Override
    public void render(float dt) {
        totalTime += dt;
        rotation = (float) (180.f * (Math.sin(totalTime * Math.PI) + 1));
        renderer.drawSprite(texture, position, size, rotation, color);
    }
}
