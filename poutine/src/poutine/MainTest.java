package poutine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainTest {

	public static void main(String[] args) throws IOException {
		Facture facture;

		facture = new Facture();

		lecture(ouvrirDossier(trouverDossier()), facture);
		
		facture.affichageFacture();

	}

	public static BufferedReader ouvrirDossier(Path chemin) {
		BufferedReader ficLecture = null;
		try {
			ficLecture = Files.newBufferedReader(chemin, Charset.defaultCharset());
		} catch (IOException err) {
			System.out.print(err);
		}
		
		return ficLecture;
	}

	public static Path trouverDossier() {
		Path chemin = null;
		try {
			chemin = Paths.get("commande.txt").toAbsolutePath();
		} catch (InvalidPathException errNomFichier) {
			System.out.print(errNomFichier);
		}
		
		return chemin;
	}

	public static void lecture(BufferedReader ficLecture, Facture facture) throws IOException {
		String ligne;

		ligne = ficLecture.readLine();

		while (!(ligne = ficLecture.readLine()).contains("Plat")) {
			facture.addListeClients(ligne);
		}
		while (!(ligne = ficLecture.readLine()).contains("Commande")) {
			facture.addListePlats(ligne);
		}
		while (!(ligne = ficLecture.readLine()).contains("Fin")) {
			facture.addListeCommandes(ligne);
		}
		
		ficLecture.close();
	}

}
