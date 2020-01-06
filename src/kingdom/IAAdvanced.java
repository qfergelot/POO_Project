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
		int defenseScore = castle.getNbPikemen()+castle.getNbKnight()*Constants.LIFE_KNIGHT+castle.getNbOnager()*Constants.LIFE_ONAGER;
		if(castle.isShielded())
			defenseScore += Constants.LIFE_SHIELD;

		
		
		processProduction(castle,defenseScore);
	}
	

	private void processAttack(Castle castle, int defenseScore) {
		if(target != null) {
			
		}
		
	}
	
	/**
	 * Launch a production or not with criteria of defenseProduction and random
	 * @param castle castle who process the production
	 * @param defenseScore a defenseScore to know what to produce
	 */
	private void processProduction(Castle castle, int defenseScore) {
		boolean freeProduction = false;
		for(int i=0; i<castle.getNbBarracks(); i++) {
			if(!castle.getBarracks().get(i).inProduction()) {
				freeProduction = true;
				break;
			}
		}
		
		if(freeProduction) {
			if(defenseScore < 8) {
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
			else if(defenseScore < 15) {
				try{
					if(castle.getTreasure() > Shield.PRODUCTION_COST && !castle.isShielded())
						castle.launchProduction(Constants.SHIELD);
					else if(rdm.nextBoolean())
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
						if(castle.getTreasure() > Barrack.PRODUCTION_COST)
							castle.launchProduction(Constants.BARRACK);
						else if(defenseScore < 25)
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
				if(defenseScore > 30 && castle.getNbBarracks() < 5) {
					try {
						castle.launchProduction(Constants.BARRACK);
					}
					catch (ProdException e) {
						//
					}
				}
				else if(defenseScore > 30) {
					try {
						castle.launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						if(!castle.isShielded())
							castle.launchProduction(Constants.PIKEMEN);
						else if(rdm.nextBoolean())
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
