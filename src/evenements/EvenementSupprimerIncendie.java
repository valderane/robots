package evenements;

import carte.Incendie;
import io.DonneesSimulation;

public class EvenementSupprimerIncendie extends Evenement{
	
	private Incendie incendie;
	
	private DonneesSimulation donneesSimu;
	
	public EvenementSupprimerIncendie(long date, Incendie incendie, DonneesSimulation donneesSimu) {
		super(date);
		this.incendie = incendie;
		this.donneesSimu = donneesSimu;
	}

	@Override
	public void execute() {
		this.donneesSimu.supprimerIncendie(incendie);
	}

}
