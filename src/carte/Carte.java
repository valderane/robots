package carte;

import carte.Case;


public class Carte{

    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    
    private Case[][] cases;

    public Carte(){
        this.tailleCases = 0;
        this.nbLignes = 0;
        this.nbColonnes = 0;
    }

    public Carte(int nbLignes, int nbColonnes, int tailleCases){
        this.tailleCases = tailleCases;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.cases = new Case[this.nbLignes][this.nbColonnes];
    }

    public int getNbLignes(){
        return this.nbLignes;
    }

    public int getNbColonnes(){
        return this.nbColonnes;
    }

    public int getTailleCases(){
        return this.tailleCases;
    }

    public Case getCase(int lig, int col){
    	//TODO verifications
        return this.cases[lig][col];
    }
    
    public void setCase(int lig, int col, NatureTerrain nature) {
    	//TODO verif
    	Case c = new Case(lig, col, nature);
    	this.cases[lig][col] = c;
    }
    
    public void setCase(Case c) {
    	//TODO verifications
    	this.cases[c.getLigne()][c.getColonne()] = c;
    }

    public void setNbLignes(int nbLignes){
        this.nbLignes = nbLignes;
        this.cases = new Case[this.nbLignes][this.nbColonnes];
    }

    public void setNbColonnes(int nbColonnes){
        this.nbColonnes = nbColonnes;
        this.cases = new Case[this.nbLignes][this.nbColonnes];
    }

    public void setTailleCases(int tailleCases){
        this.tailleCases = tailleCases;
    }

    public boolean voisinExiste(Case src, Direction dir){
        return false;
        //TODO
    }

    public Case getVoisin(Case src, Direction dir){
        return null;
        //TODO
    }


}
