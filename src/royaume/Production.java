package royaume;

import troupes.Chevalier;
import troupes.Onagre;
import troupes.Piquier;

public class Production {
	private int unite;
	private int nbTours;
	private int nbToursInit;
	
	public Production(int unite, int niveauChateau) {
		this.unite = unite;
		if(unite==Constantes.PIQUIER)
			nbTours = Piquier.TEMPS_PRODUCTION;
		else if(unite==Constantes.CHEVALIER)
			nbTours = Chevalier.TEMPS_PRODUCTION;
		else if(unite==Constantes.ONAGRE)
			nbTours = Onagre.TEMPS_PRODUCTION;
		else
			nbTours = 100+50*niveauChateau;
		nbToursInit = nbTours;
	}
	
	public int getUnite() {
		return unite;
	}
	
	public boolean estAmelioration() {
		return unite == Constantes.AMELIORATION;
	}
	
	public int getNbTours() {
		return nbTours;
	}
	
	public int getNbToursInit() {
		return nbToursInit;
	}
	
	public double pourcentage() {
		return 1.0-((double)nbTours/(double)nbToursInit);
	}
	
	/*
	 * Fin de production, il faut produire l'unite
	 */
	public boolean finProduction() {
		return nbTours == 0;
	}
	
	/*
	 * A executer à chaque fin de tour
	 */
	public void finTourProduction() {
		nbTours--;
	}
}
