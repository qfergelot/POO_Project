package troops;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import kingdom.Ost;
import kingdom.Castle;

/**
 * Knight troop that correspond to the medium and fast troop of the game.
 */
public class Knight extends Troop {
	
	/**
	 * Constant which determines the count of rounds necessary to produce a knight
	 */
	public static final int PRODUCTION_TIME = 500;
	/**
	 * Constant which determines the count of florins necessary to produce a knight
	 */
	public static final int PRODUCTION_COST = 500;
	
	/**
	 * Knight constructor
	 * @param layer
	 * 			use for display. see Sprite
	 * @param image
	 * 			use for display. See Sprite
	 * @param pos_x
	 * 			gives the x coordinate of initial position of the Knight
	 * @param pos_y
	 * 			gives the y coordinate of initial position of the Knight
	 */
	public Knight(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 6, 5, pos_x, pos_y);
	}
	
	public void transfer(Castle target, Ost host) {
		target.addKnight();
		host.getTroop().remove(this);
		this.getImageView().setImage(null);
	}


}
