package kingdom;

import game.Popup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class CastleIAAdvanced extends Castle {
	
	/**
	 * Construct a advanced ai castle
	 * @param layer Pane in which the castle must appear
	 * @param image Image of the castle
	 * @param duke Owner ( @see Duke)
	 * @param treasure Treasurey
	 * @param nbPikemen Number of spearmen
	 * @param nbKnight Number of knight
	 * @param nbOnager Number of onager
	 * @param x Position x
	 * @param y Position y
	 * @param popupOst Popup to display for attacks or transfer
	 */
	public CastleIAAdvanced(Pane layer, Image image, Duke duke, double treasure, int nbPikemen, int nbKnight,
			int nbOnager, double x, double y, Popup popupOst){
		super(layer, image, duke, treasure, nbPikemen, nbKnight, nbOnager, x, y, popupOst);
		
	}
	
	/**
	 * Function called for each turn
	 * Process the advance of production, exit units and treasury
	 */
	public void finishRoundCastle() {
		treasure += level;
		if(inProduction()) {
			for (int i = 0; i<barracks.size(); i++) {
				Barrack b = barracks.get(i);
				if (b.inProduction()) {
					b.getCurrentProduction().finishRoundProduction();
					if(b.getCurrentProduction().isFinishedProduction()) {
						finishProduction(b.getCurrentProduction());
						b.removeCurrentProduction();
					}
				}
			}
		}
		if(order()) {
			removeTroopsOrder();
		}
		roundIAAdvanced();
	}
	
	private void roundIAAdvanced() {
		int defenseScore = castle.getNbPikemen()+castle.getNbKnight()*Constants.LIFE_KNIGHT+castle.getNbOnager()*Constants.LIFE_ONAGER;
		if(castle.isShielded())
			defenseScore += Constants.LIFE_SHIELD;

		
		
		processProduction(castle,defenseScore);
	}
}
