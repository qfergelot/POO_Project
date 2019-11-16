package royaume;

import java.util.ArrayList;
import java.util.Random;

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
	
	private ArrayList<Troupe> troupes;
	
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
		
		troupes = new ArrayList<Troupe>();

		/*Définition basique de nom à améliorer*/
		for(int i=0; i<(nbJoueurs+nbIA); i++) {
			nomJoueurs[i] = "joueur" + i;
		}
		//Chateaux Joeurs+IA
		int temp = nbJoueurs+nbIA;
		for(int i=0; i<temp; i++) {
			int x = rdm.nextInt(longueur);
			int y = rdm.nextInt(hauteur);
			while(!positionChateauLibre(x,y,i)) {
				x = rdm.nextInt(longueur);
				y = rdm.nextInt(hauteur);
			}
			chateaux[i] = new Chateau(nomJoueurs[i],0,GenererInitPiquiers(nbPiquiers_init,nomJoueurs[i]),
					GenererInitChevaliers(nbChevaliers_init,nomJoueurs[i]),
					GenererInitOnagres(nbOnagres_init,nomJoueurs[i]),porteAleatoire(),x,y);
		}
		//Chateaux Neutres
		for(int i=temp; i<nbChateaux; i++) {
			int x = rdm.nextInt(longueur);
			int y = rdm.nextInt(hauteur);
			while(!positionChateauLibre(x,y,i)) {
				x = rdm.nextInt(longueur);
				y = rdm.nextInt(hauteur);
			}
			chateaux[i] = new Chateau(rdm.nextInt(900)+101,GenererInitPiquiers(rdm.nextInt(3)+2,"neutre"),
					GenererInitChevaliers(rdm.nextInt(3)+1,"neutre"),
					GenererInitOnagres(rdm.nextInt(3),"neutre"),porteAleatoire(),x,y);
		}
	}
	
	private ArrayList<Piquier> GenererInitPiquiers(int n, String duc){
		ArrayList<Piquier> piquiers = new ArrayList<Piquier>();
		for(int i = 0; i < n; i++)
			piquiers.add(new Piquier(duc));
		return piquiers;
	}
	private ArrayList<Chevalier> GenererInitChevaliers(int n, String duc){
		ArrayList<Chevalier> chevaliers = new ArrayList<Chevalier>();
		for(int i = 0; i < n; i++)
			chevaliers.add(new Chevalier(duc));
		return chevaliers;
	}
	private ArrayList<Onagre> GenererInitOnagres(int n, String duc){
		ArrayList<Onagre> onagres = new ArrayList<Onagre>();
		for(int i = 0; i < n; i++)
			onagres.add(new Onagre(duc));
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
	
	private boolean positionChateauLibre(int x, int y, int nbChateaux) {
		boolean libre = true;
		for(int i=0; i<nbChateaux; i++) {
			if (chateaux[i].distance(x, y) < distMinChateaux) {
				libre = false;
				break;
			}
		}
		return libre;		
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
	
	public int getLongueur() {
		return longueur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
