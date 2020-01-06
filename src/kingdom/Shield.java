package kingdom;

public class Shield {
	
	private int health;
	
	/**
	 * Constant which determines the count of rounds necessary to produce a knight
	 */
	public static final int PRODUCTION_TIME = 750;
	/**
	 * Constant which determines the count of florins necessary to produce a knight
	 */
	public static final int PRODUCTION_COST = 1200;
	
	/**
	 * 
	 */
	public Shield() {
		this.health = Constants.LIFE_SHIELD;
	}
	
	public void takeDamage(int dmg) {
		this.health -= dmg;
	}
	
	public boolean isShieldBroken() {
		return this.health <= 0;
	}
}
