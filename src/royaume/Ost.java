package royaume;

import java.util.ArrayList;

import troupes.Chevalier;
import troupes.Onagre;
import troupes.Piquier;

public class Ost {
	private Chateau cible;
	private int nbTroupes = 0;
	
	private ArrayList<Piquier> piquiers;
	private ArrayList<Chevalier> chevaliers;
	private ArrayList<Onagre> onagres;
	
	private boolean auComplet;
	
	private int pos_x;
	private int pos_y;
	
	public Ost(Chateau cible) {
		this.cible = cible;
		piquiers = new ArrayList<Piquier>();
		chevaliers = new ArrayList<Chevalier>();
		onagres = new ArrayList<Onagre>();
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
	
	public void ajouterPiquier(String duc) {
		piquiers.add(new Piquier(duc));
		nbTroupes++;
	}
	
	public void ajouterChevalier(String duc) {
		chevaliers.add(new Chevalier(duc));
		nbTroupes++;
	}
	
	public void ajouterOnagre(String duc) {
		onagres.add(new Onagre(duc));
		nbTroupes++;
	}
}
