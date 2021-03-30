package partie;

import java.io.IOException;
import java.util.List;

import gestionFichiers.FileParser;

public class Jeu {
	private List<Robot> listRobot;
	private Carte carte;
	
	public Jeu() throws IOException {
		this.carte = new Carte(FileParser.lectureCarte());
		this.listRobot = FileParser.lectureRobot(carte);
	}
	
	public void lancer() {
		for(int i = 0; i < 10; i++) { // Jouer 10 tours
			for(Robot robot : listRobot) {
				Programme.lancer(robot.getProgramme().charAt(robot.getRegistreC()));
			}
		}
	}
}
