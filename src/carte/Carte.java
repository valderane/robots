package carte;

import carte.Case;

/** La carte contient des cases de plusieurs natures. Elle ne contient pas directement les robots, chaque robot a connaissance de la case où il se situe, mais
 * pas de la carte 
 * @author 
 *
 */
public class Carte {

	/**
	 * Taille de chaque case en mètre.
	 */
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;

	private Case[][] cases;

	/**
	 * COnstructeur par défaut.
	 */
	public Carte() {
		this.setTailleCases(0);
		this.setNbLignes(0);
		this.setNbColonnes(0);
	}

	/**
	 * Constructeur
	 * 
	 * @param nbLignes Nombre de lignes de la carte
	 * @param nbColonnes Nombre de colonnes de la carte
	 * @param tailleCases Taille de chaque case en mètre
	 */
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.setTailleCases(tailleCases);
		this.setNbLignes(nbLignes);
		this.setNbColonnes(nbColonnes);
		this.cases = new Case[this.nbLignes][this.nbColonnes];
	}

	public int getNbLignes() {
		return this.nbLignes;
	}

	public int getNbColonnes() {
		return this.nbColonnes;
	}

	public int getTailleCases() {
		return this.tailleCases;
	}

	public Case getCase(int lig, int col) {
		// TODO verifications
		return this.cases[lig][col];
	}

	public void setCase(int lig, int col, NatureTerrain nature) {
		// TODO verif
		Case c = new Case(lig, col, nature);
		this.cases[lig][col] = c;
	}

	public void setCase(Case c) {
		// TODO verifications
		this.cases[c.getLigne()][c.getColonne()] = c;
	}

	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
		this.cases = new Case[this.nbLignes][this.nbColonnes];
	}

	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
		this.cases = new Case[this.nbLignes][this.nbColonnes];
	}

	public void setTailleCases(int tailleCases) {
		this.tailleCases = tailleCases;
	}

	/**
	 * Indique si il existe un voisin en partant de la case src avec la direction dir.
	 * @param src Case de départ
	 * @param dir Direction à prendre
	 * @return true si un voisin existe, false sinon
	 */
	public boolean voisinExiste(Case src, Direction dir) {
		boolean existe = false;

		switch (dir) {
		case NORD:
			existe = (src.getLigne() - 1) >= 0;
			break;
		case SUD:
			existe = (src.getLigne() + 1) < nbLignes;
			break;
		case EST:
			existe = (src.getColonne() + 1) < nbColonnes;
			break;
		case OUEST:
			existe = (src.getColonne() - 1) >= 0;
			break;
		}
		return existe;

	}

	/**
	 * Retourne le voisin de la case en prenant une certaine direction
	 * @param src Case de départ
	 * @param dir Direction à prendre
	 * @return Le voisin s'il existe, null sinon
	 */
	public Case getVoisin(Case src, Direction dir) {

		Case voisin = null;

		if (this.voisinExiste(src, dir)) {

			switch (dir) {
			case NORD:
				voisin = this.getCase(src.getLigne() - 1, src.getColonne());
				break;
			case SUD:
				voisin = this.getCase(src.getLigne() + 1, src.getColonne());
				break;
			case EST:
				voisin = this.getCase(src.getLigne(), src.getColonne() + 1);
				break;
			case OUEST:
				voisin = this.getCase(src.getLigne(), src.getColonne() - 1);
				break;
			}
		}

		return voisin;
	}

}
