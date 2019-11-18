package royaume;

import java.util.ArrayList;
import java.util.Random;

import troupes.*;

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
	private Ordre ordreDeplacement;
	private Ost ost;
	private Porte porte; //"gauche"/"haut"/"droite"/"bas"
	
	private int pos_x;
	private int pos_y;
	
	/* Chateau Duc */
	public Chateau(String duc, int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, int x, int y) {
		this.duc = duc;
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		
		pos_x = x;
		pos_y = y;
	}
	
	/* Chateau Neutre (pas de duc) */
	public Chateau(int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, int x, int y) {
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.porte = new Porte();
		
		pos_x = x;
		pos_y = y;
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
	public boolean lancerProduction() {
		if(tresor < 1000*niveau) {
			//Pas assez de florins pour l'amélioration
			return false;
		}
		else {
			tresor = tresor - 1000*niveau;
			production = new Production(100+50*niveau);
			return true;
		}
	}
	
	public boolean lancerProduction(Troupe t) {
		if(tresor < t.getCoutProduction()) {
			// pas assez de sousous
			return false;
		}
		else {
			tresor = tresor - t.getCoutProduction();
			production = new Production(t, t.getTempsProduction());
			return true;
		}
		
	}
	
	public boolean enProduction() {
		return production != null;
	}

	/*
	 * Executer quand production.finProduction() == true
	 */
	public void terminerProduction() {
		if(production.estAmelioration())
			niveau++;
		else {
			Troupe t = production.getUnite();
			if(t.getClass() == Piquier.class)
				piquiers.add(new Piquier());
			else if(t.getClass() == Chevalier.class)
				chevaliers.add(new Chevalier());
			else
				onagres.add(new Onagre());
		}
		production = null;
			
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	/* * * * * * * * DEBUT : Fonctions Ordre * * * * * * * */
	/* true si l'odre a été lancé
	 * false si le nombre de troupes est insuffisant
	 */
	public boolean creerOrdre(Ost ost, Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		if(piquiers.size()<nbPiquiers || chevaliers.size()<nbChevaliers || onagres.size()<nbOnagres) {
			return false;
		}
		this.ost = ost;
		ordreDeplacement = new Ordre(cible, nbPiquiers, nbChevaliers, nbOnagres);
		return true;
	}
	
	public void sortirTroupesOrdre() {
		int stop = (ordreDeplacement.getNbTroupes()>=3? 3 : ordreDeplacement.getNbTroupes());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(ordreDeplacement.getNbOnagres()>0) {
				ost.ajouterOnagre();
				ordreDeplacement.sortirOnagre();
			}
			else if(ordreDeplacement.getNbPiquiers()>0) {
				ost.ajouterPiquier();
				ordreDeplacement.sortirPiquier();
			}
			else {
				ost.ajouterChevalier();
				ordreDeplacement.sortirChevalier();
			}
		}
		if(ordreDeplacement.getNbTroupes()==0) {
			ordreDeplacement = null;
			ost.setAuComplet();
			ost = null;
		}
	}
	
	public boolean ordre() {
		return ordreDeplacement != null;
	}
		
	/* * * * * * * * FIN : Fonctions Ordre * * * * * * * */
	
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
	
	public void finTourChateau() {
		if(!neutre) {
			if(enProduction()) {
				production.finTourProduction();
				if(production.finProduction()) {
					terminerProduction();
				}
			}
			if(ordre()) {
				sortirTroupesOrdre();
			}
		}
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


	public Ordre getOrdreDeplacement() {
		return ordreDeplacement;
	}
	
	public Ost getOst() {
		return ost;
	}

	public char getPorte() {
		return porte.getPorte();
	}


	public int getPos_x() {
		return pos_x;
	}


	public int getPos_y() {
		return pos_y;
	}
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
