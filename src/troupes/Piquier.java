package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public class Piquier extends Troupe {

	public static final int TEMPS_PRODUCTION = 100;
	public static final int COUT_PRODUCTION = 5;
	
	public Piquier(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 2, Constantes.VIE_PIQUIER, 1, pos_x, pos_y);
	}
	

}
