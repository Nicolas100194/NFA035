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
 * Classe permettant la lecture de fichier .txt contenant les donn�es � incorporer dans le tableur du programme
 * @author Nicolas
 */

public class LectureFichier {
	static HashMap<Integer, String> tableText = new HashMap<Integer, String>();
	/**
	 * M�thode permettant d'extraire les donn�es utiles pour un tableur depuis un fichier texte.
	 * exemples : formules, op�rations, donn�es...
	 * La m�thode analyse le fichier ligne par ligne puis pour chaque ligne analyse chaque caract�re
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
				//premi�re ligne du fichier
				ligne = ligne.trim();
				//traitement pour la ligne en cours
					if (ligne != null) {
						//lecture caract�re par caract�re de la ligne en cours
						for (int i=0; i<ligne.length();i++) {
							//si le caract�re est : & alors on passe a la cellule suivante
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
				//passage a la ligne suivante et remise nb colonne � 0
				ligne = b.readLine();
				colonneNumero = 0;
				ligneNumero ++;
			}
			b.close();
			
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "fichier non trouv� !", 
					  "Probl�me entr�e/sortie",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "probl�me de lecture", 
					  "Probl�me entr�e/sortie",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Getter pour r�cup�rer la HashMap avec le contenu des cellules et leurs coordonn�es
	 * @return tableText : hashmap
	 */
	public static HashMap<Integer, String> getTableText() {
		return tableText;
	}
}
