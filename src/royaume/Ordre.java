package royaume;

import javafx.scene.image.Image;
import troupes.*;

/**
 * Class that represents an order
 * @author Moi
 *
 */
public class Ordre {
	private Chateau cible;
	private int nbTroupes;
	
	private int nbPiquiers;
	private int nbChevaliers;
	private int nbOnagres;
	
	private double sortie_x;
	private double sortie_y;
	
	private Image image_piquier = new Image(getClass().getResource("/images/militar.png").toExternalForm(), 20, 20, true, true);
	private Image image_chevalier = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 20, 20, true, true);
	private Image image_onagre = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 20, 20, false, true);

	/**
	 * Construct an order
	 * @param cible Target Castle
	 * @param nbPiquiers Number of pikemen
	 * @param nbChevaliers Number of knight
	 * @param nbOnagres Number of onager
	 * @param sortie_x First position x of units
	 * @param sortie_y Firts position y of units
	 */
	public Ordre(Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres, double sortie_x, double sortie_y) {
		this.cible = cible;
		this.nbTroupes = nbPiquiers + nbChevaliers + nbOnagres;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
		this.sortie_x = sortie_x;
		this.sortie_y = sortie_y;
	}
	
	public int getNbTroupes() {
		return nbTroupes;
	}
	
	public int getNbPiquiers() {
		return nbPiquiers;
	}
	
	public int getNbChevaliers() {
		return nbChevaliers;
	}
	
	public int getNbOnagres() {
		return nbOnagres;
	}
	
	/**
	 * Exit a pikemen
	 * @param ost Ost linked to this order
	 */
	public void sortirPiquier(Ost ost) {
		nbPiquiers--;
		nbTroupes--;
		ost.ajouterTroupe(new Piquier(cible.getLayer(),image_piquier,sortie_x,sortie_y));
	}
	
	/**
	 * Exit a knight
	 * @param ost Ost linked to this order
	 */
	public void sortirChevalier(Ost ost) {
		nbChevaliers--;
		nbTroupes--;
		ost.ajouterTroupe(new Chevalier(cible.getLayer(),image_chevalier,sortie_x,sortie_y));
	}
	
	/**
	 * Exit a onager
	 * @param ost Ost linked to this order
	 */
	public void sortirOnagre(Ost ost) {
		nbOnagres--;
		nbTroupes--;
		ost.ajouterTroupe(new Onagre(cible.getLayer(),image_onagre,sortie_x,sortie_y));
	}
}
