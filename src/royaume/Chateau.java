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

	private ArrayList<Piquier> piquiers;
	private ArrayList<Chevalier> chevaliers;
	private ArrayList<Onagre> onagres;
	
	private Production production;
	private Ordre ordreDeplacement;
	private Ost ost;
	private Porte porte;
	
	private int pos_x;
	private int pos_y;
	
	/* Chateau Duc */
	public Chateau(Pane layer, Image image, Duc duc, int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, double x, double y) {
		super(layer, image, x, y);
		this.duc = duc;
		duc.ajouterChateau();
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
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
	public Chateau(Pane layer, Image image, int tresor, ArrayList<Piquier> piquiers, ArrayList<Chevalier> chevaliers,
			ArrayList<Onagre> onagres, double x, double y) {
		super(layer, image, x, y);
		this.tresor = tresor;
		this.niveau = rdm.nextInt(10)+1;
		this.piquiers = piquiers;
		this.chevaliers = chevaliers;
		this.onagres = onagres;
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
				piquiers.add((Piquier)t);
			else if(t.getClass() == Chevalier.class)
				chevaliers.add((Chevalier)t);
			else
				onagres.add((Onagre)t);
		}
		production = null;
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	/* * * * * * * * DEBUT : Fonctions Ordre * * * * * * * */
	/* true si l'odre a été lancé
	 * false si le nombre de troupes est insuffisant
	 */
	public boolean creerOrdre(Ost ost, Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		if(piquiers.size()<nbPiquiers || chevaliers.size()<nbChevaliers || onagres.size()<nbOnagres) {
			return false;
		}
		this.ost = ost;
		ordreDeplacement = new Ordre(cible, nbPiquiers, nbChevaliers, nbOnagres);
		return true;
	}
	
	public void sortirTroupesOrdre() {
		int stop = (ordreDeplacement.getNbTroupes()>=3? 3 : ordreDeplacement.getNbTroupes());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(ordreDeplacement.getNbOnagres()>0) {
				ost.ajouterTroupe(onagres.get(0));
				onagres.remove(0);
				ordreDeplacement.sortirOnagre();
			}
			else if(ordreDeplacement.getNbPiquiers()>0) {
				ost.ajouterTroupe(piquiers.get(0));
				piquiers.remove(0);
				ordreDeplacement.sortirPiquier();
			}
			else {
				ost.ajouterTroupe(chevaliers.get(0));
				chevaliers.remove(0);
				ordreDeplacement.sortirChevalier();
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
		return piquiers.size() == 0 && chevaliers.size() == 0 && onagres.size() == 0;
	}
	
	public boolean restePiquiers() {
		return piquiers.size() > 0;
	}
	
	public boolean resteChevaliers() {
		return chevaliers.size() > 0;
	}
	
	public boolean resteOnagres() {
		return onagres.size() > 0;
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


	public ArrayList<Piquier> getPiquiers() {
		return piquiers;
	}


	public ArrayList<Chevalier> getChevaliers() {
		return chevaliers;
	}


	public ArrayList<Onagre> getOnagres() {
		return onagres;
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
