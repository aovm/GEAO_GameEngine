package com.orlang.engine.core;

public class Launcher {

    private Launcher() {}

    public static void start(GameLogic logic) {
        Application.launch(logic, null);
    }

    public static void start(GameLogic logic, NuklearLogic imgui) {
        Application.launch(logic, imgui);
    }

}
