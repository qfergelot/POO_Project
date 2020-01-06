package game;

import javafx.scene.text.Text;
import kingdom.Castle;
import kingdom.Duke;

/**
 * Singleton Class
 *
 *
 */
public final class UIsingleton {
	private static final UIsingleton instance = new UIsingleton();
	
	private Duke dukePlayer = null;
	private Castle castleSelection = null;
	private boolean toUpdateTroops = false;
	private boolean toUpdateShield = false;
	
	private boolean pause = false;
	
	private Text errorLabelProduction;
	
	/**
	 * Construct the Singleton
	 */
	private UIsingleton() {
		//
	}
	
	/**
	 * Get the current instance of this class
	 * @return current instance
	 */
	public static UIsingleton getUIsingleton() {
		return instance;
	}
	
	/**
	 * Get the current castle selected by the player
	 * @return castle selected
	 */
	public Castle getCastleSelection() {
		return castleSelection;
	}
	
	/**
	 * set the current selection
	 * @param c castle to be set as currently selected
	 */
	public void setCastleSelection(Castle c) {
		castleSelection = c;
	}
	
	/**
	 * Getter
	 * @return true if display of troops need to be updated, else false
	 */
	public boolean toUpdateTroops() {
		return toUpdateTroops;
	}
	
	/**
	 * Setter
	 * @param toUpdateTroops true if display of troops need to be updated, else false
	 */
	public void setToUpdateTroops(boolean toUpdateTroops) {
		this.toUpdateTroops = toUpdateTroops;
	}
	

	/**
	 * get the real player
	 * @return duke of the player
	 */
	public Duke getDukePlayer() {
		return dukePlayer;
	}
	
	/**
	 * set the real player
	 * @param dukePlayer duke of the player
	 */
	public void setDukePlayer(Duke dukePlayer) {
		this.dukePlayer = dukePlayer;
	}
	
	/**
	 * set the pauseTrigger affecting the pause
	 */
	public void setPause() {
		pause = !pause;
	}
	
	/**
	 * 
	 * @return true if the game can be paused, else false
	 */
	public boolean getPause() {
		return pause;
	}
	
	/**
	 * 
	 * @param errorLabelProduction error message
	 */
	public void setErrorLabelProduction(Text errorLabelProduction) {
		this.errorLabelProduction = errorLabelProduction;
	}
	
	/**
	 * Display a message ingame
	 * @param message Error message
	 */
	public void setErrorMessageProduction(String message) {
		errorLabelProduction.setText(message);
	}
	
}
