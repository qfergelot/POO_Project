package royaume;

public class Duc {
	private String nom;
	private int nbChateaux = 0;
	
	public Duc(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
	
	public int getNbChateaux() {
		return nbChateaux;
	}
	
	public void ajouterChateau() {
		nbChateaux++;
	}
	
	public void retirerChateau() {
		nbChateaux--;
	}
}
