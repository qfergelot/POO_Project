package kingdom;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class that represents a duke owner
 * @author Moi
 *
 */
public class Duke {
	private String name;
	protected int nbCastle = 0;
	private Color color;
	private Image imgCastle;
	
	/**
	 * Construct a duke 
	 * @param name Name of this duke
	 * @param color Color that is link to this duke
	 * @param imgCastle Image of the castle
	 */
	public Duke(String name, Color color, Image imgCastle) {
		this.name = name;
		this.color = color;
		this.imgCastle = imgCastle;
	}

	/**
	 * Getter of the name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter of the number of castle under this duke
	 * @return Number of castle
	 */
	public int getNbCastle() {
		return nbCastle;
	}
	
	/**
	 * Getter of the color linked to this duke
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Getter of the image of castle linked to this duke
	 * @return image of castle
	 */
	public Image getImgCastle() {
		return imgCastle;
	}
	
	/**
	 * Add a castle to this duke
	 */
	public void addCastle() {
		nbCastle++;
	}
	
	/**
	 * Remove of a castle from this duke
	 */
	public void removeCastle() {
		nbCastle--;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass()!=getClass()) {
			return false;
		}
		Duke d = (Duke)o;
		return this.name == d.name;
	}
	
	/**
	 * Reset this duke's number of castle 
	 */
	public void reset() {
		this.nbCastle = 0;
	}

}
