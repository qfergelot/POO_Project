package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public class Piquier extends Troupe {

	public Piquier(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 100, 5, 2, Constantes.VIE_PIQUIER, 1, pos_x, pos_y);
	}
	

}
