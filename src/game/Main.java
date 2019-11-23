package game;
import royaume.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {
    private Royaume royaume;
	
	private Pane gameFieldLayer;
	
	private Scene scene;
	private AnimationTimer gameLoop;
	private AnimationTimer pauseLoop;
	
	private Input input;
	
	private int compteur_temps = 0;
	private double dernier_temps = 0;
	
	private boolean pauseTrigger = false;
	
	
	Group root;
	
	public static void main(String[] args) {
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		
		root = new Group();
		scene = new Scene(root, Constantes.SCENE_WIDTH, Constantes.SCENE_HEIGHT);

		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		// create layers
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		initRoyaume(800,600);
		royaume = new Royaume(gameFieldLayer,1,0,0,800,600,3,8,3,2,0);
		
		loadGame();
		
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				
				if((now - dernier_temps) >= Constantes.GAME_FREQUENCY) {
					dernier_temps = now;
					compteur_temps++;
				}
				if(compteur_temps == Constantes.DUREE_TOUR) {
					royaume.finTour();
					compteur_temps = 0;
					
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
		
		pauseLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				
				
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
		
		gameLoop.start();
	}
	
	private void pause(long now) {
		if (pauseTrigger) {
			pauseTrigger = false;
			pauseLoop.stop();
			gameLoop.start();			
		}
		else {
			pauseTrigger = true;
			gameLoop.stop();
			pauseLoop.start();
		}
	}
	
	private void loadGame() {
		
		input = new Input(scene);
		input.addListeners();
		
	}
	
	private void initRoyaume(int longueur, int hauteur) {
		Rectangle fond = new Rectangle(longueur,hauteur);
		fond.setFill(Color.GREY);
		gameFieldLayer.getChildren().add(fond);
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
