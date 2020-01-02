package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Chateau;
import royaume.Ost;

/**
 * Troupe Chevalier qui correspond à la troupe rapide moyenne du jeu.
 */
public class Chevalier extends Troupe {
	
	/**
	 * Constante qui détermine le nombre de tours nécessaires à produire un Chevalier
	 */
	public static final int TEMPS_PRODUCTION = 500;
	/**
	 * Constante qui détermine le nombre de florins nécessaires pour produire un Chevalier
	 */
	public static final int COUT_PRODUCTION = 500;
	
	/**
	 * Constructeur Chevalier
	 * @param layer
	 * 			layer sert à l'affichage. Voir Sprite
	 * @param image
	 * 			image sert à l'affichage. Voir Sprite
	 * @param pos_x
	 * 			donne la coordonnée x de la position initiale du Chevalier
	 * @param pos_y
	 * 			donne la coordonnée y de la position initiale du Chevalier
	 */
	public Chevalier(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 6, 5, pos_x, pos_y);
	}
	
	public void transferer(Chateau cible, Ost hote) {
		cible.ajouterChevalier();
		hote.getTroupe().remove(this);
		this.getImageView().setImage(null);
	}


}
