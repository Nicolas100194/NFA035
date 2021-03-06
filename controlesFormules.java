package projet_tableur;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Un ensemble de m?thodes pour g?rer les formules entr?es par l'utilisateur
 * @author AUDITEUR_GET473040
 */

public class controlesFormules {
	
	/**
	 * V?rification de la syntaxe de la formule math?matique de type " somme((x,y),(a,b)) " entr? par l'utilisateur
	 * utilisation d'une expression r?guli?re
	 * @param contenu : contenu de la cellule comportant une formule
	 * @param form : contient le mot cl? de la formule, tel que "moy" ou "somme"
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
						  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
		} 
		return isForm;
	}
	
	/**
	 * V?rification de la syntaxe pour une op?ration arithm?tique de type " (x,y) + n " entr? par l'utilisateur 
	 * utilisation d'une expression r?guli?re
	 * @param contenu : contenu de la cellule comportant une formule
	 * @param operator : op?rateur type : *,-,+ ou / 
	 * @return isFormOperator, renseigne si la syntaxe de la formule arith?mtique est correcte
	 */
	
	public static boolean isOperator (String contenu, String operator) {
		boolean isFormOperator = false;
		if (contenu.indexOf(operator) != -1) {
			String regExp = "^\\(((1[0]|[0-9])[,])([0-9])\\)(\\+|-|\\*|/)\\d{1,10}$";
			if (contenu.matches(regExp)) {
				isFormOperator = true;
			} else
				JOptionPane.showMessageDialog(null, "votre formule arithm?tique contient une erreur syntaxique", 
						  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
		}
		return isFormOperator;
	}
	
	/**
	 * V?rification de la syntaxe entr?e par l'utilisateur, si il s'agit bien d'une d?signation relative d'une cellule
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
						  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
		}		
		return isRelative;
	}

	
	/**
	 * Extraction des donn?es entr?es par l'utilisateur lors de l'utilisation de formule, pour ensuite les traiter
	 * @param contenu : contenu de la cellule ? traiter comportant une formule
	 * @return retourne une liste contenant les coordonn?es des de 2 cellules r?f?rences C1 et C2
	 */
	
	public static ArrayList extractInputFormule(String contenu) {
			ArrayList<Integer> listeCoordonnees = new ArrayList<Integer>();
			contenu = contenu.replaceAll("[^\\d]", " ");
			contenu = contenu.trim();
			contenu = contenu.replaceAll(" +", "");
			int longueurContenu = contenu.length();
			int abscisseC1;
			int abscisseC2;
			//contr?le longueur donn?es extraites
			if (longueurContenu == 4) {
				abscisseC1 = Integer.valueOf(contenu.trim().substring(0,1));
				abscisseC2 = Integer.valueOf(contenu.trim().substring(2,3));
				//v?rification que la cellule c2 a une coordonn?es en abscisse inf?rieur ou ?gale ? la cellule c1
				if (abscisseC1 <= abscisseC2) {
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(0,1)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(1,2)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(2,3)));
					listeCoordonnees.add(Integer.valueOf(contenu.trim().substring(3,4)));
					
				}else {
					JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
							  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if (longueurContenu == 5) {
				//longueur 5 : soit c1 a pour abscisse < 10 et c2 aura une absicsse sup?rieur a c1
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
							  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
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
							  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
				}
			} 
		return listeCoordonnees;
	}
	
	/**
	 * Extraction des donn?es entr?es par l'utilisateur lors de l'utilisation de formule pour ensuite les traiter
	 * @param contenu : contenu de la cellule ? traiter comportant une formule
	 * @return retourne une liste contenant les coordonn?es x,y de la cellule, l'op?rateur, l'op?rande
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
					  "Formule erron?e",JOptionPane.ERROR_MESSAGE);
		}
		return listeCoordonneesOperator;

	}
	
	
}
