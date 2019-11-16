package royaume;

import java.util.ArrayList;
import java.util.Random;

public class Chateau {
	Random rdm = new Random();
	
	private String duc = "neutre";
	private boolean neutre = true;
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
	
	private int pos_x;
	private int pos_y;
	
	/* Chateau Duc */
	public Chateau(String duc, int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, String porte, int x, int y) {
		this.duc = duc;
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.production = null;
		this.ordre_deplacement = null;
		this.porte = porte;
		
		pos_x = x;
		pos_y = y;
	}
	
	/* Chateau Neutre (pas de duc) */
	public Chateau(int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, String porte, int x, int y) {
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.production = null;
		this.ordre_deplacement = null;
		this.porte = porte;
		
		pos_x = x;
		pos_y = y;
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
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

	/*
	 * Executer quand production.finProduction() == true
	 */
	public void terminerProduction() {
		switch(production.getUnite()) {
		case "amelioration":
			niveau++;
			break;
		case "piquier":
			piquiers.add(new Piquier(duc));
			break;
		case "chevalier":
			chevaliers.add(new Chevalier(duc));
			break;
		default :
			onagres.add(new Onagre(duc));
			break;
		}
		production = null;
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	public int distance(Chateau c) {
		if (c.getPos_x() == pos_x && c.getPos_y() == pos_y) {
			System.err.println("Erreur : Deux chateaux ne peuvent être sur la même position.");
			return 0;
		}
		return Math.abs(c.getPos_y() - pos_y) + Math.abs(c.getPos_x() - pos_x);
	}
	
	public int distance(int x, int y) {
		return Math.abs(y - pos_y) + Math.abs(x - pos_x);
	}
	
	
	
	
	
	
	
	

	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	public String getDuc() {
		return duc;
	}
	
	public boolean getNeutr() {
		return neutre;
	}

	public int getTresor() {
		return tresor;
	}


	public int getNiveau() {
		return niveau;
	}


	public ArrayList<Piquier> getPiquiers() {
		return piquiers;
	}


	public ArrayList<Chevalier> getChevaliers() {
		return chevaliers;
	}


	public ArrayList<Onagre> getOnagres() {
		return onagres;
	}


	public Production getProduction() {
		return production;
	}


	public Ordre getOrdre_deplacement() {
		return ordre_deplacement;
	}


	public String getPorte() {
		return porte;
	}


	public int getPos_x() {
		return pos_x;
	}


	public int getPos_y() {
		return pos_y;
	}
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
