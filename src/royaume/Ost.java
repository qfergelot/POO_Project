package royaume;

import java.util.ArrayList;

import javafx.scene.image.Image;
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
	
	public boolean attaqueFinie() {
		return attaqueFinie;
	}
	
	public void tourOst() {
		for(int i=0; i<troupes.size(); i++) {
			if(troupes.get(i).surCible()) {
				if(duc.equals(cible.getDuc())) {
					troupes.get(i).transferer(cible, this);
				}
				else {
					attaqueFinie = troupes.get(i).attaquer(cible);
					if(troupes.get(i).estMort()) {
						troupes.get(i).getImageView().setImage(null);
						troupes.remove(i);
					}
					if(attaqueFinie == true) {
						if(!cible.getNeutre()) {
							cible.getDuc().retirerChateau();
						}
						cible.setDuc(this.duc);
						cible.setNeutre(false);
						cible.changeImage(new Image(getClass().getResource("/images/Chateau joueur.png").toExternalForm(), 40, 40, true, false));
						this.duc.ajouterChateau();
						
						//return new Chateau(cible.getLayer(),duc.getImgChateau(),duc,cible.getTresor(),0,0,0,cible.getPos_x(),cible.getPos_y(),cible.getPopupOst());
						
					}
				}
			}
			else {
				troupes.get(i).deplacement(cible);
			}
		}
		//return null;
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
