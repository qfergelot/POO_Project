package kingdom;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import kingdom.ProdException;

import java.util.Random;

/**
 * Class managing the AI 
 * @author Moi
 *
 */
public class IABasic extends Duke {
	private Kingdom kingdom;
	private int phaseDuration = 600;
	private int attackCounter;
	private boolean economicPhase = true;
	private Castle target = null;
	
	private Random rdm = new Random();
	
	/**
	 * Construct a Basic AI
	 * @param nom Name of the AI 
	 * @param couleur Color
	 * @param image Image of the AI's castle
	 * @param kingdom Kingdom in which the AI operates
	 */
	public IABasic(String nom, Color couleur, Image image, Kingdom kingdom) {
		super(nom, couleur, image);
		this.kingdom = kingdom;
	}

	/**
	 * Compute the decision of the AI for this round
	 * @param castle Castle owned by the AI
	 */
	public void roundCastleIA(Castle castle) {
		if(castle.getDuke().equals(this)) {
			if(economicPhase) {
				try{
					castle.launchProduction(Constants.AMELIORATION);
				}
				catch (ProdException e){
					try{
						castle.launchProduction(Constants.ONAGER);
					}
					catch (ProdException f){
						try{
							castle.launchProduction(Constants.KNIGHT);
						}
						catch (ProdException g){
							try{
								castle.launchProduction(Constants.PIKEMEN);
							}
							catch (ProdException h) {
								//
							}
						}
					}
				}
			}
			else {
				if(target != null) {
					if(attackCounter == 0) {
						kingdom.createOrder(castle, target, castle.getNbPikemen()/3, castle.getNbKnight()/3, castle.getNbOnager()/3);
						attackCounter = 100;
					}
					else
						attackCounter--;
				}
				else {
					double distMin = 99999;
					double dist;
					Castle c = null;
					for(int i=0; i < kingdom.getNbCastle(); i++) {
						c = kingdom.getCastle(i);
						if(c.getNeutral()) {
							dist = c.distance(castle);
							if (dist < distMin) {
								distMin = dist;
								target = c;
							}
						}
						else if(!c.getDuke().equals(this)) {
							dist = c.distance(castle);
							if (dist < distMin) {
								distMin = dist;
								target = c;
							}
						}
					}
				}
			}
			phaseDuration--;
			if(phaseDuration == 0) {
				economicPhase = !economicPhase;
				phaseDuration = (economicPhase==true ? 600 : rdm.nextInt(900)+300);
				attackCounter = 0;
				target = null;
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o.getClass()!=getClass()) {
			return false;
		}
		IABasic d = (IABasic)o;
		return getName() == d.getName();
	}
	
	/**
	 * Reset the AI
	 */
	public void reset() {
		this.nbCastle = 0;
		economicPhase = true;
		phaseDuration = 600;
		attackCounter = 0;
		target = null;
	}
}
