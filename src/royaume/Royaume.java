package royaume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import troupes.*;

public class Royaume {
	private Random rdm = new Random();
	
	private Pane layer;
	
	private Image imagePlayerChateau = new Image(getClass().getResource("/images/Chateau joueur.png").toExternalForm(), 40, 40, true, false);
	private Image imageNeutralChateau = new Image(getClass().getResource("/images/Chateau neutre.png").toExternalForm(), 40, 40, true, false);
//	private Image imagePiquier = new Image(getClass().getResource("/images/militar.png").toExternalForm(), 20, 20, true, true);
//	private Image imageChevalier = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 20, 20, true, true);
//	private Image imageOnagre = new Image(getClass().getResource("/images/oangre.png").toExternalForm(), 20, 20, true, true);
	
	/* A clarifier:
	 * Comment on attaque un chateau ? On se pose une case devant ? Forcément devant sa porte ?
	 * Il faut rentrer dans le chateau ?
	 */
	private Duc []ducs;
	private Chateau []chateaux;
	private int nbChateaux;
	
	private int nbJoueurs; //Normalement restera 1 ou 0
	private int nbIA; //Pour plus tard
	private int niveauIA; //Pour plus tard
	
	private int distMinChateaux;
	private int hauteur;
	private int longueur;

	private ArrayList<Ost> ost;
	
	public Royaume(Pane layer, int nbJoueurs, int nbIA, int niveauIA, int longueur_plateau, int hauteur_plateau,
			int dist_min_chateaux, int nbChateauxNeutres, int nbPiquiers_init, int nbChevaliers_init, int nbOnagres_init) {
		this.layer = layer;
		this.nbJoueurs = nbJoueurs;
		this.nbIA = nbIA;
		this.niveauIA = niveauIA;
		hauteur = hauteur_plateau;
		longueur = longueur_plateau;
		this.distMinChateaux = dist_min_chateaux;
		nbChateaux = nbJoueurs + nbIA + nbChateauxNeutres;
		
		chateaux = new Chateau[nbJoueurs+nbIA+nbChateauxNeutres];
		ducs = new Duc[nbJoueurs+nbIA];
		
		ost = new ArrayList<Ost>();

		/*Définition basique de nom à améliorer*/
		
		for(int i=0; i<(nbJoueurs+nbIA); i++) {
			ducs[i] = new Duc("joueur" + i, Color.BLUE);
		}
		//Chateaux Joueurs+IA
		int temp = nbJoueurs+nbIA;
		long x, y;
		for(int i=0; i<temp; i++) {
			do {
				x = rdm.nextInt(longueur-160-(int)imagePlayerChateau.getWidth()) + 80;
				y = rdm.nextInt(hauteur-160-(int)imagePlayerChateau.getHeight()) + 80;
			} while(positionChateauLibre(x,y,i) == false);
			
			chateaux[i] = new Chateau(layer,imagePlayerChateau,ducs[i],0,nbPiquiers_init,nbChevaliers_init,nbOnagres_init,x,y, this);
		}
		//Chateaux Neutres
		for(int i=temp; i<nbChateaux; i++) {
			do {
				x = rdm.nextInt(longueur-(int)imageNeutralChateau.getWidth());
				y = rdm.nextInt(hauteur-(int)imageNeutralChateau.getHeight());
			} while (positionChateauLibre(x,y,i) == false);
			chateaux[i] = new Chateau(layer,imageNeutralChateau,rdm.nextInt(900)+101,rdm.nextInt(3)+2,
					rdm.nextInt(3)+1,rdm.nextInt(3),x,y, this);
		}	
	}
	
	private boolean positionChateauLibre(long x, long y, int nbChateaux) {
		for(int cpt=0; cpt<nbChateaux; cpt++) {
			if (chateaux[cpt].distance(x, y) < distMinChateaux) {
				return false;
			}
		}
		return true;		
	}
	
	public void creerOrdre(Chateau c, Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		if(c.ordre()) {
			//Déjà un ordre en cours
		}
		else {
			Ost o = new Ost(c.getDuc(), cible);
			c.creerOrdre(o,cible, nbPiquiers, nbChevaliers, nbOnagres);
			ost.add(o);
		}
	}
	
	private void tourOst(Ost ost) {
		ost.deplacement();
		/*if(ost.distanceCible() == 1) {
			if(ost.getCible().getDuc() == ost.getDuc()) {
				ost.transfererTroupes();
				this.ost.remove(ost);
			}
			else {
				ost.attaquerCible();
				if(ost.attaqueFinie())
					this.ost.remove(ost);
			}
		}
		else {
			ost.deplacement();;
		}*/
		
	}
	
	public void finTour() {
		for(int i=0; i<nbChateaux; i++) {
			chateaux[i].finTourChateau();
		}
		for(int i=0; i<ost.size(); i++) {
			tourOst(ost.get(i));
		}
	}
	
	public boolean partieTerminee() {
		int nbRestants = 0;
		for(int i=0; i<nbJoueurs+nbIA; i++) {
			if(ducs[i].getNbChateaux() > 0) {
				nbRestants++;
				if(nbRestants > 1)
					return false;
			}
		}
		return true;
	}
	
	/*private void deplacementOst(Ost ost) {
		int v = ost.getVitesse();
		while(v > 0 && ost.distanceCible() > 1) {
			int dx = ost.getPos_x() - ost.getCible().getPos_x();
			int dy = ost.getPos_y() - ost.getCible().getPos_y();
			if(Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0) {
					if(deplacementPossible(ost.getPos_x()+1,ost.getPos_y()))
						ost.move(Constantes.DROITE);
					else if(deplacementPossible(ost.getPos_x(),ost.getPos_y()+1))
						ost.move(Constantes.BAS);
					else
						ost.move(Constantes.HAUT);
				}
				else {
					if(deplacementPossible(ost.getPos_x()-1,ost.getPos_y()))
						ost.move(Constantes.GAUCHE);
					else if(deplacementPossible(ost.getPos_x(),ost.getPos_y()+1))
						ost.move(Constantes.HAUT);
					else
						ost.move(Constantes.BAS);
				}
			}
			else {
				if (dy > 0) {
					if(deplacementPossible(ost.getPos_x(),ost.getPos_y()+1))
						ost.move(Constantes.BAS);
					else if(deplacementPossible(ost.getPos_x()+1,ost.getPos_y()))
						ost.move(Constantes.DROITE);
					else
						ost.move(Constantes.GAUCHE);
				}
				else {
					if(deplacementPossible(ost.getPos_x(),ost.getPos_y()-1))
						ost.move(Constantes.HAUT);
					else if(deplacementPossible(ost.getPos_x()+1,ost.getPos_y()))
						ost.move(Constantes.GAUCHE);
					else
						ost.move(Constantes.DROITE);
				}
			}
			v--;
		}
	}*/
	
	private boolean deplacementPossible(double x, double y) {
		if(x<0 || y<0 || x >= longueur || y >= hauteur)
			return false;
		for(int i=0; i<nbChateaux; i++) {
			if(chateaux[i].getPos_x() == x && chateaux[i].getPos_y() == y) {
				return false;
			}
		}
		return true;
	}
	
	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	public Chateau getChateau(int i) {
		return chateaux[i];
	}
	
	public int getNbChateaux() {
		return nbChateaux;
	}
	
	public int getNbJoueurs() {
		return nbJoueurs;
	}
	
	public int getNbIA() {
		return nbJoueurs;
	}
	
	public int getNiveauIA() {
		return niveauIA;
	}
	
	public int getDistMinChateaux() {
		return distMinChateaux;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLongueur() {
		return longueur;
	}
	
	public ArrayList<Ost> getOst() {
		return ost;
	}
	
	public Duc getPlayer() {
		for(int i = 0; i < ducs.length; i++) {
			if (ducs[i] != null)
				return ducs[i];
		}
		System.out.println("Exception to throw");
		return null;
	}
	
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
