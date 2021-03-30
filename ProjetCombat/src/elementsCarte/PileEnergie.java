package elementsCarte;

public class PileEnergie extends Case {
	private int valeur;
	
	public PileEnergie() {
		super('%', "Pile d'energie");
		this.setValeur(1);
	}
	
	public PileEnergie(int valeur) {
		super('%', "Pile d'energie");
		this.setValeur(valeur);
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
}
