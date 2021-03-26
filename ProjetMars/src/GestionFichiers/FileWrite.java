package GestionFichiers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class FileWrite {

	/**
	 * Permet d'écrire dans le fichier mission.txt tous les mouvements du robot
	 * @param historique
	 * @throws IOException
	 */
	public static void ecritureMission (ArrayList<String> historique) throws IOException {
		// Chemin du fichier
		Path P1 = FileSystems.getDefault().getPath("fichiers", "mission.txt");
		Files.write(P1, historique, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
}
