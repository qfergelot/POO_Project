package troops;

import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import kingdom.Ost;

import java.util.Random;

import kingdom.Castle;
import kingdom.Constants;
import kingdom.Kingdom;
/**
 * Troop is the abstract class that represents all the units of the game
 * The Troop is caracterised by
 * <ul>
 * <li>A speed that is the account of pixel traversable per frame</li>
 * <li>Damages, the troop dies when he have no more damages.
 * Damages are decremented every frame when the troop remove one life point to other troop in a castle.</li>
 * </ul>
 * Troop extends class Sprite to manage display.
 */
public abstract class Troop extends Sprite{

	private int speed;
	private int damage;
	private boolean inTarget = false;
	
	private boolean contourning = false;
	private int dirContourning;
	
	/**
	 * Troop constructor
	 * @param layer
	 * 			Use for display. @See Sprite
	 * @param img
	 * 			Use for display. @See Sprite
	 * @param speed
	 * 			The speed of the Troop that can't be modified
	 * @param damage
	 * 			Damages that are decremented at every damage given
	 * @param pos_x
	 * 			The x position in pixels
	 * @param pos_y
	 * 			The y position in pixels
	 */
	public Troop(Pane layer, Image img, int speed, int damage, double pos_x, double pos_y) {
		super(layer, img, pos_x, pos_y);

		this.speed = speed;
		this.damage = damage;
	}
	
	/**
	 * Distance with the target
	 * @param target
	 * 			The castle whose distance we want.
	 * @return The distance in pixels between troop and target castle.
	 */
	public double distance(Castle target) {
		double x = pos_x + getWidth()/2;
		double y = pos_y + getHeight()/2;
		double cx = target.getPos_x() + target.getWidth()/2;
		double cy = target.getPos_y() + target.getHeight()/2;
		return Math.sqrt((cx-x)*(cx-x)+(cy-y)*(cy-y));
	}
	
	/**
	 * Distance between (x,y) coordinates and target
	 * @param x
	 * 			The x position in pixels
	 * @param y
	 * 			The y position in pixels
	 * @param target
	 * 			The castle whose distance we want.
	 * @return the Distance in pixels between (x,y) coordinates and target castle.
	 */
	public double distance(double x, double y, Castle target) {
		double dx = x + getWidth()/2;
		double dy = y + getHeight()/2;
		double cx = target.getPos_x() + target.getWidth()/2;
		double cy = target.getPos_y() + target.getHeight()/2;
		return Math.sqrt((cx-dx)*(cx-dx)+(cy-dy)*(cy-dy));
	}
	
	/**
	 * move the troop to the target.
 	 * @param target
	 * 			target in case of collision to recalculate direction.
	 * @param kingdom
	 * 			the kingdom to get all the castles and manage collisions.
	 */
	public void displacement(Castle target, Kingdom kingdom) {
		int v = getSpeed();
		while(v > 0) {
			double angle = Math.atan2(target.getPos_y()-getPos_y(),target.getPos_x()-getPos_x())/Math.PI;
			move(angle, kingdom, target);
			if(inTarget)
				break;
			v--;
		}
		getImageView().relocate(getPos_x(), getPos_y());
	}
	
	/**
	 * moving of one pixel in a direction from an angle to the target.
	 * @param angle
	 * 			Angle between the troop and the direction that allows to move in 8 axes rather than only up,down,left and right.
	 * @param kingdom
	 * 			the kingdom to get all the castles and manage collisions.
	 * @param target
	 * 			target in case of collision to recalculate direction.
	 */
    private void move(double angle, Kingdom kingdom, Castle target) {
    	if(contourning) {
    		alternativeMove(angle, kingdom, target);
    	}
    	else {
	    	double new_x = pos_x, new_y = pos_y;
			if((angle > 0.875) || (angle <= -0.875)) {
				new_x--;
			}else if(angle > 0.625) {
				new_x-=0.7;
				new_y+=0.7;
			}else if(angle > 0.375) {
				new_y++;
			}else if(angle > 0.125) {
				new_x+=0.7;
				new_y+=0.7;
			}else if(angle > -0.125) {
				new_x++;
			}else if(angle > -0.375) {
				new_x+=0.7;
				new_y-=0.7;
			}else if(angle > -0.625) {
				new_y--;
			}else if(angle > -0.875) {
				new_x-=0.7;
				new_y-=0.7;
			}
			if(!collision(new_x, new_y, kingdom, target)) {
				pos_x = new_x;
				pos_y = new_y;
				contourning = false;
			}
			else {
				alternativeMove(angle, kingdom, target);
			}
    	}
	}
    
    /**
     * Same function as move but takes 4 direction axes to potentially avoid a castle.
	 * @param angle
	 * 			Angle between the troop and the direction.
	 * @param kingdom
	 * 			the kingdom to get all the castles and manage collisions.
	 * @param target
	 * 			target in case of collision to recalculate direction.
     */
    private void alternativeMove(double angle, Kingdom kingdom, Castle target) {
    	double new_x = pos_x, new_y = pos_y;
    	if(angle > 0.75 || angle <= -0.75)
    		new_x--;
    	else if(angle > 0.25)
    		new_y++;
    	else if(angle > -0.25)
    		new_x++;
    	else
    		new_y--;
    	if(!collision(new_x, new_y, kingdom, target)) {
			pos_x = new_x;
			pos_y = new_y;
			contourning = false;
		}
    	else {
    		if(contourning) {
    			getAround();
    		}
    		else {
	    		if(new_x != pos_x) {
	    			if(distance(pos_x, pos_y-1, target) < distance(pos_x, pos_y+1, target))
	    				dirContourning = Constants.UP;
	    			else
	    				dirContourning = Constants.DOWN;
	    		}
	    		else {
	    			if(distance(pos_x-1, pos_y, target) < distance(pos_x+1, pos_y, target))
	    				dirContourning = Constants.LEFT;
	    			else
	    				dirContourning = Constants.RIGHT;
    			}
	    		contourning = true;
	    		getAround();
    		}
    	}
    }
    
    /**
     * If the troop needs to get around the Castle, function called by alternativeMove which follow an direction to get around the castle.
     */
    private void getAround() {
    	if(dirContourning == Constants.UP)
    		pos_y--;
    	else if(dirContourning == Constants.DOWN)
    		pos_y++;
    	else if(dirContourning == Constants.LEFT)
    		pos_x--;
    	else
    		pos_x++;
    }
    
    /**
     * Setter of inTarget
     */
    public void setInTarget() {
    	inTarget = true;
    }
    
    /**
     * Test if the troop is on the target
     * @return True if the troop is front of the target, else if not
     */
    public boolean inTarget() {
    	return inTarget;
    }
    
    /**
     * Transfer the troop to the target Castle.
     * @param target
     * 			The Castle where the troop have to be transfer
     * @param host
     * 			L'Ost where is the troop.
     */
	public abstract void transfer(Castle target, Ost host);
	
	/**
	 * Attack a Castle removing a life point to a type of troop drawn randomly
	 * @param c
	 * 			Castle target of the attack
	 * @return true if the attack is finished (all the troops of the castle are defeated), false if not
	 */
	public boolean attack(Castle c) {
		Random rdm = new Random();
		int rand;
		if(c.leftPikemen() && c.leftKnight() && c.leftOnager()) {
			rand = rdm.nextInt(3);
			if(rand == 0) {
				attackPikemen(c);
			}
			else if(rand == 1){
				attackKnight(c);
			}
			else {
				attackOnager(c);
			}
		}
		else if (c.leftPikemen()) {
			if(c.leftKnight()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attackPikemen(c);
				}
				else if(rand == 1){
					attackKnight(c);
				}
			}
			else if(c.leftOnager()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attackPikemen(c);
				}
				else if(rand == 1){
					attackOnager(c);
				}
			}
			else {
				attackPikemen(c);
			}
		}
		else if(c.leftKnight()) {
			if(c.leftOnager()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attackKnight(c);
				}
				else if(rand == 1){
					attackOnager(c);
				}
			}
			else {
				attackKnight(c);
			}
		}
		else {
			attackOnager(c);
		}
		return c.noTroop();
	}
	
	/**
	 * Attack a pikeman of the target Castle
	 * @param c
	 * 			Castle target of the attack
	 */
	private void attackPikemen(Castle c) {
		c.receiveAttackPikemen();
		damage--;
	}
	
	/**
	 * Attack a knight of the target Castle
	 * @param c
	 * 			Castle target of the attack
	 */
	private void attackKnight(Castle c) {
		c.receiveAttackKnight();
		damage--;
	}
	
	/**
	 * Attack an onagre of the target Castle
	 * @param c
	 * 			Castle target of the attack
	 */
	private void attackOnager(Castle c) {
		c.receiveAttackOnager();
		damage--;
	}
	
	/**
	 * getter of speed
	 * @return the speed of the troop
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * test if the troop is dead (no more damage) or not.
	 * @return true if the troop is dead, false if not.
	 */
	public boolean isDie() {
		return damage < 1;
	}
	
	/**
	 * function that verify if the (x,y) point are in the Castle c.
	 * @param x
	 * 			The x position in pixels
	 * @param y
	 * 			The y position in pixels
	 * @param c
	 * 			Castle of test.
	 * @return true if (x,y) point are in the castle, false if not.
	 */
	public boolean isIn(double x, double y, Castle c) {
		return ((x + getWidth()) > c.getPos_x()) && (x < (c.getPos_x() + c.getWidth()))
				&& ((y + getHeight()) > c.getPos_y()) && (y < (c.getPos_y() + c.getHeight()));
	}
	
	/**
	 * Test if the (x,y) point is in a castle of the kingdom and then put inTarget at true if there is a collision with the target.
	 * @param x
	 * 			The x position in pixels
	 * @param y
	 * 			The y position in pixels
	 * @param kingdom
	 * 			kingdom to get all castles.
	 * @param target
	 * 			Castle target.
	 * @return true if there is an collision, false if not.
	 */
	public boolean collision(double x, double y, Kingdom kingdom, Castle target) {
		for(int i=0; i<kingdom.getNbCastle(); i++) {
			if(isIn(x, y, kingdom.getCastle(i))) {
				if(kingdom.getCastle(i) == target)
					inTarget = true;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Remove display of this troop
	 */
	public void delete() {
		this.removeFromLayer();
	}
	
}
