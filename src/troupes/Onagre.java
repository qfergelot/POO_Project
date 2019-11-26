package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public class Onagre extends Troupe {

	public static final int TEMPS_PRODUCTION = 1000;
	public static final int COUT_PRODUCTION = 1000;
	
	public Onagre(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 1, Constantes.VIE_ONAGRE, 10, pos_x, pos_y);
	}

}
