package game;

import javafx.scene.text.Text;
import royaume.Chateau;
import royaume.Duc;

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
	
	public static UIsingleton getUIsingleton() {
		return instance;
	}
	
	public Chateau getChateauSelection() {
		return chateauSelection;
	}
	
	public void setChateauSelection(Chateau c) {
		chateauSelection = c;
	}
	
	public boolean toUpdateTroupes() {
		return toUpdateTroupes;
	}
	
	public void setToUpdateTroupes(boolean toUpdateTroupes) {
		this.toUpdateTroupes = toUpdateTroupes;
	}
	
	public Duc getDucJoueur() {
		return ducJoueur;
	}
	
	public void setDucJoueur(Duc ducJoueur) {
		this.ducJoueur = ducJoueur;
	}
	
	public void setPause() {
		pause = !pause;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void setLabelErreurProduction(Text labelErreurProduction) {
		this.labelErreurProduction = labelErreurProduction;
	}
	
	public void setMessageErreurProduction(String message) {
		labelErreurProduction.setText(message);
	}
	
}
