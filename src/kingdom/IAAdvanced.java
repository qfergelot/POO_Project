package kingdom;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class IAAdvanced extends Duke {
	private Kingdom kingdom;
	
	private Castle target;
	
	/**
	 * Construct a Advanced AI
	 * @param name Name of the AI 
	 * @param couleur Color
	 * @param image Image of the AI's castle
	 * @param kingdom Kingdom in which the AI operates
	 */
	public IAAdvanced(String name, Color couleur, Image image, Kingdom kingdom) {
		super(name, couleur, image);
		this.kingdom = kingdom;
	}
	
	public void roundCastleAI(Castle castle) {
		int defenseScore = castle.getNbPikemen()+castle.getNbKnight()*2+castle.getNbOnager()*5;
		if(defenseScore < 5) {
			try{
				castle.launchProduction(Constants.PIKEMEN);
			}
			catch (ProdException e){
				//
			}
		}
		else if(castle.getLevel() < 3) {
			try{
				castle.launchProduction(Constants.AMELIORATION);
			}
			catch (ProdException e){
				//
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass()!=getClass()) {
			return false;
		}
		IAAdvanced d = (IAAdvanced)o;
		return getName() == d.getName();
	}
	
	/**
	 * Reset the AI
	 */
	public void reset() {
		this.nbCastle = 0;
		economicPhase = true;
		phaseDuration = 600;
		attackCounter = 0;
		target = null;
	}
}
