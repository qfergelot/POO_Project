package royaume;

import java.util.ArrayList;

import troupes.*;

public class Ost {
	
	private Duc duc;
	
	private Chateau cible;
	
	private ArrayList<Troupe> troupes;
	private boolean attaqueFinie = false;
	
	public Ost(Duc duc, Chateau cible) {
		this.duc = duc;
		this.cible = cible;
		troupes = new ArrayList<Troupe>();
	}
	
	public void deplacement() {
		for(int i = 0; i<troupes.size(); i++) {
			Troupe t = troupes.get(i);
			int v = t.getVitesse();
			while(v > 0 && t.distance(cible)>(cible.getHeight()/2+t.getHeight()/2)) {
				double angle = Math.atan2(cible.getPos_y()-t.getPos_y(),cible.getPos_x()-t.getPos_x())/Math.PI;
				t.move(angle);
				v--;
			}
			t.getImageView().relocate(t.getPos_x(), t.getPos_y());
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
			attaqueFinie = true;
		}
	}
	
	public boolean attaqueFinie() {
		return attaqueFinie;
	}
	
	public void transfererTroupes() {
		while(!troupes.isEmpty()) {
			if(troupes.get(0).getClass() == Piquier.class) {
				cible.ajouterPiquier();
			}
			else if(troupes.get(0).getClass() == Chevalier.class) {
				cible.ajouterChevalier();
			}
			else {
				cible.ajouterOnagre();
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
	
	public ArrayList<Troupe> getTroupe(){
		return troupes;
	}
}
