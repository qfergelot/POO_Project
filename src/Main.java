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
    private int bordures;
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
		Rectangle2D bounds = screen.getVisualBounds();
		
		root = new Group();
		scene = new Scene(root);
		
		primaryStage.setX(bounds.getMinX()-2);
		primaryStage.setY(bounds.getMinY()-2);
		primaryStage.setWidth(bounds.getWidth()+4);
		primaryStage.setHeight(bounds.getHeight()+4);
		primaryStage.setFullScreen(true);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		// create layers
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		royaume = new Royaume(4,0,0,20,10,3,8,3,2,0);
		
		bordures = (int)(primaryStage.getWidth() - royaume.getLongueur())/2;
		
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
		
		initRoyaume();
		
		input = new Input(scene);
		input.addListeners();
		
	}
	
	private void initRoyaume() {
		Rectangle fond = new Rectangle(2 + royaume.getLongueur(),2 + royaume.getHauteur());
		fond.setFill(Color.GREY);
		fond.relocate(bordures-2, 0);
		gameFieldLayer.getChildren().add(fond);
		for(int i=0; i<royaume.getNbChateaux(); i++) {
			ImageView img = new ImageView(new Image(getClass().getResource("/images/chateau.jpg").toExternalForm(), 20, 20, true, true));
			img.relocate(bordures + royaume.getChateau(i).getPos_x(), 2 + royaume.getChateau(i).getPos_y());
			switch(royaume.getChateau(i).getPorte()) {
				case Constantes.GAUCHE:
					img.setRotate(90);
					break;
				case Constantes.HAUT:
					img.setRotate(180);
					break;
				case Constantes.DROITE:
					img.setRotate(270);
					break;
				default:
					break;
			}	
			gameFieldLayer.getChildren().add(img);
		}
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
