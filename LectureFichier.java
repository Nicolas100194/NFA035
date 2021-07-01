package projet_tableur;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * Classe permettant la lecture de fichier .txt contenant les données à incorporer dans le tableur du programme
 * @author Nicolas
 */

public class LectureFichier {
	static HashMap<Integer, String> tableText = new HashMap<Integer, String>();
	/**
	 * Méthode permettant d'extraire les données utiles pour un tableur depuis un fichier texte.
	 * exemples : formules, opérations, données...
	 * La méthode analyse le fichier ligne par ligne puis pour chaque ligne analyse chaque caractère
	 */
	public static void lectureTableur() {
		String monfichier = "D:\\CNAM\\NFA035_PROJET_GET473040\\NFA035_TABLEUR_GET473040\\src\\projet_tableur\\nouveau.txt";
		try {
			FileReader f = new FileReader(monfichier);
			BufferedReader b = new BufferedReader(f);
			String ligne;
			String contenuCellule = "";
			String contenu = "";
			int ligneNumero = 1;
			int colonneNumero = 0;
			ligne = b.readLine();
			while(ligne != null) {
				//première ligne du fichier
				ligne = ligne.trim();
				//traitement pour la ligne en cours
					if (ligne != null) {
						//lecture caractère par caractère de la ligne en cours
						for (int i=0; i<ligne.length();i++) {
							//si le caractère est : & alors on passe a la cellule suivante
							if (ligne.charAt(i) == '&' && colonneNumero != 7) {
								colonneNumero ++;
							}
							//stockage contenu
							if(ligne.charAt(i) != '&') {
								contenu += ligne.charAt(i);
								
							}else if (contenu !="") {
								contenuCellule = contenu.trim();
								contenu = "";
							}
							//ajout dans HashMap
							String coordonneesX = String.valueOf(ligneNumero);
							String coordonneesY = String.valueOf(colonneNumero);
							String coordonnees = coordonneesX+=coordonneesY;
							int coordonneesCouple = Integer.valueOf(coordonnees);
							tableText.put(coordonneesCouple, contenuCellule);
						}
					}
				//passage a la ligne suivante et remise nb colonne à 0
				ligne = b.readLine();
				colonneNumero = 0;
				ligneNumero ++;
			}
			b.close();
			
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "fichier non trouvé !", 
					  "Problème entrée/sortie",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "problème de lecture", 
					  "Problème entrée/sortie",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Getter pour récupérer la HashMap avec le contenu des cellules et leurs coordonnées
	 * @return tableText : hashmap
	 */
	public static HashMap<Integer, String> getTableText() {
		return tableText;
	}
}
