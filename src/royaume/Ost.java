package royaume;

import java.util.ArrayList;
import java.util.Random;

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
	
	public void attaquer(Chateau c) {
		for(int i = 0; i < troupes.size(); i++) {
			troupes.get(i).attaquer(c);
			if(troupes.get(i).estMort()){
				troupes.remove(i);
			}
		}
		if(c.aucuneTroupe() && (nbTroupes > 0)) {
			c.setDuc(duc);
			transfererTroupes(c);
		}
	}
	
	private void transfererTroupes(Chateau c) {
		while(!troupes.isEmpty()) {
			if(troupes.get(0).getClass() == Piquier.class) {
				c.getPiquiers().add((Piquier)troupes.get(0));
			}
			else if(troupes.get(0).getClass() == Chevalier.class) {
				c.getChevaliers().add((Chevalier)troupes.get(0));
			}
			else {
				c.getOnagres().add((Onagre)troupes.get(0));
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
	}
	
	public void ajouterChevalier(Chevalier c) {
		troupes.add(c);
	}
	
	public void ajouterOnagre(Onagre o) {
		troupes.add(o);
	}
}
