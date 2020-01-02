package kingdom;

import javafx.scene.image.Image;
import kingdom.Ost;
import troops.*;

/**
 * Class that represents an order
 * @author Moi
 *
 */
public class Order {
	private Castle target;
	private int nbTroops;
	
	private int nbPikemen;
	private int nbKnight;
	private int nbOnager;
	
	private double exit_x;
	private double exit_y;
	
	private Image image_pikemen = new Image(getClass().getResource("/images/militar.png").toExternalForm(), 20, 20, true, true);
	private Image image_knight = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 20, 20, true, true);
	private Image image_onager = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 20, 20, false, true);

	/**
	 * Construct an order
	 * @param target Target Castle
	 * @param nbPikemen Number of pikemen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param exit_x First position x of units
	 * @param exit_y Firts position y of units
	 */
	public Order(Castle target, int nbPikemen, int nbKnight, int nbOnager, double exit_x, double exit_y) {
		this.target = target;
		this.nbTroops = nbPikemen + nbKnight + nbOnager;
		this.nbPikemen = nbPikemen;
		this.nbKnight = nbKnight;
		this.nbOnager = nbOnager;
		this.exit_x = exit_x;
		this.exit_y = exit_y;
	}
	
	public int getNbTroops() {
		return nbTroops;
	}
	
	public int getNbPikemen() {
		return nbPikemen;
	}
	
	public int getNbKnight() {
		return nbKnight;
	}
	
	public int getNbOnager() {
		return nbOnager;
	}
	
	/**
	 * Exit a pikemen
	 * @param ost Ost linked to this order
	 */
	public void exitPikemen(Ost ost) {
		nbPikemen--;
		nbTroops--;
		ost.addTroop(new Pikemen(target.getLayer(),image_pikemen,exit_x,exit_y));
	}
	
	/**
	 * Exit a knight
	 * @param ost Ost linked to this order
	 */
	public void exitKnight(Ost ost) {
		nbKnight--;
		nbTroops--;
		ost.addTroop(new Knight(target.getLayer(),image_knight,exit_x,exit_y));
	}
	
	/**
	 * Exit a onager
	 * @param ost Ost linked to this order
	 */
	public void exitOnager(Ost ost) {
		nbOnager--;
		nbTroops--;
		ost.addTroop(new Onager(target.getLayer(),image_onager,exit_x,exit_y));
	}
}
