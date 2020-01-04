package kingdom;

import java.util.Random;

/**
 * Class that represents a door
 * @author Moi
 *
 */
public class Door {
	private Random rdm = new Random();
	
	private char door;
	
	/**
	 * Construct a door by default ()
	 */
	public Door() {
		door = (char)rdm.nextInt(4);
	}
	
	/**
	 * Construct a door
	 * @param rot set the rotation of the door (0 is Left, 1 is Up, 2 is Right, 3 is Down)
	 */
	public Door(int rot) {
		door = (char) rot;
	}
	
	/**
	 * Getter of current door 
	 * @return current door
	 */
	public char getDoor() {
		return door;
	}
	
}
