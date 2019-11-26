package troupes;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import royaume.Constantes;

public class Chevalier extends Troupe {
	
	public static final int TEMPS_PRODUCTION = 500;
	public static final int COUT_PRODUCTION = 500;
	
	public Chevalier(Pane layer, Image image, double pos_x, double pos_y) {
		super(layer, image, 6, Constantes.VIE_CHEVALIER, 5, pos_x, pos_y);
	}


}
