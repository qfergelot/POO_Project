package royaume;

public class Ordre {
	private Chateau cible;
	private int nbTroupes;
	
	public Ordre(Chateau cible, int nbTroupes) {
		this.cible = cible;
		this.nbTroupes = nbTroupes;
	}
}
