package royaume;

import java.util.Random;

import game.Popup;
import game.Sprite;
import game.UIsingleton;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import troupes.*;

/**
 * Class that represents a castle
 * @author Moi
 *
 */
public class Chateau extends Sprite{
	Random rdm = new Random();
	
	private Popup popupOst;
	
	private Duc duc = null;
	private boolean neutre = true;
	private double tresor;
	private int niveau;

	private int nbPiquiers;
	private int nbChevaliers;
	private int nbOnagres;
	
	private int viePiquier = Constantes.VIE_PIQUIER;
	private int vieChevalier = Constantes.VIE_CHEVALIER;
	private int vieOnagre = Constantes.VIE_ONAGRE;
	
	private Production production;
	private Ordre ordreDeplacement;
	private Ost ost;
	private Porte porte;
	
	/**
	 * Construct a player castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param duc Owner (@see Duc)
	 * @param tresor Tresory
	 * @param nbPiquiers Number of spearmen
	 * @param nbChevaliers Number of knight
	 * @param nbOnagres Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Chateau(Pane layer, Image image, Duc duc, double tresor, int nbPiquiers, int nbChevaliers,
			int nbOnagres, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.duc = duc;
		duc.ajouterChateau();
		this.neutre = false;
		this.tresor = tresor;
		this.niveau = 1;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		switch(getPorte()) {
			case Constantes.GAUCHE:
				imageView.setRotate(90);
				break;
			case Constantes.HAUT:
				imageView.setRotate(180);
				break;
			case Constantes.DROITE:
				imageView.setRotate(270);
				break;
			default:
				break;
		}
		
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent e) {
        		Chateau dernierChateau = UIsingleton.getUIsingleton().getChateauSelection();
        		UIsingleton.getUIsingleton().setChateauSelection(getChateau());
        		
        		if(dernierChateau != null) {
    				if(!dernierChateau.getNeutre()) {
    					if(!(UIsingleton.getUIsingleton().getChateauSelection() == dernierChateau) && dernierChateau.getDuc().equals(UIsingleton.getUIsingleton().getDucJoueur())) {
    						popupOst.display(dernierChateau,UIsingleton.getUIsingleton().getChateauSelection());
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
	 * @param nbPiquiers Number of spearmen
	 * @param nbChevaliers Number of knight
	 * @param nbOnagres Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public Chateau(Pane layer, Image image, double tresor, int nbPiquiers, int nbChevaliers,
			int nbOnagres, double x, double y, Popup popupOst) {
		super(layer, image, x, y);
		this.popupOst = popupOst;
		this.tresor = tresor;
		this.neutre = true;
		this.niveau = rdm.nextInt(4)+1;
		this.nbPiquiers = nbPiquiers;
		this.nbChevaliers = nbChevaliers;
		this.nbOnagres = nbOnagres;
		this.production = null;
		this.ordreDeplacement = null;
		this.ost = null;
		this.porte = new Porte();
		switch(getPorte()) {
		case Constantes.GAUCHE:
			imageView.setRotate(90);
			break;
		case Constantes.HAUT:
			imageView.setRotate(180);
			break;
		case Constantes.DROITE:
			imageView.setRotate(270);
			break;
		default:
			break;
		}
				
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent e) {
        		Chateau dernierChateau = UIsingleton.getUIsingleton().getChateauSelection();
        		UIsingleton.getUIsingleton().setChateauSelection(getChateau());
        		        		
        		if(dernierChateau != null) {
    				if(!dernierChateau.getNeutre()) {
    					if (UIsingleton.getUIsingleton().getDucJoueur().equals(duc)) {
    						if(!(UIsingleton.getUIsingleton().getChateauSelection() == dernierChateau) && dernierChateau.getDuc().equals(UIsingleton.getUIsingleton().getDucJoueur())) {
        						popupOst.display(dernierChateau,UIsingleton.getUIsingleton().getChateauSelection());
        					}
    					}
    					else if(dernierChateau.getDuc().equals(UIsingleton.getUIsingleton().getDucJoueur())) {
    						popupOst.display(dernierChateau,UIsingleton.getUIsingleton().getChateauSelection());
    					}
    				}
    			}
        		/*ContextMenu contextMenu = new ContextMenu();
    			MenuItem attack = new MenuItem("Attack");
    			
    			attack.setOnAction(evt -> {
    				if (!ordre())
    					getChateau().creerOrdre(new Ost( kingdom.getPlayer(), getChateau(), x, y), getChateau(), nbPiquiers, nbChevaliers, nbOnagres);
    			});
    			contextMenu.getItems().addAll(attack);
    			contextMenu.show(getChateau().getView(), e.getScreenX(), e.getScreenY());*/
        	}
        });
	}
	
	
	/* * * * * * * * DEBUT : Fonctions Production * * * * * * * */
	
	/**
	 * Launch a production
	 * @param unite Unit to produce
	 * @throws ProdException Exception throws when a production is already launched
	 */
	public void lancerProduction(int unite) throws ProdException {
		if(enProduction()) {
			throw new ProdException("En cours de production");
			//return false;
		} else {
			
			int cout;
			if(unite==Constantes.PIQUIER)
				cout = Piquier.COUT_PRODUCTION;
			else if(unite==Constantes.CHEVALIER)
				cout = Chevalier.COUT_PRODUCTION;
			else if(unite==Constantes.ONAGRE)
				cout = Onagre.COUT_PRODUCTION;
			else {
				cout = 1000*niveau;
				if(niveau == Constantes.NIVEAU_MAX)
					throw new ProdException("Niveau du Chateau Maximal");
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
		if(production.estAmelioration()) {
			tresor += 1000*niveau;
		} else {
			if(production.getUnite()==Constantes.PIQUIER)
				tresor += Piquier.COUT_PRODUCTION;
			else if(production.getUnite()==Constantes.CHEVALIER)
				tresor += Chevalier.COUT_PRODUCTION;
			else
				tresor += Onagre.COUT_PRODUCTION;
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
			int t = production.getUnite();
			if(t == Constantes.PIQUIER)
				nbPiquiers++;
			else if(t == Constantes.CHEVALIER)
				nbChevaliers++;
			else
				nbOnagres++;
		}
		if (this == UIsingleton.getUIsingleton().getChateauSelection())
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
	 * @param nbPiquiers Number of pikemen 
	 * @param nbChevaliers Number of knights
	 * @param nbOnagres Number of onagers
	 * @return true if order launched, else false
	 */
	public boolean creerOrdre(Ost ost, Chateau cible, int nbPiquiers, int nbChevaliers, int nbOnagres) {
		if(this.nbPiquiers<nbPiquiers || this.nbChevaliers<nbChevaliers || this.nbOnagres<nbOnagres) {
			return false;
		}
		this.ost = ost;
		double x = pos_x + getWidth()/2, y = pos_y + getHeight()/2;
		if(getPorte()==Constantes.DROITE) {
			x += getWidth()/2;
			y -= 10;
		}
		else if(getPorte()==Constantes.GAUCHE) {
			x -= getWidth();
			y -= 10;
		}
		else if(getPorte()==Constantes.HAUT) {
			x -= 10;
			y -= getHeight();
		}
		else {
			x -= 10;
			y += getHeight()/2;
		}
		ordreDeplacement = new Ordre(cible, nbPiquiers, nbChevaliers, nbOnagres, x, y);
		return true;
	}
	
	/**
	 * Manage the "exiting the castle" phase of an ost
	 */
	public void sortirTroupesOrdre() {
		int stop = (ordreDeplacement.getNbTroupes()>=3? 3 : ordreDeplacement.getNbTroupes());
		if(ost == null) return;
		for(int i=0; i<stop; i++) {
			if(ordreDeplacement.getNbOnagres()>0) {
				ordreDeplacement.sortirOnagre(ost);
				nbOnagres--;
			}
			else if(ordreDeplacement.getNbPiquiers()>0) {
				ordreDeplacement.sortirPiquier(ost);
				nbPiquiers--;			}
			else {
				ordreDeplacement.sortirChevalier(ost);
				nbChevaliers--;
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
	public double distance(Chateau c) {
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
	public void finTourChateau() {
		if(!neutre) {
			if(duc.getClass()==IAbasique.class) {
				IAbasique ia = (IAbasique)duc;
				ia.tourChateauIA(this);
			}
				
			tresor += niveau;
			if(enProduction()) {
				production.finTourProduction();
				if(production.finProduction()) {
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
		return nbPiquiers == 0 && nbChevaliers == 0 && nbOnagres == 0;
	}
	
	/**
	 * Getter of the remaining number of pikemen
	 * @return Number of remaining pikemen
	 */
	public boolean restePiquiers() {
		return nbPiquiers > 0;
	}
	
	/**
	 * Getter of the remaining number of knight
	 * @return Number of remaining knight
	 */
	public boolean resteChevaliers() {
		return nbChevaliers > 0;
	}
	
	/**
	 * Getter of the remaining number of onager
	 * @return Number of remaining onager
	 */
	public boolean resteOnagres() {
		return nbOnagres > 0;
	}
	
	/**
	 * Add a pikemen to this castle's army
	 */
	public void ajouterPiquier() {
		nbPiquiers++;
	}
	
	/**
	 * Add a knight to this castle's army
	 */
	public void ajouterChevalier() {
		nbChevaliers++;
	}
	
	/**
	 * Add an onager to this castle's army
	 */
	public void ajouterOnagre() {
		nbOnagres++;
	}
	
	/**
	 * Remove health equivalent to the attack of one pikemen
	 */
	public void recoisAttaquePiquier() {
		viePiquier--;
		if(viePiquier==0) {
			nbPiquiers--;
			viePiquier = Constantes.VIE_PIQUIER;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one knight
	 */
	public void recoisAttaqueChevalier() {
		vieChevalier--;
		if(vieChevalier==0) {
			nbChevaliers--;
			vieChevalier = Constantes.VIE_CHEVALIER;
		}
	}
	
	/**
	 * Remove health equivalent to the attack of one onager
	 */
	public void recoisAttaqueOnagre() {
		vieOnagre--;
		if(vieOnagre==0) {
			nbOnagres--;
			vieOnagre = Constantes.VIE_ONAGRE;
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
		if (this.duc != null) {
			d = this.duc.getNom();
		}
		s += "Castle " + this.pos_x + " " + this.pos_y + " " + d + " " + this.niveau + " " + this.tresor + " " +
				this.nbPiquiers + " " + this.nbChevaliers + " " + this.nbOnagres + " " + this.getPorte(); 
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
	 * Getter of this castle's duc owner
	 * @return Castle's duc owner
	 */
	public Duc getDuc() {
		return duc;
	}
	
	/**
	 * Setter of the owner
	 * @param duc New owner
	 */
	public void setDuc(Duc duc) {
		this.duc = duc;
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
	public int getNbPiquiers() {
		return nbPiquiers;
	}

	/**
	 * Getter of the number of knight of the army
	 * @return Number of knight
	 */
	public int getNbChevaliers() {
		return nbChevaliers;
	}

	/**
	 * Getter of the number of onager of the army
	 * @return Number of onager
	 */
	public int getNbOnagres() {
		return nbOnagres;
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
		case Constantes.GAUCHE:
			imageView.setRotate(90);
			break;
		case Constantes.HAUT:
			imageView.setRotate(180);
			break;
		case Constantes.DROITE:
			imageView.setRotate(270);
			break;
		case Constantes.BAS:
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
	private Chateau getChateau() {
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
