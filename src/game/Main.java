package game;

import kingdom.*;
import kingdom.Castle;

import java.io.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {
    private Kingdom kingdom;
	
	private Pane gameFieldLayer;
	private Pane menuFieldLayer;
	
	private Scene scene;
	
	private Scene menu_scene;
	private AnimationTimer menuLoop;
	
	private AnimationTimer gameLoop;
	private AnimationTimer pauseLoop;
	
	private Input input;
	
	private double dernier_temps = 0;
	
	private static boolean pauseTrigger = false;
	
	private Duke dukePlayer = null;
	private Castle castleSelection = null;
	private Text textFlorins = new Text("--");
	private Text textPikemen = new Text("--");
	private Text textKnight = new Text("--");
	private Text textOnager = new Text("--");
	private Text textPlayer = new Text ("--");
	private Text errorMessageProduction = new Text("");
	private Button buttonProducePikemen = new Button();
	private Button buttonProduceKnight = new Button();
	private Button buttonProduceOnager = new Button();
	private Button buttonProduceAmelioration = new Button();
	private Rectangle barProgression;
	
	private Rectangle borderCastle = new Rectangle();
	
	private Button buttonPlay = new Button("Jouer");
	private Button buttonLoad = new Button("Charger Partie");
	private Button buttonSave = new Button("Sauvegarder Partie");
	
	//private Popup popupOst = new Popup();
	
	Group menu;
	Group root;
	 /**
	  * Initialize all needed components of the game and run the game
	  */
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
		
		initKingdom(1100,630,bounds.getWidth()+4,bounds.getHeight()+4);
		
		kingdom = new Kingdom(gameFieldLayer,1,2,0,1100,630,200,6,3,2,5);
		dukePlayer = kingdom.getCastle(0).getDuke();
		UIsingleton.getUIsingleton().setDukePlayer(dukePlayer);
		borderCastle.setWidth(kingdom.getCastle(0).getWidth());
		borderCastle.setHeight(kingdom.getCastle(0).getHeight());
		borderCastle.setFill(null);
		borderCastle.setStroke(Color.BLUE);
		borderCastle.setStrokeWidth(4);
		
		initMenu(bounds.getWidth()+4,bounds.getHeight()+4,primaryStage);
		
		input = new Input(scene);
		input.addListeners();
		
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
				
				if(UIsingleton.getUIsingleton().getPause()){
					UIsingleton.getUIsingleton().setPause();
					pause();
				}
				
				//********COMPTEUR TEMPS********/
				if((now - dernier_temps) >= Constants.GAME_FREQUENCY/2) {
					dernier_temps = now;
					pauseTrigger = false;
					updateTresor();
				}
				
				/*if(compteur_temps == Constants.DUREE_TOUR) {
					compteur_temps = 0;
				}*/
				//******************************/
				//if(!pauseTrigger)
				kingdom.finishRound();

				if(UIsingleton.getUIsingleton().getCastleSelection()!=castleSelection)
					updateClick();
				if (UIsingleton.getUIsingleton().toUpdateTroops()) {
					updateTroupe();
					UIsingleton.getUIsingleton().setToUpdateTroops(false);
				}
				update();
				
			}
			
			private void processInput(Input input, long now) {
				if (input.isExit()) {
					saveGame();
					Platform.exit();
					System.exit(0);
				} else if (input.isPause()) { 
					pause();
				} else if (input.isLoad()) { 
					loadGame();
				}
			}
		};
		
		pauseLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				
				if(UIsingleton.getUIsingleton().getPause()) {
					UIsingleton.getUIsingleton().setPause();
					pause();
				}
				
				if(UIsingleton.getUIsingleton().getCastleSelection()!=castleSelection)
					updateClick();
				
				if((now - dernier_temps) >= Constants.GAME_FREQUENCY/2) {
					dernier_temps = now;
					pauseTrigger = true;
				}				
			}
			
			private void processInput(Input input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} else if (input.isPause()) { 
					pause();
				}
			}
		};
		
		menuLoop.start();
	}
	
	private void pause() {
		if (pauseTrigger) {
			pauseLoop.stop();
			gameLoop.start();	
		}
		else {
			gameLoop.stop();
			pauseLoop.start();
		}
	}
	
	private void saveGame() {
		File f = new File("Save.sav");
		
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(kingdom.saveGame());
			
			fw.flush();
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void loadGame() {
		try {
			File f = new File("Save.sav");
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			try {
				String line = br.readLine();
				kingdom.clean();
				String args[] = line.split(" ");
				kingdom.setNbCastle(Integer.parseInt(args[0]));
				
				for (int i = 0; i < Integer.parseInt(args[0]); i++) {
					line = br.readLine();
					kingdom.addCastle(line);
				}
				
				br.close();
				
			}catch(IOException e) {
				System.out.println("Erreur lecture : " + e.getMessage());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier de sauvegarde n'a pas été trouvé !");
		}
	}
	
	private void updateTresor() {
		if(castleSelection == null)
			textFlorins.setText("--");
		else
			textFlorins.setText(""+castleSelection.getTreasure());
	}
	
	private void updateTroupe() {
		textPikemen.setText(""+castleSelection.getNbPikemen());
		textKnight.setText(""+castleSelection.getNbKnight());
		textOnager.setText(""+castleSelection.getNbOnager());
		if(!castleSelection.getNeutral()) {
    		textPlayer.setText(castleSelection.getDuke().getName()+" - Niveau "+castleSelection.getLevel());
    		textPlayer.setFill(castleSelection.getDuke().getColor());
		} else {
			textPlayer.setText("Neutral - Niveau "+castleSelection.getLevel());
    		textPlayer.setFill(Color.GREY);
		}
	}
	
	private void updateClick() {
		castleSelection = UIsingleton.getUIsingleton().getCastleSelection();
		
		errorMessageProduction.setText("");
		if(castleSelection == null) {
			borderCastle.relocate(-100, -100);
			textFlorins.setText("--");
			textPikemen.setText("--");
    		textKnight.setText("--");
    		textOnager.setText("--");
    		textPlayer.setText("--");
    		barProgression.setWidth(1);
		}
		else {
			borderCastle.relocate(castleSelection.getPos_x()-2, castleSelection.getPos_y()-2);
			textFlorins.setText(""+castleSelection.getTreasure());
    		textPikemen.setText(""+castleSelection.getNbPikemen());
    		textKnight.setText(""+castleSelection.getNbKnight());
    		textOnager.setText(""+castleSelection.getNbOnager());
    		if(!castleSelection.getNeutral()) {
        		textPlayer.setText(castleSelection.getDuke().getName()+" - Niveau "+castleSelection.getLevel());
        		textPlayer.setFill(castleSelection.getDuke().getColor());
        		if(castleSelection.getDuke().equals(dukePlayer)) {
	        		buttonProducePikemen.setStyle("");
	        		buttonProduceKnight.setStyle("");
	        		buttonProduceOnager.setStyle("");
	        		buttonProduceAmelioration.setStyle("-fx-padding: 12 67 12 67;");
        		}
        		else {
        			buttonProducePikemen.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceKnight.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceOnager.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceAmelioration.setStyle("-fx-background-color: #E9E9E9;-fx-padding: 12 67 12 67;");
        		}
        		if(castleSelection.inProduction()) {
    				barProgression.setWidth(castleSelection.getProduction().pourcentage()*160+1);
        		} else {
        			barProgression.setWidth(1);
        		}
    		}
    		else {
    			textPlayer.setText("Neutre - Niveau "+castleSelection.getLevel());
        		textPlayer.setFill(Color.GREY);
        		buttonProducePikemen.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceKnight.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceOnager.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceAmelioration.setStyle("-fx-background-color: #E9E9E9;-fx-padding: 12 67 12 67;");
    		}
		}
	}
	
	private void update() {
		castleSelection = UIsingleton.getUIsingleton().getCastleSelection();
	
		//dernierCastleSelection = castleSelection;
		if(castleSelection == null) {
/*			textPikemen.setText("--");
    		textKnight.setText("--");
    		textOnager.setText("--");
    		textPlayer.setText("--");*/
		}
		else {
    		/*textPikemen.setText(""+castleSelection.getNbPikemen());
    		textKnight.setText(""+castleSelection.getNbKnight());
    		textOnager.setText(""+castleSelection.getNbOnager());*/
    		if(castleSelection.inProduction()) {
				barProgression.setWidth(castleSelection.getProduction().pourcentage()*160+1);
    		} else {
    			barProgression.setWidth(1);
    		}
    		/*if(!castleSelection.getNeutral()) {
        		textPlayer.setText(castleSelection.getDuke().getNom()+" - Niveau "+castleSelection.getNiveau());
        		textPlayer.setFill(castleSelection.getDuke().getCouleur());
    		}
    		else {
    			textPlayer.setText("Neutral - Niveau "+castleSelection.getNiveau());
        		textPlayer.setFill(Color.GREY);
    		}*/
		}
	}
	
	private void updateMenu() {
		
	}
	
	private void initMenu(double l_ecran, double h_ecran, Stage stage) {
		Rectangle fond = new Rectangle(l_ecran,h_ecran);
		
		fond.setFill(Color.GREY);
		
		buttonPlay.relocate(l_ecran/2-140, h_ecran/2-80);
		buttonPlay.getStyleClass().add("gbutton_menu");
		buttonLoad.relocate(l_ecran/2-140, h_ecran/2);
		buttonLoad.getStyleClass().add("gbutton_menu");
		buttonLoad.setStyle("-fx-padding: 10 69 10 69;");

		menuFieldLayer.getChildren().add(fond);
		menuFieldLayer.getChildren().addAll(buttonPlay,buttonLoad);
		
		buttonPlay.setOnAction(e -> {
			stage.setScene(scene);
			menuLoop.stop();
			gameLoop.start();
		});
		
		buttonLoad.setOnAction(e -> {
			stage.setScene(scene);
			menuLoop.stop();
			loadGame();
			dukePlayer = kingdom.getCastle(0).getDuke();
			UIsingleton.getUIsingleton().setDukePlayer(dukePlayer);
			gameLoop.start();
		});
	}
	
	private void initKingdom(double longueur, double hauteur, double l_ecran, double h_ecran) {
		Pane layoutProduction = new Pane();
		layoutProduction.relocate(longueur+5, 0);
		
		Rectangle fond = new Rectangle(longueur,h_ecran);
		Rectangle bar_ressources = new Rectangle(longueur,28);
		Rectangle bar_actions = new Rectangle(l_ecran-longueur,h_ecran);
		Rectangle bar_amelioration = new Rectangle(162, 15);
		barProgression = new Rectangle(1,13);
		ImageView img_florins = new ImageView(new Image(getClass().getResource("/images/coins.png").toExternalForm(),28,28,true,true));
		ImageView img_pikemen = new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true));
		ImageView img_knight = new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true));
		ImageView img_onager = new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true));
		Text produce = new Text("Produire");
		
		buttonProducePikemen.setGraphic(new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceKnight.setGraphic(new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceOnager.setGraphic(new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceAmelioration.setGraphic(new ImageView(new Image(getClass().getResource("/images/up.png").toExternalForm(), 28, 28, true, false)));
		
		fond.setFill(Color.GREY);
		fond.setOnMouseClicked(e -> {
			UIsingleton.getUIsingleton().setCastleSelection(null);
		});
		bar_ressources.setArcWidth(5);
		bar_ressources.setArcHeight(5);
		bar_ressources.relocate(0, hauteur);
		bar_ressources.setFill(Color.DARKSLATEGREY);
		bar_actions.relocate(longueur, 0);
		bar_actions.setFill(Color.ANTIQUEWHITE);
		bar_actions.setStroke(Color.BLACK);
		bar_amelioration.relocate(0, 180);
		bar_amelioration.setFill(Color.ANTIQUEWHITE);
		bar_amelioration.setStroke(Color.BLACK);
		barProgression.relocate(1, 181);
		barProgression.setFill(Color.AQUA);
		borderCastle.relocate(-100, -100);
		
		textFlorins.relocate(70, hauteur+4);
		textFlorins.getStyleClass().add("gtext");
		textPikemen.relocate(220, hauteur+4);
		textPikemen.getStyleClass().add("gtext");
		textKnight.relocate(320, hauteur+4);
		textKnight.getStyleClass().add("gtext");
		textOnager.relocate(420, hauteur+4);
		textOnager.getStyleClass().add("gtext");
		textPlayer.relocate(600, hauteur+4);
		textPlayer.getStyleClass().add("gtext");
		produce.relocate(0, 30);
		produce.setStyle("-fx-fill: #545454;-fx-font-size: 30;");
		errorMessageProduction.relocate(1,  200);
		errorMessageProduction.setStyle("-fx-fill: red; fx-font-size: 14");
		
		img_florins.relocate(20, hauteur);
		img_pikemen.relocate(180, hauteur);
		img_knight.relocate(280, hauteur);
		img_onager.relocate(380, hauteur);
		
		buttonProducePikemen.relocate(0, 50);
		buttonProducePikemen.getStyleClass().add("gbutton");
		buttonProduceKnight.relocate(55, 50);
		buttonProduceKnight.getStyleClass().add("gbutton");
		buttonProduceOnager.relocate(110, 50);
		buttonProduceOnager.getStyleClass().add("gbutton");
		buttonProduceAmelioration.relocate(0, 120);
		buttonProduceAmelioration.getStyleClass().add("gbutton");
		buttonProduceAmelioration.setStyle("-fx-padding: 12 67 12 67;");
		
		buttonSave.relocate(0, 300);
		buttonSave.getStyleClass().add("gbutton_menu");
		buttonSave.setStyle("-fx-padding: 10 14 10 14;");
		
		
		gameFieldLayer.getChildren().add(fond);
		gameFieldLayer.getChildren().add(bar_ressources);
		gameFieldLayer.getChildren().add(bar_actions);
		gameFieldLayer.getChildren().add(borderCastle);
		gameFieldLayer.getChildren().add(img_florins);
		gameFieldLayer.getChildren().add(textFlorins);
		gameFieldLayer.getChildren().add(img_pikemen);
		gameFieldLayer.getChildren().add(textPikemen);
		gameFieldLayer.getChildren().add(img_knight);
		gameFieldLayer.getChildren().add(textKnight);
		gameFieldLayer.getChildren().add(img_onager);
		gameFieldLayer.getChildren().add(textOnager);
		gameFieldLayer.getChildren().add(textPlayer);
		layoutProduction.getChildren().addAll(bar_amelioration,barProgression,produce,errorMessageProduction);
		layoutProduction.getChildren().addAll(buttonProducePikemen,buttonProduceKnight,buttonProduceOnager,buttonProduceAmelioration,buttonSave);
		gameFieldLayer.getChildren().add(layoutProduction);
		
		UIsingleton.getUIsingleton().setErrorLabelProduction(errorMessageProduction);
		
		buttonProducePikemen.setOnAction(e -> {
			if (castleSelection!=null) {
				if(!castleSelection.getNeutral()){
					if(castleSelection.getDuke().equals(dukePlayer)) {
		    			try {
		    				castleSelection.launchProduction(Constants.PIKEMEN);
		    				updateTresor();
		    				errorMessageProduction.setText("");
		    			} catch (ProdException err) {
		    				err.printError();
		    			}
		    		}
				}
			}
		});
		
		buttonProduceKnight.setOnAction(e -> {
			if (castleSelection!=null) {
				if(!castleSelection.getNeutral()){
					if(castleSelection.getDuke().equals(dukePlayer)) {
						try {
		    				castleSelection.launchProduction(Constants.KNIGHT);
		    				updateTresor();
		    				errorMessageProduction.setText("");
		    			} catch (ProdException err) {
		    				err.printError();
		    			}
		    		}
				}
			}
		});
		
		buttonProduceOnager.setOnAction(e -> {
			if (castleSelection!=null) {
				if(!castleSelection.getNeutral()){
					if(castleSelection.getDuke().equals(dukePlayer)) {
						try {
		    				castleSelection.launchProduction(Constants.ONAGER);
		    				updateTresor();
		    				errorMessageProduction.setText("");
		    			} catch (ProdException err) {
		    				err.printError();
		    			}
		    		}
				}
			}				
		});
		
		buttonProduceAmelioration.setOnAction(e -> {
			if (castleSelection!=null) {
				if(!castleSelection.getNeutral()){
					if(castleSelection.getDuke().equals(dukePlayer)) {
						try {
		    				castleSelection.launchProduction(Constants.AMELIORATION);
		    				updateTresor();
		    				errorMessageProduction.setText("");
		    			} catch (ProdException err) {
		    				err.printError();
		    			}
		    		}
				}
			}
		});
		
		buttonSave.setOnAction(e -> {
			saveGame();
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
