package partie;

import elementsCarte.Case;

public class Carte {
	
	private Case[][] grille;
	
	public Carte(Case[][] grille) {
		this.grille = grille;
	}

	public Case[][] getGrille() {
		return grille;
	}

	public void setGrille(Case[][] grille) {
		this.grille = grille;
	}
}
