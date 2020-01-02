package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Chateau;
import royaume.Ost;

/**
 * Troupe Piquier qui correspond à la petite troupe du jeu.
 */
public class Piquier extends Troupe {

	/**
	 * Constante qui détermine le nombre de tours nécessaires à produire un Piquier
	 */
	public static final int TEMPS_PRODUCTION = 100;
	/**
	 * Constante qui détermine le nombre de florins nécessaires pour produire un Piquier
	 */
	public static final int COUT_PRODUCTION = 200;
	
	/**
	 * Constructeur Piquier
	 * @param layer
	 * 			layer sert à l'affichage. Voir Sprite
	 * @param image
	 * 			image sert à l'affichage. Voir Sprite
	 * @param pos_x
	 * 			donne la coordonnée x de la position initiale du Piquier
	 * @param pos_y
	 * 			donne la coordonnée y de la position initiale du Piquier
	 */
	public Piquier(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 2, 1, pos_x, pos_y);
	}
	
	public void transferer(Chateau cible, Ost hote) {
		cible.ajouterPiquier();
		hote.getTroupe().remove(this);
		this.getImageView().setImage(null);
	}
	

}
