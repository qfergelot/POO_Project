package royaume;

import java.util.ArrayList;

public class Ordre {
	private Chateau cible;
	private int nbTroupes;
	
	private int nbPiquiers;
	private int nbChevaliers;
	private int nbOnagres;
	
	public Ordre(Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		this.cible = cible;
		this.nbTroupes = nbPiquiers + nbChevaliers + nbOnagres;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
	}
	
	public int getNbTroupes() {
		return nbTroupes;
	}
	
	public int getNbPiquiers() {
		return nbPiquiers;
	}
	
	public int getNbChevaliers() {
		return nbChevaliers;
	}
	
	public int getNbOnagres() {
		return nbOnagres;
	}
	
	public void sortirPiquier() {
		nbPiquiers--;
		nbTroupes--;
	}
	
	public void sortirChevalier() {
		nbChevaliers--;
		nbTroupes--;
	}
	
	public void sortirOnagre() {
		nbOnagres--;
		nbTroupes--;
	}
}
