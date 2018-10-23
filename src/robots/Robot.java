package robots;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

public abstract class Robot{


    protected Case position;

    public Robot(Case c){
    		this.position = c;
    }

    public Case getPosition(){
    	return this.position;
    }

    public void setPosition(Case c){
    	this.position = c;
    	//TODO
    }
    
    //TODO: on vérifie que le robot peut aller sur le terrain "nature".
    //chaque robot à ses terrains 
    public abstract boolean appartientTerrainRobot(NatureTerrain nature);
    //Pour vérifier, doit être en connaissance de la carte.
    
    
    //deplace le robot si il a le droit. Sinon ne fait rien.
    public void deplacer(Direction direction, Carte carte) {
    	//Verification qu'on peut le déplacer (case existe + bonne nature)
    	if ((carte.voisinExiste(this.getPosition(), direction)) &
			(this.appartientTerrainRobot(carte.getVoisin(this.getPosition(), direction).getNature())))
    	{
    		switch(direction) {
 
    			case NORD:
    				this.position.setLigne(this.position.getLigne() - 1);
    				break;
    			
    			case SUD:
    				this.position.setLigne(this.position.getLigne() + 1);
    				break;
    			
    			case EST:
    				this.position.setColonne(this.position.getColonne() + 1);
    				break;

    			case OUEST:
    				this.position.setColonne(this.position.getColonne()  -1);
    				break;
    	
    	}
      }
   }
    
    public abstract double getVitesse(NatureTerrain nat);

    public abstract void deverserEau(int vol);
    
    public abstract void remplirReservoir();

}
