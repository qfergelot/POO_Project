package game;

import royaume.Chateau;

import javafx.scene.text.Text;

public final class UIsingleton {
	private static final UIsingleton instance = new UIsingleton();
	
	private Chateau chateauSelection = null;
	private boolean toUpdateTroupes = false;
	
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
	
}
