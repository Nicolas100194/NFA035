package projet_tableur;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Un ensemble de méthodes pour gérer les formules entrées par l'utilisateur
 * @author AUDITEUR_GET473040
 */

public class controlesFormules {
	
	/**
	 * Vérification de la syntaxe de la formule mathématique de type " somme((x,y),(a,b)) " entré par l'utilisateur
	 * utilisation d'une expression régulière
	 * @param contenu : contenu de la cellule comportant une formule
	 * @param form : contient le mot clé de la formule, tel que "moy" ou "somme"
	 * @return : isForm, renseigne si la syntaxe de la formule est correcte
	 */
	
	public static boolean isForm(String contenu, String form) {
		boolean isForm = false;
		if (contenu.indexOf(form) != -1) {
			String regExp = "^[A-Za-z]{3,5}\\(\\((1[0]|[0-9])[,]\\d(\\d)?\\)[,]\\(\\d(\\d)?[,]\\d(\\d)?\\)\\)$";
			if (contenu.matches(regExp)) {
				isForm = true;
			} else 
				JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
						  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		} 
		return isForm;
	}
	
	/**
	 * Vérification de la syntaxe pour une opération arithmétique de type " (x,y) + n " entré par l'utilisateur 
	 * utilisation d'une expression régulière
	 * @param contenu : contenu de la cellule comportant une formule
	 * @param operator : opérateur type : *,-,+ ou / 
	 * @return isFormOperator, renseigne si la syntaxe de la formule arithémtique est correcte
	 */
	
	public static boolean isOperator (String contenu, String operator) {
		boolean isFormOperator = false;
		if (contenu.indexOf(operator) != -1) {
			String regExp = "^\\(((1[0]|[0-9])[,])([0-9])\\)(\\+|-|\\*|/)\\d{1,10}$";
			if (contenu.matches(regExp)) {
				isFormOperator = true;
			} else
				JOptionPane.showMessageDialog(null, "votre formule arithmétique contient une erreur syntaxique", 
						  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		}
		return isFormOperator;
	}
	
	/**
	 * Vérification de la syntaxe entrée par l'utilisateur, si il s'agit bien d'une désignation relative d'une cellule
	 * @param contenu : contenu de la formule comportant une forumle
	 * @return isRelative, renseigne si la syntaxe de la formule relative est correcte
	 */
	
	public static boolean isRelative (String contenu) {
		boolean isRelative = false;
		if (contenu.indexOf("$") != -1) {
			String regExp = "^\\$\\(((1[0]|[0-9])[,])([0-9])\\)(\\+|-|\\*|/)\\d{1,10}$";
			if (contenu.matches(regExp)) {
				isRelative = true;
			} else 
				JOptionPane.showMessageDialog(null, "votre formule n'est pas relative, erreur de syntaxe", 
						  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		}		
		return isRelative;
	}

	
	/**
	 * Extraction des données entrées par l'utilisateur lors de l'utilisation de formule, pour ensuite les traiter
	 * @param contenu : contenu de la cellule à traiter comportant une formule
	 * @return retourne une liste contenant les coordonnées des de 2 cellules références C1 et C2
	 */
	
	public static ArrayList extractInputFormule(String contenu) {
			ArrayList<Integer> listeCoordonnees = new ArrayList<Integer>();
			contenu = contenu.replaceAll("[^\\d]", " ");
			contenu = contenu.trim();
			contenu = contenu.replaceAll(" +", "");
			int longueurContenu = contenu.length();
			int abscisseC1;
			int abscisseC2;
			//contrôle longueur données extraites
			if (longueurContenu == 4) {
				abscisseC1 = Integer.valueOf(contenu.trim().substring(0,1));
				abscisseC2 = Integer.valueOf(contenu.trim().substring(2,3));
				//vérification que la cellule c2 a une coordonnées en abscisse inférieur ou égale à la cellule c1
				if (abscisseC1 <= abscisseC2) {
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(0,1)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(1,2)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(2,3)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(3,4)));
					
				}else {
					JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
							  "Formule erronée",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if (longueurContenu == 5) {
				//longueur 5 : soit c1 a pour abscisse < 10 et c2 aura une absicsse supérieur a c1
				abscisseC1 = Integer.valueOf(contenu.trim().substring(0,1));
				abscisseC2 = Integer.valueOf(contenu.trim().substring(2,4));
				if (abscisseC1 < abscisseC2) {
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(0,1)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(1,2)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(2,4)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(4,5)));
				} 
				else {
					JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
							  "Formule erronée",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if (longueurContenu == 6) {
				abscisseC1 = Integer.valueOf(contenu.trim().substring(0,2));
				abscisseC2 = Integer.valueOf(contenu.trim().substring(3,5));
				if (abscisseC1 == abscisseC2) {
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(0,2)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(2,3)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(3,5)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(5,6)));
				}else {
					JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
							  "Formule erronée",JOptionPane.ERROR_MESSAGE);
				}
			} 
		return listeCoordonnees;
	}
	
	/**
	 * Extraction des données entrées par l'utilisateur lors de l'utilisation de formule pour ensuite les traiter
	 * @param contenu : contenu de la cellule à traiter comportant une formule
	 * @return retourne une liste contenant les coordonnées x,y de la cellule, l'opérateur, l'opérande
	 */
	
	public static ArrayList extractInputFormuleOperator(String contenu) {
		ArrayList<String> listeCoordonneesOperator = new ArrayList<String>();
		try {
			contenu = contenu.replaceAll("\\$", "");
			contenu = contenu.replaceAll("[\\(]", "");
			contenu = contenu.replaceAll("[\\)]", "");
			contenu = contenu.replaceAll("[,]", "");
			contenu = contenu.trim();
			if (contenu.substring(0,3).matches("\\d\\d\\d") == false) {
				listeCoordonneesOperator.add(contenu.trim().substring(0,1));
				listeCoordonneesOperator.add(contenu.trim().substring(1,2));
				listeCoordonneesOperator.add(contenu.trim().substring(2,3));
				listeCoordonneesOperator.add(contenu.trim().substring(3,contenu.length()));
			} else {
				listeCoordonneesOperator.add(contenu.trim().substring(0,2));
				listeCoordonneesOperator.add(contenu.trim().substring(2,3));
				listeCoordonneesOperator.add(contenu.trim().substring(3,4));
				listeCoordonneesOperator.add(contenu.trim().substring(4,contenu.length()));
			}
		} catch (IndexOutOfBoundsException exception) {
			JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
					  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		}
		return listeCoordonneesOperator;

	}
	
	
}
