package royaume;

import game.UIsingleton;

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
