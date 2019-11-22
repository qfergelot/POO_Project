package troupes;

import javafx.scene.image.Image;

public class Onagre extends Troupe {

	public Onagre(Pane layer) {
		Image image = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 20, 20, true, true);
		
		super(layer, image, 1000, 50, 1, 5, 10);
	}

}
