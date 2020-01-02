package troupes;
import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Random;

import royaume.Chateau;
import royaume.Constantes;
import royaume.Ost;
import royaume.Royaume;
/**
 * Troupe est la classe abstraite représentant les troupes du jeu.
 * La troupe est caractérisée par
 * <ul>
 * <li>Une vitesse qui est le nombre de pixels parcourus par frame</li>
 * <li>Des dégats, la troupe meurt quand elle n'a plus de dégats.
 * Les dégats sont décrémentés à chaque frame lorsqu'une troupe retire un point de vie à une autre dans un chateau.</li>
 * </ul>
 * Troupe étends la classe Sprite pour gérer l'affichage.
 */
public abstract class Troupe extends Sprite{

	private int vitesse;
	private int degats;
	private boolean surCible = false;
	
	private boolean contourne = false;
	private int dirContournement;
	
	/**
	 * Constructeur Troupe
	 * @param layer
	 * 			layer sert à l'affichage. Voir Sprite
	 * @param img
	 * 			img sert à l'affichage. Voir Sprite
	 * @param vitesse
	 * 			La vitesse de la troupe qui ne peut pas être modifiée
	 * @param degats
	 * 			Les degats qui se décrémentent à chaque degat infligé
	 * @param pos_x
	 * 			La position x en pixel
	 * @param pos_y
	 * 			La position y en pixel
	 */
	public Troupe(Pane layer, Image img, int vitesse, int degats, double pos_x, double pos_y) {
		super(layer, img, pos_x, pos_y);

		this.vitesse = vitesse;
		this.degats = degats;
	}
	
	/**
	 * Distance avec la cible
	 * @param cible
	 * 			Le chateau dont on veut la distance.
	 * @return La distance en pixels entre la troupe et la cible.
	 */
	public double distance(Chateau cible) {
		double x = pos_x + getWidth()/2;
		double y = pos_y + getHeight()/2;
		double cx = cible.getPos_x() + cible.getWidth()/2;
		double cy = cible.getPos_y() + cible.getHeight()/2;
		return Math.sqrt((cx-x)*(cx-x)+(cy-y)*(cy-y));
	}
	
	/**
	 * Distance entre des coordonnées et la cible
	 * @param x
	 * 			coordonnée x en pixel
	 * @param y
	 * 			coordonnée y en pixel
	 * @param cible
	 * 			Le chateau dont on veut la distance.
	 * @return La distance en pixels entre le point de coordonnées x,y et la cible.
	 */
	public double distance(double x, double y, Chateau cible) {
		double dx = x + getWidth()/2;
		double dy = y + getHeight()/2;
		double cx = cible.getPos_x() + cible.getWidth()/2;
		double cy = cible.getPos_y() + cible.getHeight()/2;
		return Math.sqrt((cx-dx)*(cx-dx)+(cy-dy)*(cy-dy));
	}
	
	/**
	 * Deplace la troupe vers sa cible.
	 * @param cible
	 * 			direction de la troupe pour le déplacement.
	 * @param royaume
	 * 			le royaume pour accéder à tous les chateaux et gérer les collisions.
	 */
	public void deplacement(Chateau cible, Royaume royaume) {
		int v = getVitesse();
		while(v > 0) {
			double angle = Math.atan2(cible.getPos_y()-getPos_y(),cible.getPos_x()-getPos_x())/Math.PI;
			move(angle, royaume, cible);
			if(surCible)
				break;
			v--;
		}
		getImageView().relocate(getPos_x(), getPos_y());
	}
	
	/**
	 * déplacement d'un pixel en direction de la cible à partir d'un angle.
	 * @param angle
	 * 			Angle entre la troupe et la direction qui permettra de se déplacer sur 8 axes plutot que haut,bas,gauche,droite.
	 * @param royaume
	 * 			royaume pour la gestion des collisions.
	 * @param cible
	 * 			cible en cas de collision pour recalculer la direction.
	 */
    private void move(double angle, Royaume royaume, Chateau cible) {
    	if(contourne) {
    		alternativeMove(angle, royaume, cible);
    	}
    	else {
	    	double new_x = pos_x, new_y = pos_y;
			if((angle > 0.875) || (angle <= -0.875)) {
				new_x--;
			}else if(angle > 0.625) {
				new_x-=0.7;
				new_y+=0.7;
			}else if(angle > 0.375) {
				new_y++;
			}else if(angle > 0.125) {
				new_x+=0.7;
				new_y+=0.7;
			}else if(angle > -0.125) {
				new_x++;
			}else if(angle > -0.375) {
				new_x+=0.7;
				new_y-=0.7;
			}else if(angle > -0.625) {
				new_y--;
			}else if(angle > -0.875) {
				new_x-=0.7;
				new_y-=0.7;
			}
			if(!collision(new_x, new_y, royaume, cible)) {
				pos_x = new_x;
				pos_y = new_y;
				contourne = false;
			}
			else {
				alternativeMove(angle, royaume, cible);
			}
    	}
	}
    
    /**
     * Même fonction que move mais prends 4 axes de direction pour potentiellement éviter un chateau.
	 * @param angle
	 * 			Angle entre la troupe et la direction qui permettra de se déplacer sur 8 axes plutot que haut,bas,gauche,droite.
	 * @param royaume
	 * 			royaume pour la gestion des collisions.
	 * @param cible
	 * 			cible en cas de collision pour recalculer la direction.
     */
    private void alternativeMove(double angle, Royaume royaume, Chateau cible) {
    	double new_x = pos_x, new_y = pos_y;
    	if(angle > 0.75 || angle <= -0.75)
    		new_x--;
    	else if(angle > 0.25)
    		new_y++;
    	else if(angle > -0.25)
    		new_x++;
    	else
    		new_y--;
    	if(!collision(new_x, new_y, royaume, cible)) {
			pos_x = new_x;
			pos_y = new_y;
			contourne = false;
		}
    	else {
    		if(contourne) {
    			contourner();
    		}
    		else {
	    		if(new_x != pos_x) {
	    			if(distance(pos_x, pos_y-1, cible) < distance(pos_x, pos_y+1, cible))
	    				dirContournement = Constantes.HAUT;
	    			else
	    				dirContournement = Constantes.BAS;
	    		}
	    		else {
	    			if(distance(pos_x-1, pos_y, cible) < distance(pos_x+1, pos_y, cible))
	    				dirContournement = Constantes.GAUCHE;
	    			else
	    				dirContournement = Constantes.DROITE;
    			}
	    		contourne = true;
	    		contourner();
    		}
    	}
    }
    
    /**
     * Si la troupe a besoin de contourner un chateau, fonction appelée par alternativeMove qui suis une direction pour contourner le chateau.
     */
    private void contourner() {
    	if(dirContournement == Constantes.HAUT)
    		pos_y--;
    	else if(dirContournement == Constantes.BAS)
    		pos_y++;
    	else if(dirContournement == Constantes.GAUCHE)
    		pos_x--;
    	else
    		pos_x++;
    }
    
    /**
     * Setter, la troupe se trouve face au chateau cible
     */
    public void setSurCible() {
    	surCible = true;
    }
    
    /**
     * Test si la troupe est sur cible
     * @return Un booléen qui vaut vrai si la Troupe est face à la cible et false sinon.
     */
    public boolean surCible() {
    	return surCible;
    }
    
    /**
     * Transfère la troupe au chateau cible.
     * @param cible
     * 			Le chateau où la cible doit être transférée
     * @param hote
     * 			L'Ost où se trouve la troupe.
     */
	public abstract void transferer(Chateau cible, Ost hote);
	
	/**
	 * Attaque un chateau en retirant un point de vie à un type de troupe tiré aléatoirement
	 * @param c
	 * 			chateau cible de l'attaque
	 * @return true si l'attaque est fini (toutes les troupes du chateau ont été tuées), false sinon
	 */
	public boolean attaquer(Chateau c) {
		Random rdm = new Random();
		int rand;
		if(c.restePiquiers() && c.resteChevaliers() && c.resteOnagres()) {
			rand = rdm.nextInt(3);
			if(rand == 0) {
				attaquerPiquier(c);
			}
			else if(rand == 1){
				attaquerChevalier(c);
			}
			else {
				attaquerOnagre(c);
			}
		}
		else if (c.restePiquiers()) {
			if(c.resteChevaliers()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerPiquier(c);
				}
				else if(rand == 1){
					attaquerChevalier(c);
				}
			}
			else if(c.resteOnagres()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerPiquier(c);
				}
				else if(rand == 1){
					attaquerOnagre(c);
				}
			}
			else {
				attaquerPiquier(c);
			}
		}
		else if(c.resteChevaliers()) {
			if(c.resteOnagres()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerChevalier(c);
				}
				else if(rand == 1){
					attaquerOnagre(c);
				}
			}
			else {
				attaquerChevalier(c);
			}
		}
		else {
			attaquerOnagre(c);
		}
		return c.aucuneTroupe();
	}
	
	/**
	 * Attaque un piquier du chateau cible
	 * @param c
	 * 			Chateau cible de l'attaque
	 */
	private void attaquerPiquier(Chateau c) {
		c.recoisAttaquePiquier();
		degats--;
	}
	
	/**
	 * Attaque un chevalier du chateau cible
	 * @param c
	 * 			Chateau cible de l'attaque
	 */
	private void attaquerChevalier(Chateau c) {
		c.recoisAttaqueChevalier();
		degats--;
	}
	
	/**
	 * Attaque un onagre du chateau cible
	 * @param c
	 * 			Chateau cible de l'attaque
	 */
	private void attaquerOnagre(Chateau c) {
		c.recoisAttaqueOnagre();
		degats--;
	}
	
	/**
	 * getter sur la vitesse
	 * @return la vitesse de la troupe
	 */
	public int getVitesse() {
		return vitesse;
	}
	
	/**
	 * test si la troupe est morte (plus aucun degat) ou non.
	 * @return true si la troupe est morte, false sinon.
	 */
	public boolean estMort() {
		return degats < 1;
	}
	
	/**
	 * fonction pour vérifier si le point de coordonnée x,y est dans le chateau c.
	 * @param x
	 * 			coordonnée x en pixel.
	 * @param y
	 * 			coordonnée y en pixel.
	 * @param c
	 * 			Chateau du test.
	 * @return true si (x,y) se trouve dans le chateau, false sinon.
	 */
	public boolean estDans(double x, double y, Chateau c) {
		return ((x + getWidth()) > c.getPos_x()) && (x < (c.getPos_x() + c.getWidth()))
				&& ((y + getHeight()) > c.getPos_y()) && (y < (c.getPos_y() + c.getHeight()));
	}
	
	/**
	 * Test si le point (x,y) se trouve dans un chateau quelconque du royaume et met surCible à true si y a collision avec la cible.
	 * @param x
	 * 			coordonnée x en pixel.
	 * @param y
	 * 			coordonnée y en pixel.
	 * @param royaume
	 * 			royaume pour accéder aux chateaux.
	 * @param cible
	 * 			Chateau cible.
	 * @return true si il y a une collision, false sinon.
	 */
	public boolean collision(double x, double y, Royaume royaume, Chateau cible) {
		for(int i=0; i<royaume.getNbChateaux(); i++) {
			if(estDans(x, y, royaume.getChateau(i))) {
				if(royaume.getChateau(i) == cible)
					surCible = true;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retire l'affichage de la troupe.
	 */
	public void delete() {
		this.removeFromLayer();
	}
	
}
