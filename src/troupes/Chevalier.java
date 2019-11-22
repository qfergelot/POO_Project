package troupes;

import javafx.scene.image.Image;

public class Chevalier extends Troupe {

	public Chevalier(Pane layer) {
		Image image = new Image(getClass().getResource("/images/chevalier.png").toExternalForm(), 20, 20, true, true);
		
		super(layer, image, 500, 20, 6, 3, 5);
	}

}
