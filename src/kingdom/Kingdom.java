package kingdom;

import java.util.ArrayList;
import java.util.Random;

import game.Popup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import kingdom.Ost;

public class Kingdom {
	private Random rdm = new Random();
	
	private Pane layer;

	private Color colorDuke[] = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
	private Image imagePlayerCastle[] = {
			new Image(getClass().getResource("/images/Chateau joueur.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur2.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur3.png").toExternalForm(), 40, 40, true, false),
			new Image(getClass().getResource("/images/Chateau joueur4.png").toExternalForm(), 40, 40, true, false)
	};
	private Image imageNeutralCastle = new Image(getClass().getResource("/images/Chateau neutre.png").toExternalForm(), 40, 40, true, false);
	
	private Duke []dukes;
	private Castle []castle;
	private int nbCastle;
	
	private int nbPlayers;
	private int nbIA;
	private int levelIA;
	
	private int distMinCastle;
	private int height;
	private int width;
	
	private Popup popupOst;

	private ArrayList<Ost> ost;
	
	public Kingdom(Pane layer, int nbPlayers, int nbIA, int levelIA, int width_plateau, int height_plateau,
			int dist_min_castle, int nbCastleNeutres, int nbPikemen_init, int nbKnight_init, int nbOnager_init) {
		this.layer = layer;
		this.nbPlayers = nbPlayers;
		this.nbIA = nbIA;
		this.levelIA = levelIA;
		height = height_plateau;
		width = width_plateau;
		this.distMinCastle = dist_min_castle;
		nbCastle = nbPlayers + nbIA + nbCastleNeutres;
		
		castle = new Castle[nbPlayers+nbIA+nbCastleNeutres];
		dukes = new Duke[nbPlayers+nbIA];
		
		ost = new ArrayList<Ost>();

		/*Dﾃｩfinition basique de nom ﾃ� amﾃｩliorer*/
		for(int i=0; i<nbPlayers; i++) {
			dukes[i] = new Duke("Joueur" + i, colorDuke[i], imagePlayerCastle[i]);
		}
		for(int i=nbPlayers; i<(nbPlayers+nbIA); i++) {
			dukes[i] = new IABasic("Joueur" + i, colorDuke[i], imagePlayerCastle[i],this);
		}
		popupOst = new Popup(this);
		//Castle Players+IA
		int temp = nbPlayers+nbIA;
		long x, y;
		for(int i=0; i<temp; i++) {
			do {
				x = rdm.nextInt(width-80-(int)imagePlayerCastle[i].getWidth()) + 40;
				y = rdm.nextInt(height-80-(int)imagePlayerCastle[i].getHeight()) + 40;
			} while(positionCastleFree(x,y,i) == false);
			
			castle[i] = new Castle(layer,imagePlayerCastle[i],dukes[i],0,nbPikemen_init,nbKnight_init,nbOnager_init,x,y, popupOst);
		}
		//Castle Neutres
		for(int i=temp; i<nbCastle; i++) {
			do {
				x = rdm.nextInt(width-80-(int)imageNeutralCastle.getWidth()) + 40;
				y = rdm.nextInt(height-80-(int)imageNeutralCastle.getHeight()) + 40;
			} while (positionCastleFree(x,y,i) == false);
			castle[i] = new Castle(layer,imageNeutralCastle,rdm.nextInt(900)+101,rdm.nextInt(3)+2,
					rdm.nextInt(3)+1,rdm.nextInt(3),x,y, popupOst);
		}	
	}
	
	private boolean positionCastleFree(long x, long y, int nbCastle) {
		for(int cpt=0; cpt<nbCastle; cpt++) {
			if (castle[cpt].distance(x, y) < distMinCastle) {
				return false;
			}
		}
		return true;		
	}
	
	public void createOrder(Castle c, Castle target, int nbPikemen, int nbKnight, int nbOnager) {
		if(c.order()) {
			//Dﾃｩjﾃ� un ordre en cours
		}
		else {
			Ost o = new Ost(c.getDuke(), target);
			c.createOrder(o,target, nbPikemen, nbKnight, nbOnager);
			ost.add(o);
		}
	}
	
	private void roundOst(Ost ost) {
		ost.roundOst(this);
		
		if(ost.finishedAttack() || ost.getTroop().size() == 0) {
			for(int i=0; i<ost.getTroop().size(); i++) 
				ost.getTroop().get(i).getImageView().setImage(null);
			this.ost.remove(ost);
		}
		
			
	}
	
	public void finishRound() {
		for(int i=0; i<nbCastle; i++) {
			castle[i].finishRoundCastle();
		}
		for(int i=0; i<ost.size(); i++) {
			roundOst(ost.get(i));
		}
	}
	
	public String finishedGame() {
		int nbRestants = 0;
		String winnerName = null;
		for(int i=0; i<nbPlayers+nbIA; i++) {
			if(dukes[i].getNbCastle() > 0) {
				System.out.println(dukes[i].getName() + " " + dukes[i].getNbCastle());
				nbRestants++;
				winnerName = dukes[i].getName();
				if(nbRestants > 1)
					return null;
			}
		}
		return winnerName;
	}
	
	public void clean() {
		for (int i = 0; i<ost.size(); i++) {
			ost.get(i).delete();
		}
		ost.clear();
		for (int i = 0; i<nbCastle; i++) {
			castle[i].delete();
			castle[i] = null;
		}
		this.castle = null;
		for (int i = 0; i < dukes.length; i++) {
			while(dukes[i].getNbCastle()!=0) {
				dukes[i].removeCastle();
			}
			dukes[i].reset();
		}
	}
	
	public String saveGame() {
		String s = "";
		s += this.nbCastle + " \n";
		for (int i = 0; i < this.nbCastle; i++) {
			s += castle[i].saveGame() + " \n";
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
	public Castle getCastle(int i) {
		return castle[i];
	}
	
	public void addCastle(String line) {
		String[] args = line.split(" ");
		int i = 0;
		while (castle[i] != null) {
			i ++; 
		}
		if (args[3].contentEquals("baron")) {
			castle[i] = new Castle(layer,imageNeutralCastle, Double.parseDouble(args[5]), Integer.parseInt(args[6]),
					Integer.parseInt(args[7]), Integer.parseInt(args[8]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), popupOst);
		}
		else {
			Duke d = null;
			for (int x = 0; x < dukes.length; x++) {
				if (dukes[x].getName().contentEquals(args[3])) {
					d = dukes[x];
				}
			}
			castle[i] = new Castle(layer, d.getImgCastle(), d, Double.parseDouble(args[5]), Integer.parseInt(args[6]),
					Integer.parseInt(args[7]), Integer.parseInt(args[8]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), popupOst);
			
		}
		castle[i].setDoor(Integer.parseInt(args[9]));
		castle[i].setLevel(Integer.parseInt(args[4]));
	}
	
	public int getNbCastle() {
		return nbCastle;
	}
	
	public void setNbCastle(int nbCastle) {
		this.nbCastle =  nbCastle;
		this.castle = new Castle[nbCastle];
	}
	
	public int getNbPlayers() {
		return nbPlayers;
	}
	
	public int getNbIA() {
		return nbIA;
	}
	
	public int getLevelIA() {
		return levelIA;
	}
	
	public int getDistMinCastle() {
		return distMinCastle;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public ArrayList<Ost> getOst() {
		return ost;
	}
	
	public Duke getPlayer() {
		for(int i = 0; i < dukes.length; i++) {
			if (dukes[i] != null)
				return dukes[i];
		}
		System.out.println("Exception to throw");
		return null;
	}
	
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
