package kingdom;

/**
 * Class that represents a shield
 * it can tank a certain amount of damage before being deactivated
 *
 */
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
	 * Construct a Shield
	 */
	public Shield() {
		this.health = Constants.LIFE_SHIELD;
	}
	
	/**
	 * remove a certain amount of health
	 * @param dmg Amount to be removed from health
	 */
	public void takeDamage(int dmg) {
		this.health -= dmg;
	}
	
	/**
	 * Get if the shield is broken
	 * @return true if the shield health is equal or under 0, else false
	 */
	public boolean isShieldBroken() {
		return this.health <= 0;
	}
}
