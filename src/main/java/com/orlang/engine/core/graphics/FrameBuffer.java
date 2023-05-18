package com.orlang.engine.core.graphics;

import com.orlang.engine.utils.Texture;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

    private int fboID;
    private Texture textureID;
    private boolean created;
    private boolean destroyed;

    public FrameBuffer(int width, int height) {
        this.created = false;
        this.destroyed = false;

        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);



        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void create() {
        if (!this.created) {
            this.created = true;

            fboID = glGenFramebuffers();

        }
    }

    public void destroy() {
        if (this.created && !this.destroyed) {

            this.destroyed = true;
        }
    }

}
