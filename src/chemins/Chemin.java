package chemins;

import java.util.Stack;

import carte.Direction;

/**
 * @author 
 * 
 * Chemin représenté par une suite de directions, d'un temps de parcours et d'une vitesse moyenne pour le réaliser.
 * A noter qu'un chemin a été calculé pour un robot en particulier.
 *
 */
public class Chemin { 
	
	/**
	 * Pile de directions à suivre à partir de la case initiale pour atteindre la case de destination
	 */
	protected  Stack<Direction> directions;
	
	/**
	 * Temps pour parcourir tout le chemin
	 */
	protected double tempsParcours;
	
	/**
	 * Vitesse moyenne du robot
	 */
	protected double vitesseMoyenne;
	
	
	/**
	 * Constructeur de la classe
	 */
	public Chemin() {
		this.directions = new Stack<Direction>();
		this.tempsParcours = 0.0;
		this.vitesseMoyenne = 0.0;
	}
	
	/**
	 * Ajoute une direction à effectuer avant les autres.
	 * 
	 * @param dir Direction à ajouter
	 */
	public void pushDirection(Direction dir) {
		this.directions.push(dir);
	}
	
	/** 
	 * Renvoie la vitesse moyenne du robot pour le chemin
	 * @return vitesse moyenne
	 */
	public double getVitesseMoyenne() {
		return this.vitesseMoyenne;
	}
	
	/** 
	 * Renvoie le temps de parcours
	 * @return Le temps de parcours
	 */
	public double getTempsParcours() {
		return this.tempsParcours;
	}
	
	/**
	 * Modifie le temps de parcours du chemin
	 * @param t Temps de parcours
	 */
	public void setTempsParcours(double t) {
		this.tempsParcours = t;
	}
	
	/**
	 * Modifie la vitesse moyenne du chemin
	 * @param v vitesse moyenne
	 */
	public void setVitesseMoyenne(double v) {
		this.vitesseMoyenne = v;
	}
	
	/**
	 * Renvoie la pile de directions à suivre pour atteindre la case voulue
	 * @return Pile de directions
	 */
	public Stack<Direction> getDirection() {
			return this.directions;
	}
}
