package com.orlang.examples;

import com.orlang.engine.core.input.KeyListener;
import com.orlang.engine.scene.Scene;

import static org.lwjgl.glfw.GLFW.*;

public class ExampleScene extends Scene {

    private long window;
    private int selected = 1;

    public ExampleScene(long window) { this.window = window; }

    @Override
    public void init() {
        super.init();
        ExampleRender.init();
        ExampleRender2.init();
        ExampleRender3.init();
        ExampleRender4.init(this.window);
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_O)) {
            selected = 1;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_L)) {
            selected = 2;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_P)) {
            selected = 3;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_M)) {
            selected = 4;
        }

        if (selected == 2) {
            ExampleRender2.updateVertices(dt);
        }
    }

    @Override
    public void render(float dt) {
        switch (selected) {
            case 1 -> {
                ExampleRender.render();
            }
            case 2 -> ExampleRender2.render();
            case 3 -> ExampleRender3.render(dt);
            case 4 -> ExampleRender4.loop(dt);
        }
    }
}
