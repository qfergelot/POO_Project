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
import royaume.Chateau;
import royaume.Royaume;

public class Popup {
	private Stage popupOst;
	
	private Royaume royaume;

	private Button bPPiquiers;
	private Button bMPiquiers;
	private Button bPChevaliers;
	private Button bMChevaliers;
	private Button bPOnagres;
	private Button bMOnagres;
	private Button envoyer;
	
	private Text tNbPiquiers = new Text("0");
	private Text tNbChevaliers = new Text("0");
	private Text tNbOnagres = new Text("0");
	
	private int nbPiquiers = 0;
	private int nbChevaliers = 0;
	private int nbOnagres = 0;
	
	public Popup(Royaume royaume) {
		popupOst = new Stage();
		popupOst.initModality(Modality.APPLICATION_MODAL);
		popupOst.setTitle("Troupes à envoyer");
		popupOst.setResizable(false);
		
		Pane layout = new Pane();
		layout.setStyle("-fx-background: DARKSLATEGREY;");
		
		Scene scene = new Scene(layout, 300, 200);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		popupOst.setScene(scene);
		
		this.royaume = royaume;
		
		ImageView flecheBas1 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		flecheBas1.setRotate(180);
		ImageView flecheBas2 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		flecheBas2.setRotate(180);
		ImageView flecheBas3 = new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true));
		flecheBas3.setRotate(180);
		
		Rectangle rPiquiers = new Rectangle(15,75,50,50);
		rPiquiers.setFill(Color.ANTIQUEWHITE);
		rPiquiers.setStroke(Color.BLACK);
		ImageView iPiquiers = new ImageView(new Image(getClass().getResource("/images/militar.png").toExternalForm(), 40, 40, false, true));
		iPiquiers.relocate(20, 80);
		bPPiquiers = new Button();
		bPPiquiers.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPPiquiers.relocate(65, 74);
		bPPiquiers.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMPiquiers = new Button();
		bMPiquiers.setGraphic(flecheBas1);
		bMPiquiers.relocate(65, 100);
		bMPiquiers.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbPiquiers.relocate(40, 130);
		tNbPiquiers.getStyleClass().add("texte");
		layout.getChildren().addAll(rPiquiers,iPiquiers,bPPiquiers,bMPiquiers,tNbPiquiers);
		

		Rectangle rChevaliers = new Rectangle(115,75,50,50);
		rChevaliers.setFill(Color.ANTIQUEWHITE);
		rChevaliers.setStroke(Color.BLACK);
		ImageView iChevaliers = new ImageView(new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 40, 40, false, true));
		iChevaliers.relocate(120, 80);
		bPChevaliers = new Button();
		bPChevaliers.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPChevaliers.relocate(165, 74);
		bPChevaliers.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMChevaliers = new Button();
		bMChevaliers.setGraphic(flecheBas2);
		bMChevaliers.relocate(165, 100);
		bMChevaliers.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbChevaliers.relocate(140, 130);
		tNbChevaliers.getStyleClass().add("texte");
		layout.getChildren().addAll(rChevaliers,iChevaliers,bPChevaliers,bMChevaliers,tNbChevaliers);
		
		
		Rectangle rOnagres = new Rectangle(215,75,50,50);
		rOnagres.setFill(Color.ANTIQUEWHITE);
		rOnagres.setStroke(Color.BLACK);
		ImageView iOnagres = new ImageView(new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 40, 40, false, true));
		iOnagres.relocate(220, 80);
		bPOnagres = new Button();
		bPOnagres.setGraphic(new ImageView(new Image(getClass().getResource("/images/fleche.png").toExternalForm(), 12, 12, false, true)));
		bPOnagres.relocate(265, 74);
		bPOnagres.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		bMOnagres = new Button();
		bMOnagres.setGraphic(flecheBas3);
		bMOnagres.relocate(265, 100);
		bMOnagres.setStyle("-fx-border-width: 1; -fx-border-color: BLACK;");
		tNbOnagres.relocate(240, 130);
		tNbOnagres.getStyleClass().add("texte");
		layout.getChildren().addAll(rOnagres,iOnagres,bPOnagres,bMOnagres,tNbOnagres);
		
		envoyer = new Button("Envoyer");
		envoyer.getStyleClass().add("bouton");
		envoyer.setStyle("-fx-padding: 6 6 6 6;");
		envoyer.relocate(230, 170);
		layout.getChildren().add(envoyer);
	}
	
	public void display(Chateau c, Chateau cible) {
		
		tNbPiquiers.setText("0");
		tNbChevaliers.setText("0");
		tNbOnagres.setText("0");
		
		nbPiquiers = 0;
		nbChevaliers = 0;
		nbOnagres = 0;
		bPPiquiers.setOnAction(e -> {
			if(nbPiquiers < c.getNbPiquiers()) {
				nbPiquiers++;
				tNbPiquiers.setText(""+nbPiquiers);
			}
		});
		bMPiquiers.setOnAction(e -> {
			if(nbPiquiers > 0) {
				nbPiquiers--;
				tNbPiquiers.setText(""+nbPiquiers);
			}
		});
		bPChevaliers.setOnAction(e -> {
			if(nbChevaliers < c.getNbChevaliers()) {
				nbChevaliers++;
				tNbChevaliers.setText(""+nbChevaliers);
			}
		});
		bMChevaliers.setOnAction(e -> {
			if(nbChevaliers > 0) {
				nbChevaliers--;
				tNbChevaliers.setText(""+nbChevaliers);
			}
		});
		bPOnagres.setOnAction(e -> {
			if(nbOnagres < c.getNbOnagres()) {
				nbOnagres++;
				tNbOnagres.setText(""+nbOnagres);
			}
		});
		bMOnagres.setOnAction(e -> {
			if(nbOnagres > 0) {
				nbOnagres--;
				tNbOnagres.setText(""+nbOnagres);
			}
		});
		
		envoyer.setOnAction(e -> {
			if(nbPiquiers == 0 && nbChevaliers == 0 && nbOnagres == 0) {
				popupOst.close();
			} else {
				royaume.creerOrdre(c, cible, nbPiquiers, nbChevaliers, nbOnagres);
				popupOst.close();
			}
		});
		
		popupOst.showAndWait();
	}
}
