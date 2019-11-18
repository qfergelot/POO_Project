package royaume;

import java.util.ArrayList;

import troupes.*;

public class Ost {
	private String duc;
	
	private Chateau cible;
	private int nbTroupes = 0;
	
	private ArrayList<Troupe> troupes;
	
	private boolean auComplet;
	
	private int pos_x;
	private int pos_y;
	
	public Ost(String duc, Chateau cible) {
		this.duc = duc;
		this.cible = cible;
		troupes = new ArrayList<Troupe>();
		auComplet = false;
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
	
	public boolean auComplet() {
		return auComplet;
	}
	
	public void setAuComplet() {
		auComplet = true;
	}
	
	public void ajouterPiquier() {
		troupes.add(new Piquier());
		nbTroupes++;
	}
	
	public void ajouterChevalier() {
		troupes.add(new Chevalier());
		nbTroupes++;
	}
	
	public void ajouterOnagre() {
		troupes.add(new Onagre());
		nbTroupes++;
	}
}
