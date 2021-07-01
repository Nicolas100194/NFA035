package projet_tableur;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe servant à stocker les formules ou résultats avec leurs cellules correspondantes suivant le format choisi par l'utilisateur
 * @author AUDITEUR_GET473040
 */

public class Formats {
	static HashMap<Integer, String> listeFormule = new HashMap<Integer, String>();
	static HashMap<Integer, String> listeResultats = new HashMap<Integer, String>();
	/**
	 * Méthode pour ajouter les formules avec leur coordonnées du tableur dans une HashMap
	 * @param element : contenu de la cellule, une formule (ex : "somme((1,1)+(3,4))" )
	 * @param x : numéro ligne de la cellule
	 * @param y : numéro colonne de la cellule
	 */
	public static void setListeFormule(String element, int x, int y) {
		String coordoneesX = String.valueOf(x);
		String coordoneesY = String.valueOf(y);
		String coordonneesFormule = coordoneesX.concat(coordoneesY);
		int coordonnees = Integer.valueOf(coordonneesFormule);
		listeFormule.put( coordonnees, element);
	}
	/**
	 * Getter pour récupérer la liste des formules avec leurs coordonnées
	 * @return listeFormule : Hashmap 
	 */
	public static HashMap<Integer, String> getListeFormule() {
		return listeFormule;
	}
	
	/**
	 * Getter pour récupérer la liste des résultats avec leurs coordonées
	 * @return listeResultat : Hashmap
	 */
	public static HashMap<Integer, String> getListeResultats() {
		return listeResultats;
	}

	/**
	 * Méthode pour ajouter les résultats des formules du tableur dans une HashMap
	 * @param element : contenu de la cellule, un résultat (ex : 56.0) 
	 * @param x : numéro ligne de la cellule
	 * @param y : numéro colonne de la cellule
	 */
	public static void setListeResultats(String element, int x, int y) {
		String coordoneesX = String.valueOf(x);
		String coordoneesY = String.valueOf(y);
		String coordonneesFormule = coordoneesX.concat(coordoneesY);
		int coordonnees = Integer.valueOf(coordonneesFormule);
		listeResultats.put( coordonnees, element);
	}	
}