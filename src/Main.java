import royaume.*;
import java.util.ArrayList;
import troupes.*;

import javafx.application.Application;
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
    
    private int pas;
    private Royaume royaume;
	
	private Pane gameFieldLayer;
	
	private Scene scene;
	
	
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
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth()+3);
		primaryStage.setHeight(bounds.getHeight());
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		// create layers
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		royaume = new Royaume(4,0,0,10,10,3,8,3,2,0);
		pas = (int)Constantes.SCENE_WIDTH/(royaume.getLongueur());

		initRoyaume();
	}
	
	private void initRoyaume() {
		Rectangle fond = new Rectangle(pas*royaume.getLongueur(),pas*royaume.getLongueur());
		fond.setFill(Color.GREY);
		gameFieldLayer.getChildren().add(fond);
		for(int i=0; i<royaume.getHauteur(); i++) {
			for(int j=0; j<royaume.getLongueur(); j++) {
				ImageView img = new ImageView(new Image(getClass().getResource("/images/herbe.jpg").toExternalForm(), pas-1, pas-1, true, true));
				img.relocate(i*pas, j*pas);
				gameFieldLayer.getChildren().add(img);
			}
		}
		for(int i=0; i<royaume.getNbChateaux(); i++) {
			ImageView img = new ImageView(new Image(getClass().getResource("/images/chateau.jpg").toExternalForm(), pas-1, pas-1, true, true));
			img.relocate(royaume.getChateau(i).getPos_x()*pas, royaume.getChateau(i).getPos_y()*pas);
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
