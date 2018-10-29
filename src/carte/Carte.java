package carte;

import carte.Case;


public class Carte{

    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    
    private Case[][] cases;

    public Carte(){
        this.setTailleCases(0);
        this.setNbLignes(0);
        this.setNbColonnes(0);
    }

    public Carte(int nbLignes, int nbColonnes, int tailleCases){
        this.setTailleCases(tailleCases);
        this.setNbLignes(nbLignes);
        this.setNbColonnes(nbColonnes);
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
    	boolean existe = false;
    	
    	switch(dir)
    	{
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
    	//System.out.println(existe);
        return existe;
        
    }

    //retourne le voisin de la  case (pas copie)
    public Case getVoisin(Case src, Direction dir){	
        
    	Case voisin = null;
    	
    	if (this.voisinExiste(src, dir))
    	{

        	switch(dir)
        	{
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
