package kingdom;

import java.util.ArrayList;

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
	
	Barrack(){
		productions = new ArrayList<Production>();
	}
	
	public boolean isBarrackProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.BARRACK) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isUpgradeProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.AMELIORATION) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isShieldProd() {
		for (int i = 0; i<productions.size(); i++) {
			if (productions.get(i).getUnit() == Constants.SHIELD) {
				return true;
			}
		}
		return false;
	}
	
	
	public void clear() {
		productions.clear();
	}
	
	public Production getCurrentProduction() {
		return productions.get(0);
	}
	
	public void removeCurrentProduction() {
		productions.remove(0);
	}
	
	public Production getProduction(int index) {
		return productions.get(index);
	}
	
	public void addProduction(Production prod){
		productions.add(prod);
	}
	
	public int getSizeofProd() {
		return productions.size();
	}
	
	public boolean inProduction() {
		return !productions.isEmpty();
	}
}
