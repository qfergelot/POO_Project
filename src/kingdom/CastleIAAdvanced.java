package kingdom;

import game.Popup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class CastleIAAdvanced extends Castle {
	private Kingdom kingdom;
	
	private boolean economicPhase = true;
	private Castle target = null;
	
	private int phaseDuration = 600;
	private long timerAttack = 0;
	
	/**
	 * Construct a advanced ai castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param duke Owner ( @see Duke)
	 * @param treasure Treasurey
	 * @param nbPikemen Number of spearmen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public CastleIAAdvanced(Pane layer, Image image, Duke duke, double treasure, int nbPikemen, int nbKnight,
			int nbOnager, double x, double y, Popup popupOst){
		super(layer, image, duke, treasure, nbPikemen, nbKnight, nbOnager, x, y, popupOst);
		IAAdvanced d = (IAAdvanced)duke;
		kingdom = d.getKingdom();
	}
	
	/**
	 * Function called for each turn
	 * Process the advance of production, exit units and treasury
	 */
	@Override
	public void finishRoundCastle() {
		treasure += level;
		if(inProduction()) {
			for (int i = 0; i<barracks.size(); i++) {
				Barrack b = barracks.get(i);
				if (b.inProduction()) {
					b.getCurrentProduction().finishRoundProduction();
					if(b.getCurrentProduction().isFinishedProduction()) {
						finishProduction(b.getCurrentProduction());
						b.removeCurrentProduction();
					}
				}
			}
		}
		if(order()) {
			removeTroopsOrder();
		}
		roundIAAdvanced();
	}
	
	/**
	 * Process a round producing or attacking or both for a castle of AI advanced
	 */
	private void roundIAAdvanced() {
		int defenseScore = nbPikemen*Constants.LIFE_PIKEMEN+nbKnight*Constants.LIFE_KNIGHT+nbOnager*Constants.LIFE_ONAGER;
		int offensiveScore = nbPikemen+nbKnight*5+nbOnager*5;
		
		if(isShielded())
			defenseScore += Constants.LIFE_SHIELD;
		IAAdvanced d = (IAAdvanced)duke;
		
		if(phaseDuration-- < 1) {
			economicPhase = !economicPhase;
			phaseDuration = 600;
		}
		
		if(d.getNemesis() != null) {
			if(d.getNemesis().getDuke().equals(this.getDuke())) {
				d.setNemesis(null);
				economicPhase = true;
				phaseDuration = 600;
			}
			else {
				target = d.getNemesis();
				economicPhase = false;
			}
		}
		
		if(economicPhase) {
			processProduction(defenseScore);
		} 
		else {
			for(int i=0; i<getNbBarracks(); i++) {
				try{
					if(!barracks.get(i).inProduction())
						launchProduction(Constants.PIKEMEN);
				}
				catch (ProdException e){
					//
				}
			}
			processAttack();
		}
		timerAttack++;
	}

	/**
	 * Launch an attack to the castle target or the AI nemesis
	 */
	private void processAttack() {
		if(timerAttack > 60*5) {
			if(target == null)
				searchCloseCastle();
			else if(!target.getNeutral()) {
				if(target.getDuke().equals(duke)) {
					searchCloseCastle();
				}
			}
			
			kingdom.createOrder(this, target, nbPikemen/3, nbKnight/3, nbOnager/3);
			timerAttack = 0;
		}
	}
	
	private void searchCloseCastle() {
		double distMin = 9999;
		for(int i=0; i < kingdom.getNbCastle(); i++) {
			if(kingdom.getCastle(i).getNeutral()) {
				if(distance(kingdom.getCastle(i)) < distMin) {
					target = kingdom.getCastle(i);
					distMin = distance(target);
				}
			}
			else if(!kingdom.getCastle(i).getDuke().equals(duke)) {
				if(distance(kingdom.getCastle(i)) < distMin) {
					target = kingdom.getCastle(i);
					distMin = distance(target);
				}
			}
		}
	}
	
	/**
	 * Launch a production or not with criteria of defenseProduction and random
	 * @param score a score to know what to produce
	 */
	private void processProduction(int score) {
		boolean freeProduction = false;
		for(int i=0; i<getNbBarracks(); i++) {
			if(!getBarracks().get(i).inProduction()) {
				freeProduction = true;
				break;
			}
		}
		
		if(freeProduction) {
			if(score < 8) {
				try{
					launchProduction(Constants.PIKEMEN);
				}
				catch (ProdException e){
					//
				}
			}
			else if(getLevel() < 3) {
				try{
					launchProduction(Constants.AMELIORATION);
				}
				catch (ProdException e){
					//
				}
			}
			else if(score < 15) {
				try{
					if(getTreasure() > Shield.PRODUCTION_COST && !isShielded() && !alreadyShieldProduction())
						launchProduction(Constants.SHIELD);
					else if(rdm.nextBoolean())
						launchProduction(Constants.PIKEMEN);
					else
						launchProduction(Constants.KNIGHT);
				}
				catch (ProdException e){
					try {
						launchProduction(Constants.PIKEMEN);
					}
					catch (ProdException f) {
						//
					}
				}
			}
			else if(getLevel() < Constants.LEVEL_MAX) {
				if(getTreasure() < getLevel()*1000 - 1000) {
					//if we are close to be able to upgrade castle we wait, else we produce an onager
					try {
						if(getTreasure() > Barrack.PRODUCTION_COST && !alreadyBarrackProduction())
							launchProduction(Constants.BARRACK);
						else if(score < 25)
							launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						launchProduction(Constants.AMELIORATION);
					}
					catch (ProdException e) {
						//
					}
				}
			}
			else {
				if(score > 30 && getNbBarracks() < 5 && !alreadyBarrackProduction()) {
					try {
						launchProduction(Constants.BARRACK);
					}
					catch (ProdException e) {
						//
					}
				}
				else if(score > 30) {
					try {
						launchProduction(Constants.ONAGER);
					}
					catch (ProdException e) {
						//
					}
				}
				else {
					try {
						if(!isShielded() && !alreadyShieldProduction())
							launchProduction(Constants.SHIELD);
						else if(rdm.nextBoolean())
							launchProduction(Constants.PIKEMEN);
						else
							launchProduction(Constants.KNIGHT);
					}
					catch (ProdException e) {
						//
					}
				}
			}
		}
	}
}
