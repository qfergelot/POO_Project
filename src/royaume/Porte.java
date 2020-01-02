package royaume;

import java.util.Random;

/**
 * Class that represents a door
 * @author Moi
 *
 */
public class Porte {
	private Random rdm = new Random();
	
	private char porte;
	
	/**
	 * Construct a door by default
	 */
	public Porte() {
		porte = (char)rdm.nextInt(4);
	}
	
	/**
	 * Construct a door
	 * @param rot set the rotation of the door
	 */
	public Porte(int rot) {
		porte = (char) rot;
	}
	
	/**
	 * 
	 * @return
	 */
	public char getPorte() {
		return porte;
	}
	
}
