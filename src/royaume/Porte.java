package royaume;

import java.util.Random;

public class Porte {
	private Random rdm = new Random();
	
	private char porte;
	
	public Porte() {
		porte = (char)rdm.nextInt(4);
	}
	
	public Porte(int rot) {
		porte = (char) rot;
	}
	
	public char getPorte() {
		return porte;
	}
	
}
