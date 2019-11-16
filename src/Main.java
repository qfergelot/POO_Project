import royaume.*;
import troupes.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {
	public static final double SCENE_WIDTH = 400;
    public static final double SCENE_HEIGHT = 600;
	
	private Pane gameFieldLayer;
	
	private Scene scene;
	
	Group root;
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub*/
		
	@Override
	public void start(Stage primaryStage) {
		root = new Group();
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		// create layers
		gameFieldLayer = new Pane();
		root.getChildren().add(gameFieldLayer);
		
		
		Royaume r = new Royaume(4,0,0,10,10,3,8,3,2,0);
		int pas = (int)SCENE_WIDTH/(r.getLongueur());

		for(int i=0; i<r.getHauteur(); i++) {
			for(int j=0; j<r.getLongueur(); j++) {
				ImageView img = new ImageView(new Image(getClass().getResource("/images/herbe.jpg").toExternalForm(), pas-1, pas-1, true, true));
				img.relocate(i*pas, j*pas);
				gameFieldLayer.getChildren().add(img);
			}
		}
		for(int i=0; i<r.getNbChateaux(); i++) {
			ImageView img = new ImageView(new Image(getClass().getResource("/images/chateau.jpg").toExternalForm(), pas-1, pas-1, true, true));
			img.relocate(r.getChateau(i).getPos_x()*pas, r.getChateau(i).getPos_y()*pas);
			switch(r.getChateau(i).getPorte()) {
				case "gauche":
					img.setRotate(90);
					break;
				case "haut":
					img.setRotate(180);
					break;
				case "droite":
					img.setRotate(270);
					break;
				default:
					break;
			}
			gameFieldLayer.getChildren().add(img);
		}
	}

}
