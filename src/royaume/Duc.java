package royaume;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class that represents a duke owner
 * @author Moi
 *
 */
public class Duc {
	private String nom;
	private int nbChateaux = 0;
	private Color couleur;
	private Image imgChateau;
	
	/**
	 * Construct a duke 
	 * @param nom Name of this duke
	 * @param couleur Color that is link to this duke
	 * @param imgChateau Image of the castle
	 */
	public Duc(String nom, Color couleur, Image imgChateau) {
		this.nom = nom;
		this.couleur = couleur;
		this.imgChateau = imgChateau;
	}

	/**
	 * Getter of the name
	 * @return name
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Getter of the number of castle under this duke
	 * @return Number of castle
	 */
	public int getNbChateaux() {
		return nbChateaux;
	}
	
	/**
	 * Getter of the color linked to this duke
	 * @return color
	 */
	public Color getCouleur() {
		return couleur;
	}
	
	/**
	 * Getter of the image of castle linked to this duke
	 * @return image of castle
	 */
	public Image getImgChateau() {
		return imgChateau;
	}
	
	/**
	 * Add a castle to this duke
	 */
	public void ajouterChateau() {
		nbChateaux++;
	}
	
	/**
	 * Remove of a castle from this duke
	 */
	public void retirerChateau() {
		nbChateaux--;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass()!=getClass()) {
			return false;
		}
		Duc d = (Duc)o;
		return this.nom == d.nom;
	}

}
