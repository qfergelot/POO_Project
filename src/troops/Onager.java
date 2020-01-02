package troops;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import kingdom.Castle;
import kingdom.Ost;

/**
 * Onager troop that correspond to the slow but strong troop of the game.
 */
public class Onager extends Troop {

	/**
	 * Constant which determines the count of rounds necessary to produce a Onager
	 */
	public static final int PRODUCTION_TIME = 1000;
	/**
	 * Constant which determines the count of florins necessary to produce a Onager
	 */
	public static final int PRODUCTION_COST = 1000;
	
	/**
	 * Onager constructor
	 * @param layer
	 * 			use for display. see Sprite
	 * @param image
	 * 			use for display. See Sprite
	 * @param pos_x
	 * 			gives the x coordinate of initial position of the Onager
	 * @param pos_y
	 * 			gives the y coordinate of initial position of the Onager
	 */
	public Onager(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 1, 10, pos_x, pos_y);
	}

	public void transfer(Castle target, Ost host) {
		target.addOnagre();
		host.getTroop().remove(this);
		this.getImageView().setImage(null);
	}
}
