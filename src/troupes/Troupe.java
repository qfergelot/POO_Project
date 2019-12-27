package troupes;
import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Random;

import royaume.Chateau;
import royaume.Constantes;
import royaume.Ost;
import royaume.Royaume;

public abstract class Troupe extends Sprite{

	private int vitesse;
	private int vie;
	private int degats;
	private boolean surCible = false;
	
	private boolean contourne = false;
	private int dirContournement;
	
	public Troupe(Pane layer, Image img, int vitesse, int vie, int degats, double pos_x, double pos_y) {
		super(layer, img, pos_x, pos_y);

		this.vitesse = vitesse;
		this.vie = vie;
		this.degats = degats;
	}
	
	public double distance(Chateau cible) {
		double x = pos_x + getWidth()/2;
		double y = pos_y + getHeight()/2;
		double cx = cible.getPos_x() + cible.getWidth()/2;
		double cy = cible.getPos_y() + cible.getHeight()/2;
		return Math.sqrt((cx-x)*(cx-x)+(cy-y)*(cy-y));
	}
	
	public double distance(double x, double y, Chateau cible) {
		double dx = x + getWidth()/2;
		double dy = y + getHeight()/2;
		double cx = cible.getPos_x() + cible.getWidth()/2;
		double cy = cible.getPos_y() + cible.getHeight()/2;
		return Math.sqrt((cx-dx)*(cx-dx)+(cy-dy)*(cy-dy));
	}
	
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
	
    public void move(double angle, Royaume royaume, Chateau cible) {
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
    
    public void alternativeMove(double angle, Royaume royaume, Chateau cible) {
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
    
    public void contourner() {
    	if(dirContournement == Constantes.HAUT)
    		pos_y--;
    	else if(dirContournement == Constantes.BAS)
    		pos_y++;
    	else if(dirContournement == Constantes.GAUCHE)
    		pos_x--;
    	else
    		pos_x++;
    }
    
    public void setSurCible() {
    	surCible = true;
    }
    
    public boolean surCible() {
    	return surCible;
    }
    
	public abstract void transferer(Chateau cible, Ost hote);
	
	/*
	 * return: attaque finie
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
	
	private void attaquerPiquier(Chateau c) {
		c.recoisAttaquePiquier();
		degats--;
	}
	
	private void attaquerChevalier(Chateau c) {
		c.recoisAttaqueChevalier();
		degats--;
	}
	
	private void attaquerOnagre(Chateau c) {
		c.recoisAttaqueOnagre();
		degats--;
	}
	
	public int getVitesse() {
		return vitesse;
	}
	
	public void envoyerAttaque(Troupe t) {
		degats--;
	}
	
	public boolean estMort() {
		return vie == 0 || degats == 0;
	}
	
	public boolean estDans(double x, double y, Chateau c) {
		return ((x + getWidth()) > c.getPos_x()) && (x < (c.getPos_x() + c.getWidth()))
				&& ((y + getHeight()) > c.getPos_y()) && (y < (c.getPos_y() + c.getHeight()));
	}
	
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
	
	public void delete() {
		this.removeFromLayer();
	}
	
}
