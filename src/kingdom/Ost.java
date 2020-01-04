package kingdom;

import java.util.ArrayList;

import kingdom.Castle;
import kingdom.Duke;
import kingdom.Kingdom;
import troops.*;

/**
 * Class that represents an ost
 * @author Moi
 *
 */
public class Ost {
	
	private Duke duke;
	
	private Castle target;
	
	private ArrayList<Troop> troops;
	private boolean finishedAttack = false;
	
	/**
	 * Construct an ost
	 * @param duke Owner 
	 * @param target Target castle
	 */
	public Ost(Duke duke, Castle target) {
		this.duke = duke;
		this.target = target;
		troops = new ArrayList<Troop>();
	}
	
	/**
	 * Getter of the state of the attack
	 * @return true if attack is finished, else false
	 */
	public boolean finishedAttack() {
		return finishedAttack;
	}
	
	/**
	 * Process the turn of an ost
	 * @param kingdom Kingdom in which the ost appears
	 */
	public void roundOst(Kingdom kingdom) {
		for(int i=0; i<troops.size(); i++) {
			if(troops.get(i).inTarget()) {
				if(duke.equals(target.getDuke())) {
					troops.get(i).transfer(target, this);
				}
				else {
					finishedAttack = troops.get(i).attack(target);
					if(troops.get(i).isDie()) {
						troops.get(i).getImageView().setImage(null);
						troops.remove(i);
					}
					if(finishedAttack == true) {
						if(!target.getNeutral()) {
							target.getDuke().removeCastle();
						}
						target.setDuke(this.duke);
						target.setNeutral(false);
						target.setImageView(this.duke.getImgCastle());
						target.cancelProduction();
						
						this.duke.addCastle();						
					}
				}
			}
			else {
				troops.get(i).displacement(target, kingdom);
			}
		}
	}
	
	/**
	 * Add a unit to this ost
	 * @param t Unit to be added
	 */
	public void addTroop (Troop t) {
		troops.add(t);
	}

	/**
	 * getter for duke
	 * @return the duke owner of this ost
	 */
	public Duke getDuke() {
		return duke;
	}
	
	/**
	 * getter for target Castle
	 * @return the target Castle of this ost
	 */
	public Castle getTarget() {
		return target;
	}
	
	/**
	 * getter for troops
	 * @return the troops on this ost
	 */
	public ArrayList<Troop> getTroop(){
		return troops;
	}
	
	/**
	 * Remove this ost from the game
	 */
	public void delete() {
		for (int i = 0; i<troops.size(); i++)
			troops.get(i).delete();
		troops.clear();
	}
	
}
