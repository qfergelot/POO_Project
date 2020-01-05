package kingdom;

import troops.Knight;
import troops.Onager;
import troops.Pikemen;

/**
 * Class that represents a production
 * A production is what happens when you create a unit or want an upgrade of the castle
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
		else if (unit == Constants.BARRACK)
			nbRounds = Barrack.PRODUCTION_TIME;
		else
			nbRounds = 100+50*castleLevel;
		nbRoundsInit = nbRounds;
	}
	
	/**
	 * Get the unit that is produced
	 * @return current unit to be produced
	 */
	public int getUnit() {
		return unit;
	}
	
	/**
	 * Get if this is an upgrade
	 * @return true if current production is an upgrade, else false
	 */
	public boolean isAmelioration() {
		return unit == Constants.AMELIORATION;
	}
	
	/**
	 * Get if this is a barrack
	 * @return true if current production is a barrack, else false
	 */
	public boolean isBarrack() {
		return unit == Constants.BARRACK;
	}
	
	/**
	 * Compute the percentage of progression of the production
	 * @return Percentage of completion
	 */
	public double pourcentage() {
		return 1.0-((double)nbRounds/(double)nbRoundsInit);
	}
	
	/**
	 * End of production, a troop must be produced
	 * @return true if production ended, else false
	 */
	public boolean isFinishedProduction() {
		return nbRounds == 0;
	}
	
	/**
	 * To execute at each end of turn to advance the production
	 */
	public void finishRoundProduction() {
		nbRounds--;
	}
}
