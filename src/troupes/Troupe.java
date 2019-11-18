package troupes;

public class Troupe {
	
	private int coutProduction;
	private int tempsProduction;
	private int vitesse;
	private int vie;
	private int degats;
	
	public Troupe(int coutProduction, int tempsProduction, int vitesse, int vie, int degats) {
		this.coutProduction = coutProduction;
		this.tempsProduction = tempsProduction;
		this.vitesse = vitesse;
		this.vie = vie;
		this.degats = degats;
	}
	
	public int getCoutProduction() {
		return coutProduction;
	}
	
	public int getTempsProduction() {
		return tempsProduction;
	}
	
}
