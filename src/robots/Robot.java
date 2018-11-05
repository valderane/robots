package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import exceptions.ExceptionsDeplacement.ProchaineCaseMauvaiseNature;
import exceptions.ExceptionsDeplacement.RobotSorsCarte;
import exceptions.exceptionsChemins.AucunCheminPossible;

public abstract class Robot{

	protected Case position;
    /*qqté d'eau max que peut tranqporter le robot*/
    /*9000 pour test*/
	protected int reservoirEau = 0;
    
	//capacité vider_litre en capacité vider_ms 
	protected int capaciteViderLitre;
	protected int capaciteViderSec;
	//temps qu'il faut pour remplie tout le reservoir
    protected int capaciteRemplirSec;
    // nombre de litre maximum du reservoir
    protected int capaciteRemplirLitre;
	
    protected double vitesse; 
    /*la taille des case doit etre > 1*/
    private int positionDansCase = 0;
    
  
    
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
		    				this.setPosition(carte.getVoisin(this.position, direction));
		    				
    			}else {
    				throw new ProchaineCaseMauvaiseNature(this + ": Mauvaise nature terrain pour prochaine case");
    			}
    	}
    	
    	else {
    		throw new RobotSorsCarte("Robot sort de la carte");
    	}
    	
    	
  	}
    
    // Retourne la qqté d'eau deversé (en fct du résevoir du robot)
    public int deverserEau(int vol) {
    	int avant_vidage = this.reservoirEau;
    	int apres_vidage = this.reservoirEau - vol;
    	if (apres_vidage <= 0){
    		this.reservoirEau = 0;
    		/*on a vidé ce qu'il restait*/
    		return avant_vidage;
    	}
    	else {
    		this.reservoirEau = apres_vidage;
    		return vol;
    	}
    }
    	

    public int getPositionDansCase() {
		return positionDansCase;
	}

    
	public void setPositionDansCase(int nouvelle_positionDansCase, int taille_case) {
		if (nouvelle_positionDansCase < taille_case & nouvelle_positionDansCase >= 0)
			this.positionDansCase = nouvelle_positionDansCase;
		else
            throw new IllegalArgumentException("nouvelle_position incorrect : "+ nouvelle_positionDansCase + "< 0 ou > taille_case"); 
	}
	
	
	public int getCapaciteViderLitre() {
		return capaciteViderLitre;
	}

	public int getCapaciteViderSec() {
		return capaciteViderSec;
	}
	
	public int getCapaciteRemplirLitre() {
		return capaciteViderLitre;
	}

	public int getCapaciteRemplirSec() {
		return capaciteRemplirSec;
	}
    
    public abstract double getVitesse(NatureTerrain nat);
    
    /**
     * Retourne le temps de parcours de deux cases adjacentes avec chacune une nature donnée.
     * 
     * @param nat1 Nature du terrain de la première case
     * @param nat2 Nature du terrain de la seconde case
     * @return
     */
    public double getTempsParcours(NatureTerrain nat1, NatureTerrain nat2, int taille_case) throws AucunCheminPossible
    {
    	if(this.getVitesse(nat1) == 0 || this.getVitesse(nat2) == 0) {
    		throw new AucunCheminPossible("Chemin impossible");
    	}
    	
    	double vitesse_moyenne = (this.getVitesse(nat1) + this.getVitesse(nat2)) / 2;
    	return (2*taille_case / (vitesse_moyenne/3.6));
    }

    /**
     * remplir le reservoir d'un robot de vol
     * si reservoir + vol > reservoir_max, mettre cpacite a capacite_max
     * 
     * genere une execption si le robot est deja plein
     * @param vol
     */
    public abstract void remplirReservoir(int vol);
    
    public abstract void setVitesse(double vitesse)  throws DataFormatException;

	public int getReservoir_eau() {
		return this.reservoirEau;
	}

    
}
