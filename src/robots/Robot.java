package robots;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

public abstract class Robot{

	protected int type;
  


	protected Case position;
    /*qqté d'eau max que peut tranqporter le robot*/
    protected int reservoir_eau;
    
	//capacité vider_litre en capacité vider_ms 
	protected int capacite_vider_litre;
	protected int capacite_vider_ms;
	//temps qu'il faut pour remplie tout le reservoir
    protected int capacite_remplir_ms;
	
  
 
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
    		System.out.println("nature:" + this.getPosition());
    		System.out.println("nature au dessus:" + carte.getVoisin(this.getPosition(), direction));
    		System.out.println("nature a droite" + carte.getVoisin(this.getPosition(), Direction.EST));
;
    		switch(direction) {
 
    			case NORD:
    				System.out.println("direction nord");
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
    //commun a tous les robots.
    //l'evenement qui appelle cette métohe doit vérifier de quel robot il s'agit + selon le pas.
    public  void deverserEau(int vol) {
    	this.reservoir_eau -= vol;
    	if (this.reservoir_eau < 0){
    		this.reservoir_eau = 0;
    	}
    }

    public int getCapacite_vider_litre() {
		return capacite_vider_litre;
	}

	public int getCapacite_vider_ms() {
		return capacite_vider_ms;
	}

	public int getCapacite_remplir_ms() {
		return capacite_remplir_ms;
	}
    
    public abstract double getVitesse(NatureTerrain nat);

    
    public abstract void remplirReservoir();

	public int getType() {
		return type;
	}
    
}
