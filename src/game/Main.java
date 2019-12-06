 package game;
import royaume.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {
    private Royaume royaume;
	
	private Pane gameFieldLayer;
	private Pane menuFieldLayer;
	
	private Scene scene;	
	
	private Scene menu_scene;
	private AnimationTimer menuLoop;
	
	private AnimationTimer gameLoop;
	private AnimationTimer pauseLoop;
	
	private Input input;
	
	private int compteur_temps = 0;
	private double dernier_temps = 0;
	
	public boolean pauseTrigger = false;
	
	private Duc ducJoueur = null;
	private Chateau dernierChateau = null;
	private Chateau chateauSelection = null;
	private Text texteFlorins = new Text("--");
	private Text textePiquiers = new Text("--");
	private Text texteChevaliers = new Text("--");
	private Text texteOnagres = new Text("--");
	private Text texteJoueur = new Text ("--");
	private Text messageErreurProduction = new Text("");
	private Button boutonProduirePiquier = new Button();
	private Button boutonProduireChevalier = new Button();
	private Button boutonProduireOnagre = new Button();
	private Button boutonProduireAmelioration = new Button();
	private Rectangle barreProgression;
	
	private Rectangle bordureChateau = new Rectangle();
	
	private Button boutonJouer = new Button("Jouer");
	
	//private Popup popupOst = new Popup();
	
	Group menu;
	Group root;
		
	@Override
	public void start(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		primaryStage.setX(bounds.getMinX()-2);
		primaryStage.setY(bounds.getMinY()-2);
		primaryStage.setWidth(bounds.getWidth()+4);
		primaryStage.setHeight(bounds.getHeight()+4);
		primaryStage.setTitle("Le projet qui vaut 23/20");
		primaryStage.setResizable(false);
		
		menu = new Group();
		menu_scene = new Scene(menu);
		menu_scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		root = new Group();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		
		primaryStage.setScene(menu_scene);
		primaryStage.show();
		// create layers
		menuFieldLayer = new Pane();
		menu.getChildren().add(menuFieldLayer);
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		initRoyaume(1100,630,bounds.getWidth()+4,bounds.getHeight()+4);
		royaume = new Royaume(gameFieldLayer,1,0,0,1100,630,200,8,3,2,5);
		ducJoueur = royaume.getChateau(0).getDuc();
		UIsingleton.getUIsingleton().setDucJoueur(ducJoueur);
		initMenu(bounds.getWidth()+4,bounds.getHeight()+4,primaryStage);
		bordureChateau.setWidth(royaume.getChateau(0).getWidth());
		bordureChateau.setHeight(royaume.getChateau(0).getHeight());
		bordureChateau.setFill(null);
		bordureChateau.setStroke(Color.BLUE);
		bordureChateau.setStrokeWidth(4);
		
		loadGame();
		
		menuLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateMenu();
			}
		};
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				//********COMPTEUR TEMPS********/
				if((now - dernier_temps) >= Constantes.GAME_FREQUENCY/2) {
					dernier_temps = now;
					compteur_temps++;
					pauseTrigger = false;
					updateTresor();
				}
				/*if(compteur_temps == Constantes.DUREE_TOUR) {
					compteur_temps = 0;
				}*/
				//******************************/
				//if(!pauseTrigger)
				royaume.finTour();
				
				if(UIsingleton.getUIsingleton().getChateauSelection()!=chateauSelection)
					updateClick();
				if (UIsingleton.getUIsingleton().toUpdateTroupes()) {
					updateTroupe();
					UIsingleton.getUIsingleton().setToUpdateTroupes(false);
				}
				update();
				
				
				/************IDEE DE DINGUE
				 * Creer une variable dans UIsingleton qui dit si un update a besoin d'être fait
				 */
				
			}
			
			private void processInput(Input input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} else if (input.isPause()) { 
					//pauseTrigger = !pauseTrigger;
					pause(now);
				}
			}
		};
		
		pauseLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				
				if(UIsingleton.getUIsingleton().getChateauSelection()!=chateauSelection)
					updateClick();
				
				if((now - dernier_temps) >= Constantes.GAME_FREQUENCY/2) {
					dernier_temps = now;
					pauseTrigger = true;
				}				
			}
			
			private void processInput(Input input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} else if (input.isPause()) { 
					pause(now);
				}

			}

		};
		
		menuLoop.start();
	}
	
	public void pause(long now) {
		if (pauseTrigger) {
			pauseLoop.stop();
			gameLoop.start();	
		}
		else {
			gameLoop.stop();
			pauseLoop.start();
		}
	}
	
	private void loadGame() {
		
		input = new Input(scene);
		input.addListeners();
		
	}
	
	private void updateTresor() {
		if(chateauSelection == null)
			texteFlorins.setText("--");
		else
			texteFlorins.setText(""+chateauSelection.getTresor());
	}
	
	private void updateTroupe() {
		textePiquiers.setText(""+chateauSelection.getNbPiquiers());
		texteChevaliers.setText(""+chateauSelection.getNbChevaliers());
		texteOnagres.setText(""+chateauSelection.getNbOnagres());
		if(!chateauSelection.getNeutre()) {
    		texteJoueur.setText(chateauSelection.getDuc().getNom()+" - Niveau "+chateauSelection.getNiveau());
    		texteJoueur.setFill(chateauSelection.getDuc().getCouleur());
		} else {
			texteJoueur.setText("Neutre - Niveau "+chateauSelection.getNiveau());
    		texteJoueur.setFill(Color.GREY);
		}
	}
	
	private void updateClick() {
		chateauSelection = UIsingleton.getUIsingleton().getChateauSelection();
		
		
		
		messageErreurProduction.setText("");
		if(chateauSelection == null) {
			bordureChateau.relocate(-100, -100);
			texteFlorins.setText("--");
			textePiquiers.setText("--");
    		texteChevaliers.setText("--");
    		texteOnagres.setText("--");
    		texteJoueur.setText("--");
    		dernierChateau = null;
		}
		else {
			dernierChateau = chateauSelection;
			bordureChateau.relocate(chateauSelection.getPos_x()-2, chateauSelection.getPos_y()-2);
			texteFlorins.setText(""+chateauSelection.getTresor());
    		textePiquiers.setText(""+chateauSelection.getNbPiquiers());
    		texteChevaliers.setText(""+chateauSelection.getNbChevaliers());
    		texteOnagres.setText(""+chateauSelection.getNbOnagres());
    		if(!chateauSelection.getNeutre()) {
        		texteJoueur.setText(chateauSelection.getDuc().getNom()+" - Niveau "+chateauSelection.getNiveau());
        		texteJoueur.setFill(chateauSelection.getDuc().getCouleur());
        		boutonProduirePiquier.setStyle("");
        		boutonProduireChevalier.setStyle("");
        		boutonProduireOnagre.setStyle("");
        		boutonProduireAmelioration.setStyle("-fx-padding: 12 67 12 67;");
        		if(chateauSelection.enProduction()) {
    				barreProgression.setWidth(chateauSelection.getProduction().pourcentage()*160+1);
        		} else {
        			barreProgression.setWidth(1);
        		}
    		}
    		else {
    			texteJoueur.setText("Neutre - Niveau "+chateauSelection.getNiveau());
        		texteJoueur.setFill(Color.GREY);
        		boutonProduirePiquier.setStyle("-fx-background-color: #E9E9E9");
        		boutonProduireChevalier.setStyle("-fx-background-color: #E9E9E9");
        		boutonProduireOnagre.setStyle("-fx-background-color: #E9E9E9");
        		boutonProduireAmelioration.setStyle("-fx-background-color: #E9E9E9;-fx-padding: 12 67 12 67;");
    		}
		}
	}
	
	private void update() {
		chateauSelection = UIsingleton.getUIsingleton().getChateauSelection();
	
		//dernierChateauSelection = chateauSelection;
		if(chateauSelection == null) {
/*			textePiquiers.setText("--");
    		texteChevaliers.setText("--");
    		texteOnagres.setText("--");
    		texteJoueur.setText("--");*/
		}
		else {
    		/*textePiquiers.setText(""+chateauSelection.getNbPiquiers());
    		texteChevaliers.setText(""+chateauSelection.getNbChevaliers());
    		texteOnagres.setText(""+chateauSelection.getNbOnagres());*/
    		if(chateauSelection.enProduction()) {
				barreProgression.setWidth(chateauSelection.getProduction().pourcentage()*160+1);
    		} else {
    			barreProgression.setWidth(1);
    		}
    		/*if(!chateauSelection.getNeutre()) {
        		texteJoueur.setText(chateauSelection.getDuc().getNom()+" - Niveau "+chateauSelection.getNiveau());
        		texteJoueur.setFill(chateauSelection.getDuc().getCouleur());
    		}
    		else {
    			texteJoueur.setText("Neutre - Niveau "+chateauSelection.getNiveau());
        		texteJoueur.setFill(Color.GREY);
    		}*/
		}
	}
	
	private void updateMenu() {
		
	}
	
	private void initMenu(double l_ecran, double h_ecran, Stage stage) {
		Rectangle fond = new Rectangle(l_ecran,h_ecran);
		
		fond.setFill(Color.GREY);
		
		boutonJouer.relocate(l_ecran/2-110, h_ecran/2-80);
		boutonJouer.getStyleClass().add("bouton_menu");

		menuFieldLayer.getChildren().add(fond);
		menuFieldLayer.getChildren().add(boutonJouer);
		
		boutonJouer.setOnAction(e -> {
			stage.setScene(scene);
			menuLoop.stop();
			gameLoop.start();
		});
	}
	
	private void initRoyaume(double longueur, double hauteur, double l_ecran, double h_ecran) {
		Rectangle fond = new Rectangle(longueur,h_ecran);
		Rectangle barre_ressources = new Rectangle(longueur,28);
		Rectangle barre_actions = new Rectangle(l_ecran-longueur,h_ecran);
		Rectangle barre_amelioration = new Rectangle(162, 15);
		barreProgression = new Rectangle(1,13);
		ImageView img_florins = new ImageView(new Image(getClass().getResource("/images/coins.png").toExternalForm(),28,28,true,true));
		ImageView img_piquier = new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true));
		ImageView img_chevalier = new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true));
		ImageView img_onagre = new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true));
		Text produire = new Text("Produire");
		
		boutonProduirePiquier.setGraphic(new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true)));
		boutonProduireChevalier.setGraphic(new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true)));
		boutonProduireOnagre.setGraphic(new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true)));
		boutonProduireAmelioration.setGraphic(new ImageView(new Image(getClass().getResource("/images/up.png").toExternalForm(), 28, 28, true, false)));
		
		fond.setFill(Color.GREY);
		fond.setOnMouseClicked(e -> {
			UIsingleton.getUIsingleton().setChateauSelection(null);
		});
		barre_ressources.setArcWidth(5);
		barre_ressources.setArcHeight(5);
		barre_ressources.relocate(0, hauteur);
		barre_ressources.setFill(Color.DARKSLATEGREY);
		barre_actions.relocate(longueur, 0);
		barre_actions.setFill(Color.ANTIQUEWHITE);
		barre_actions.setStroke(Color.BLACK);
		barre_amelioration.relocate(longueur+5, 180);
		barre_amelioration.setFill(Color.ANTIQUEWHITE);
		barre_amelioration.setStroke(Color.BLACK);
		barreProgression.relocate(longueur+6, 181);
		barreProgression.setFill(Color.AQUA);
		bordureChateau.relocate(-100, -100);
		
		texteFlorins.relocate(70, hauteur+4);
		texteFlorins.getStyleClass().add("texte");
		textePiquiers.relocate(220, hauteur+4);
		textePiquiers.getStyleClass().add("texte");
		texteChevaliers.relocate(320, hauteur+4);
		texteChevaliers.getStyleClass().add("texte");
		texteOnagres.relocate(420, hauteur+4);
		texteOnagres.getStyleClass().add("texte");
		texteJoueur.relocate(600, hauteur+4);
		texteJoueur.getStyleClass().add("texte");
		produire.relocate(longueur+5, 30);
		produire.setStyle("-fx-fill: #545454;-fx-font-size: 30;");
		messageErreurProduction.relocate(longueur+6,  200);
		messageErreurProduction.setStyle("-fx-fill: red; fx-font-size: 14");
		
		img_florins.relocate(20, hauteur);
		img_piquier.relocate(180, hauteur);
		img_chevalier.relocate(280, hauteur);
		img_onagre.relocate(380, hauteur);
		
		boutonProduirePiquier.relocate(longueur+5, 50);
		boutonProduirePiquier.getStyleClass().add("bouton");
		boutonProduireChevalier.relocate(longueur+60, 50);
		boutonProduireChevalier.getStyleClass().add("bouton");
		boutonProduireOnagre.relocate(longueur+115, 50);
		boutonProduireOnagre.getStyleClass().add("bouton");
		boutonProduireAmelioration.relocate(longueur+5, 120);
		boutonProduireAmelioration.getStyleClass().add("bouton");
		boutonProduireAmelioration.setStyle("-fx-padding: 12 67 12 67;");
		
		gameFieldLayer.getChildren().add(fond);
		gameFieldLayer.getChildren().add(barre_ressources);
		gameFieldLayer.getChildren().add(barre_actions);
		gameFieldLayer.getChildren().add(barre_amelioration);
		gameFieldLayer.getChildren().add(barreProgression);
		gameFieldLayer.getChildren().add(bordureChateau);
		gameFieldLayer.getChildren().add(img_florins);
		gameFieldLayer.getChildren().add(texteFlorins);
		gameFieldLayer.getChildren().add(img_piquier);
		gameFieldLayer.getChildren().add(textePiquiers);
		gameFieldLayer.getChildren().add(img_chevalier);
		gameFieldLayer.getChildren().add(texteChevaliers);
		gameFieldLayer.getChildren().add(img_onagre);
		gameFieldLayer.getChildren().add(texteOnagres);
		gameFieldLayer.getChildren().add(texteJoueur);
		gameFieldLayer.getChildren().add(produire);
		gameFieldLayer.getChildren().add(messageErreurProduction);
		gameFieldLayer.getChildren().add(boutonProduirePiquier);
		gameFieldLayer.getChildren().add(boutonProduireChevalier);
		gameFieldLayer.getChildren().add(boutonProduireOnagre);
		gameFieldLayer.getChildren().add(boutonProduireAmelioration);
		
		boutonProduirePiquier.setOnAction(e -> {
			if(!chateauSelection.getNeutre()) {
    			boolean prod = chateauSelection.lancerProduction(Constantes.PIQUIER);
    			if(prod) {
    				updateTresor();
    				messageErreurProduction.setText("");
    			} else {
    				messageErreurProduction.setText("Pas assez de florins");
    			}
    		}
		});
		boutonProduireChevalier.setOnAction(e -> {
			if(!chateauSelection.getNeutre()) {
    			boolean prod = chateauSelection.lancerProduction(Constantes.CHEVALIER);
    			if(prod) {
    				updateTresor();
    				messageErreurProduction.setText("");
    			} else {
    				messageErreurProduction.setText("Pas assez de florins");
    			}
    		}
		});
		boutonProduireOnagre.setOnAction(e -> {
			if(!chateauSelection.getNeutre()) {
    			boolean prod = chateauSelection.lancerProduction(Constantes.ONAGRE);
    			if(prod) {
    				updateTresor();
    				messageErreurProduction.setText("");
    			} else {
    				messageErreurProduction.setText("Pas assez de florins");
    			}
    		}
		});
		boutonProduireAmelioration.setOnAction(e -> {
			if(!chateauSelection.getNeutre()) {
    			boolean prod = chateauSelection.lancerProduction(Constantes.AMELIORATION);
    			if(prod) {
    				updateTresor();
    				messageErreurProduction.setText("");
    			}
    			else {
    				messageErreurProduction.setText("Pas assez de florins");
    			}
    		}
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

// ANTISECHE QCM DE MORT
/* boolean setX(double X)
 * if X<0 return false
 * sinon this.X = X return true
 * on peut pas ! On lance une exception
 */
/* setX -> pas bonne coordonnée : boucle pour demander nouvelle coordonnée?
 * Pas bien car ce n'est pas forcément un humain qui va appeler un setter
 */
