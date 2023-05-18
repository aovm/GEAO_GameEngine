package com.orlang.engine.core;

import com.orlang.engine.Resources;
import com.orlang.engine.core.input.MouseListener;
import com.orlang.engine.scene.Scene;
import com.orlang.examples.ExampleScene;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;

public final class Application {

    private static Application app = new Application();
    private Application() { }
    public static Application getInstance() {
        return app;
    }

    //
    // TimedEventHandler
    private GameLogic logic;
    private NuklearLogic guiLogic;
    private Scene currentScene;
    private Window window;
    private String windowTitle = "Orlang Tetris V1.0 ALPHA";
    private boolean running;

    // Time variables
    private float startTime = 0.0f, endTime = 0.0f;
    private float deltaTime = 0.0f;

    private float fpsCounter = 0.0f;

    static void launch(GameLogic logic, NuklearLogic imgui) {
        app.logic = logic;

        Resources.init();
        app.init();
        logic.init(app.window);
        app.running = true;

        app.gameLoop();
        app.beforeClosing();
    }

    private void init() {
        window = Window.getInstance();
        window.initWindow(windowTitle);
        window.showWindow();

        // Temporal.
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

        currentScene = new ExampleScene(window.getWindowID());
        currentScene.init();

        startTime = (float) glfwGetTime();
    }

    private void gameLoop() {
        while (running && !window.shouldClose()) {
            endTime = (float) glfwGetTime();
            deltaTime = endTime - startTime;
            processInput(deltaTime);

            // Background color rendering.
            glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            currentScene.update(deltaTime);
            currentScene.render(deltaTime);

            fpsCounter += deltaTime;
            if (fpsCounter > 1.5f) {
                window.setWindowTitle("FPS: " + (1.0d / deltaTime));
                fpsCounter = 0.0f;
            }

            // Rendering ends.
            glfwSwapBuffers(window.getWindowID());
            MouseListener.endFrame();

            startTime = endTime;
        }
    }

    private void processInput(float dt) {
        window.pollEvents();
    }

    private void beforeClosing() {
        window.endWindow();
    }

    public static void stopRunning() { app.running = false; }

    public static void changeScene(Scene other) {
        other.init();
        app.currentScene = other;
    }

    public static Window getWindow() { return app.window; }

}
