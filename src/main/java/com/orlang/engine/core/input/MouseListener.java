package com.orlang.engine.core.input;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {

    private static boolean mouseButtonsPressed[] = new boolean[9];
    private static double previousX = -1;
    private static double previousY = -1;
    private static double currentX = -1;
    private static double currentY = -1;
    private static double scrollX = 0;
    private static double scrollY = 0;
    private static boolean isMiddleDragging = false;
    private static boolean isLeftDragging = false;
    private static boolean isRightDragging = false;

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < mouseButtonsPressed.length) {
                mouseButtonsPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < mouseButtonsPressed.length) {
                mouseButtonsPressed[button] = false;
                if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    isLeftDragging = false;
                }
                if (button == GLFW_MOUSE_BUTTON_RIGHT) {
                    isRightDragging = false;
                }
            }
        }
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        currentX = xpos;
        currentY = ypos;

        isLeftDragging = mouseButtonsPressed[GLFW_MOUSE_BUTTON_LEFT];
        isRightDragging = mouseButtonsPressed[GLFW_MOUSE_BUTTON_RIGHT];
        isMiddleDragging = mouseButtonsPressed[GLFW_MOUSE_BUTTON_MIDDLE];
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    public static void endFrame() {
        previousX = currentX;
        previousY = currentY;

        scrollX = 0;
        scrollY = 0;
    }

    public static boolean isMouseButtonPressed(int button) {
        if (button < mouseButtonsPressed.length) {
            return mouseButtonsPressed[button];
        } else {
            return false;
        }
    }

    public static double getCurrentX() { return currentX; }
    public static double getCurrentY() { return currentY; }
    public static double getScrollX() { return scrollX; }
    public static double getScrollY() { return scrollY; }
    public static boolean isLeftDragging() { return isLeftDragging; }
    public static boolean isRightDragging() { return isRightDragging; }
    public static boolean isMiddleDragging() { return isMiddleDragging; }

}
