package kingdom;

import troops.Knight;
import troops.Onager;
import troops.Pikemen;

/**
 * Class that represents a production
 * @author Moi
 *
 */
public class Production {
	private int unit;
	private int nbRounds;
	private int nbRoundsInit;
	
	/**
	 * Construct a production
	 * @param unit unit to produce
	 * @param castleLevel current level of the castle
	 */
	public Production(int unit, int castleLevel) {
		this.unit = unit;
		if(unit==Constants.PIKEMEN)
			nbRounds = Pikemen.PRODUCTION_TIME;
		else if(unit==Constants.KNIGHT)
			nbRounds = Knight.PRODUCTION_TIME;
		else if(unit==Constants.ONAGER)
			nbRounds = Onager.PRODUCTION_TIME;
		else
			nbRounds = 100+50*castleLevel;
		nbRoundsInit = nbRounds;
	}
	
	/**
	 * 
	 * @return current unit to be produced
	 */
	public int getUnit() {
		return unit;
	}
	
	/**
	 * 
	 * @return true if current production is an upgrade, else false
	 */
	public boolean isAmelioration() {
		return unit == Constants.AMELIORATION;
	}
	
	/**
	 * 
	 * @return number of rounds before production end 
	 */
	public int getNbRounds() {
		return nbRounds;
	}
	
	/**
	 * 
	 * @return number of initial rounds of the production
	 */
	public int getNbRoundsInit() {
		return nbRoundsInit;
	}
	
	/**
	 * Compute the percentage of progression of the production
	 * @return 
	 */
	public double pourcentage() {
		return 1.0-((double)nbRounds/(double)nbRoundsInit);
	}
	
	/**
	 * End of production, a troop must be produced
	 * @return true if production ended, else false
	 */
	public boolean finishedProduction() {
		return nbRounds == 0;
	}
	
	/**
	 * To execute at each end of turn
	 */
	public void finishRoundProduction() {
		nbRounds--;
	}
}
