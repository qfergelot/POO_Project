package royaume;

import java.util.ArrayList;
import troupes.*;

public class Chateau {
	private String duc;
	private int tresor;
	private int niveau;
	/* Si j'ai bien compris c'est une liste pour chaque type de troupe
	 * j'ai choisi ArrayList car très variable */
	private ArrayList<Piquier> piquiers;
	private ArrayList<Chevalier> chevaliers;
	private ArrayList<Onagre> onagres;
	/* Pas compris le compteur de dégats à voir plus tard 
	 * Peut être définir une classe reserve pour les troupes*/
	private Production production;
	private Ordre ordre_deplacement;
	private String porte; //"gauche"/"haut"/"droite"/"bas"
	
	public Chateau(String duc, int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, String porte) {
		this.duc = duc;
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.production = null;
		this.ordre_deplacement = null;
		this.porte = porte;
	}
	
	public void lancerProduction(String unite) {
		int nbTours;
		switch(unite) {
			case "amelioration":
				nbTours = 100+50*niveau;
				break;
			case "piquier":
				nbTours = 5;
				break;
			case "chevalier":
				nbTours = 20;
				break;
			default :
				nbTours = 50;
				break;
		}
		production = new Production(unite, nbTours);
	}
	
	public boolean enProduction() {
		return production != null;
	}

	public void terminerProduction() {
		switch(production.getUnite()) {
		case "amelioration":
			niveau++;
			break;
		case "piquier":
			piquiers.add(new Piquier());
			break;
		case "chevalier":
			chevaliers.add(new Chevalier());
			break;
		default :
			onagres.add(new Onagre());
			break;
		}
		production = null;
	}
	
}
