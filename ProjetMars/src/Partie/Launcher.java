package Partie;

import java.io.IOException;

public class Launcher {

	private static Jeu partie;

	public static void main(String[] args) {
		final String ERREUR_JEU = "Le jeu n'a pas pu être lancé à cause d'une erreur de fichiers.";
		try {
			setPartie(new Jeu());
		} catch (IOException e) {
			System.out.println(ERREUR_JEU);
		}
	}

	public static Jeu getPartie() {
		return partie;
	}

	public static void setPartie(Jeu partie) {
		Launcher.partie = partie;
	}
	
}
