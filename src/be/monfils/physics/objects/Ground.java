package be.monfils.physics.objects;

import be.monfils.physics.math.Vecteur;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by nathan on 27/03/15.
 */
public class Ground extends PhysicsObject {

    /**
     * Coefficient de frottement entre le sol et un objet. Totalement arbitraire (on assume que tous les objets sont faits de la même matière).
     */
    protected double coeffFrottement;

    /**
     * Crée un sol à l'endroit donné. Le sol sera de longueur infinie (10000) à droite et en bas (il faut donc positionner le sol en -1 pour ne pas créer de "trou").
     * @param position
     *        La position du sol (en -1 si on ne veut pas créer de "trou").
     * @param coeffFortement
     *        Le coefficient de frottement entre le sol et un objet. Totalement arbitraire (on assume que tous les objets sotn faits de la même matière).
     */
    public Ground(Vecteur position, double coeffFortement) {
        super(1, 0, position);
        this.coeffFrottement = coeffFortement;
    }

    @Override
    public void render() {;
        System.out.println("\tPosition du sol : " + position);
        glPushMatrix();
        glTranslated(position.getX(), position.getY(), 0);

        glColor3f(.5f, .5f, .5f);
        glBegin(GL_QUADS);
            glVertex2f(0, 0);
            glVertex2f(10000, 0);
            glVertex2f(10000, -10000);
            glVertex2f(0, -10000);
        glEnd();

        glPopMatrix();
    }

    public void logic() {
    }

    public double getCoeffFrottement() {
        return coeffFrottement;
    }

    public void setCoeffFrottement(double coeffFrottement) {
        this.coeffFrottement = coeffFrottement;
    }
}
