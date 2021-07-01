package projet_tableur;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe servant � stocker les formules ou r�sultats avec leurs cellules correspondantes suivant le format choisi par l'utilisateur
 * @author AUDITEUR_GET473040
 */

public class Formats {
	static HashMap<Integer, String> listeFormule = new HashMap<Integer, String>();
	static HashMap<Integer, String> listeResultats = new HashMap<Integer, String>();
	/**
	 * M�thode pour ajouter les formules avec leur coordonn�es du tableur dans une HashMap
	 * @param element : contenu de la cellule, une formule (ex : "somme((1,1)+(3,4))" )
	 * @param x : num�ro ligne de la cellule
	 * @param y : num�ro colonne de la cellule
	 */
	public static void setListeFormule(String element, int x, int y) {
		String coordoneesX = String.valueOf(x);
		String coordoneesY = String.valueOf(y);
		String coordonneesFormule = coordoneesX.concat(coordoneesY);
		int coordonnees = Integer.valueOf(coordonneesFormule);
		listeFormule.put( coordonnees, element);
	}
	/**
	 * Getter pour r�cup�rer la liste des formules avec leurs coordonn�es
	 * @return listeFormule : Hashmap 
	 */
	public static HashMap<Integer, String> getListeFormule() {
		return listeFormule;
	}
	
	/**
	 * Getter pour r�cup�rer la liste des r�sultats avec leurs coordon�es
	 * @return listeResultat : Hashmap
	 */
	public static HashMap<Integer, String> getListeResultats() {
		return listeResultats;
	}

	/**
	 * M�thode pour ajouter les r�sultats des formules du tableur dans une HashMap
	 * @param element : contenu de la cellule, un r�sultat (ex : 56.0) 
	 * @param x : num�ro ligne de la cellule
	 * @param y : num�ro colonne de la cellule
	 */
	public static void setListeResultats(String element, int x, int y) {
		String coordoneesX = String.valueOf(x);
		String coordoneesY = String.valueOf(y);
		String coordonneesFormule = coordoneesX.concat(coordoneesY);
		int coordonnees = Integer.valueOf(coordonneesFormule);
		listeResultats.put( coordonnees, element);
	}	
}