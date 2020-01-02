package kingdom;

import java.util.Random;

import game.Popup;
import game.Sprite;
import game.UIsingleton;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import kingdom.Ost;
import kingdom.ProdException;
import kingdom.Production;
import troupes.*;

/**
 * Class that represents a castle
 * @author Moi
 *
 */
public class Castle extends Sprite{
	Random rdm = new Random();
	
	private Popup popupOst;
	
	private Duke duke = null;
	private boolean neutre = true;
	private double tresor;
	private int niveau;

	private int nbPikemens;
	private int nbKnights;
	private int nbOnagers;
	
	private int lifePikemen = Constants.LIFE_PIKEMEN;
	private int lifeKnight = Constants.LIFE_KNIGHT;
	private int lifeOnager = Constants.LIFE_ONAGER;
	
	private Production production;
	private Ordre ordreDeplacement;
	private Ost ost;
	private Porte porte;
	
	/**
	 * Construct a player castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param duke Owner (@see Duke)
	 * @param tresor Tresory
	 * @param nbPikemens Number of spearmen
	 * @param nbKnights Number of knight
	 * @param nbOnagers Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Castle(Pane layer, Image image, Duke duke, double tresor, int nbPikemens, int nbKnights,
			int nbOnagers, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.duke = duke;
		duke.ajouterCastle();
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.nbPikemens = nbPikemens;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		switch(getPorte()) {
			case Constants.LEFT:
				imageView.setRotate(90);
				break;
			case Constants.UP:
				imageView.setRotate(180);
				break;
			case Constants.RIGHT:
				imageView.setRotate(270);
				break;
			default:
				break;
		}
		
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent e) {
        		Castle dernierCastle = UIsingleton.getUIsingleton().getCastleSelection();
        		UIsingleton.getUIsingleton().setCastleSelection(getCastle());
        		
        		if(dernierCastle != null) {
    				if(!dernierCastle.getNeutre()) {
    					if(!(UIsingleton.getUIsingleton().getCastleSelection() == dernierCastle) && dernierCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
    						popupOst.display(dernierCastle,UIsingleton.getUIsingleton().getCastleSelection());
    					}
    				}
    			}
        	}
        });
	}
	
	/**
	 * Construct a neutral castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param tresor Tresory
	 * @param nbPikemens Number of spearmen
	 * @param nbKnights Number of knight
	 * @param nbOnagers Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Castle(Pane layer, Image image, double tresor, int nbPikemens, int nbKnights,
			int nbOnagers, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.tresor = tresor;
		this.neutre = true;
		this.niveau = rdm.nextInt(4)+1;
		this.nbPikemens = nbPikemens;
		this.nbKnights = nbKnights;
		this.nbOnagers = nbOnagers;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		switch(getPorte()) {
		case Constants.LEFT:
			imageView.setRotate(90);
			break;
		case Constants.UP:
			imageView.setRotate(180);
			break;
		case Constants.RIGHT:
			imageView.setRotate(270);
			break;
		default:
			break;
		}
				
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent e) {
        		Castle dernierCastle = UIsingleton.getUIsingleton().getCastleSelection();
        		UIsingleton.getUIsingleton().setCastleSelection(getCastle());
        		        		
        		if(dernierCastle != null) {
    				if(!dernierCastle.getNeutre()) {
    					if (UIsingleton.getUIsingleton().getDukePlayer().equals(duke)) {
    						if(!(UIsingleton.getUIsingleton().getCastleSelection() == dernierCastle) && dernierCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
        						popupOst.display(dernierCastle,UIsingleton.getUIsingleton().getCastleSelection());
        					}
    					}
    					else if(dernierCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
    						popupOst.display(dernierCastle,UIsingleton.getUIsingleton().getCastleSelection());
    					}
    				}
    			}
        		/*ContextMenu contextMenu = new ContextMenu();
    			MenuItem attack = new MenuItem("Attack");
    			
    			attack.setOnAction(evt -> {
    				if (!ordre())
    					getCastle().createOrder(new Ost( kingdom.getPlayer(), getCastle(), x, y), getCastle(), nbPikemens, nbKnights, nbOnagers);
    			});
    			contextMenu.getItems().addAll(attack);
    			contextMenu.show(getCastle().getView(), e.getScreenX(), e.getScreenY());*/
        	}
        });
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
	
	/**
	 * Launch a production
	 * @param unite Unit to produkee
	 * @throws ProdException Exception throws when a production is already launched
	 */
	public void lancerProduction(int unite) throws ProdException {
		if(enProduction()) {
			throw new ProdException("En cours de production");
			//return false;
		} else {
			
			int cout;
			if(unite==Constants.PIKEMEN)
				cout = Pikemen.PRODUCTION_COST;
			else if(unite==Constants.KNIGHT)
				cout = Knight.PRODUCTION_COST;
			else if(unite==Constants.ONAGER)
				cout = Onager.PRODUCTION_COST;
			else {
				cout = 1000*niveau;
				if(niveau == Constants.NIVEAU_MAX)
					throw new ProdException("Niveau du Castle Maximal");
			}
			if(tresor < cout) {
				throw new ProdException("Pas assez de Florins");
			}
			else {
				production = new Production(unite,niveau);
				tresor -= cout;
				//return true;
			}
		}
		
	}
	
	/**
	 * Cancel a production
	 */
	public void annulerProduction() {
		if(production != null) {
			if(production.estAmelioration()) {
				tresor += 1000*niveau;
			} else {
				if(production.getUnit()==Constants.PIKEMEN)
					tresor += Pikemen.PRODUCTION_COST;
				else if(production.getUnit()==Constants.KNIGHT)
					tresor += Knight.PRODUCTION_COST;
				else
					tresor += Onager.PRODUCTION_COST;
			}
		}
		production = null;
	}
	
	/**
	 * Getter of the state of the production
	 * @return true if a production is launched , else false
	 */
	public boolean enProduction() {
		return production != null;
	}
	
	/**
	 * Normal ending for a production 
	 */
	public void terminerProduction() {
		if(production.estAmelioration())
			niveau++;
		else {
			int t = production.getUnit();
			if(t == Constants.PIKEMEN)
				nbPikemens++;
			else if(t == Constants.KNIGHT)
				nbKnights++;
			else
				nbOnagers++;
		}
		if (this == UIsingleton.getUIsingleton().getCastleSelection())
			UIsingleton.getUIsingleton().setToUpdateTroupes(true);
		production = null;
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	/* * * * * * * * DEBUT : Fonctions Ordre * * * * * * * */
	/* true si l'odre a été lancé
	 * false si le nombre de troupes est insuffisant
	 */
	/**
	 * Initialize an order for this castle
	 * @param ost Ost that is to be link to this order
	 * @param cible Target castle 
	 * @param nbPikemens Number of pikemen 
	 * @param nbKnights Number of knights
	 * @param nbOnagers Number of onagers
	 * @return true if order launched, else false
	 */
	public boolean createOrder(Ost ost, Castle cible, int nbPikemens, int nbKnights, int nbOnagers) {
		if(this.nbPikemens<nbPikemens || this.nbKnights<nbKnights || this.nbOnagers<nbOnagers) {
			return false;
		}
		this.ost = ost;
		double x = pos_x + getWidth()/2, y = pos_y + getHeight()/2;
		if(getPorte()==Constants.RIGHT) {
			x += getWidth()/2;
			y -= 10;
		}
		else if(getPorte()==Constants.LEFT) {
			x -= getWidth();
			y -= 10;
		}
		else if(getPorte()==Constants.UP) {
			x -= 10;
			y -= getHeight();
		}
		else {
			x -= 10;
			y += getHeight()/2;
		}
		ordreDeplacement = new Ordre(cible, nbPikemens, nbKnights, nbOnagers, x, y);
		return true;
	}
	
	/**
	 * Manage the "exiting the castle" phase of an ost
	 */
	public void sortirTroupesOrdre() {
		int stop = (ordreDeplacement.getNbTroupes()>=3? 3 : ordreDeplacement.getNbTroupes());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(ordreDeplacement.getNbOnagers()>0) {
				ordreDeplacement.sortirOnager(ost);
				nbOnagers--;
			}
			else if(ordreDeplacement.getNbPikemens()>0) {
				ordreDeplacement.sortirPikemen(ost);
				nbPikemens--;			}
			else {
				ordreDeplacement.sortirKnight(ost);
				nbKnights--;
			}
		}
		if(ordreDeplacement.getNbTroupes()==0) {
			ordreDeplacement = null;
			ost = null;
		}
	}
	
	/**
	 * Getter of the state of an order
	 * @return true if an order is launched, else false
	 */
	public boolean ordre() {
		return ordreDeplacement != null;
	}
		
	/* * * * * * * * FIN : Fonctions Ordre * * * * * * * */
	
	/**
	 * Compute the distance between this castle and another castle
	 * @param c Other castle
	 * @return distance between this castle and the other
	 */
	public double distance(Castle c) {
		if (c.getPos_x() == pos_x && c.getPos_y() == pos_y) {
			System.err.println("Erreur : Deux chateaux ne peuvent être sur la même position.");
			return 0;
		}
		return Math.sqrt((c.getPos_y() - pos_y)*(c.getPos_y() - pos_y) + Math.abs(c.getPos_x() - pos_x)*Math.abs(c.getPos_x() - pos_x));
	}
	
	/**
	 * Compute the distance between two points
	 * @param x one point
	 * @param y the other one
	 * @return distance between x and y
	 */
	public double distance(double x, double y) {
		return Math.sqrt((y - pos_y)*(y - pos_y) + (x - pos_x)*(x - pos_x));
	}
	
	/**
	 * Function called for each turn
	 * Process the advance of production, exit units and tresory
	 */
	public void finTourCastle() {
		if(!neutre) {
			if(duke.getClass()==IABasique.class) {
				IAbasique ia = (IAbasique)duke;
				ia.tourCastleIA(this);
			}
				
			tresor += niveau;
			if(enProduction()) {
				production.finishRoundProduction();
				if(production.finishedProduction()) {
					terminerProduction();
				}
			}
			if(ordre()) {
				sortirTroupesOrdre();
			}
		}
		else tresor += (double)niveau/10;
	}
	
	/**
	 * Getter of the state of this castle's army
	 * @return true if there are no units, else false
	 */
	public boolean aucuneTroupe() {
		return nbPikemens == 0 && nbKnights == 0 && nbOnagers == 0;
	}
	
	/**
	 * Getter of the remaining number of pikemen
	 * @return Number of remaining pikemen
	 */
	public boolean restePikemens() {
		return nbPikemens > 0;
	}
	
	/**
	 * Getter of the remaining number of knight
	 * @return Number of remaining knight
	 */
	public boolean resteKnights() {
		return nbKnights > 0;
	}
	
	/**
	 * Getter of the remaining number of onager
	 * @return Number of remaining onager
	 */
	public boolean resteOnagers() {
		return nbOnagers > 0;
	}
	
	/**
	 * Add a pikemen to this castle's army
	 */
	public void addPikemen() {
		nbPikemens++;
	}
	
	/**
	 * Add a knight to this castle's army
	 */
	public void addKnight() {
		nbKnights++;
	}
	
	/**
	 * Add an onager to this castle's army
	 */
	public void addOnager() {
		nbOnagers++;
	}
	
	/**
	 * Remove health equivalent to the attack of one pikemen
	 */
	public void recoisAttaquePikemen() {
		lifePikemen--;
		if(lifePikemen==0) {
			nbPikemens--;
			lifePikemen = Constants.LIFE_PIKEMEN;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one knight
	 */
	public void recoisAttaqueKnight() {
		lifeKnight--;
		if(lifeKnight==0) {
			nbKnights--;
			lifeKnight = Constants.LIFE_KNIGHT;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one onager
	 */
	public void recoisAttaqueOnager() {
		lifeOnager--;
		if(lifeOnager==0) {
			nbOnagers--;
			lifeOnager = Constants.LIFE_ONAGER;
		}
	}
	/* * * * * * * * DEBUT : SAVE * * * * * * * */
	/**
	 * Transform this castle to a String to save this castle's information 
	 * @return This castle's information as a string
	 */
	public String saveGame() {
		String s = "";
		String d = "baron";
		if (this.duke != null) {
			d = this.duke.getNom();
		}
		s += "Castle " + this.pos_x + " " + this.pos_y + " " + d + " " + this.niveau + " " + this.tresor + " " +
				this.nbPikemens + " " + this.nbKnights + " " + this.nbOnagers + " " + this.getPorte(); 
		return s;
	}
	
	/**
	 * Remove the this castle from the game
	 */
	public void delete() {
		this.removeFromLayer();
		
	}
	
	/* * * * * * * * FIN : SAVE * * * * * * * */
	
	/* * * * * * * * DEBUT : Getters/Setters * * * * * * * */
	
	/**
	 * Getter of this castle's duke owner
	 * @return Castle's duke owner
	 */
	public Duke getDuke() {
		return duke;
	}
	
	/**
	 * Setter of the owner
	 * @param duke New owner
	 */
	public void setDuke(Duke duke) {
		this.duke = duke;
	}
	
	/**
	 * Getter of the state of this castle
	 * @return true if this is a neutral castle, else false
	 */
	public boolean getNeutre() {
		return neutre;
	}
	
	/**
	 * Setter of the state
	 * @param neutre Boolean to set the neutral state of the castle (true: neutral, false: player)
	 */
	public void setNeutre(boolean neutre) {
		this.neutre = neutre;
	}

	/**
	 * Getter of the tresory
	 * @return Tresory
	 */
	public int getTresor() {
		return (int)tresor;
	}

	/**
	 * Getter of the level
	 * @return Level
	 */
	public int getNiveau() {
		return niveau;
	}
	
	/**
	 * Setter of the level
	 * @param level Level to be set
	 */
	public void setLevel(int level) {
		this.niveau = level;
	}

	/**
	 * Getter of the number of pikemen of the army
	 * @return Number of pikemen
	 */
	public int getNbPikemens() {
		return nbPikemens;
	}

	/**
	 * Getter of the number of knight of the army
	 * @return Number of knight
	 */
	public int getNbKnights() {
		return nbKnights;
	}

	/**
	 * Getter of the number of onager of the army
	 * @return Number of onager
	 */
	public int getNbOnagers() {
		return nbOnagers;
	}

	/**
	 * Getter of the production state 
	 * @return true if a production is launched, else false
	 */
	public Production getProduction() {
		return production;
	}

	/**
	 * Getter of the order state 
	 * @return true if an order is launched, else false
	 */
	public Ordre getOrdreDeplacement() {
		return ordreDeplacement;
	}
	
	/**
	 * Getter of the ost
	 * @return Ost
	 */
	public Ost getOst() {
		return ost;
	}

	/**
	 * Getter of door position
	 * @return door
	 */
	public int getPorte() {
		return porte.getPorte();
	}
	
	/**
	 * Setter of door position
	 * @param porte New door
	 */
	public void setPorte(int porte) {	
		this.porte = new Porte(porte);
		switch(getPorte()) {
		case Constants.LEFT:
			imageView.setRotate(90);
			break;
		case Constants.UP:
			imageView.setRotate(180);
			break;
		case Constants.RIGHT:
			imageView.setRotate(270);
			break;
		case Constants.DOWN:
			imageView.setRotate(0);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Getter of this Castle
	 * @return this castle
	 */
	private Castle getCastle() {
		return this;
	}
	
	/**
	 * Getter of popup
	 * @return popup
	 */
	public Popup getPopupOst() {
		return popupOst;
	}

	

	/* * * * * * * * FIN : Getters/Setters * * * * * * * */
}
