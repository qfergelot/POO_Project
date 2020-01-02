package royaume;

import java.util.ArrayList;
import java.util.Random;

import game.Popup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import kingdom.Ost;

public class Royaume {
	private Random rdm = new Random();
	
	private Pane layer;

	private Color couleurDuc[] = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
	private Image imagePlayerChateau[] = {
			new Image(getClass().getResource("/images/Chateau joueur.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur2.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur3.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur4.png").toExternalForm(), 40, 40, true, false)
	};
	private Image imageNeutralChateau = new Image(getClass().getResource("/images/Chateau neutre.png").toExternalForm(), 40, 40, true, false);
	
	private Duc []ducs;
	private Chateau []chateaux;
	private int nbChateaux;
	
	private int nbJoueurs;
	private int nbIA;
	private int niveauIA;
	
	private int distMinChateaux;
	private int hauteur;
	private int longueur;
	
	private Popup popupOst;

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
		for(int i=0; i<nbJoueurs; i++) {
			ducs[i] = new Duc("Joueur" + i, couleurDuc[i], imagePlayerChateau[i]);
		}
		for(int i=nbJoueurs; i<(nbJoueurs+nbIA); i++) {
			ducs[i] = new IAbasique("joueur" + i, couleurDuc[i], imagePlayerChateau[i],this);
		}
		popupOst = new Popup(this);
		//Chateaux Joueurs+IA
		int temp = nbJoueurs+nbIA;
		long x, y;
		for(int i=0; i<temp; i++) {
			do {
				x = rdm.nextInt(longueur-80-(int)imagePlayerChateau[i].getWidth()) + 40;
				y = rdm.nextInt(hauteur-80-(int)imagePlayerChateau[i].getHeight()) + 40;
			} while(positionChateauLibre(x,y,i) == false);
			
			chateaux[i] = new Chateau(layer,imagePlayerChateau[i],ducs[i],0,nbPiquiers_init,nbChevaliers_init,nbOnagres_init,x,y, popupOst);
		}
		//Chateaux Neutres
		for(int i=temp; i<nbChateaux; i++) {
			do {
				x = rdm.nextInt(longueur-80-(int)imageNeutralChateau.getWidth()) + 40;
				y = rdm.nextInt(hauteur-80-(int)imageNeutralChateau.getHeight()) + 40;
			} while (positionChateauLibre(x,y,i) == false);
			chateaux[i] = new Chateau(layer,imageNeutralChateau,rdm.nextInt(900)+101,rdm.nextInt(3)+2,
					rdm.nextInt(3)+1,rdm.nextInt(3),x,y, popupOst);
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
		ost.tourOst(this);
		
		if(ost.attaqueFinie() || ost.getTroupe().size() == 0) {
			for(int i=0; i<ost.getTroupe().size(); i++) 
				ost.getTroupe().get(i).getImageView().setImage(null);
			this.ost.remove(ost);
		}
		
			
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
	
	public void clean() {
		for (int i = 0; i<ost.size(); i++) {
			ost.get(i).delete();
		}
		for (int i = 0; i<nbChateaux; i++) {
			chateaux[i].delete();
		}
		this.chateaux = null;
		for (int i = 0; i < ducs.length; i++) {
			while(ducs[i].getNbChateaux()!=0) {
				ducs[i].retirerChateau();
			}
		}
	}
	
	public String saveGame() {
		String s = "";
		s += this.nbChateaux + " \n";
		for (int i = 0; i < this.nbChateaux; i++) {
			s += chateaux[i].saveGame() + " \n";
		}
		return s;
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
	
	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	public Chateau getChateau(int i) {
		return chateaux[i];
	}
	
	public void addCastle(String line) {
		String[] args = line.split(" ");
		int i = 0;
		while (chateaux[i] != null) {
			i ++; 
		}
		if (args[3].contentEquals("baron")) {
			chateaux[i] = new Chateau(layer,imageNeutralChateau, Double.parseDouble(args[5]), Integer.parseInt(args[6]),
					Integer.parseInt(args[7]), Integer.parseInt(args[8]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), popupOst);
		}
		else {
			Duc d = null;
			for (int x = 0; x < ducs.length; x++) {
				if (ducs[x].getNom().contentEquals(args[3])) {
					d = ducs[x];
				}
			}
			chateaux[i] = new Chateau(layer, d.getImgChateau(), d, Double.parseDouble(args[5]), Integer.parseInt(args[6]),
					Integer.parseInt(args[7]), Integer.parseInt(args[8]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), popupOst);
			d.ajouterChateau();
			
		}
		chateaux[i].setPorte(Integer.parseInt(args[9]));
		chateaux[i].setLevel(Integer.parseInt(args[4]));
	}
	
	public int getNbChateaux() {
		return nbChateaux;
	}
	
	public void setNbChateaux(int nbChateaux) {
		this.nbChateaux =  nbChateaux;
		this.chateaux = new Chateau[nbChateaux];
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
