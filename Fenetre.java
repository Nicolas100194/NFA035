package projet_tableur;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe pour générer fenêtre principale du programme contenant le tableur et toute les options du programme
 * @author AUDITEUR_GET473040
 */
public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField[][] tft = new JTextField[10][6];
	public JButton btnSauvFichierTxt = new JButton("Sauvegarder la table dans un fichier texte");
	public JButton btnInitFichierTxt = new JButton("Initialiser la table avec des données provenant d'un fichier texte");
	public JButton btnSupp = new JButton("Supprimer le contenu d'une cellule");
	public JButton btnSaisie = new JButton("Modifier ou saisir le contenu d'une cellule");
	public JButton btnFormatLit = new JButton("Afficher la table sous un format littéral");
	public JButton btnFormatNum = new JButton("Afficher la table sous format numérique");
	float contenucel;
	
	class MyJLabel extends JTextField{
		MyJLabel(String s, int i){
			super(s,i);
			this.setForeground(new Color(255,255,0));
			this.setBackground(new Color(205,26,26));
			this.setHorizontalAlignment(JTextField.CENTER);
			this.setEditable(false);
		}
	}

	public Fenetre(String Title, int x, int y) {
		
		this.setTitle(Title);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel label1 = new JLabel("Bienvenue");
	
		//organisation des composants GUI
		
		JPanel panelA = new JPanel();
		JPanel panelB = new JPanel();
		
		//panel A avec ses composants et caractéristiques
		this.add(panelA);
		
		panelA.setLayout(new GridLayout(11,7));
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<6; j++) {
				tft[i][j]=new JTextField();
				tft[i][j].setHorizontalAlignment(JTextField.CENTER);
				tft[i][j].setEditable(false);
				tft[i][j].setBackground(new Color(255,228,225));
			}
		}
		panelA.add(new Label(" "));
		for (int i=0; i<6; i++) {
			panelA.add(new MyJLabel("" + (i+1), JLabel.CENTER));
		}
		for (int i=0; i<10; i++) {
			panelA.add(new MyJLabel("" + (i+1), JLabel.CENTER));
			for (int j=0; j<6; j++) {
				panelA.add(tft[i][j]);
			}
		}
		
		//panel B avec ses composants et caractéristiques
		this.add(panelB);
		panelB.setBorder(BorderFactory.createLineBorder(Color.black));
		panelB.add(btnFormatNum);
		panelB.add(btnFormatLit);
		panelB.add(btnSaisie);
		panelB.add(btnSupp);
		panelB.add(btnInitFichierTxt);
		panelB.add(btnSauvFichierTxt);
		panelB.add(label1);
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		this.setLayout(new GridLayout(1,2));
		this.pack();
		
		//événments
		btnFormatNum.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label1.setText("Vous affichez la table sous format numérique");
			}
		});
		btnFormatLit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label1.setText("Vous affichez la table sous format littéral");
			}
		});
	}
	
	//METHODES
	
	/**
	 * Getter pour récupérer le tableur 
	 * @return tft : tableau de JTextField
	 */
	public JTextField[][] getTft() {
		return tft;
	}
	
	/**
	 * Fonction qui affiche dans une cellule du tableur les coordonnées et contenu entré par l'utilisateur
	 * @param s : contenu de la cellule, résultat d'une formule, formule, ou donnée
	 * @param x : numéro ligne cellule
	 * @param y : numéro colonne cellule
	 */
	public void afficheString(String s, int x, int y) {
		if (x == 0 || y == 0) {
			tft[x][y].setText(s);
		} else {
		tft[x-1][y-1].setText(s);
		} 
	}
	
	/**
	 * Fonction qui supprime le contenu d'une cellule
	 * @param x : numéro ligne cellule
	 * @param y : numéro colonne cellule
	 */
	public void supprimeString(int x, int y) {
		tft[x-1][y-1].setText("");
	}
	
	/**
	 * Fonction effectue un calcul arithmétique entré par l'utilisateur dans une cellule
	 * @param ligne : ligne de la cellule sur lequel l'utilisateur veut effectuer le calcul
	 * @param colonne : colonne de la cellule sur lequel l'utilisateur veut effectuer le calcul
	 * @param operator : type d'opération : division, soustraction, multiplication ou addition
	 * @param chiffre : l'opérande du calcul
	 * @return resultat : retourne le résultat au format double
	 */
	public double calculArithmétique(String ligne, String colonne, String operator, String chiffre) {
		float resultat = 0;
		try {
			int x = Integer.parseInt(ligne);
			int y = Integer.parseInt(colonne);
			int chiffreOperation = Integer.parseInt(chiffre);
			float contenu = (float) Double.parseDouble(tft[x-1][y-1].getText());
			if (operator.indexOf('+') != -1) {
				contenu = contenu + chiffreOperation;
				resultat = contenu;
			}
			if (operator.indexOf('*') != -1) {
				contenu = contenu * chiffreOperation;
				resultat =  contenu;
			}
			if (operator.indexOf('/') != -1) {
				contenu = contenu / chiffreOperation;
				resultat =  contenu;
			}
			if (operator.indexOf('-') != -1) {
				contenu = contenu - chiffreOperation;
				resultat = contenu;
			}
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(null, "problème lors du calcul, il y a une erreur", 
					  "Erreur calcul",JOptionPane.ERROR_MESSAGE);
		}
		return resultat;
	}
	
	/**
	 * Fonction qui effectue la moyenne à partir de deux cellules entrées en paramètre
	 * @param ligneC1 : ligne de la première cellule qu'englobe la formule 
	 * @param colonneC1 : colonne de la première cellule qu'englobe la formule
	 * @param ligneC2 : ligne de la dernière cellule qu'englobe la formule
	 * @param colonneC2 : colonne de la dernière cellule qu'englobe la formule
	 * @return resultat : retourne le résultat au formal float
	 */
	public float calculMoyenne(int ligneC1, int colonneC1, int ligneC2, int colonneC2) {
		float resultat = 0;
		try {
			double nbChiffre = 0;
			double somme = 0;
			String verif;
			for(int i=ligneC1-1; i<ligneC2; i++) { 
				for(int j=colonneC1-1; j<colonneC2; j++) { 
					verif = (tft[i][j].getText().trim()); 
					if (!verif.isEmpty()) {
						contenucel = (float) Double.parseDouble(tft[i][j].getText().trim());
						if(contenucel != 0 ) {
							nbChiffre ++;
							somme += contenucel;
						}
					}
				}
			}
			resultat = (float) (somme / nbChiffre);
		}catch (ArrayIndexOutOfBoundsException exception) {
			JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
					  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		}
		return resultat;
	}
	
	/**
	 * Fonction qui effectue la somme à partir de deux cellules entrées en paramètre
	 * @param ligneC1 : ligne de la première cellule qu'englobe la formule 
	 * @param colonneC1 : colonne de la première cellule qu'englobe la formule
	 * @param ligneC2 : ligne de la dernière cellule qu'englobe la formule
	 * @param colonneC2 : colonne de la dernière cellule qu'englobe la formule
	 * @return resultat : retourne le résultat au formal float
	 */
	public float calculSomme(int ligneC1, int colonneC1, int ligneC2, int colonneC2) {
		float somme = 0;
		try {
			String verif;
			for(int i=ligneC1-1; i<ligneC2; i++) { 
				for(int j=colonneC1-1; j<colonneC2; j++) { 
					verif = (tft[i][j].getText().trim());
					if (!verif.isEmpty()) {
						contenucel = (float) Double.parseDouble(tft[i][j].getText().trim());
						if(contenucel != 0) {
							somme += contenucel;
						}
					}
				}
			}
		}catch (ArrayIndexOutOfBoundsException exception) {
			JOptionPane.showMessageDialog(null, "votre formule contient une erreur syntaxique", 
					  "Formule erronée",JOptionPane.ERROR_MESSAGE);
		}
		return somme;
	}
}

