package kingdom;

import java.util.ArrayList;

/**
 * Class that represents a barrack
 * Each barrack allows the player to produce units on a different queue
 * A barrack is basically an array list of Production @see Production 
 */
public class Barrack {
	
	/**
	 * Constant which determines the count of rounds necessary to produce a barrack
	 */
	public static final int PRODUCTION_TIME = 1200;
	/**
	 * Constant which determines the count of florins necessary to produce a barrack
	 */
	public static final int PRODUCTION_COST = 1500;
	
	private ArrayList<Production> productions;
	
	/**
	 * Construct a barrack
	 */
	Barrack(){
		productions = new ArrayList<Production>();
	}
	
	/**
	 * 
	 * @return true if a barrack is in production, else false
	 */
	public boolean isBarrackProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.BARRACK) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if an upgrade is in production, else false
	 */
	public boolean isUpgradeProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.AMELIORATION) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true if a shield is in production, else false
	 */
	public boolean isShieldProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.SHIELD) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * clear the array of production
	 */
	public void clear() {
		productions.clear();
	}
	
	/**
	 * 
	 * @return the first production of the array
	 */
	public Production getCurrentProduction() {
		return productions.get(0);
	}
	
	/**
	 * remove the first production of the array
	 */
	public void removeCurrentProduction() {
		productions.remove(0);
	}
	
	/**
	 * Get of a specified production
	 * @param index index at which the production is
	 * @return the ith production in the array
	 */
	public Production getProduction(int index) {
		return productions.get(index);
	}
	
	/**
	 * Add a new production
	 * @param prod production to be added
	 */
	public void addProduction(Production prod){
		productions.add(prod);
	}
	
	/**
	 * Get the number of production in the queue
	 * @return Number of production in the queue
	 */
	public int getSizeofProd() {
		return productions.size();
	}
	
	/**
	 * 
	 * @return true if this barrack is in production, else false
	 */
	public boolean inProduction() {
		return !productions.isEmpty();
	}
}
