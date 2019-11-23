package royaume;

import java.util.ArrayList;
import java.util.Random;

import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import troupes.*;

public class Chateau extends Sprite{
	Random rdm = new Random();
	
	private Duc duc = null;
	private boolean neutre = true;
	private int tresor;
	private int niveau;

	private int nbPiquiers;
	private int nbChevaliers;
	private int nbOnagres;
	
	private int viePiquier = Constantes.VIE_PIQUIER;
	private int vieChevalier = Constantes.VIE_CHEVALIER;
	private int vieOnagre = Constantes.VIE_ONAGRE;
	
	private Production production;
	private Ordre ordreDeplacement;
	private Ost ost;
	private Porte porte;
	
	private int pos_x;
	private int pos_y;
	
	/* Chateau Duc */
	public Chateau(Pane layer, Image image, Duc duc, int tresor, int nbPiquiers, int nbChevaliers,
			int nbOnagres, double x, double y) {
		super(layer, image, x, y);
		this.duc = duc;
		duc.ajouterChateau();
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		switch(getPorte()) {
			case Constantes.GAUCHE:
				imageView.setRotate(90);
				break;
			case Constantes.HAUT:
				imageView.setRotate(180);
				break;
			case Constantes.DROITE:
				imageView.setRotate(270);
				break;
			default:
				break;
		}	
	}
	
	/* Chateau Neutre (pas de duc) */
	public Chateau(Pane layer, Image image, int tresor, int nbPiquiers, int nbChevaliers,
			int nbOnagres, double x, double y) {
		super(layer, image, x, y);
		this.tresor = tresor;
		this.niveau = rdm.nextInt(10)+1;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.porte = new Porte();
		switch(getPorte()) {
		case Constantes.GAUCHE:
			imageView.setRotate(90);
			break;
		case Constantes.HAUT:
			imageView.setRotate(180);
			break;
		case Constantes.DROITE:
			imageView.setRotate(270);
			break;
		default:
			break;
		}
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
	public boolean lancerProduction() {
		if(tresor < 1000*niveau) {
			//Pas assez de florins pour l'amélioration
			return false;
		}
		else {
			tresor -= 1000*niveau;
			production = new Production(niveau);
			return true;
		}
	}
	
	public boolean lancerProduction(Troupe t) {
		if(tresor < t.getCoutProduction()) {
			// pas assez de sousous
			return false;
		}
		else {
			tresor -= t.getCoutProduction();
			production = new Production(t);
			return true;
		}
		
	}
	
	public void annulerProduction() {
		if(production.estAmelioration()) {
			tresor += 1000*niveau;
		} else {
			tresor += production.getUnite().getCoutProduction();
		}
		
		production = null;
	}
	
	public boolean enProduction() {
		return production != null;
	}

	/*
	 * Executer quand production.finProduction() == true
	 */
	public void terminerProduction() {
		if(production.estAmelioration())
			niveau++;
		else {
			Troupe t = production.getUnite();
			if(t.getClass() == Piquier.class)
				nbPiquiers++;
			else if(t.getClass() == Chevalier.class)
				nbChevaliers++;
			else
				nbOnagres++;
		}
		production = null;
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	/* * * * * * * * DEBUT : Fonctions Ordre * * * * * * * */
	/* true si l'odre a été lancé
	 * false si le nombre de troupes est insuffisant
	 */
	public boolean creerOrdre(Ost ost, Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		if(this.nbPiquiers<nbPiquiers || this.nbChevaliers<nbChevaliers || this.nbOnagres<nbOnagres) {
			return false;
		}
		this.ost = ost;
		int sortie_x = pos_x, sortie_y = pos_y;
		if(getPorte() == Constantes.GAUCHE)
			sortie_x--;
		else if(getPorte() == Constantes.HAUT)
			sortie_y--;
		else if(getPorte() == Constantes.DROITE)
			sortie_x++;
		else
			sortie_y++;
		ordreDeplacement = new Ordre(cible, nbPiquiers, nbChevaliers, nbOnagres, sortie_x, sortie_y);
		return true;
	}
	
	public void sortirTroupesOrdre() {
		int stop = (ordreDeplacement.getNbTroupes()>=3? 3 : ordreDeplacement.getNbTroupes());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(ordreDeplacement.getNbOnagres()>0) {
				ordreDeplacement.sortirOnagre(ost);
				nbOnagres--;
			}
			else if(ordreDeplacement.getNbPiquiers()>0) {
				ordreDeplacement.sortirPiquier(ost);
				nbPiquiers--;
			}
			else {
				ordreDeplacement.sortirChevalier(ost);
				nbChevaliers--;
			}
		}
		if(ordreDeplacement.getNbTroupes()==0) {
			ordreDeplacement = null;
			ost = null;
		}
	}
	
	public boolean ordre() {
		return ordreDeplacement != null;
	}
		
	/* * * * * * * * FIN : Fonctions Ordre * * * * * * * */
	
	public double distance(Chateau c) {
		if (c.getPos_x() == pos_x && c.getPos_y() == pos_y) {
			System.err.println("Erreur : Deux chateaux ne peuvent être sur la même position.");
			return 0;
		}
		return Math.sqrt((c.getPos_y() - pos_y)*(c.getPos_y() - pos_y) + (c.getPos_x() - pos_x)*(c.getPos_x() - pos_x));
	}
	
	public int distance(int x, int y) {
		return Math.abs(y - pos_y) + Math.abs(x - pos_x);
	}
	
	public void finTourChateau() {
		if(!neutre) {
			tresor += niveau*10;
			if(enProduction()) {
				production.finTourProduction();
				if(production.finProduction()) {
					terminerProduction();
				}
			}
			if(ordre()) {
				sortirTroupesOrdre();
			}
		}
		else tresor += niveau;
	}
	
	public boolean aucuneTroupe() {
		return nbPiquiers == 0 && nbChevaliers == 0 && nbOnagres == 0;
	}
	
	public boolean restePiquiers() {
		return nbPiquiers > 0;
	}
	
	public boolean resteChevaliers() {
		return nbChevaliers > 0;
	}
	
	public boolean resteOnagres() {
		return nbOnagres > 0;
	}

	public void ajouterPiquier() {
		nbPiquiers++;
	}
	
	public void ajouterChevalier() {
		nbChevaliers++;
	}
	
	public void ajouterOnagre() {
		nbOnagres++;
	}
	
	public void recoisAttaquePiquier() {
		viePiquier--;
		if(viePiquier==0) {
			nbPiquiers--;
			viePiquier = Constantes.VIE_PIQUIER;
		}
	}
	
	public void recoisAttaqueChevalier() {
		vieChevalier--;
		if(vieChevalier==0) {
			nbChevaliers--;
			vieChevalier = Constantes.VIE_CHEVALIER;
		}
	}
	
	public void recoisAttaqueOnagre() {
		vieOnagre--;
		if(vieOnagre==0) {
			nbOnagres--;
			vieOnagre = Constantes.VIE_ONAGRE;
		}
	}
	
	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	public Duc getDuc() {
		return duc;
	}
	
	public void setDuc(Duc duc) {
		this.duc = duc;
	}
	
	public boolean getNeutr() {
		return neutre;
	}

	public int getTresor() {
		return tresor;
	}


	public int getNiveau() {
		return niveau;
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

	public Production getProduction() {
		return production;
	}


	public Ordre getOrdreDeplacement() {
		return ordreDeplacement;
	}
	
	public Ost getOst() {
		return ost;
	}

	public int getPorte() {
		return porte.getPorte();
	}

	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
