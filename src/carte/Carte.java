package carte;


public class Carte{

    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    //TODO

    public Carte(int nbLignes, int nbColonnes){
        //TODO
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
        //TODO
    }

    public boolean voisinExiste(Case src, Direction dir){
        //TODO
    }

    public Case getVoisin(Case src, Direction dir){
        //TODO
    }


}
