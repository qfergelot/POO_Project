package royaume;

import java.util.ArrayList;
import java.util.Random;

import troupes.*;

public class Ost {
	
	private Duc duc;
	
	private Chateau cible;
	private int nbTroupes = 0;
	private int vitesse=10;
	
	private ArrayList<Troupe> troupes;
	
	private boolean auComplet;
	
	private int pos_x;
	private int pos_y;
	
	public Ost(Duc duc, Chateau cible, int x, int y) {
		this.duc = duc;
		this.cible = cible;
		troupes = new ArrayList<Troupe>();
		auComplet = false;
		
		pos_x = x;
		pos_y = y;
	}
	
	public void move(int dir) {
		switch(dir) {
			case Constantes.GAUCHE:
				pos_x--;
				break;
			case Constantes.HAUT:
				pos_y--;
				break;
			case Constantes.DROITE:
				pos_x++;
				break;
			default:
				pos_y++;
				break;
		}
	}
	
	public int distance(int x, int y) {
		return Math.abs(y - pos_y) + Math.abs(x - pos_x);
	}
	
	public int distanceCible() {
		return Math.abs(cible.getPos_y() - pos_y) + Math.abs(cible.getPos_x() - pos_x);
	}
	
	public void attaquerCible() {
		for(int i = 0; i < troupes.size(); i++) {
			troupes.get(i).attaquer(cible);
			if(troupes.get(i).estMort()){
				troupes.remove(i);
			}
		}
		if(cible.aucuneTroupe() && (nbTroupes > 0)) {
			cible.getDuc().retirerChateau();
			cible.setDuc(duc);
			transfererTroupes();
		}
	}
	
	public void transfererTroupes() {
		while(!troupes.isEmpty()) {
			if(troupes.get(0).getClass() == Piquier.class) {
				cible.getPiquiers().add((Piquier)troupes.get(0));
			}
			else if(troupes.get(0).getClass() == Chevalier.class) {
				cible.getChevaliers().add((Chevalier)troupes.get(0));
			}
			else {
				cible.getOnagres().add((Onagre)troupes.get(0));
			}
			troupes.remove(0);
		}
	}
	
	public boolean auComplet() {
		return auComplet;
	}
	
	public void setAuComplet() {
		auComplet = true;
	}
	
	public void ajouterPiquier(Piquier p) {
		troupes.add(p);
		if(vitesse > p.getVitesse()) {
			vitesse = p.getVitesse();
		}
	}
	
	public void ajouterChevalier(Chevalier c) {
		troupes.add(c);
		if(vitesse > c.getVitesse()) {
			vitesse = c.getVitesse();
		}
	}
	
	public void ajouterOnagre(Onagre o) {
		troupes.add(o);
		if(vitesse > o.getVitesse()) {
			vitesse = o.getVitesse();
		}
	}
	
	public Duc getDuc() {
		return duc;
	}
	
	public Chateau getCible() {
		return cible;
	}
	
	public int getVitesse() {
		return vitesse;
	}
	
	public int getPos_x() {
		return pos_x;
	}
	
	public int getPos_y() {
		return pos_y;
	}
}
