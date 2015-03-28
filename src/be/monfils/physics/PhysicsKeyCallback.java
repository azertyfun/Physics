package be.monfils.physics;

import be.monfils.physics.math.Vecteur;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Gère les événements clavier de la fenêtre.
 */
public class PhysicsKeyCallback extends GLFWKeyCallback {
    @Override
    public void invoke(long p_window, int key, int scancode, int action, int mods) {
        switch(key) {
            case GLFW_KEY_ESCAPE:
                if (action == GLFW_PRESS)
                    glfwSetWindowShouldClose(p_window, GL_TRUE);
                break;
            case GLFW_KEY_ENTER:
                if(action == GLFW_PRESS)
                    Physics.drawVectors = !Physics.drawVectors;
                break;
        }
    }
}
