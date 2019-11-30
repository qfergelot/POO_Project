package troupes;
import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

import royaume.Chateau;
import royaume.Constantes;

public class Troupe extends Sprite{

	private int vitesse;
	private int vie;
	private int degats;
	
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
	
	public void attaquer(Chateau c) {
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
	
	private void recevoirAttaque() {
		vie--;
	}
	
	public void envoyerAttaque(Troupe t) {
		degats--;
	}
	
	public boolean estMort() {
		return vie == 0 || degats == 0;
	}
	
}
