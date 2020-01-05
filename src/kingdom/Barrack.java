package kingdom;

import java.util.ArrayList;

public class Barrack {
	/**
	 * Constant which determines the count of rounds necessary to produce a barrack
	 */
	public static final int PRODUCTION_TIME = 500;
	/**
	 * Constant which determines the count of florins necessary to produce a barrack
	 */
	public static final int PRODUCTION_COST = 100;
	
	private ArrayList<Production> productions;
	
	Barrack(){
		productions = new ArrayList<Production>();
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
