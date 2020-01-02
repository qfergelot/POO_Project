package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Chateau;
import royaume.Ost;

/**
 * Troupe Chevalier qui correspond à la troupe lente mais forte du jeu.
 */
public class Onagre extends Troupe {

	/**
	 * Constante qui détermine le nombre de tours nécessaires à produire un Onagre
	 */
	public static final int TEMPS_PRODUCTION = 1000;
	/**
	 * Constante qui détermine le nombre de florins nécessaires pour produire un Onagre
	 */
	public static final int COUT_PRODUCTION = 1000;
	
	/**
	 * Constructeur Onagre
	 * @param layer
	 * 			layer sert à l'affichage. Voir Sprite
	 * @param image
	 * 			image sert à l'affichage. Voir Sprite
	 * @param pos_x
	 * 			donne la coordonnée x de la position initiale de l'Onagre
	 * @param pos_y
	 * 			donne la coordonnée y de la position initiale de l'Onagre
	 */
	public Onagre(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 1, 10, pos_x, pos_y);
	}

	public void transferer(Chateau cible, Ost hote) {
		cible.ajouterOnagre();
		hote.getTroupe().remove(this);
		this.getImageView().setImage(null);
	}
}
