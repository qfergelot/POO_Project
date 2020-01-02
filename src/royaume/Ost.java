package royaume;

import java.util.ArrayList;

import troupes.*;

/**
 * Class that represents an ost
 * @author Moi
 *
 */
public class Ost {
	
	private Duc duc;
	
	private Chateau cible;
	
	private ArrayList<Troupe> troupes;
	private boolean attaqueFinie = false;
	
	/**
	 * Construct an ost
	 * @param duc Owner 
	 * @param cible Target castle
	 */
	public Ost(Duc duc, Chateau cible) {
		this.duc = duc;
		this.cible = cible;
		troupes = new ArrayList<Troupe>();
	}
	
	/**
	 * Getter of the state of the attack
	 * @return true if attack is finished, else false
	 */
	public boolean attaqueFinie() {
		return attaqueFinie;
	}
	
	/**
	 * Process the turn of an ost
	 * @param royaume Kingdom in which the ost appears
	 */
	public void tourOst(Royaume royaume) {
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
						cible.setImageView(this.duc.getImgChateau());
						cible.annulerProduction();
						
						this.duc.ajouterChateau();						
					}
				}
			}
			else {
				troupes.get(i).deplacement(cible, royaume);
			}
		}
	}
	
	/**
	 * Add a unit to this ost
	 * @param t Unit to be added
	 */
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
	
	/**
	 * Remove this ost from the game
	 */
	public void delete() {
		for (int i = 0; i<troupes.size(); i++)
			troupes.get(i).delete();
	}
	
}
