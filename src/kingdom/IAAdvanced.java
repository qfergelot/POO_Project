package kingdom;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * 
 *Class managing the advanced IA
 *
 */
public class IAAdvanced extends Duke {
	private Kingdom kingdom;
	
	private Castle nemesis = null;
	
	private boolean economicPhase = true;
	private int phaseDuration = 1200;
	private long timerAttack = 0;
	
	Random rdm = new Random();
	
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
	 * Process a round producing or attacking or both for a castle of AI advanced
	 */
	public void roundIAAdvanced(Castle castle) {
		int defenseScore = castle.getNbPikemen()*Constants.LIFE_PIKEMEN+castle.getNbKnight()*Constants.LIFE_KNIGHT+castle.getNbOnager()*Constants.LIFE_ONAGER;
		Castle target = null;
		
		if(castle.isShielded())
			defenseScore += Constants.LIFE_SHIELD;
		
		if(phaseDuration-- < 1) {
			economicPhase = !economicPhase;
			if(economicPhase)
				phaseDuration = 1000;
			else
				phaseDuration = 500;
		}
		
		if(nemesis != null) {
			if(nemesis.getDuke().equals(castle.getDuke())) {
				nemesis = null;
				economicPhase = true;
				phaseDuration = 600;
			}
			else {
				target = nemesis;
				economicPhase = false;
			}
		}
		
		if(economicPhase) {
			processProduction(castle, defenseScore);
		} 
		else {
			for(int i=0; i<castle.getNbBarracks(); i++) {
				try{
					if(!castle.getBarracks().get(i).inProduction())
						castle.launchProduction(Constants.PIKEMEN);
				}
				catch (ProdException e){
					//
				}
			}
			processAttack(castle, target);
		}
		timerAttack++;
	}
	
	/**
	 * Launch an attack to the castle target or the AI nemesis
	 */
	private void processAttack(Castle castle, Castle target) {
		if(timerAttack > 60*5) {
			if(target == null)
				target = searchCloseCastle(castle);
			else if(!target.getNeutral()) {
				if(target.getDuke().equals(castle.getDuke())) {
					target = searchCloseCastle(castle);
				}
			}
			
			kingdom.createOrder(castle, target, castle.getNbPikemen()/3, castle.getNbKnight()/3, castle.getNbOnager()/3);
			timerAttack = 0;
		}
	}
	
	/**
	 * set the target to the closest castle
	 */
	private Castle searchCloseCastle(Castle castle) {
		Castle target = null;
		double distMin = 9999;
		for(int i=0; i < kingdom.getNbCastle(); i++) {
			if(kingdom.getCastle(i).getNeutral()) {
				if(castle.distance(kingdom.getCastle(i)) < distMin) {
					target = kingdom.getCastle(i);
					distMin = castle.distance(target);
				}
			}
			else if(!kingdom.getCastle(i).getDuke().equals(castle.getDuke())) {
				if(castle.distance(kingdom.getCastle(i)) < distMin) {
					target = kingdom.getCastle(i);
					distMin = castle.distance(target);
				}
			}
		}
		return target;
	}
	
	/**
	 * Launch a production or not with criteria of defenseProduction and random
	 * @param score a score to know what to produce
	 */
	private void processProduction(Castle castle, int score) {
		boolean freeProduction = false;
		for(int i=0; i<castle.getNbBarracks(); i++) {
			if(!castle.getBarracks().get(i).inProduction()) {
				freeProduction = true;
				break;
			}
		}
		
		if(freeProduction) {
			if(score < 8) {
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
			else if(score < 15) {
				try{
					if(castle.getTreasure() > Shield.PRODUCTION_COST && !castle.isShielded() && !castle.alreadyShieldProduction())
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
						if(castle.getTreasure() > Barrack.PRODUCTION_COST && !castle.alreadyBarrackProduction())
							castle.launchProduction(Constants.BARRACK);
						else if(score < 25)
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
				if(score > 30 && castle.getNbBarracks() < 5 && !castle.alreadyBarrackProduction()) {
					try {
						castle.launchProduction(Constants.BARRACK);
					}
					catch (ProdException e) {
						//
					}
				}
				else if(score > 30) {
					try {
						castle.launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						if(!castle.isShielded() && !castle.alreadyShieldProduction())
							castle.launchProduction(Constants.SHIELD);
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
	

	
	/**
	 * Setter for nemesis
	 * @param nemesis the new nemesis of this AI
	 */
	public void setNemesis(Castle nemesis) {
		this.nemesis = nemesis;
	}
	
	/**
	 * getter for nemesis
	 * @return return the nemesis of this AI
	 */
	public Castle getNemesis() {
		return nemesis;
	}
	
	/**
	 * getter for kingdom
	 * @return kingdom
	 */
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	/**
	 * Reset the AI
	 */
	public void reset() {
		this.nbCastle = 0;
		nemesis = null;
	}
}
