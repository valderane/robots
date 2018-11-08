package evenements;

import carte.Incendie;

public class EvenementLibererIncendie extends Evenement{
	
	private Incendie incendie;
	
	public EvenementLibererIncendie(long date, Incendie incendie) {
		super(date);
		this.incendie = incendie;
	}

	@Override
	public void execute() {
		this.incendie.setLibre(true);		
	}

}
