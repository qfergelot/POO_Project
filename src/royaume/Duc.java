package royaume;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Duc {
	private String nom;
	private int nbChateaux = 0;
	private Color couleur;
	private Image imgChateau;
	
	public Duc(String nom, Color couleur, Image imgChateau) {
		this.nom = nom;
		this.couleur = couleur;
		this.imgChateau = imgChateau;
	}

	public String getNom() {
		return nom;
	}
	
	public int getNbChateaux() {
		return nbChateaux;
	}
	
	public Color getCouleur() {
		return couleur;
	}
	
	public Image getImgChateau() {
		return imgChateau;
	}
	
	public void ajouterChateau() {
		nbChateaux++;
	}
	
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
