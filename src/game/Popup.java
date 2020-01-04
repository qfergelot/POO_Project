package game;

import javafx.scene.Scene;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kingdom.Castle;
import kingdom.Kingdom;
import game.UIsingleton;
/**
 * Class of the popup that will be used in the game for attacks and transfers
 * 
 * @author Moi
 *
 */
public class Popup {
	private Stage popupOst;
	
	private Kingdom kingdom;

	private Button bPPikemen;
	private Button bMPikemen;
	private Button bPKnight;
	private Button bMKnight;
	private Button bPOnager;
	private Button bMOnager;
	private Button send;
	
	private Text tNbPikemen = new Text("0");
	private Text tNbKnight = new Text("0");
	private Text tNbOnager = new Text("0");
	
	private int nbPikemen = 0;
	private int nbKnight = 0;
	private int nbOnager = 0;
	
	/**
	 * Construct the popup
	 * 
	 * @param kingdom Royaume in which the popup must be created
	 */
	public Popup(Kingdom kingdom) {
		popupOst = new Stage();
		popupOst.initModality(Modality.APPLICATION_MODAL);
		popupOst.setTitle("Troupes a envoyer");
		popupOst.setResizable(false);
		
		Pane layout = new Pane();
		layout.setStyle("-fx-background: DARKSLATEGREY;");
		
		Scene scene = new Scene(layout, 300, 200);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		popupOst.setScene(scene);
		
		this.kingdom = kingdom;
		
		ImageView downArrow1 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		downArrow1.setRotate(180);
		ImageView downArrow2 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		downArrow2.setRotate(180);
		ImageView downArrow3 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		downArrow3.setRotate(180);
		
		Rectangle rPikemen = new Rectangle(15,75,50,50);
		rPikemen.setFill(Color.ANTIQUEWHITE);
		rPikemen.setStroke(Color.BLACK);
		ImageView iPikemen = new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 40, 40, false, true));
		iPikemen.relocate(20, 80);
		bPPikemen = new Button();
		bPPikemen.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPPikemen.relocate(65, 74);
		bPPikemen.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMPikemen = new Button();
		bMPikemen.setGraphic(downArrow1);
		bMPikemen.relocate(65, 100);
		bMPikemen.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbPikemen.relocate(40, 130);
		tNbPikemen.getStyleClass().add("gtext");
		layout.getChildren().addAll(rPikemen,iPikemen,bPPikemen,bMPikemen,tNbPikemen);
		

		Rectangle rKnight = new Rectangle(115,75,50,50);
		rKnight.setFill(Color.ANTIQUEWHITE);
		rKnight.setStroke(Color.BLACK);
		ImageView iKnight = new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 40, 40, false, true));
		iKnight.relocate(120, 80);
		bPKnight = new Button();
		bPKnight.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPKnight.relocate(165, 74);
		bPKnight.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMKnight = new Button();
		bMKnight.setGraphic(downArrow2);
		bMKnight.relocate(165, 100);
		bMKnight.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbKnight.relocate(140, 130);
		tNbKnight.getStyleClass().add("gtext");
		layout.getChildren().addAll(rKnight,iKnight,bPKnight,bMKnight,tNbKnight);
		
		
		Rectangle rOnager = new Rectangle(215,75,50,50);
		rOnager.setFill(Color.ANTIQUEWHITE);
		rOnager.setStroke(Color.BLACK);
		ImageView iOnager = new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 40, 40, false, true));
		iOnager.relocate(220, 80);
		bPOnager = new Button();
		bPOnager.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPOnager.relocate(265, 74);
		bPOnager.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMOnager = new Button();
		bMOnager.setGraphic(downArrow3);
		bMOnager.relocate(265, 100);
		bMOnager.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbOnager.relocate(240, 130);
		tNbOnager.getStyleClass().add("gtext");
		layout.getChildren().addAll(rOnager,iOnager,bPOnager,bMOnager,tNbOnager);
		
		send = new Button("Envoyer");
		send.getStyleClass().add("gbutton");
		send.setStyle("-fx-padding: 6 6 6 6;");
		send.relocate(230, 170);
		layout.getChildren().add(send);
	}
	
	/**
	 * Displays the popup
	 * @param c Source Castle (@see Chateau)
	 * @param target Destination Castle (@see Chateau)
	 */
	public void display(Castle c, Castle target) {
		
		UIsingleton.getUIsingleton().setPause();
		
		tNbPikemen.setText("0");
		tNbKnight.setText("0");
		tNbOnager.setText("0");
		
		nbPikemen = 0;
		nbKnight = 0;
		nbOnager = 0;
		bPPikemen.setOnAction(e -> {
			if(nbPikemen < c.getNbPikemen()) {
				nbPikemen++;
				tNbPikemen.setText(""+nbPikemen);
			}
		});
		bMPikemen.setOnAction(e -> {
			if(nbPikemen > 0) {
				nbPikemen--;
				tNbPikemen.setText(""+nbPikemen);
			}
		});
		bPKnight.setOnAction(e -> {
			if(nbKnight < c.getNbKnight()) {
				nbKnight++;
				tNbKnight.setText(""+nbKnight);
			}
		});
		bMKnight.setOnAction(e -> {
			if(nbKnight > 0) {
				nbKnight--;
				tNbKnight.setText(""+nbKnight);
			}
		});
		bPOnager.setOnAction(e -> {
			if(nbOnager < c.getNbOnager()) {
				nbOnager++;
				tNbOnager.setText(""+nbOnager);
			}
		});
		bMOnager.setOnAction(e -> {
			if(nbOnager > 0) {
				nbOnager--;
				tNbOnager.setText(""+nbOnager);
			}
		});
		
		send.setOnAction(e -> {
			UIsingleton.getUIsingleton().setPause();
			if(nbPikemen == 0 && nbKnight == 0 && nbOnager == 0) {
				popupOst.close();
			} else {
				kingdom.createOrder(c, target, nbPikemen, nbKnight, nbOnager);
				popupOst.close();
			}
		});
		
		popupOst.setOnCloseRequest(e -> {
			UIsingleton.getUIsingleton().setPause();
		});
		
		popupOst.showAndWait();
	}
}
