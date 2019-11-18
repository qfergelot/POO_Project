package royaume;

import java.util.Random;

public class Porte {
	private Random rdm = new Random();
	
	final static char GAUCHE = 0;
	final static char HAUT = 1;
	final static char DROITE = 2;
	final static char BAS = 3;
	
	private char porte;
	
	public Porte() {
		porte = (char)rdm.nextInt(4);
	}
	
	public char getPorte() {
		return porte;
	}
	
}
