package game;

import javafx.scene.text.Text;
import royaume.Chateau;
import royaume.Duc;

/**
 * 
 * @author Moi
 *
 */
public final class UIsingleton {
	private static final UIsingleton instance = new UIsingleton();
	
	private Duc ducJoueur = null;
	private Chateau chateauSelection = null;
	private boolean toUpdateTroupes = false;
	
	private boolean pause = false;
	
	private Text labelErreurProduction;
	
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
	public Chateau getChateauSelection() {
		return chateauSelection;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setChateauSelection(Chateau c) {
		chateauSelection = c;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean toUpdateTroupes() {
		return toUpdateTroupes;
	}
	
	/**
	 * 
	 * @param toUpdateTroupes
	 */
	public void setToUpdateTroupes(boolean toUpdateTroupes) {
		this.toUpdateTroupes = toUpdateTroupes;
	}
	
	/**
	 * 
	 * @return
	 */
	public Duc getDucJoueur() {
		return ducJoueur;
	}
	
	/**
	 * 
	 * @param ducJoueur
	 */
	public void setDucJoueur(Duc ducJoueur) {
		this.ducJoueur = ducJoueur;
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
	 * @param labelErreurProduction
	 */
	public void setLabelErreurProduction(Text labelErreurProduction) {
		this.labelErreurProduction = labelErreurProduction;
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setMessageErreurProduction(String message) {
		labelErreurProduction.setText(message);
	}
	
}
