package com.orlang.engine.scene;


public abstract class Scene {

    protected Scene() { }

    public void init() {

    }

    public abstract void update(float dt);
    public abstract void render(float dt);

}
