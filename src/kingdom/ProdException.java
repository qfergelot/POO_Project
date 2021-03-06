package kingdom;

import game.UIsingleton;

/**
 * Class that represents a ProdException exception
 * it is threw when a production is launched although another production is processed
 * 
 *
 */
public class ProdException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5823174506652761962L;
	private String errorMessage;
	
	/**
	 * Construct a ProdException
	 * @param errorMessage Error message to be set
	 */
	public ProdException(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 
	 */
	public void printError(){
		UIsingleton.getUIsingleton().setErrorMessageProduction(errorMessage);
	}
	
	/**
	 * 
	 */
	public void clearError() {
		UIsingleton.getUIsingleton().setErrorMessageProduction("");
	}

}
