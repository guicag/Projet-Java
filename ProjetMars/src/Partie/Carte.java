package Partie;

import Equipements.Minerai;

public class Carte {
	
	private Minerai[][] matriceMinerais;
	
	public Carte(Minerai[][] minerais) {
		this.matriceMinerais = minerais;
		afficherCarte();
	}
	
	public void afficherCarte() {
		System.out.println("Carte : ");
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceMinerais[ligne][col]!=null)
					System.out.print(matriceMinerais[ligne][col].getCaractere());
				else System.out.print('@');
			}
			System.out.println();
		}
	}

	public Minerai[][] getMatriceMinerais() {
		return matriceMinerais;
	}

	public void setMatriceMinerais(Minerai[][] matriceMinerais) {
		this.matriceMinerais = matriceMinerais;
	}
	
	public int getRowLength() {
		return this.matriceMinerais.length;
	}
	
	public int getColumnLength() {
		return this.matriceMinerais[0].length;
	}
	
	
}
