package royaume;

import javafx.scene.paint.Color;

public class Duc {
	private String nom;
	private int nbChateaux = 0;
	private Color couleur;
	
	public Duc(String nom, Color couleur) {
		this.nom = nom;
		this.couleur = couleur;
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
	
	public void ajouterChateau() {
		nbChateaux++;
	}
	
	public void retirerChateau() {
		nbChateaux--;
	}

}
