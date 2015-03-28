package be.monfils.physics.math;

import java.nio.ByteBuffer;

/**
 * Vecteur bi-dimensionnel tout ce qu'il y a de plus classique.
 * L'origine du repère se situe au centre de l'écran. L'écran fait toujours 1/2 de long (le coin inférieur gauche est donc en -1;-1).
 */
public class Vecteur {
    double x, y, z;

    /**
     * Crée un vecteur (0;0)
     */
    public Vecteur() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Crée un vecteur (x;y)
     * @param x
     *        Composante en x
     * @param y
     *        Composante en y
     */
    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
        z = 0;
    }

    /**
     * Crée un vecteur (x;y;z)
     * @param x
     *         Composante en x
     * @param y
     *         Composante en y
     * @param z
     *         Composante en z
     */
    public Vecteur(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Ajoute un vecteur à celui-ci et le retourne
     * @param àAjouter
     *        Le vecteur à ajouter
     * @return
     *        Un vecteur avec les composantes de l'original + celles du vecteur àAjouter.
     */
    public Vecteur add(Vecteur àAjouter) {
        Vecteur vecteur = new Vecteur();
        vecteur.setX(x + àAjouter.getX());
        vecteur.setY(y + àAjouter.getY());
        vecteur.setZ(z + àAjouter.getZ());

        return vecteur;
    }

    /**
     * Multiplie un vecteur à celui-ci et le retourne
     * @param facteur
     *        Le vecteur à multiplier
     * @return
     *        Un vecteur avec les composantes de l'original multipliées celles du vecteur àAjouter.
     */
    public Vecteur mul(double facteur) {
        Vecteur vecteur = new Vecteur();
        vecteur.setX(x * facteur);
        vecteur.setY(y * facteur);
        vecteur.setZ(z * facteur);

        return vecteur;
    }

    /**
     * @return La norme du vecteur
     */
    public double getNorme() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Calcule le produit scalaire entre ce vecteur et un vecteur donné
     * @param vecteur
     *       Le deuxième vecteur du produit scalaire
     * @return
     *       Le produit scalaire entre ce vecteur et le vecteur donné
     */
    public double scalaire(Vecteur vecteur) {
        return x * vecteur.getX() + y * vecteur.getY() + z * vecteur.getZ();
    }

    /**
     * Calcule l'angle entre ce vecteur et un vecteur donné
     * @param vecteur
     *        Le deuxième vecteur
     * @return
     *        L'angle entre ce vecteur et le vecteur donné
     */
    public double angle(Vecteur vecteur) {
        double scalaire = scalaire(vecteur);
        return Math.acos(this.scalaire(vecteur)/(this.getNorme() * vecteur.getNorme()));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String toString() {
        return "(" + x + ";" + y + ";" + z + ")";
    }
}
