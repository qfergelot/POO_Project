package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public class Onagre extends Troupe {

	public Onagre(Pane layer, Image image, long pos_x, long pos_y) {
		super(layer, image, 1000, 50, 1, Constantes.VIE_ONAGRE, 10, pos_x, pos_y);
	}

}
