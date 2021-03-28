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
	 * Permet d'�crire dans le fichier mission.txt tous les mouvements du robot.
	 * @param historique Une liste contenant les actions qu'a effectu� le robot durant la mission.
	 * @throws IOException L�ve une exception de lecture de fichier en cas de d'exception dans la cr�ation de l'objet Path, ou � l'appel de la m�thode Files.write.
	 */
	public static void ecritureMission (List<String> historique) throws IOException {
		// Chemin du fichier
		Path P1 = FileSystems.getDefault().getPath("fichiers", "mission.txt");
		Files.write(P1, historique, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
}
