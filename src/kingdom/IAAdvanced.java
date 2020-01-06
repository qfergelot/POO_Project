package kingdom;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class IAAdvanced extends Duke {
	private Kingdom kingdom;
	
	private Castle nemesis = null;
	
	/**
	 * Construct a Advanced AI
	 * @param name Name of the AI 
	 * @param couleur Color
	 * @param image Image of the AI's castle
	 * @param kingdom Kingdom in which the AI operates
	 */
	public IAAdvanced(String name, Color couleur, Image image, Kingdom kingdom) {
		super(name, couleur, image);
		this.kingdom = kingdom;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass()!=getClass()) {
			return false;
		}
		IAAdvanced d = (IAAdvanced)o;
		return getName() == d.getName();
	}
	
	/**
	 * Setter for nemesis
	 * @param nemesis the new nemesis of this AI
	 */
	public void setNemesis(Castle nemesis) {
		this.nemesis = nemesis;
	}
	
	/**
	 * getter for nemesis
	 * @return return the nemesis of this AI
	 */
	public Castle getNemesis() {
		return nemesis;
	}
	
	/**
	 * getter for kingdom
	 * @return kingdom
	 */
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	/**
	 * Reset the AI
	 */
	public void reset() {
		this.nbCastle = 0;
		nemesis = null;
	}
}
