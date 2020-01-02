package game;

import javafx.scene.text.Text;
import kingdom.Castle;
import kingdom.Duke;

/**
 * 
 * @author Moi
 *
 */
public final class UIsingleton {
	private static final UIsingleton instance = new UIsingleton();
	
	private Duke dukePlayer = null;
	private Castle castleSelection = null;
	private boolean toUpdateTroops = false;
	
	private boolean pause = false;
	
	private Text errorLabelProduction;
	
	private UIsingleton() {
		//
	}
	
	/**
	 * 
	 * @return
	 */
	public static UIsingleton getUIsingleton() {
		return instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public Castle getCastleSelection() {
		return castleSelection;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setCastleSelection(Castle c) {
		castleSelection = c;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean toUpdateTroops() {
		return toUpdateTroops;
	}
	
	/**
	 * 
	 * @param toUpdateTroops
	 */
	public void setToUpdateTroops(boolean toUpdateTroops) {
		this.toUpdateTroops = toUpdateTroops;
	}
	
	/**
	 * 
	 * @return
	 */
	public Duke getDukePlayer() {
		return dukePlayer;
	}
	
	/**
	 * 
	 * @param dukePlayer
	 */
	public void setDukePlayer(Duke dukePlayer) {
		this.dukePlayer = dukePlayer;
	}
	
	/**
	 * 
	 */
	public void setPause() {
		pause = !pause;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getPause() {
		return pause;
	}
	
	/**
	 * 
	 * @param labelErrorProduction
	 */
	public void setErrorLabelProduction(Text errorLabelProduction) {
		this.errorLabelProduction = errorLabelProduction;
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setErrorMessageProduction(String message) {
		errorLabelProduction.setText(message);
	}
	
}
