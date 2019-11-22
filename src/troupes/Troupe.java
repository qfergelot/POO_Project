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
				attaquerPiquier(c.getPiquiers());
			}
			else if(rand == 1){
				attaquerChevalier(c.getChevaliers());
			}
			else {
				attaquerOnagre(c.getOnagres());
			}
		}
		else if (c.restePiquiers()) {
			if(c.resteChevaliers()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerPiquier(c.getPiquiers());
				}
				else if(rand == 1){
					attaquerChevalier(c.getChevaliers());
				}
			}
			else if(c.resteOnagres()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerPiquier(c.getPiquiers());
				}
				else if(rand == 1){
					attaquerOnagre(c.getOnagres());
				}
			}
			else {
				attaquerPiquier(c.getPiquiers());
			}
		}
		else if(c.resteChevaliers()) {
			if(c.resteOnagres()) {
				rand = rdm.nextInt(2);
				if(rand == 0) {
					attaquerChevalier(c.getChevaliers());
				}
				else if(rand == 1){
					attaquerOnagre(c.getOnagres());
				}
			}
			else {
				attaquerChevalier(c.getChevaliers());
			}
		}
		else {
			attaquerOnagre(c.getOnagres());
		}
	}
	
	private void attaquerPiquier(ArrayList<Piquier> lt) {
		Troupe t = lt.get(0);
		envoyerAttaque(t);
		if(t.estMort())
			lt.remove(0);
	}
	
	private void attaquerChevalier(ArrayList<Chevalier> lt) {
		Troupe t = lt.get(0);
		envoyerAttaque(t);
		if(t.estMort())
			lt.remove(0);
	}
	
	private void attaquerOnagre(ArrayList<Onagre> lt) {
		Troupe t = lt.get(0);
		envoyerAttaque(t);
		if(t.estMort())
			lt.remove(0);
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
		t.recevoirAttaque();
	}
	
	public boolean estMort() {
		return vie == 0 || degats == 0;
	}
	
	public void checkRemovability() {
		
	}
	
}
