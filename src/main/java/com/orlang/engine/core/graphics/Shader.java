package com.orlang.engine.core.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class Shader {

    private int shaderID;
    private boolean beingUsed;

    private String vertexSource;
    private String fragmentSource;
    private String vertexPath, fragmentPath;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertexPath = vertexPath;
        this.fragmentPath = fragmentPath;
        this.beingUsed = false;

        try {
            this.vertexSource = new String(Files.readAllBytes(Paths.get(vertexPath)));
        } catch (IOException ve) {
            throw new IllegalStateException("ERROR: Could not load vertex shader from\n" + vertexPath);
        }

        try {
            this.fragmentSource = new String(Files.readAllBytes(Paths.get(fragmentPath)));
        } catch (IOException fe) {
            throw new IllegalStateException("ERROR: Could not load fragment shader from\n" + fragmentPath);
        }
    }

    public void compile() {
        int vertexID, fragmentID;

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + this.vertexPath + "'\n\t Vertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + this.fragmentPath + "'\n\t Fragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        shaderID = glCreateProgram();
        glAttachShader(shaderID, vertexID);
        glAttachShader(shaderID, fragmentID);
        glLinkProgram(shaderID);

        // Check for error
        success = glGetProgrami(shaderID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + this.vertexPath + " - " + this.fragmentPath + "'\n\t Linking of shaders.");
            System.out.println(glGetProgramInfoLog(shaderID, len));
            assert false : "";
        }
    }

    public void attach() {
        if (!beingUsed) {
            // USE PROGRAM
            glUseProgram(shaderID);
            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadUniformMatrix4f(String varName, Matrix4f matrix4f) {
        int varLocation = glGetUniformLocation(shaderID, varName);
        attach();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        matrix4f.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadUniformInt(String varName, int value) {
        int varLocation = glGetUniformLocation(shaderID, varName);
        attach();
        glUniform1i(varLocation, value);
    }

    public void uploadUniformFloat(String varName, float value) {
        int varLocation = glGetUniformLocation(shaderID, varName);
        attach();
        glUniform1f(varLocation, value);
    }

    public void uploadVector3Float(String varName, Vector3f vector) {
        int varLocation = glGetUniformLocation(shaderID, varName);
        attach();
        glUniform3f(varLocation, vector.x, vector.y, vector.z);
    }

}
