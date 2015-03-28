package be.monfils.physics;

import be.monfils.physics.math.Vecteur;
import be.monfils.physics.objects.Ground;
import be.monfils.physics.objects.PhysicsObject;
import be.monfils.physics.objects.Point;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLContext;

import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.system.MemoryUtil.*;

/**
 * Classe principale, contenant la main loop et tout le bordel.
 */
public class Physics {

    private static GLFWErrorCallback errorCallback;
    private static PhysicsKeyCallback keyCallback = new PhysicsKeyCallback();

    public static LinkedList<PhysicsObject> objects;

    private static long p_window;

    public static final float SCALE = 0.1f;

    public static boolean drawVectors = false;

    /*
     * Objets nécessaires
     */
    public static Ground ground;
    public static Point point;

    public static void main(String[] args) {
        errorCallback = Callbacks.errorCallbackPrint(System.err);
        glfwSetErrorCallback(errorCallback);
        System.out.println("GLFW Error callback set to " + errorCallback.getPointer());

        System.out.print("GLFW Init... ");
        if(glfwInit() != GL_TRUE) {
            throw new IllegalStateException("GLFW Cannot into instantation"); // /r/polandball is leaking
        }
        System.out.println("Done.");

        System.out.print("Creating window... ");
        p_window = glfwCreateWindow(800, 600, "Physics", NULL, NULL);
        if(p_window == NULL) {
            glfwTerminate();
            throw new RuntimeException("GLFW Window cannot into screen");
        }
        System.out.println("Done.");

        System.out.print("Setting GL Ccontext... ");
        glfwMakeContextCurrent(p_window);
        GLContext.createFromCurrent();
        System.out.println("Done.");

        glfwSwapInterval(1);
        glfwSetKeyCallback(p_window, keyCallback);

        /*
         * Creating objects
         */
        objects = new LinkedList<PhysicsObject>();

        point = new Point(50, 5, 5, new Vecteur(0, 1), new Vecteur(0, -9.81 * SCALE));
        ground = new Ground(new Vecteur(-1, -0.5), 0.5);
        objects.add(ground);
        objects.add(point);

        mainLoop();

        keyCallback.release();

        glfwDestroyWindow(p_window);
        glfwTerminate();
        errorCallback.release();
    }

    /**
     * Main loop
     */
    public static void mainLoop() {
        double delta = 0;
        while(glfwWindowShouldClose(p_window) != GL_TRUE) {
            //System.out.println("FPS: " + ((int) (1 / delta)));

            double startTime = glfwGetTime();

            input();
            logic(delta);
            render(delta);

            glfwSwapBuffers(p_window);
            glfwPollEvents();

            double endTime = glfwGetTime();
            delta = endTime - startTime;
        }
    }

    /**
     * Gère les entrées clavier/souris/whatever
     */
    public static void input() {
        Vecteur impulsion = new Vecteur();
        if(glfwGetKey(p_window, GLFW_KEY_UP) == GLFW_PRESS && point.onGround())
            impulsion = impulsion.add(new Vecteur(0, 15000 * SCALE));
        //if(glfwGetKey(p_window, GLFW_KEY_DOWN) == GLFW_PRESS)
        //    impulsion = impulsion.add(new Vecteur(0, -800 * SCALE));
        if(glfwGetKey(p_window, GLFW_KEY_LEFT) == GLFW_PRESS)
            if(point.onGround())
                impulsion = impulsion.add(new Vecteur(-800 * SCALE, 0));
            else
                impulsion = impulsion.add(new Vecteur(-200 * SCALE, 0));
        if(glfwGetKey(p_window, GLFW_KEY_RIGHT) == GLFW_PRESS)
            if(point.onGround())
                impulsion = impulsion.add(new Vecteur(800 * SCALE, 0));
            else
                impulsion = impulsion.add(new Vecteur(200 * SCALE, 0));
        point.impulsion(impulsion);
    }

    /**
     * Gère la logique (déplacements à chaque frame etc)
     * @param delta
     *        Le temps écoulé depuis la dernière frame.
     */
    public static void logic(double delta) {
        for(PhysicsObject object : objects) {
            object.logic(delta);
        }
    }

    /**
     * Fait le rendu de tous les objets
     * @param delta
     *        Le temps écoulé depuis la dernière frame.
     */
    public static void render(double delta) {
        //Rendering background
        glColor3f(1, 1, 1);
        glBegin(GL_QUADS);
            glVertex2f(-1, -1);
            glVertex2f(-1, 1);
            glVertex2f(1, 1);
            glVertex2f(1, -1);
        glEnd();

        for(PhysicsObject object : objects) {
            object.render();
        }
    }
}
