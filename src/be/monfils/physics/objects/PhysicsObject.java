package be.monfils.physics.objects;

import be.monfils.physics.Physics;
import be.monfils.physics.math.Vecteur;
import org.lwjgl.Sys;

import java.util.LinkedList;

/**
 * Object soumis au moteur physique (accélération, vitesse, etc).
 */
public abstract class PhysicsObject implements Renderable {
    protected Vecteur position, accélération, vitesse;
    protected double masse;
    protected Vecteur appliedForce;

    /**
     * Coefficient de résistance du au frottement de l'air, arbitraire (dépend de variable trop compliquées ou dénuées de sens dans nos cas "parfaits").
     */
    protected double coeffRésistanceAir;

    /**
     * Crée un objet de position donnée
     * @param position
     *          La position initiale de l'objet
     */
    public PhysicsObject(Vecteur position) {
        this.position = position;

        accélération = new Vecteur(0, 0);
        vitesse = new Vecteur(0, 0);
        masse = 0;
        appliedForce = new Vecteur();
        coeffRésistanceAir = 0;
    }

    /**
     * Crée un objet de masse et de position données
     * @param position
     *        La position initiale de l'objet
     * @param masse
     *        La masse initiale de l'objet
     */
    public PhysicsObject(double masse, double coeffRésistanceAir, Vecteur position) {
        this.position = position;

        accélération = new Vecteur(0, 0);
        vitesse = new Vecteur(0, 0);
        this.masse = masse;
        appliedForce = new Vecteur();
        this.coeffRésistanceAir = coeffRésistanceAir;
    }

    /**
     * Crée un objet de masse, de position et d'accélération données
     * @param position
     *        La position initiale de l'objet
     * @param masse
     *        La masse initiale de l'objet
     * @param accélération
     *        L'accélération initiale de l'objet ((0;-9.81) pour un objet soumis à la gravité par exemple).
     */
    public PhysicsObject(double masse, double coeffRésistanceAir, Vecteur position, Vecteur accélération) {
        this.position = position;
        this.accélération = accélération;

        vitesse = new Vecteur();

        this.masse = masse;
        appliedForce = new Vecteur();
        this.coeffRésistanceAir = coeffRésistanceAir;
    }

    public void accélérer(Vecteur àAjouter) {
        accélération = accélération.add(àAjouter);
    }

    public void impulsion(Vecteur àAjouter) {
        appliedForce = àAjouter;
    }

    protected void updatePos(double delta, LinkedList<Vecteur> forces) {
        /*
         * v = Δd/Δt
         *     <=> Δd = v*Δt
         * v = v0 + a*Δt
         *     <=> a = (v - v0) / Δt
         * a = F/m
         */

        Vecteur forceRésultante = new Vecteur();
        for(Vecteur force : forces) {
            forceRésultante = forceRésultante.add(force);
        }

        accélération = forceRésultante.mul(1/masse); //a = F/m

        vitesse = vitesse.add(accélération.mul(delta));
        position = position.add(vitesse.mul(delta));
    }

    public void logic(double delta) {
        updatePos(delta, new LinkedList<Vecteur>());
    }

    public Vecteur getPosition() {
        return position;
    }
}
