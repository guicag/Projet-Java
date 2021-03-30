package partie;

import java.io.IOException;

public class Launcher {

	public static void main(String[] args) {
		try {
			Jeu jeu = new Jeu();
		} catch (IOException e) {
			System.out.println("Erreur de lecture de fichiers.");
			e.printStackTrace();
		}
	}

}
