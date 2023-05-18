package com.orlang.game.tetris;

import com.orlang.engine.Resources;
import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.scene.Scene;

public class TetrisScene extends Scene {

    private Shader spriteShader;

    public TetrisScene() {
        spriteShader = Resources.lazyShader("res/game/shaders/sprite_v.glsl",
                                            "res/game/shaders/sprite_f.glsl");
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(float dt) {

    }
}
