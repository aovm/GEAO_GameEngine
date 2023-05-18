package com.orlang.engine;

import com.orlang.engine.core.graphics.Shader;
import com.orlang.engine.elements.Spritesheet;
import com.orlang.engine.utils.Texture;

import java.io.File;
import java.util.HashMap;

public class Resources {

    private static HashMap<String, Texture> TEXTURES;
    private static HashMap<String, Shader> SHADERS;
    private static HashMap<String, Spritesheet> SPRITESHEETS;
    private static boolean initialized = false;

    public static void init() {
        TEXTURES = new HashMap<>();
        SHADERS = new HashMap<>();
        SPRITESHEETS = new HashMap<>();
        initialized = true;
    }

    public static Shader lazyShader(String vertexPath, String fragmentPath) {
        if (!initialized) { init(); }

        File vertexFile = new File(vertexPath);
        File fragmentFile = new File(fragmentPath);
        String key = vertexFile.getAbsolutePath() + "$" + fragmentFile.getAbsolutePath();
        if (SHADERS.containsKey(key)) {
            return SHADERS.get(key);
        } else {
            Shader program = new Shader(vertexPath, fragmentPath);
            program.compile();
            SHADERS.put(key, program);
            return program;
        }
    }

    public static Texture lazyTexture(String texturePath) {
        if (!initialized) { init(); }

        File file = new File(texturePath);
        if (TEXTURES.containsKey(file.getAbsolutePath())) {
            return TEXTURES.get(file.getAbsolutePath());
        } else {
            Texture text = new Texture();
            text.load(texturePath);
            TEXTURES.put(file.getAbsolutePath(), text);
            return text;
        }
    }

}
