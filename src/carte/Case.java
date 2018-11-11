package carte;

/**
 * Une case a une position (ligne, colonne) et une nature. 
 * @author 
 *
 */
public class Case{
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    /**
     * Constructeur
     * 
     * @param lig Ligne de la case dans la carte
     * @param col Colonne de la case dans la carte
     * @param nature Nature de la case
     */
    public Case(int lig, int col, NatureTerrain nature){
        this.ligne = lig;
        this.colonne = col;
        this.nature = nature;
    }

    public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public int getLigne(){
        return this.ligne;
    }

    public int getColonne(){
        return this.colonne;
    }

    public NatureTerrain getNature(){
        return this.nature;
    }
    
    @Override
    public String toString() {
    	return "CASE position = ("+this.colonne+","+this.ligne+"); nature = "+this.nature;
    }
}
