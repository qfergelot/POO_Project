package royaume;

import java.util.ArrayList;
import java.util.Random;

import troupes.*;

public class Ost {
	
	private Duc duc;
	
	private Chateau cible;
	
	private ArrayList<Troupe> troupes;
	
	public Ost(Duc duc, Chateau cible, double x, double y) {
		this.duc = duc;
		this.cible = cible;
		troupes = new ArrayList<Troupe>();
	}
	
	public int distanceCible() {
		int cmp = 1;
		for(int i = 0; i<troupes.size(); i++) {
			Troupe t = troupes.get(i);
			if (t.distance(cible) != 1) {
				cmp ++;
			}
		}
		return cmp;
		
	}
	
	public void deplacement() {
		for(int i = 0; i<troupes.size(); i++) {
			Troupe t = troupes.get(i);
			int v = t.getVitesse();
			while(v > 0) {
				double dx = t.getPos_x() - cible.getPos_x();
				double dy = t.getPos_y() - cible.getPos_y();
				
				if(Math.abs(dx) > Math.abs(dy)) {
					if (dx > 0) {
						t.move(Constantes.DROITE);
						
					}
					else {
						t.move(Constantes.GAUCHE);
						
					}
				}
				
				else {
					if (dy > 0) {
						t.move(Constantes.BAS);

					}
					else {
						t.move(Constantes.HAUT);

					}
				}
			}
			v--;
		}
		
	}
	
	public void attaquerCible() {
		for(int i = 0; i < troupes.size(); i++) {
			troupes.get(i).attaquer(cible);
			if(troupes.get(i).estMort()){
				troupes.remove(i);
			}
		}
		if(cible.aucuneTroupe() && (troupes.size() > 0)) {
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
	
	
	public void ajouterTroupe(Troupe t) {
		troupes.add(t);
	}
	
	public Duc getDuc() {
		return duc;
	}
	
	public Chateau getCible() {
		return cible;
	}
	
}
