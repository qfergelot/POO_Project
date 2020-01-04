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
import troops.*;

/**
 * Class that represents a castle
 * @author Moi
 *
 */
public class Castle extends Sprite{
	Random rdm = new Random();
	
	private Popup popupOst;
	
	private Duke duke = null;
	private boolean neutral = true;
	private double treasure;
	private int level;

	private int nbPikemen;
	private int nbKnight;
	private int nbOnager;
	
	private int lifePikemen = Constants.LIFE_PIKEMEN;
	private int lifeKnight = Constants.LIFE_KNIGHT;
	private int lifeOnager = Constants.LIFE_ONAGER;
	
	private Production production;
	private Order displacementOrder;
	private Ost ost;
	private Door door;
	
	/**
	 * Construct a player castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param duke Owner ( @see Duke)
	 * @param treasure Treasurey
	 * @param nbPikemen Number of spearmen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Castle(Pane layer, Image image, Duke duke, double treasure, int nbPikemen, int nbKnight,
			int nbOnager, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.duke = duke;
		duke.addCastle();
		this.neutral = false;
		this.treasure = treasure;
		this.level = 1;
		this.nbPikemen = nbPikemen;
		this.nbKnight = nbKnight;
		this.nbOnager = nbOnager;
		this.production = null;
		this.displacementOrder = null;
		this.ost = null;
		this.door = new Door();
		switch(getDoor()) {
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
        		Castle lastCastle = UIsingleton.getUIsingleton().getCastleSelection();
        		UIsingleton.getUIsingleton().setCastleSelection(getCastle());
        		
        		if(lastCastle != null) {
    				if(!lastCastle.getNeutral()) {
    					if(!(UIsingleton.getUIsingleton().getCastleSelection() == lastCastle) && lastCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
    						popupOst.display(lastCastle,UIsingleton.getUIsingleton().getCastleSelection());
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
	 * @param treasure Treasurey
	 * @param nbPikemen Number of spearmen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Castle(Pane layer, Image image, double treasure, int nbPikemen, int nbKnight,
			int nbOnager, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.treasure = treasure;
		this.neutral = true;
		this.level = rdm.nextInt(4)+1;
		this.nbPikemen = nbPikemen;
		this.nbKnight = nbKnight;
		this.nbOnager = nbOnager;
		this.production = null;
		this.displacementOrder = null;
		this.ost = null;
		this.door = new Door();
		switch(getDoor()) {
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
        		Castle lastCastle = UIsingleton.getUIsingleton().getCastleSelection();
        		UIsingleton.getUIsingleton().setCastleSelection(getCastle());
        		        		
        		if(lastCastle != null) {
    				if(!lastCastle.getNeutral()) {
    					if (UIsingleton.getUIsingleton().getDukePlayer().equals(duke)) {
    						if(!(UIsingleton.getUIsingleton().getCastleSelection() == lastCastle) && lastCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
        						popupOst.display(lastCastle,UIsingleton.getUIsingleton().getCastleSelection());
        					}
    					}
    					else if(lastCastle.getDuke().equals(UIsingleton.getUIsingleton().getDukePlayer())) {
    						popupOst.display(lastCastle,UIsingleton.getUIsingleton().getCastleSelection());
    					}
    				}
    			}
        		/*ContextMenu contextMenu = new ContextMenu();
    			MenuItem attack = new MenuItem("Attack");
    			
    			attack.setOnAction(evt -> {
    				if (!order())
    					getCastle().createOrder(new Ost( kingdom.getPlayer(), getCastle(), x, y), getCastle(), nbPikemen, nbKnight, nbOnager);
    			});
    			contextMenu.getItems().addAll(attack);
    			contextMenu.show(getCastle().getView(), e.getScreenX(), e.getScreenY());*/
        	}
        });
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
	
	/**
	 * Launch a production
	 * @param unit Unit to produkee
	 * @throws ProdException Exception throws when a production is already launched
	 */
	public void launchProduction(int unit) throws ProdException {
		if(inProduction()) {
			throw new ProdException("En cours de production");
			//return false;
		} else {
			
			int cout;
			if(unit==Constants.PIKEMEN)
				cout = Pikemen.PRODUCTION_COST;
			else if(unit==Constants.KNIGHT)
				cout = Knight.PRODUCTION_COST;
			else if(unit==Constants.ONAGER)
				cout = Onager.PRODUCTION_COST;
			else {
				cout = 1000*level;
				if(level == Constants.LEVEL_MAX)
					throw new ProdException("Niveau du Chateau Maximal");
			}
			if(treasure < cout) {
				throw new ProdException("Pas assez de Florins");
			}
			else {
				production = new Production(unit,level);
				treasure -= cout;
				//return true;
			}
		}
		
	}
	
	/**
	 * Cancel a production
	 */
	public void cancelProduction() {
		if(production != null) {
			if(production.isAmelioration()) {
				treasure += 1000*level;
			} else {
				if(production.getUnit()==Constants.PIKEMEN)
					treasure += Pikemen.PRODUCTION_COST;
				else if(production.getUnit()==Constants.KNIGHT)
					treasure += Knight.PRODUCTION_COST;
				else
					treasure += Onager.PRODUCTION_COST;
			}
		}
		production = null;
	}
	
	/**
	 * Getter of the state of the production
	 * @return true if a production is launched , else false
	 */
	public boolean inProduction() {
		return production != null;
	}
	
	/**
	 * Normal ending for a production 
	 */
	public void finishProduction() {
		if(production.isAmelioration())
			level++;
		else {
			int t = production.getUnit();
			if(t == Constants.PIKEMEN)
				nbPikemen++;
			else if(t == Constants.KNIGHT)
				nbKnight++;
			else
				nbOnager++;
		}
		if (this == UIsingleton.getUIsingleton().getCastleSelection())
			UIsingleton.getUIsingleton().setToUpdateTroops(true);
		production = null;
	}
	/* * * * * * * * FIN : Fonctions Production * * * * * * * */
	
	/* * * * * * * * DEBUT : Fonctions Order * * * * * * * */
	/* true si l'odre a été lancé
	 * false si le nombre de troupes est insuffisant
	 */
	/**
	 * Initialize an order for this castle
	 * @param ost Ost that is to be link to this order
	 * @param target Target castle 
	 * @param nbPikemen Number of pikemen 
	 * @param nbKnight Number of knights
	 * @param nbOnager Number of onagers
	 * @return true if order launched, else false
	 */
	public boolean createOrder(Ost ost, Castle target, int nbPikemen, int nbKnight, int nbOnager) {
		if(this.nbPikemen<nbPikemen || this.nbKnight<nbKnight || this.nbOnager<nbOnager) {
			return false;
		}
		this.ost = ost;
		double x = pos_x + getWidth()/2, y = pos_y + getHeight()/2;
		double x2, y2, x3, y3;
		if(getDoor()==Constants.RIGHT) {
			x += getWidth()/2;
			y -= 10;
			x2 = x;
			x3 = x;
			y2 = y-20;
			y3 = y+20;
		}
		else if(getDoor()==Constants.LEFT) {
			x -= getWidth();
			y -= 10;
			x2 = x;
			x3 = x;
			y2 = y-20;
			y3 = y+20;
		}
		else if(getDoor()==Constants.UP) {
			x -= 10;
			y -= getHeight();
			y2 = y;
			y3 = y;
			x2 = x-20;
			x3 = x+20;
		}
		else {
			x -= 10;
			y += getHeight()/2;
			y2 = y;
			y3 = y;
			x2 = x-20;
			x3 = x+20;
		}
		displacementOrder = new Order(target, nbPikemen, nbKnight, nbOnager, x, y, x2, y2, x3, y3);
		return true;
	}
	
	/**
	 * Manage the "exiting the castle" phase of an ost
	 */
	public void removeTroopsOrder() {
		int stop = (displacementOrder.getNbTroops()>=3? 3 : displacementOrder.getNbTroops());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(displacementOrder.getNbOnager()>0) {
				displacementOrder.exitOnager(ost);
				nbOnager--;
			}
			else if(displacementOrder.getNbPikemen()>0) {
				displacementOrder.exitPikemen(ost);
				nbPikemen--;			}
			else {
				displacementOrder.exitKnight(ost);
				nbKnight--;
			}
		}
		if(displacementOrder.getNbTroops()==0) {
			displacementOrder = null;
			ost = null;
		}
	}
	
	/**
	 * Getter of the state of an order
	 * @return true if an order is launched, else false
	 */
	public boolean order() {
		return displacementOrder != null;
	}
		
	/* * * * * * * * FIN : Fonctions Order * * * * * * * */
	
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
	 * Process the advance of production, exit units and treasury
	 */
	public void finishRoundCastle() {
		if(!neutral) {
			if(duke.getClass()==IABasic.class) {
				IABasic ia = (IABasic)duke;
				ia.roundCastleIA(this);
			}
				
			treasure += level;
			if(inProduction()) {
				production.finishRoundProduction();
				if(production.finishedProduction()) {
					finishProduction();
				}
			}
			if(order()) {
				removeTroopsOrder();
			}
		}
		else treasure += (double)level/10;
	}
	
	/**
	 * Getter of the state of this castle's army
	 * @return true if there are no units, else false
	 */
	public boolean noTroop() {
		return nbPikemen == 0 && nbKnight == 0 && nbOnager == 0;
	}
	
	/**
	 * Getter of the remaining number of pikemen
	 * @return Number of remaining pikemen
	 */
	public boolean leftPikemen() {
		return nbPikemen > 0;
	}
	
	/**
	 * Getter of the remaining number of knight
	 * @return Number of remaining knight
	 */
	public boolean leftKnight() {
		return nbKnight > 0;
	}
	
	/**
	 * Getter of the remaining number of onager
	 * @return Number of remaining onager
	 */
	public boolean leftOnager() {
		return nbOnager > 0;
	}
	
	/**
	 * Add a pikemen to this castle's army
	 */
	public void addPikemen() {
		nbPikemen++;
	}
	
	/**
	 * Add a knight to this castle's army
	 */
	public void addKnight() {
		nbKnight++;
	}
	
	/**
	 * Add an onager to this castle's army
	 */
	public void addOnager() {
		nbOnager++;
	}
	
	/**
	 * Remove health equivalent to the attack of one pikemen
	 */
	public void receiveAttackPikemen() {
		lifePikemen--;
		if(lifePikemen==0) {
			nbPikemen--;
			lifePikemen = Constants.LIFE_PIKEMEN;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one knight
	 */
	public void receiveAttackKnight() {
		lifeKnight--;
		if(lifeKnight==0) {
			nbKnight--;
			lifeKnight = Constants.LIFE_KNIGHT;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one onager
	 */
	public void receiveAttackOnager() {
		lifeOnager--;
		if(lifeOnager==0) {
			nbOnager--;
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
			d = this.duke.getName();
		}
		s += "Castle " + this.pos_x + " " + this.pos_y + " " + d + " " + this.level + " " + this.treasure + " " +
				this.nbPikemen + " " + this.nbKnight + " " + this.nbOnager + " " + this.getDoor(); 
		return s;
	}
	
	/**
	 * Remove this castle from the game
	 */
	public void delete() {
		this.removeFromLayer();	
		this.ost = null;
		this.displacementOrder = null;
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
	public boolean getNeutral() {
		return neutral;
	}
	
	/**
	 * Setter of the state
	 * @param neutral Boolean to set the neutral state of the castle (true: neutral, false: player)
	 */
	public void setNeutral(boolean neutral) {
		this.neutral = neutral;
	}

	/**
	 * Getter of the treasurey
	 * @return Treasurey
	 */
	public int getTreasure() {
		return (int)treasure;
	}

	/**
	 * Getter of the level
	 * @return Level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Setter of the level
	 * @param level Level to be set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Getter of the number of pikemen of the army
	 * @return Number of pikemen
	 */
	public int getNbPikemen() {
		return nbPikemen;
	}

	/**
	 * Getter of the number of knight of the army
	 * @return Number of knight
	 */
	public int getNbKnight() {
		return nbKnight;
	}

	/**
	 * Getter of the number of onager of the army
	 * @return Number of onager
	 */
	public int getNbOnager() {
		return nbOnager;
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
	public Order getOrderDisplacement() {
		return displacementOrder;
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
	public int getDoor() {
		return door.getDoor();
	}
	
	/**
	 * Setter of door position
	 * @param door New door
	 */
	public void setDoor(int door) {	
		this.door = new Door(door);
		switch(getDoor()) {
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
