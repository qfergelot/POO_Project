package troupes;
import game.Sprite;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

import royaume.Chateau;
import royaume.Constantes;

public class Troupe extends Sprite{
	
	private int coutProduction;
	private int tempsProduction;
	private int vitesse;
	private int vie;
	private int degats;
	
	public Troupe(Pane layer, Image img, int coutProduction, int tempsProduction, int vitesse, int vie, int degats, double pos_x, double pos_y) {
		super(layer, img, pos_x, pos_y);
		this.coutProduction = coutProduction;
		this.tempsProduction = tempsProduction;
		this.vitesse = vitesse;
		this.vie = vie;
		this.degats = degats;
	}
	
	public int distance(Chateau cible) {
		return 1;
	}
	
	public void move(int dir) {
		switch(dir) {
			case Constantes.GAUCHE:
				pos_x--;
				break;
			case Constantes.HAUT:
				pos_y--;
				break;
			case Constantes.DROITE:
				pos_x++;
				break;
			default:
				pos_y++;
				break;
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
	
	public int getCoutProduction() {
		return coutProduction;
	}
	
	public int getTempsProduction() {
		return tempsProduction;
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
	
	public void checkRemovability() {
		
	}
	
}
