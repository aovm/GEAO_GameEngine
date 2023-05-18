package com.orlang;

import com.orlang.engine.core.*;
import com.orlang.engine.core.Window;
import com.orlang.examples.ExampleScene;
import com.orlang.game.thousand.ThousandScene;

/*
* Hacer un Tetris, Ajedrez y un mario en un solo juego, con un game engine CASERO.
* También un cubo rubik movible usando QUATERNIONS.
*
* no hay forma correcta de programar, solo hay programadores que quieren divertirse programando y aquellos que
* quieren ser vistos bien. VENGA.
*
*
* A implementar ->.
* Render targets.
* Batch rendering. (o instanced rendering)
* Material (shader?)
*
* ANTES DE PROSEGUIR...
* Probar Unity, UE5.
*
* */

public class BundleGame implements GameLogic {

    public static void main(String[] args) {
        Launcher.start(new BundleGame());
    }

    @Override
    public void init(Window window) {
        //BreakoutScene scene = new BreakoutScene();
        ExampleScene scene = new ExampleScene(window.getWindowID());
        scene.init();
        Application.changeScene(scene);
    }

    @Override
    public void dispose() {

    }

}