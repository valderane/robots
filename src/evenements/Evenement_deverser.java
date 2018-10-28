package evenements;

import Exceptions.Exceptions.Exceptions_remplissage.ReservoirVide;
import carte.Carte;
import robots.Robot;

//Le robot deverse son eau pour éteindre l'incendie à la case courante
//le robot doit connaître le pas de temps du next.
//ie le robot vide une certaine qqté du reservoir en un temps donné.
//exemple: si le pas est une sec: on doit avoir:

//100 litres en 	5 sec 
// ?  litres en 	pas de temps.

//implementer deverser_eau(?) 

//-> l'evenement apelera la méthode déverser
// on deverse une certaine qqté fixe a chq dois que la methode est appelé (en fct du pas de temps)
// le nombre d'evenement a effectué dépendra du temps.
// next -> + 1sec -> 

//on passe un volume a vider. (selon le pas de temps)


//pb: si le next c'est de 1 sec en 1 sec.
/* si RobotA vide 10l en 0.5sec:
 * next1= il arrive case B
 * next2: 1sec plus tard. donc il a vidé pdt une sec. ie on doit faire vider 20l  */


//case 1: 1sec next1
//case 2: 2sec next2

//1sec3

//deverse une certaine qqté d'eau en fonction du pas de temps.


//1 choix: on passe une qqté d'eau a déverser:
// -avantage: 	  méthode commune a tous les robots.  (si volumme == 0 -> volume = 0)
// inconvénient:  les vérifications se font dans EVENEMENT qui en fonction du robot le fait.
// évenement doit connaître le pas ( lors de la création)

//2 choix: chaque robot vide son réservoir selon ca capacité -> dois connaître le pas de temps 


public class Evenement_deverser extends Evenement {
	private Robot robot;
	private Carte carte;
	
	//prochciane date = date_courante(simulateur) + pas_simulateur
	
	//date_evenement + pas 
	
	public Evenement_deverser(long date_evenement, Robot robot, Carte carte) {
		super(date_evenement);
		this.robot = robot;
		this.carte = carte;
	}

	@Override
	public void execute(long prochaine_date) {
		//robot.capacite_litre * pas de temps  / capacite temps 
		//Le pas doit être en sec
		long difference_temps = prochaine_date - this.date;
		try {
		robot.deverserEau((int)(robot.getCapacite_vider_litre() * difference_temps) / robot.getCapacite_vider_ms());
		}
		catch (ReservoirVide e) {
			System.out.println(e);
		}
	}
	
	

}
