package gestionFichiers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class FileWrite {

	private FileWrite() {
		super();
	}
	/**
	 * Permet d'écrire dans le fichier mission.txt tous les mouvements du robot.
	 * @param historique Une liste contenant les actions qu'a effectué le robot durant la mission.
	 * @throws IOException Lève une exception de lecture de fichier en cas de d'exception dans la création de l'objet Path, ou à l'appel de la méthode Files.write.
	 */
	public static void ecritureMission (List<String> historique) throws IOException {
		// Chemin du fichier
		Path P1 = FileSystems.getDefault().getPath("fichiers", "mission.txt");
		Files.write(P1, historique, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
}
