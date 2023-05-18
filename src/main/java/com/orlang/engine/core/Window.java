package com.orlang.engine.core;

/* FUTURE? 3 Windows??? */

// Single threaded ONLY.

import com.orlang.engine.core.input.KeyListener;
import com.orlang.engine.core.input.MouseListener;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

    private Window() { }

    private static Window singleton = new Window();
    static Window getInstance() { return singleton; }

    private long windowID;
    private int windowWidth, windowHeight;
    private String title;

    public final void setWindowTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(windowID, this.title);
    }

    public void initWindow(String title) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            throw new IllegalStateException("ERROR: GLFW could not be initialized.");
        }

        // Window default configuration.
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Generates new window.
        windowWidth = 1920;
        windowHeight = 1080;
        this.title = title;
        windowID = GLFW.glfwCreateWindow(windowWidth, windowHeight, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowID == MemoryUtil.NULL) {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            throw new IllegalStateException("ERROR: Failed to create GLFW window.");
        }
        glfwMakeContextCurrent(windowID);

        // Setting up callbacks.W
        // Input documentation: https://www.glfw.org/docs/3.3/input_guide.html#joystick
        glfwSetCursorPosCallback(windowID, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(windowID, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(windowID, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(windowID, KeyListener::keyCallback);
        glfwSetJoystickCallback((joystickID, event) -> { });
        glfwSetFramebufferSizeCallback(windowID, (w, width, height) -> {
            glViewport(0, 0, width, height);
        });

        // Setup GLFW and OpenGL context (V-SYNC ON)
        glfwSwapInterval(1);

        GL.createCapabilities();
    }

    protected void endWindow() {
        glfwDestroyWindow(windowID);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        GL.destroy();
    }

    public void pollEvents() {
        glfwPollEvents();
    }
    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public final void showWindow() { glfwShowWindow(windowID); }
    public final void hideWindow() { glfwHideWindow(windowID); }

    public final String getTitle() { return this.title; }
    public final int getHeight() { return this.windowWidth; }
    public final int getWidth() { return this.windowHeight; }
    public long getWindowID() { return this.windowID; }

}
