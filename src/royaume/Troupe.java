package royaume;

public class Troupe {
	private String duc;
	
	private int cout_production;
	private int temps_production;
	private int vitesse;
	private int vie;
	private int degats;
	
	private int pos_x;
	private int pos_y;
	
	public Troupe(int cout_production, int temps_production, int vitesse, int vie, int degats, String duc) {
		this.cout_production = cout_production;
		this.temps_production = temps_production;
		this.vitesse = vitesse;
		this.vie = vie;
		this.degats = degats;
		this.duc = duc;
	}
	
	public void move(String dir) {
		switch(dir) {
			case "gauche":
				pos_x--;
				break;
			case "haut":
				pos_y--;
				break;
			case "droite":
				pos_x++;
				break;
			default:
				pos_y++;
				break;
		}
	}
	
	
}
