package kingdom;

import game.UIsingleton;

/**
 * Class that represents a ProdException exception
 * it is throwed when a production is launched although another production is processed
 * @author Moi
 *
 */
public class ProdException extends Exception {
	private String errorMessage;
	
	public ProdException(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	public void printError(){
		UIsingleton.getUIsingleton().setMessageErreurProduction(errorMessage);
	}
	
	public void clearError() {
		UIsingleton.getUIsingleton().setMessageErreurProduction("");
	}

}
