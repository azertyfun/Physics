package be.monfils.physics.objects;

import be.monfils.physics.Physics;
import be.monfils.physics.math.Vecteur;
import org.lwjgl.Sys;

import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Un point : l'objet physique le plus simple :D
 */
public final class Point extends PhysicsObject {

    private int size;

    /**
     * Crée un point de taille et position données
     * @param size
     *        Taille de l'objet (en pixels)
     * @param position
     *        Position de l'objet
     *
     */
    public Point(int size, Vecteur position) {
        super(position);
        this.size = size;
    }

    /**
     * Crée un point de taille, de masse et position données
     * @param size
     *        Taille de l'objet (en pixels)
     * @param position
     *        Position de l'objet
     * @param masse
     *        Masse de l'objet (en kg)
     */
    public Point(double masse, double coeffRésistanceAir, int size, Vecteur position) {
        super(masse, coeffRésistanceAir, position);
        this.size = size;
    }

    /**
     * Crée un point de taille, de masse, de position et d'accélération données
     * @param size
     *        Taille de l'objet (en pixels)
     * @param position
     *        Position de l'objet
     * @param masse
     *        Masse de l'objet (en kg)
     * @param accélération
     *        L'accélération initiale de l'objet ((0;-9.81) pour un objet soumis à la gravité par exemple).
     */
    public Point(double masse, double coeffRésistanceAir, int size, Vecteur position, Vecteur accélération) {
        super(masse, coeffRésistanceAir, position, accélération);
        this.size = size;
    }

    @Override
    public void render() {
        glColor3f(0, 0, 0);

        glPushMatrix();
        glTranslated(position.getX(), position.getY(), 0);
        glBegin(GL_QUADS);
            glVertex2d(0, 0);
            glVertex2d(0, ((double) size) / 300d);
            glVertex2d(((double) size) / 400d, ((double) size) / 300d);
            glVertex2d(((double) size) / 400d, 0);
        glEnd();
        glPopMatrix();

        if(Physics.drawVectors) {
            glPushMatrix();

            glTranslated(position.getX(), position.getY(), 0);
            glColor3f(0, 0, 1);

            glBegin(GL_LINES);
                glVertex2d(0, 0);
                glVertex2d(vitesse.getX() * Physics.SCALE, vitesse.getY() * Physics.SCALE);
            glEnd();

            glColor3f(1, 0, 0);
            glBegin(GL_LINES);
                glVertex2d(0, 0);
                glVertex2d(accélération.getX() * Physics.SCALE, accélération.getY() * Physics.SCALE);
            glEnd();

            glPopMatrix();
        }
    }

    public void logic(double delta) {
        LinkedList<Vecteur> forces = new LinkedList<Vecteur>();
        forces.add((new Vecteur(0, -9.81 * Physics.SCALE)).mul(masse)); //Force de pesanteur

        if(onGround()) {
            position.setY(Physics.ground.getPosition().getY()); //On évite le clipping
            if(vitesse.getY() < 0)
                vitesse.setY(0);

            System.out.println("ON GROUND!");

            forces.add((new Vecteur(0, 9.81 * Physics.SCALE)).mul(masse)); //Force normale
            if(vitesse.getX() != 0) {
                forces.add(new Vecteur(Physics.ground.getCoeffFrottement() * masse * ((vitesse.getX() > 0) ? -1 : 1), 0));
            }
        } else if(vitesse.getNorme() > 0) {
            Vecteur résistance = new Vecteur();
            résistance.setX(vitesse.getX() * vitesse.getX() * ((vitesse.getX() > 0) ? -1 : 1));
            résistance.setY(vitesse.getY() * vitesse.getY() * ((vitesse.getY() > 0) ? -1 : 1));
            résistance = résistance.mul(coeffRésistanceAir);
            forces.add(résistance);
            System.out.println("Résistance : " + résistance + " (coeff " + coeffRésistanceAir + ")");
        }

        forces.add(appliedForce);

        updatePos(delta, forces);

        if(onGround()) {
            position.setY(Physics.ground.getPosition().getY());
        }

        if(position.getX() > 1) {
            position.setX(1);
            vitesse.setX(0);
        }
        if(position.getX() < -1) {
            position.setX(-1);
            vitesse.setX(0);
        }
        if(position.getY() > 1) {
            position.setY(1);
            vitesse.setY(0);
        }
        if(position.getY() < -1) {
            position.setY(-1);
            vitesse.setY(0);
        }

        System.out.println("Position du point : (" + position.getX() + ";" + position.getY() + ")");
    }

    /**
     * Vérifie si le point est sur le sol
     * @return
     *        true si le point est sur le sol, false sinon.
     */
    public boolean onGround() {
        if(position.getY() <= Physics.ground.getPosition().getY()) {
            return true;
        }

        return false;
    }
}
