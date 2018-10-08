package robots;

import carte.Case;
import carte.NatureTerrain;

public abstract class Robot{


    protected Case position;

    public Robot(Case c){

    }

    public Case getPosition(){
    	return null;
        //TODO
    }

    public void setPosition(Case c){
    	this.position = c;
    	//TODO
    }

    public double getVitesse(NatureTerrain nat){
        return 0;
    	//TODO
    }

    public void deverserEau(int vol){
        //TODO
    }

    public void remplirReservoir(){
        //TODO
    }


}
