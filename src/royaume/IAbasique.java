package royaume;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Random;

public class IAbasique extends Duc {
	private Royaume royaume;
	private int dureePhase = 600;
	private int compteurAttaque;
	private boolean phaseEconomie = true;
	private Chateau cible = null;
	
	private Random rdm = new Random();
	
	public IAbasique(String nom, Color couleur, Image image, Royaume royaume) {
		super(nom, couleur, image);
		this.royaume = royaume;
	}

	public void tourChateauIA(Chateau chateau) {
		if(chateau.getDuc().equals(this)) {
			if(phaseEconomie) {
				if(chateau.lancerProduction(Constantes.AMELIORATION));
				else if(chateau.lancerProduction(Constantes.ONAGRE));
				else if(chateau.lancerProduction(Constantes.CHEVALIER));
				else if(chateau.lancerProduction(Constantes.PIQUIER));
			}
			else {
				if(cible != null) {
					if(compteurAttaque == 0) {
						royaume.creerOrdre(chateau, cible, chateau.getNbPiquiers()/3, chateau.getNbChevaliers()/3, chateau.getNbOnagres()/3);
						compteurAttaque = 100;
					}
					else
						compteurAttaque--;
				}
				else {
					double distMin = 99999;
					double dist;
					Chateau c = null;
					for(int i=0; i < royaume.getNbChateaux(); i++) {
						c = royaume.getChateau(i);
						if(c.getNeutre()) {
							dist = c.distance(chateau);
							if (dist < distMin) {
								distMin = dist;
								cible = c;
							}
						}
						else if(!c.getDuc().equals(this)) {
							dist = c.distance(chateau);
							if (dist < distMin) {
								distMin = dist;
								cible = c;
							}
						}
					}
				}
			}
			dureePhase--;
			if(dureePhase == 0) {
				phaseEconomie = !phaseEconomie;
				dureePhase = (phaseEconomie==true ? 600 : rdm.nextInt(900)+300);
				compteurAttaque = 0;
				cible = null;
			}
		}
	}
}
