package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Piquier extends Troupe {

	public Piquier(Pane layer) {
		Image image = new Image(getClass().getResource("/images/militar.png").toExternalForm(), 20, 20, true, true);
		
		super(layer, image, 100, 5, 2, 1, 1);
	}
	

}
