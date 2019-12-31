package royaume;

import game.UIsingleton;

/**
 * Class that represents a ProdException exception
 * it is throwed when a production is launched although another production is processed
 * @author Moi
 *
 */
public class ProdException extends Exception {
	private String messageErreur;
	
	public ProdException(String messageErreur){
		this.messageErreur = messageErreur;
	}
	
	public void printError(){

		UIsingleton.getUIsingleton().setMessageErreurProduction(messageErreur);
	}
	
	public void clearError() {
		UIsingleton.getUIsingleton().setMessageErreurProduction("");
	}

}
