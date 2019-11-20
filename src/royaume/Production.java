package royaume;

import troupes.Troupe;

public class Production {
	private Troupe unite;
	private int nbTours;
	
	public Production(int niveauChateau) {
		this.unite = null;
		this.nbTours = 100+50*niveauChateau;
	}
	
	public Production(Troupe unite) {
		this.unite = unite;
		this.nbTours = unite.getTempsProduction();
	}
	
	public Troupe getUnite() {
		return unite;
	}
	
	public boolean estAmelioration() {
		return unite == null;
	}
	
	public int getNbTours() {
		return nbTours;
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
