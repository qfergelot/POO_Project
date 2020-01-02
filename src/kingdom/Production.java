package kingdom;

import troops.Knight;
import troops.Onager;
import troops.Pikemen;

public class Production {
	private int unit;
	private int nbRounds;
	private int nbRoundsInit;
	
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
	
	public int getUnit() {
		return unit;
	}
	
	public boolean isAmelioration() {
		return unit == Constants.AMELIORATION;
	}
	
	public int getNbRounds() {
		return nbRounds;
	}
	
	public int getNbRoundsInit() {
		return nbRoundsInit;
	}
	
	public double pourcentage() {
		return 1.0-((double)nbRounds/(double)nbRoundsInit);
	}
	
	/*
	 * Fin de production, il faut produire l'unit
	 */
	public boolean finishedProduction() {
		return nbRounds == 0;
	}
	
	/*
	 * A executer à chaque fin de tour
	 */
	public void finishRoundProduction() {
		nbRounds--;
	}
}
