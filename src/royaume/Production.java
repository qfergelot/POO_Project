package royaume;

public class Production {
	private String unite;
	private int nbTours;
	
	public Production(String unite, int nbTours) {
		this.unite = unite;
		this.nbTours = nbTours;
	}
	
	public String getUnite() {
		return unite;
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
