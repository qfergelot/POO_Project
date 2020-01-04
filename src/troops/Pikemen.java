package troops;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import kingdom.Ost;
import kingdom.Castle;

/**
 * Pikeman troop that correspond to the little troop of the game.
 */
public class Pikemen extends Troop {

	/**
	 * Constant which determines the count of rounds necessary to produce a pikemen
	 */
	public static final int PRODUCTION_TIME = 100;
	/**
	 * Constant which determines the count of florins necessary to produce a pikemen
	 */
	public static final int PRODUCTION_COST = 200;
	
	/**
	 * Pikemen constructor
	 * @param layer
	 * 			use for display. see Sprite
	 * @param image
	 * 			use for display. See Sprite
	 * @param pos_x
	 * 			gives the x coordinate of initial position of the Pikemen
	 * @param pos_y
	 * 			gives the y coordinate of initial position of the Pikemen
	 */
	public Pikemen(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 2, 1, pos_x, pos_y);
	}
	
	/**
	 * Transfer a pikeman from the ost to the target's army
	 * @param target 
	 * 			Target castle
	 * @param host
	 * 			Source ost
	 */
	public void transfer(Castle target, Ost host) {
		target.addPikemen();
		host.getTroop().remove(this);
		this.getImageView().setImage(null);
	}
	

}
