package com.orlang.engine.core.graphics;

import com.orlang.engine.Resources;
import org.joml.Matrix4f;

import java.util.List;

public class Surface {

    private FrameBuffer frameBuffer;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Shader boundShader;
    private int width, height;

    public Surface(int widthPx, int heightPx) {
        this.width = widthPx;
        this.height = heightPx;



        boundShader = Resources.lazyShader("", "");
    }



}
