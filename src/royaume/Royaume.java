package royaume;

import java.util.ArrayList;
import java.util.Random;

import troupes.*;

public class Royaume {
	private Random rdm = new Random();
	/* A clarifier:
	 * qu'es-ce qu'une zone ? Les chateaux doivent-ils avoir des coordonnées en paramètres ?
	 * faut-il rajouter un booléen pour si un chateau est neutre ou pas ?
	 * un chateau non neutre veut dire que le duc est soit un joueur soit une IA ?
	 */
	private Chateau []chateaux;
	private int nbChateaux;
	
	private String []nomJoueurs;
	private int nbJoueurs;
	private int nbIA; //Pour plus tard
	private int niveauIA; //Pour plus tard
	
	private int distMinChateaux;
	private int longueur;
	private int hauteur;
	
	public Royaume(int nbJoueurs, int nbIA, int niveauIA, int longueur_plateau, int hauteur_plateau,
			int dist_min_chateaux, int nbChateauxNeutres, int nbPiquiers_init, int nbChevaliers_init, int nbOnagres_init) {
		this.nbJoueurs = nbJoueurs;
		this.nbIA = nbIA;
		this.niveauIA = niveauIA;
		longueur = longueur_plateau;
		hauteur = hauteur_plateau;
		this.distMinChateaux = dist_min_chateaux;
		nbChateaux = nbJoueurs + nbIA + nbChateauxNeutres;
		
		chateaux = new Chateau[nbJoueurs+nbIA+nbChateauxNeutres];
		nomJoueurs = new String[nbJoueurs+nbIA];

		/*Définition basique de nom à améliorer*/
		for(int i=0; i<(nbJoueurs+nbIA); i++) {
			nomJoueurs[i] = "joueur" + i;
		}
		int temp = nbJoueurs+nbIA;
		for(int i=0; i<temp; i++) {
			chateaux[i] = new Chateau(nomJoueurs[i],0,GenererInitPiquiers(nbPiquiers_init),
					GenererInitChevaliers(nbChevaliers_init),GenererInitOnagres(nbOnagres_init),porteAleatoire());
		}
		for(int i=temp; i<nbChateaux; i++) {
			chateaux[i] = new Chateau(rdm.nextInt(900)+101,GenererInitPiquiers(rdm.nextInt(3)+2),
					GenererInitChevaliers(rdm.nextInt(3)+1),GenererInitOnagres(rdm.nextInt(3)),porteAleatoire());
		}
	}
	
	private ArrayList<Piquier> GenererInitPiquiers(int n){
		ArrayList<Piquier> piquiers = new ArrayList<Piquier>();
		for(int i = 0; i < n; i++)
			piquiers.add(new Piquier());
		return piquiers;
	}
	private ArrayList<Chevalier> GenererInitChevaliers(int n){
		ArrayList<Chevalier> chevaliers = new ArrayList<Chevalier>();
		for(int i = 0; i < n; i++)
			chevaliers.add(new Chevalier());
		return chevaliers;
	}
	private ArrayList<Onagre> GenererInitOnagres(int n){
		ArrayList<Onagre> onagres = new ArrayList<Onagre>();
		for(int i = 0; i < n; i++)
			onagres.add(new Onagre());
		return onagres;
	}
	private String porteAleatoire() {
		int p = rdm.nextInt(4);
		if(p==0)
			return "gauche";
		else if(p==1)
			return "haut";
		else if(p==2)
			return "droite";
		else
			return "bas";
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
	
	public int getDistanceMinChateaux() {
		return distMinChateaux;
	}
	
	public int getLongueur() {
		return longueur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
