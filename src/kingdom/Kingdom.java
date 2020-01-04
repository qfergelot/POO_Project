package kingdom;

import java.util.ArrayList;
import java.util.Random;

import game.Popup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import kingdom.Ost;

/**
 * Class that represents the kingdom of the game
 * @author Moi
 *
 */
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
	
	/**
	 * Construct a Kingdom
	 * @param layer Layer in which the elements of the game will appear 
	 * @param nbPlayers Number of real players (0 or 1)
	 * @param nbIA Number of AI
	 * @param levelIA Level maximum of the AI
	 * @param width_plateau Width
	 * @param height_plateau Height
	 * @param dist_min_castle Minimal distance between castles
	 * @param nbCastleNeutres Number of neutral castles
	 * @param nbPikemen_init Initial number of pikemen
	 * @param nbKnight_init Initial number of knight
	 * @param nbOnager_init Initial number of onager
	 */
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

		/*Definition basique de nom a ameliorer*/
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
	
	/**
	 * Compute if a position is correct for a castle (if this is not another castle here)
	 * @param x Coordinate x
	 * @param y Coordinate y
	 * @param nbCastle Number of castle
	 * @return true if the position is correct, else false
	 */
	private boolean positionCastleFree(long x, long y, int nbCastle) {
		for(int cpt=0; cpt<nbCastle; cpt++) {
			if (castle[cpt].distance(x, y) < distMinCastle) {
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * Call the construction of an order for the castle source if it possible
	 * @param c Source castle
	 * @param target Target castle
	 * @param nbPikemen Number of pikemen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 */
	public void createOrder(Castle c, Castle target, int nbPikemen, int nbKnight, int nbOnager) {
		if(c.order()) {
			//Deja un ordre en cours
		}
		else {
			Ost o = new Ost(c.getDuke(), target);
			c.createOrder(o,target, nbPikemen, nbKnight, nbOnager);
			ost.add(o);
		}
	}
	
	/**
	 * Function called at each round (each frame) to advance the round of an ost
	 * @param ost Ost that will be progressed
	 */
	private void roundOst(Ost ost) {
		ost.roundOst(this);
		
		if(ost.finishedAttack() || ost.getTroop().size() == 0) {
			for(int i=0; i<ost.getTroop().size(); i++) 
				ost.getTroop().get(i).getImageView().setImage(null);
			this.ost.remove(ost);
		}
		
			
	}
	
	/**
	 * Function called at each end of round (each frame) to end a round
	 */
	public void finishRound() {
		for(int i=0; i<nbCastle; i++) {
			castle[i].finishRoundCastle();
		}
		for(int i=0; i<ost.size(); i++) {
			roundOst(ost.get(i));
		}
	}
	
	/**
	 * Compute if there is a winner
	 * @return Name of the winner as a String if there is a winner, else null
	 */
	public String finishedGame() {
		int nbRestants = 0;
		String winnerName = null;
		for(int i=0; i<nbPlayers+nbIA; i++) {
			if(dukes[i].getNbCastle() > 0) {
				nbRestants++;
				winnerName = dukes[i].getName();
				if(nbRestants > 1)
					return null;
			}
		}
		return winnerName;
	}
	
	/**
	 * Clean the kingdom from castles and osts
	 */
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
	
	/**
	 * Save the kingdom as a string
	 * @return Kingdom's information as a string
	 */
	public String saveGame() {
		String s = "";
		s += this.nbCastle + " \n";
		for (int i = 0; i < this.nbCastle; i++) {
			s += castle[i].saveGame() + " \n";
		}
		return s;
	}
	
	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	
	/**
	 * Get a castle
	 * @param i Index of the castle
	 * @return Castle at the index i
	 */
	public Castle getCastle(int i) {
		return castle[i];
	}
	
	/**
	 * Add a castle to the array of castle from a save file (array must not be completed) 
	 * @param line line String from a save file 
	 */
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
	
	/**
	 * Getter for nbCastle
	 * @return Current number of castle
	 */
	public int getNbCastle() {
		return nbCastle;
	}
	
	/**
	 * Setter for nbCastle
	 * @param nbCastle Number of Castle
	 */
	public void setNbCastle(int nbCastle) {
		this.nbCastle =  nbCastle;
		this.castle = new Castle[nbCastle];
	}
	
	/**
	 * Getter for nbPlayer
	 * @return Number of players
	 */
	public int getNbPlayers() {
		return nbPlayers;
	}
	
	/**
	 * Getter for nbIA
	 * @return Number of AI 
	 */
	public int getNbIA() {
		return nbIA;
	}
	
	/**
	 * Getter for LevelAI
	 * @return Level max of AI
	 */
	public int getLevelIA() {
		return levelIA;
	}
	
	/**
	 * getter for distMinCastle
	 * @return Minimal distance between two castles
	 */
	public int getDistMinCastle() {
		return distMinCastle;
	}
	
	/**
	 * getter for height
	 * @return Height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Getter for width
	 * @return Width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Getter of the list of all currently displayed ost
	 * @return Ost array
	 */
	public ArrayList<Ost> getOst() {
		return ost;
	}
	
	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
