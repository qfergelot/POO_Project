package kingdom;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class IAAdvanced extends Duke {
	private Kingdom kingdom;
	
	private Castle target;
	
	private Random rdm = new Random();
	
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

		if(!castle.inProduction()) {
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
			else if(defenseScore < 8) {
				try{
					if(rdm.nextBoolean())
						castle.launchProduction(Constants.PIKEMEN);
					else
						castle.launchProduction(Constants.KNIGHT);
				}
				catch (ProdException e){
					try {
						castle.launchProduction(Constants.PIKEMEN);
					}
					catch (ProdException f) {
						//
					}
				}
			}
			else if(castle.getLevel() < Constants.LEVEL_MAX) {
				if(castle.getTreasure() < castle.getLevel()*1000 - 1000) {
					//if we are close to be able to upgrade castle we wait, else we produce an onager
					try {
						castle.launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						castle.launchProduction(Constants.AMELIORATION);
					}
					catch (ProdException e) {
						//
					}
				}
			}
			else {
				if(defenseScore > 9) {
					try {
						castle.launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						if(rdm.nextBoolean())
							castle.launchProduction(Constants.PIKEMEN);
						else
							castle.launchProduction(Constants.KNIGHT);
					}
					catch (ProdException e) {
						//
					}
				}
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
		target = null;
	}
}
