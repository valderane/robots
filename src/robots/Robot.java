package robots;

import java.util.zip.DataFormatException;
import Exceptions.Exceptions_deplacement.ProchaineCaseMauvaiseNature;
import Exceptions.Exceptions_deplacement.RobotSorsCarte;
import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

public abstract class Robot{

	private double position_dans_case;
	protected Case position;
    /*qqté d'eau max que peut tranqporter le robot*/
    /*9000 pour test*/
	protected int reservoir_eau = 9000;
    
	//capacité vider_litre en capacité vider_ms 
	protected int capacite_vider_litre;
	protected int capacite_vider_sec;
	//temps qu'il faut pour remplie tout le reservoir
    protected int capacite_remplir_sec;
	
    protected double vitesse; 

  
    
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
    public void deplacer(Direction direction, Carte carte) throws RobotSorsCarte, ProchaineCaseMauvaiseNature  {
    	//Verification qu'on peut le déplacer (case existe + bonne nature)
    	if (carte.voisinExiste(this.getPosition(), direction)){
    		
    			if (this.appartientTerrainRobot(carte.getVoisin(this.getPosition(), direction).getNature())){
    				
//		    		System.out.println("nature:" + this.getPosition());
//		    		System.out.println("nature au dessus:" + carte.getVoisin(this.getPosition(), direction));
//		    		System.out.println("nature a droite" + carte.getVoisin(this.getPosition(), Direction.EST));
				
		    		switch(direction) {
		 
		    			case NORD:
		    				System.out.println("direction nord");
		    				this.position = carte.getVoisin(this.position, Direction.NORD);
		 		    		break;
		    			
		    			case SUD:
		    				this.position = carte.getVoisin(this.position, Direction.SUD);
		    				break;
		    			
		    			case EST:
		    				this.position = carte.getVoisin(this.position, Direction.EST);
		    				break;
		
		    			case OUEST:
		    				this.position = carte.getVoisin(this.position, Direction.OUEST);
		    				break;
    	
		    		}
    			}
    			else {
    				throw new ProchaineCaseMauvaiseNature(this + ": Mauvaise nature terrain pour prochaine case");
    			}
    	}
    	
    	else {
    		throw new RobotSorsCarte("Robot sors de la carte");
    	}
    	
    	
  	}
    
    // Retourne la qqté d'eau deversé (en fct du résevoir du robot)
    public int deverserEau(int vol) {
    	int avant_vidage = this.reservoir_eau;
    	int apres_vidage = this.reservoir_eau - vol;
    	if (apres_vidage <= 0){
    		this.reservoir_eau = 0;
    		/*on a vidé ce qu'il restait*/
    		return avant_vidage;
    	}
    	else {
    		this.reservoir_eau = apres_vidage;
    		return vol;
    	}
    }
    	

    public double getPosition_dans_case() {
		return position_dans_case;
	}

    
	public void setPosition_dans_case(double nouvelle_position_dans_case) {
		if (nouvelle_position_dans_case >= 0.0)
			this.position_dans_case = nouvelle_position_dans_case;
		else
            throw new IllegalArgumentException("nouvelle_position incorrect : "+ nouvelle_position_dans_case + "< 0"); 
	}
	
	
	public int getCapacite_vider_litre() {
		return capacite_vider_litre;
	}

	public int getCapacite_vider_sec() {
		return capacite_vider_sec;
	}

	public int getCapacite_remplir_sec() {
		return capacite_remplir_sec;
	}
    
    public abstract double getVitesse(NatureTerrain nat);
    
    /**
     * Retourne le temps de parcours de deux cases adjacentes avec chacune une nature donnée.
     * 
     * @param nat1 Nature du terrain de la première case
     * @param nat2 Nature du terrain de la seconde case
     * @return
     */
    public abstract double getTempsParcours(NatureTerrain nat1, NatureTerrain nat2);

    
    public abstract void remplirReservoir();
    
    public abstract void setVitesse(double vitesse)  throws DataFormatException;

	public int getReservoir_eau() {
		return reservoir_eau;
	}

    
}
