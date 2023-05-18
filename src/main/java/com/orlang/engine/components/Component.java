package com.orlang.engine.components;

public abstract class Component {

    private boolean activated = true;

    public abstract void update(float dt);

    private void activate() { this.activated = true; }
    private void deactivate() { this.activated = false; }

}
