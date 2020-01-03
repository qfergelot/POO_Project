package game;

import javafx.scene.text.Text;
import kingdom.Castle;
import kingdom.Duke;

/**
 * Singleton Class
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
	 * get the real player
	 * @return duke of the player
	 */
	public Duke getDukePlayer() {
		return dukePlayer;
	}
	
	/**
	 * set the real player
	 * @param dukePlayer
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
