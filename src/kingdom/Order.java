package kingdom;

import javafx.scene.image.Image;
import kingdom.Ost;
import troops.*;

/**
 * Class that represents an order
 * An order is an ost in creation in the castle
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
	private double exit_x2;
	private double exit_y2;
	private double exit_x3;
	private double exit_y3;
	
	private int cptExit=0;
	
	private Image image_pikemen = new Image(getClass().getResource("/images/militar.png").toExternalForm(), 20, 20, true, true);
	private Image image_knight = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 20, 20, true, true);
	private Image image_onager = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 20, 20, false, true);


	/**
	 * 
	 * Construct an order
	 * @param target Target Castle
	 * @param nbPikemen Number of pikemen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param exit_x First position x of units
	 * @param exit_y Firts position y of units
	 * @param exit_x2 Second position x of units
	 * @param exit_y2 Second position y of units
	 * @param exit_x3 Third position x of units
	 * @param exit_y3 Third position y of units
	 */
	public Order(Castle target, int nbPikemen, int nbKnight, int nbOnager, double exit_x, double exit_y, double exit_x2, double exit_y2, double exit_x3, double exit_y3) {
		this.target = target;
		this.nbTroops = nbPikemen + nbKnight + nbOnager;
		this.nbPikemen = nbPikemen;
		this.nbKnight = nbKnight;
		this.nbOnager = nbOnager;
		this.exit_x = exit_x;
		this.exit_y = exit_y;
		this.exit_x2 = exit_x2;
		this.exit_y2 = exit_y2;
		this.exit_x3 = exit_x3;
		this.exit_y3 = exit_y3;
	}
	
	/**
	 * Get the number of units that is to be produced by this order
	 * @return Number of units
	 */
	public int getNbTroops() {
		return nbTroops;
	}
	
	/**
	 * Get the number of pikemen that is to be produced by this order
	 * @return number of pikemen
	 */
	public int getNbPikemen() {
		return nbPikemen;
	}
	
	/**
	 * Get the number of knight that is to be produced by this order
	 * @return number of knight
	 */
	public int getNbKnight() {
		return nbKnight;
	}
	
	/**
	 * Get the number of onager that is to be produced by this order
	 * @return number of onager
	 */
	public int getNbOnager() {
		return nbOnager;
	}
	
	/**
	 * Exit a pikemen
	 * @param ost Ost linked to this order
	 * @return true if the unit exited the castle, else false
	 */
	public boolean exitPikemen(Ost ost) {
		boolean isExit = false;
		if(isExitFree(ost,exit_x,exit_y)) {
			ost.addTroop(new Pikemen(target.getLayer(),image_pikemen,exit_x,exit_y));
			isExit = true;
		} 
		else if(isExitFree(ost,exit_x2,exit_y2)) {
			ost.addTroop(new Pikemen(target.getLayer(),image_pikemen,exit_x2,exit_y2));
			isExit = true;
		}
		else if(isExitFree(ost,exit_x3,exit_y3)) {
			ost.addTroop(new Pikemen(target.getLayer(),image_pikemen,exit_x3,exit_y3));
			isExit = true;
		}
		if(isExit) {
			nbPikemen--;
			nbTroops--;
			cptExit = (cptExit+1)%3;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Exit a knight
	 * @param ost Ost linked to this order
	 * @return true if the unit exited the castle, else false
	 */
	public boolean exitKnight(Ost ost) {
		boolean isExit = false;
		if(isExitFree(ost,exit_x,exit_y)) {
			ost.addTroop(new Knight(target.getLayer(),image_knight,exit_x,exit_y));
			isExit = true;
		}
		else if(isExitFree(ost,exit_x2,exit_y2)) {
			ost.addTroop(new Knight(target.getLayer(),image_knight,exit_x2,exit_y2));
			isExit = true;
		}
		else if(isExitFree(ost,exit_x3,exit_y3)) {
			ost.addTroop(new Knight(target.getLayer(),image_knight,exit_x3,exit_y3));
			isExit = true;
		}
		if(isExit) {
			nbKnight--;
			nbTroops--;
			cptExit = (cptExit+1)%3;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Exit a onager
	 * @param ost Ost linked to this order
	 * @return true if the unit exited the castle, else false
	 */
	public boolean exitOnager(Ost ost) {
		boolean isExit = false;
		if(isExitFree(ost,exit_x,exit_y)) {
			ost.addTroop(new Onager(target.getLayer(),image_onager,exit_x,exit_y));
			isExit = true;
		}
		else if(isExitFree(ost, exit_x2, exit_y2)) {
			ost.addTroop(new Onager(target.getLayer(),image_onager,exit_x2,exit_y2));
			isExit = true;
		}
		else if(isExitFree(ost, exit_x3, exit_y3)) {
				ost.addTroop(new Onager(target.getLayer(),image_onager,exit_x3,exit_y3));
				isExit = true;
		}
		if(isExit) {
			nbOnager--;
			nbTroops--;
			cptExit = (cptExit+1)%3;
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isExitFree(Ost ost, double x, double y) {
		Troop t = null;
		for(int i=0; i<ost.getTroop().size(); i++) {
			t = ost.getTroop().get(i);
			if(x+20 > t.getPos_x() && x < t.getPos_x()+t.getWidth() && y+20 > t.getPos_y() && y < t.getPos_y()+t.getHeight())
				return false;
		}
		return true;
	}
}
