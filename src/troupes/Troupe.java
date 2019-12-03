package troupes;
import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Random;

import royaume.Chateau;
import royaume.Ost;

public abstract class Troupe extends Sprite{

	private int vitesse;
	private int vie;
	private int degats;
	private boolean surCible = false;
	
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
	
	public void deplacement(Chateau cible) {
		int v = getVitesse();
		while(v > 0 && distance(cible)>(cible.getHeight()/2+getHeight()/2)) {
			double angle = Math.atan2(cible.getPos_y()-getPos_y(),cible.getPos_x()-getPos_x())/Math.PI;
			move(angle);
			v--;
		}
		getImageView().relocate(getPos_x(), getPos_y());
		if(distance(cible)<=(cible.getHeight()/2+getHeight()/2))
			setSurCible();
	}
	
    public void move(double angle) {
		if((angle > 0.875) || (angle <= -0.875)) {
			pos_x--;
		}else if(angle > 0.625) {
			pos_x-=0.7;
			pos_y+=0.7;
		}else if(angle > 0.375) {
			pos_y++;
		}else if(angle > 0.125) {
			pos_x+=0.7;
			pos_y+=0.7;
		}else if(angle > -0.125) {
			pos_x++;
		}else if(angle > -0.375) {
			pos_x+=0.7;
			pos_y-=0.7;
		}else if(angle > -0.625) {
			pos_y--;
		}else if(angle > -0.875) {
			pos_x-=0.7;
			pos_y-=0.7;
		}
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
	
}
