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

/**
 * Main Class
 * 
 *
 */
public class Main extends Application {
    private Kingdom kingdom;
	
	private Pane gameFieldLayer;
	private Pane menuFieldLayer;
	private Pane endFieldLayer;
	
	private Scene scene;
	
	private Scene menu_scene;
	private Scene end_scene;
	private AnimationTimer menuLoop;
	
	private AnimationTimer gameLoop;
	private AnimationTimer pauseLoop;
	
	private Input input;
	
	private int timerErrorMessageProduction = 0;
	private double last_time = 0;
	
	private static boolean pauseTrigger = false;
	
	private Duke dukePlayer = null;
	private Castle castleSelection = null;
	private Text textFlorins = new Text("--");
	private Text textPikemen = new Text("--");
	private Text textKnight = new Text("--");
	private Text textOnager = new Text("--");
	private Text textBarrack = new Text("--");
	private Text textPlayer = new Text ("--");
	private Text errorMessageProduction = new Text("");
	private Button buttonProducePikemen = new Button();
	private Button buttonProduceKnight = new Button();
	private Button buttonProduceOnager = new Button();
	private Button buttonProduceAmelioration = new Button();
	private Button buttonProduceBarrack = new Button();
	private Button buttonProduceShield = new Button();
	//private ArrayList<Rectangle> barProgressions = new ArrayList<Rectangle>();
	private Rectangle[] barProgressions = new Rectangle[Constants.LEVEL_MAX];
	private Rectangle borderCastle = new Rectangle();
	
	private Button buttonPlay = new Button("Jouer");
	private Button buttonLoad = new Button("Charger Partie");
	private Button buttonSave = new Button("Sauvegarder Partie");
	
	private String winnerName;
	private Text textWinner = new Text("");
	private Button buttonToMenu = new Button("Menu Principal");
	
	private Image img_broken_shield = new Image(getClass().getResource("/images/BrokenShield.png").toExternalForm(), 28, 28, false, true);
	private Image img_shield = new Image(getClass().getResource("/images/Shield.png").toExternalForm(), 28, 28, false, true);
	private ImageView img_v_shield = new ImageView(img_shield);
	
	//private Popup popupOst = new Popup();
	
	Group menu;
	Group end;
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
		primaryStage.setTitle("Dukes of the Realm");
		primaryStage.setResizable(false);
		
		menu = new Group();
		menu_scene = new Scene(menu);
		menu_scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		end = new Group();
		end_scene = new Scene(end);
		end_scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		root = new Group();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		
		primaryStage.setScene(menu_scene);
		primaryStage.show();
		// create layers
		menuFieldLayer = new Pane();
		menu.getChildren().add(menuFieldLayer);
		endFieldLayer = new Pane();
		end.getChildren().add(endFieldLayer);
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		initKingdom(1100,630,bounds.getWidth()+4,bounds.getHeight()+4);
		
		borderCastle.setFill(null);
		borderCastle.setStroke(Color.BLUE);
		borderCastle.setStrokeWidth(4);
		
		initMenu(bounds.getWidth()+4,bounds.getHeight()+4,primaryStage);
		initEnd(bounds.getWidth()+4,bounds.getHeight()+4,primaryStage);
		
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
				if((now - last_time) >= Constants.GAME_FREQUENCY/2) {
					last_time = now;
					pauseTrigger = false;
					updateTresor();
					if(timerErrorMessageProduction > 0) {
						timerErrorMessageProduction--;
					}
					else {
						if(timerErrorMessageProduction == 0) {
							errorMessageProduction.setText("");
							timerErrorMessageProduction--;
						}
					}
				}
				
				/*if(compteur_temps == Constants.DUREE_TOUR) {
					compteur_temps = 0;
				}*/
				
				kingdom.finishRound();

				if(UIsingleton.getUIsingleton().getCastleSelection()!=castleSelection)
					updateClick();
				if (UIsingleton.getUIsingleton().toUpdateTroops()) {
					updateTroupe();
					UIsingleton.getUIsingleton().setToUpdateTroops(false);
				}
				update();
				
				if((winnerName = kingdom.finishedGame()) != null) {
					gameLoop.stop();
					kingdom.clean();
					textWinner.setText("Victoire de " + winnerName);
					menuLoop.start();
					primaryStage.setScene(end_scene);
				}
				
			}
			
			private void processInput(Input input, long now) {
				if (input.isExit()) {
					saveGame();
					Platform.exit();
					System.exit(0);
				} else if (input.isPause()) { 
					pause();
				} else if (input.isLoad()) { 
					try {
						loadGame();
					}catch(FileNotFoundException e) {
						System.out.println("Le fichier de sauvegarde n'a pas été trouvé !");
					}
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
				
				if((now - last_time) >= Constants.GAME_FREQUENCY/2) {
					last_time = now;
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
	
	/**
	 * Pause the game or unpause the game
	 */
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
	
	/**
	 * Save the game information in a file named save.txt
	 */
	private void saveGame() {
		File f = new File("Save.txt");
		
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(kingdom.saveGame());
			
			fw.flush();
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Load the game from a file named save.txt
	 * @throws FileNotFoundException Exception threw when the save file is not found
	 */
	private void loadGame() throws FileNotFoundException {

		File f = new File("Save.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		try {
			String line = br.readLine();
			kingdom.clean();
			String args[] = line.split(" ");
			kingdom.setNbCastle(Integer.parseInt(args[0]));			
			
			for (int i = 0; i < Integer.parseInt(args[0]); i++) {
				line = br.readLine();
				kingdom.addCastleFromSave(line);
			}
			
			br.close();
			
		}catch(IOException e) {
			System.out.println("Erreur lecture : " + e.getMessage());
		}
	}
	
	/**
	 * Update the display of treasury
	 */
	private void updateTresor() {
		if(castleSelection == null)
			textFlorins.setText("--");
		else
			textFlorins.setText(""+castleSelection.getTreasure());
	}
	
	/**
	 * Update the display of the number of units and level
	 */
	private void updateTroupe() {
		textPikemen.setText(""+castleSelection.getNbPikemen());
		textKnight.setText(""+castleSelection.getNbKnight());
		textOnager.setText(""+castleSelection.getNbOnager());
		textBarrack.setText(""+castleSelection.getNbBarracks());
		if(!castleSelection.getNeutral()) {
    		textPlayer.setText(castleSelection.getDuke().getName()+" - Niveau "+castleSelection.getLevel());
    		textPlayer.setFill(castleSelection.getDuke().getColor());
		} else {
			textPlayer.setText("Neutre - Niveau "+castleSelection.getLevel());
    		textPlayer.setFill(Color.GREY);
		}
	}
	
	/**
	 * Update the display of the game for each click
	 */
	private void updateClick() {
		castleSelection = UIsingleton.getUIsingleton().getCastleSelection();
		
		errorMessageProduction.setText("");
		if(castleSelection == null) {
			borderCastle.relocate(-100, -100);
			textFlorins.setText("--");
			textPikemen.setText("--");
    		textKnight.setText("--");
    		textOnager.setText("--");
    		textBarrack.setText("--");
    		textPlayer.setText("--");
    		img_v_shield.setImage(null);
    		for (int i = 0; i<Constants.LEVEL_MAX; i++) {
    			barProgressions[i].setWidth(1);
    		}
		}
		else {
			borderCastle.relocate(castleSelection.getPos_x()-2, castleSelection.getPos_y()-2);
			textFlorins.setText(""+castleSelection.getTreasure());
    		textPikemen.setText(""+castleSelection.getNbPikemen());
    		textKnight.setText(""+castleSelection.getNbKnight());
    		textOnager.setText(""+castleSelection.getNbOnager());
    		textBarrack.setText(""+castleSelection.getNbBarracks());
    		if (castleSelection.isShielded()) {
    			img_v_shield.setImage(img_shield);
    		}
    		else {
    			img_v_shield.setImage(img_broken_shield);
    		}
    		if(!castleSelection.getNeutral()) {
        		textPlayer.setText(castleSelection.getDuke().getName()+" - Niveau "+castleSelection.getLevel());
        		textPlayer.setFill(castleSelection.getDuke().getColor());
        		if(castleSelection.getDuke().equals(dukePlayer)) {
	        		buttonProducePikemen.setStyle("");
	        		buttonProduceKnight.setStyle("");
	        		buttonProduceOnager.setStyle("");
	        		buttonProduceAmelioration.setStyle("-fx-padding: 12 67 12 67;");
	        		buttonProduceBarrack.setStyle("");
	        		buttonProduceShield.setStyle("");

        		}
        		else {
        			buttonProducePikemen.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceKnight.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceOnager.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceAmelioration.setStyle("-fx-background-color: #E9E9E9;-fx-padding: 12 67 12 67;");
            		buttonProduceBarrack.setStyle("-fx-background-color: #E9E9E9");
            		buttonProduceShield.setStyle("-fx-background-color: #E9E9E9");
        		}
        		if(castleSelection.inProduction()) {
        			for (int i = 0; i<castleSelection.getProduction().size(); i++) {
        				barProgressions[i].setWidth(castleSelection.getProduction().get(i).pourcentage()*160+1);
        			}
        			for (int i = castleSelection.getProduction().size(); i<Constants.LEVEL_MAX; i++) {
        				barProgressions[i].setWidth(1);
        			}
        		} else {
        			for (int i = 0; i< Constants.LEVEL_MAX; i++) {
            			barProgressions[i].setWidth(1);
            		}
        		}
    		}
    		else {
    			textPlayer.setText("Neutre - Niveau "+castleSelection.getLevel());
        		textPlayer.setFill(Color.GREY);
        		buttonProducePikemen.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceKnight.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceOnager.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceAmelioration.setStyle("-fx-background-color: #E9E9E9;-fx-padding: 12 67 12 67;");
        		buttonProduceBarrack.setStyle("-fx-background-color: #E9E9E9");
        		buttonProduceShield.setStyle("-fx-background-color: #E9E9E9");
    		}
		}
	}
	
	/**
	 * Update the display of the progression bar
	 */
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
			if (castleSelection.isShielded()) {
    			img_v_shield.setImage(img_shield);
    		}
    		else {
    			img_v_shield.setImage(img_broken_shield);
    		}
			if(castleSelection.inProduction()) {
    			for (int i = 0; i<castleSelection.getProduction().size(); i++) {
    				barProgressions[i].setWidth(castleSelection.getProduction().get(i).pourcentage()*160+1);
    			}
    			for (int i = castleSelection.getProduction().size(); i<Constants.LEVEL_MAX; i++) {
    				barProgressions[i].setWidth(1);
    			}
    		} else {
    			for (int i = 0; i< Constants.LEVEL_MAX; i++) {
        			barProgressions[i].setWidth(1);
        		}
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
	
	/**
	 * 
	 */
	private void updateMenu() {
		
	}
	
	/**
	 * Initialize the menu screen 
	 * @param l_ecran Width of the screen
	 * @param h_ecran Height of the screen
	 * @param stage Stage in which the menu screen must be
	 */
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
			kingdom = new Kingdom(gameFieldLayer,1,2,1,1100,630,200,6,3,2,0);
			dukePlayer = kingdom.getCastle(0).getDuke();
			UIsingleton.getUIsingleton().setDukePlayer(dukePlayer);
			borderCastle.setWidth(kingdom.getCastle(0).getWidth());
			borderCastle.setHeight(kingdom.getCastle(0).getHeight());
			stage.setScene(scene);
			menuLoop.stop();
			gameLoop.start();
		});
		
		buttonLoad.setOnAction(e -> {
			try {
				isSaveFile();
				stage.setScene(scene);
				menuLoop.stop();
				kingdom = new Kingdom(gameFieldLayer,1,2,1,1100,630,200,6,3,2,5);
				dukePlayer = kingdom.getCastle(0).getDuke();
				UIsingleton.getUIsingleton().setDukePlayer(dukePlayer);
				borderCastle.setWidth(kingdom.getCastle(0).getWidth());
				borderCastle.setHeight(kingdom.getCastle(0).getHeight());
				loadGame();
				dukePlayer = kingdom.getCastle(0).getDuke();
				UIsingleton.getUIsingleton().setDukePlayer(dukePlayer);
				gameLoop.start();
			}catch(FileNotFoundException exc) {
				
				System.out.println("Le fichier de sauvegarde n'a pas été trouvé !");
			}
			
		});
	}
	
	/**
	 * Verifies that the save file exists
	 * @throws FileNotFoundException Exception threw when the save file is not found
	 */
	private void isSaveFile() throws FileNotFoundException{
		File f = new File("Save.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		try {
			br.close();
		}catch(IOException e) {
			System.out.println("Erreur lecture : " + e.getMessage());
		}
	}
	
	/**
	 * Initialize the ending screen 
	 * @param l_ecran Width of the screen
	 * @param h_ecran Height of the screen
	 * @param stage Stage in which the ending screen must be
	 */
	private void initEnd(double l_ecran, double h_ecran, Stage stage) {
		Rectangle fond = new Rectangle(l_ecran,h_ecran);
		
		fond.setFill(Color.GREY);
		
		textWinner.relocate(l_ecran/2-140, h_ecran/2-80);
		textWinner.getStyleClass().add("gtext");
		buttonToMenu.relocate(l_ecran/2-140, h_ecran/2);
		buttonToMenu.getStyleClass().add("gbutton_menu");
		
		endFieldLayer.getChildren().addAll(fond,textWinner,buttonToMenu);
		
		buttonToMenu.setOnAction(e -> {
			stage.setScene(menu_scene);
		});
	}
	
	/**
	 * Initialize the display of the playing screen 
	 * @param longueur Width of the game
	 * @param hauteur Height of the game
	 * @param l_ecran Width of the screen
	 * @param h_ecran Height of the screen
	 */
	private void initKingdom(double longueur, double hauteur, double l_ecran, double h_ecran) {
		Pane layoutProduction = new Pane();
		layoutProduction.relocate(longueur+5, 0);
		
		Rectangle fond = new Rectangle(longueur,h_ecran);
		Rectangle bar_ressources = new Rectangle(longueur,28);
		Rectangle bar_actions = new Rectangle(l_ecran-longueur,h_ecran);
		Rectangle[] bar_amelioration = new Rectangle[Constants.LEVEL_MAX];
		
		for(int i = 0; i<Constants.LEVEL_MAX; i++) {
			bar_amelioration[i] = new Rectangle(162, 15);
			barProgressions[i] = new Rectangle(1, 13);
		}
		ImageView img_florins = new ImageView(new Image(getClass().getResource("/images/coins.png").toExternalForm(),28,28,true,true));
		ImageView img_pikemen = new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true));
		ImageView img_knight = new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true));
		ImageView img_onager = new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true));
		ImageView img_barrack = new ImageView(new Image(getClass().getResource("/images/Barrack.png").toExternalForm(), 28, 28, true, true));

		Text produce = new Text("Produire");
		
		buttonProducePikemen.setGraphic(new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceKnight.setGraphic(new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceOnager.setGraphic(new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 28, 28, false, true)));
		buttonProduceAmelioration.setGraphic(new ImageView(new Image(getClass().getResource("/images/up.png").toExternalForm(), 28, 28, true, false)));
		buttonProduceBarrack.setGraphic(new ImageView(new Image(getClass().getResource("/images/Barrack.png").toExternalForm(), 28, 28, true, true)));
		buttonProduceShield.setGraphic(new ImageView(new Image(getClass().getResource("/images/Shield.png").toExternalForm(), 28, 28, true, true)));

		
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
		for (int i =0; i<Constants.LEVEL_MAX; i++) {
			bar_amelioration[i].relocate(0, i*50 + 260);
			bar_amelioration[i].setFill(Color.ANTIQUEWHITE);
			bar_amelioration[i].setStroke(Color.BLACK);
			barProgressions[i].relocate(1, i*50 + 261);
			barProgressions[i].setFill(Color.AQUA);
		}
		
		borderCastle.relocate(-100, -100);
		
		textFlorins.relocate(70, hauteur+4);
		textFlorins.getStyleClass().add("gtext");
		textPikemen.relocate(220, hauteur+4);
		textPikemen.getStyleClass().add("gtext");
		textKnight.relocate(320, hauteur+4);
		textKnight.getStyleClass().add("gtext");
		textOnager.relocate(420, hauteur+4);
		textOnager.getStyleClass().add("gtext");
		textBarrack.relocate(520, hauteur+4);
		textBarrack.getStyleClass().add("gtext");
		textPlayer.relocate(620, hauteur+4);
		textPlayer.getStyleClass().add("gtext");
		produce.relocate(0, 30);
		produce.setStyle("-fx-fill: #545454;-fx-font-size: 30;");
		errorMessageProduction.relocate(1,  240);
		errorMessageProduction.setStyle("-fx-fill: red; fx-font-size: 14");
		
		img_florins.relocate(20, hauteur);
		img_pikemen.relocate(180, hauteur);
		img_knight.relocate(280, hauteur);
		img_onager.relocate(380, hauteur);
		img_barrack.relocate(480, hauteur);
		img_v_shield.relocate(800, hauteur);
		
		buttonProducePikemen.relocate(0, 50);
		buttonProducePikemen.getStyleClass().add("gbutton");
		buttonProduceKnight.relocate(55, 50);
		buttonProduceKnight.getStyleClass().add("gbutton");
		buttonProduceOnager.relocate(110, 50);
		buttonProduceOnager.getStyleClass().add("gbutton");
		buttonProduceAmelioration.relocate(0, 120);
		buttonProduceAmelioration.getStyleClass().add("gbutton");
		buttonProduceAmelioration.setStyle("-fx-padding: 12 67 12 67;");
		buttonProduceBarrack.relocate(0, 180);
		buttonProduceBarrack.getStyleClass().add("gbutton");
		buttonProduceShield.relocate(55, 180);
		buttonProduceShield.getStyleClass().add("gbutton");
		
		
		buttonSave.relocate(0, 600);
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
		gameFieldLayer.getChildren().add(img_barrack);
		gameFieldLayer.getChildren().add(textBarrack);
		gameFieldLayer.getChildren().add(textPlayer);
		gameFieldLayer.getChildren().add(img_v_shield);
		
		layoutProduction.getChildren().addAll(produce,errorMessageProduction);
		for (int i = 0; i<Constants.LEVEL_MAX; i++) {
			layoutProduction.getChildren().add(bar_amelioration[i]);
			layoutProduction.getChildren().add(barProgressions[i]);
		}
		layoutProduction.getChildren().addAll(buttonProducePikemen,buttonProduceKnight,buttonProduceOnager,buttonProduceAmelioration, buttonProduceBarrack, buttonProduceShield,buttonSave);
		gameFieldLayer.getChildren().add(layoutProduction);
		
		UIsingleton.getUIsingleton().setErrorLabelProduction(errorMessageProduction);
		
		buttonProducePikemen.setOnAction(e -> {
			buttonProduction(Constants.PIKEMEN);
		});
		
		buttonProduceKnight.setOnAction(e -> {
			buttonProduction(Constants.KNIGHT);
		});
		
		buttonProduceOnager.setOnAction(e -> {
			buttonProduction(Constants.ONAGER);				
		});
		
		buttonProduceAmelioration.setOnAction(e -> {
			buttonProduction(Constants.AMELIORATION);
		});
		
		buttonProduceBarrack.setOnAction(e -> {
			buttonProduction(Constants.BARRACK);
		});
		
		buttonProduceShield.setOnAction(e -> {
			buttonProduction(Constants.SHIELD);
		});
		
		buttonSave.setOnAction(e -> {
			saveGame();
		});
		
	}
	
	/**
	 * Set a production function to the button
	 * @param cst Constant of the unit to be produced when the button is pressed
	 */
	private void buttonProduction(int cst) {
		if (castleSelection!=null) {
			if(!castleSelection.getNeutral()){
				if(castleSelection.getDuke().equals(dukePlayer)) {
					try {
	    				castleSelection.launchProduction(cst);
	    				updateTresor();
	    				errorMessageProduction.setText("");
	    			} catch (ProdException err) {
	    				err.printError();
	    				timerErrorMessageProduction = 2;
	    			}
	    		}
			}
		}
	}
	
	/**
	 * Launch the game
	 * @param args main arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
