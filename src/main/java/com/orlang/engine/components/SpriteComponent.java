package com.orlang.engine.components;

import com.orlang.engine.elements.Sprite;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteComponent extends Component {

    // ROTATION???
    // color for alpha
    private Vector4f color;
    private Vector2f position;
    private Vector2f scale;
    private Sprite spriteReference;
    private boolean isDirty;

    public SpriteComponent() {
        this(null);
    }

    public SpriteComponent(Sprite reference) {
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.position = new Vector2f(0.0f, 0.0f);
        this.scale = new Vector2f(1.0f, 1.0f);
        this.spriteReference = reference;
        this.isDirty = true;
    }

    @Override
    public void update(float dt) { }

    public Sprite getSpriteImage() { return this.spriteReference; }
    public void setSpriteImage(Sprite reference) {
        this.spriteReference = reference;
        this.isDirty = true;
    }

    public void changePosition(float posX, float posY) {
        if (!this.position.equals(posX, posY)) {
            this.position.set(posX, posY);
            this.isDirty = true;
        }
    }
    public void changeScale(float scaleX, float scaleY) {
        if (!this.scale.equals(scaleX, scaleY)) {
            this.scale.set(scaleX, scaleY);
            this.isDirty = true;
        }
    }
    public void changeColor(float r, float g, float b, float a) {
        if (!this.color.equals(r, g, b, a)) {
            this.color.set(r, g, b, a);
            this.isDirty = true;
        }
    }

    public Vector2f getPosition() { return position; }
    public Vector2f getScale() { return scale; }
    public Vector4f getColor() { return color; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof SpriteComponent)) return false;
        return obj == this;
    }

    public boolean isDirty() { return this.isDirty; }
    public void markAsClean() { this.isDirty = false; }

}
