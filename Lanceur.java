package projet_tableur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.awt.*;
import javax.swing.*;
/**
 * Classe principale du programme qui lance la fenêtre d'accueil du programme
 * @author AUDITEUR_GET473040
 *
 */
class Lanceur {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Fenetre fenetreAccueil = new Fenetre("Accueil", 800, 300);
				fenetreAccueil.setSize(1400, 1000);
				fenetreAccueil.setVisible(true);
				FenetreCellule fc = new FenetreCellule("Modifier cellule", 800, 300);
				FenetreSuppression fs = new FenetreSuppression("Supprimer contenu cellule", 800, 300);
				fs.setSize(350,400);
				fs.setVisible(false);
				fc.setSize(350, 400);
				fc.setVisible(false);
				JTextField[][] Table = fenetreAccueil.getTft();
				
				/**
				 * Gestion des événements lorsque l'utilisateur clique sur le bouton "sauvegarder la table dans un fichier texte"
				 */
				fenetreAccueil.btnSauvFichierTxt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							File f = new File("D:\\\\CNAM\\\\NFA035_PROJET_GET473040\\\\NFA035_TABLEUR_GET473040\\\\src\\\\projet_tableur\\\\sauvegarde.txt");
							if (f.createNewFile()) {
								Terminal.ecrireStringln("fichier crée !");
								FileWriter fW = new FileWriter(f);
								BufferedWriter b = new BufferedWriter(fW);
								PrintWriter p = new PrintWriter(b);
								String contenu = "";
								for (int i=0; i<10; i++) {
									for (int j=0; j<6; j++) {
										if (j==0) {
											contenu = contenu + Table[i][j].getText() + " &";
										} else  if(j != 6 || Table[i][j].getText() == "") {
											contenu = contenu + Table[i][j].getText() + " &";
										}
										if (j==5) {
											p.println(contenu);
											contenu = "";
										}
									}
								}
								Terminal.ecrireStringln(contenu);
								p.close();
							}
							else {JOptionPane.showMessageDialog(null, "le fichier existe déjà, supprimez le, puis recommencez", 
									  "Problème entrée/sortie",JOptionPane.ERROR_MESSAGE);}
						}catch (IOException e1) {
							e1.printStackTrace();
						}	
					}
				});
				
				/**
				 * Gestion des événements lorsque l'utilisateur clique sur "Initialiser la table avec des données provenant d'un fichier texte"
				 */
				fenetreAccueil.btnInitFichierTxt.addActionListener(new ActionListener() {
					float resultat = 0;
					boolean notEvaluable = false;
					public void actionPerformed(ActionEvent e) {
						HashMap<Integer, String> tableurTxt = LectureFichier.getTableText();
						LectureFichier.lectureTableur();
						Iterator it = tableurTxt.entrySet().iterator();
						while(it.hasNext()) {
							Map.Entry m = (Map.Entry) it.next();
							Integer n = (Integer) m.getKey();
							String str = n.toString();
							if (m.getValue() != "null") {
								String coordonneesx = str.substring(0,1);
								String coordonneesy = str.substring(1,2);
								int x = Integer.parseInt(coordonneesx);
								int y = Integer.parseInt(coordonneesy);
								String contenu = (String) m.getValue();
								boolean isForm = controlesFormules.isForm(contenu, "moy");
								boolean isForm2 = controlesFormules.isForm(contenu, "Moy");
								boolean isForm3 = controlesFormules.isForm(contenu, "somme");
								boolean isForm4 = controlesFormules.isForm(contenu, "Somme");
								boolean isRelative = controlesFormules.isRelative(contenu);
								boolean isCalc = false;
								
								if (isRelative == true) {
									//ajout dans liste des formules (utile pour passer en mode litéral à numérique)
									Formats.setListeFormule(contenu, x, y);
									//traitement pour formule type $(x,x) */-+ n
									ArrayList<String> listeCoordonneesRelative = controlesFormules.extractInputFormuleOperator(contenu);
									String premier = listeCoordonneesRelative.get(0);
									String deuxieme = listeCoordonneesRelative.get(1);
									String troisieme = listeCoordonneesRelative.get(2);
									String quatrieme = listeCoordonneesRelative.get(3);
									//modifier contenu cellule pointée
									int ligneRelative = Integer.parseInt(listeCoordonneesRelative.get(0));
									int colonneRelative = Integer.parseInt(listeCoordonneesRelative.get(1));
									int xReference = x - ligneRelative;
									int yReference = y - colonneRelative;
									premier = String.valueOf(xReference);
									deuxieme = String.valueOf(yReference);
									try {
									resultat = (float) fenetreAccueil.calculArithmétique(premier, deuxieme, troisieme, quatrieme);
									} catch (ArithmeticException exception) {
										notEvaluable = true;
									}
									//conversion
									String result = "";
									if (Float.isNaN(resultat) || notEvaluable == false ) {
										result = String.valueOf(resultat);
									} else {
										result = "?";
									}
									Formats.setListeResultats(result, x, y);
									fenetreAccueil.afficheString(result, x, y);
									isCalc = true;
								} else {
									boolean isFormOperator1 = controlesFormules.isOperator(contenu, "*");
									boolean isFormOperator2 = controlesFormules.isOperator(contenu, "/");
									boolean isFormOperator3 = controlesFormules.isOperator(contenu, "-");
									boolean isFormOperator4 = controlesFormules.isOperator(contenu, "+");
									
									if (isRelative == false && isFormOperator1 == true || isFormOperator2 == true || isFormOperator3 == true ||isFormOperator4 == true) {
										//ajout dans liste des fomrules (utile pour passer mode litéral à numérique)
										Formats.setListeFormule(contenu, x, y);
										//traitement pour formule type (x,x) +/-* n
										ArrayList<String> listeCoordonneesOperator = controlesFormules.extractInputFormuleOperator(contenu);
										String premier = listeCoordonneesOperator.get(0);
										String deuxieme = listeCoordonneesOperator.get(1);
										String troisieme = listeCoordonneesOperator.get(2);
										String quatrieme = listeCoordonneesOperator.get(3);
										try {
											resultat = (float) fenetreAccueil.calculArithmétique(premier, deuxieme, troisieme, quatrieme);
										} catch ( ArithmeticException exception) {
												notEvaluable = true;
										}
										//conversion resultat en String
										String result;
										if (Float.isNaN(resultat) || notEvaluable == true) {
											result = String.valueOf(resultat);
										} else {
											result = "?";
										}
										//ajout résultat dans mes résultats
										Formats.setListeResultats(result, x, y);
										//afficher dans le tableau le résultat de la formule
										fenetreAccueil.afficheString(result, x, y);
										isCalc = true;
									} 
								}
								if (isForm == true || isForm2 == true || isForm3 == true || isForm4 == true) {
										//ajout dans mes formules (utile pour passer mode litéral à numérique)
										Formats.setListeFormule(contenu, x, y);
										//traitement pour formule type somme ou moyenne
										ArrayList<Integer> listeCoordonnees = controlesFormules.extractInputFormule(contenu);
										if (listeCoordonnees != null) {
											int premier = listeCoordonnees.get(0);
											int deuxieme = listeCoordonnees.get(1);
											int troisieme = listeCoordonnees.get(2);
											int quatrieme = listeCoordonnees.get(3);
											if (isForm == true || isForm2 == true) {
												resultat = fenetreAccueil.calculMoyenne(premier,deuxieme,troisieme,quatrieme);
											} else if (isForm3 == true || isForm4 == true) {
												resultat = fenetreAccueil.calculSomme(premier, deuxieme, troisieme, quatrieme);
											}
											//conversion du resultat en String pour être compatible avec la fonction 'afficheString'
											String result = String.valueOf(resultat);
											//ajout résultat dans mesresultats
											//ajout dans mes résultats (utile pour passer mode litéral à numérique)
											Formats.setListeResultats(result, x, y);
											//afficher dans le tableau le résultat de la formule
											fenetreAccueil.afficheString(result, x, y);
											isCalc = true;
										}
									}  
								if (isCalc == false)
								{
								//affichage dans le tableur (quand ce n'est pas une forme type somme ou moyenne)
									fenetreAccueil.afficheString(contenu, x, y);
								}
							}
						}
					}
				});
				
				/**
				 * Gestion des événements lorsque l'utilisateur clique sur le bouton "valider" d'une fenetre de modification ou ajout de données dans le tableur
				 */
				fc.boutonValidation.addActionListener(new ActionListener() {
					float resultat = 0;
					boolean notEvaluable = false;
					public void actionPerformed(ActionEvent e) {
						//tableau de JtextField retournant les inputs avec leur contenu
						JTextField[][] inputs = fc.getTin();
						//extraction input coordonnées ligne et colonne
						String coordonnees = inputs[0][0].getText().trim();
						String coordonneesy = "";
						String coordonneesx = "";
						String verif;
						try {
							verif = coordonnees.substring(2,3);
							if (verif.equals(",")) {
								coordonneesx = coordonnees.substring(0,2);
								coordonneesy = coordonnees.substring(3,4);
							} else {
							coordonneesy = coordonnees.substring(2, 3);
							coordonneesx = coordonnees.substring(0,1);
							}
						} catch (StringIndexOutOfBoundsException exception) {
							JOptionPane.showMessageDialog(null, "il y a un problème dans l'entrée de vos coordonnées", 
									  "coordonnées erronnées",JOptionPane.ERROR_MESSAGE);
						}
						//conversion des string en int
						int x = Integer.parseInt(coordonneesx);
						int y = Integer.parseInt(coordonneesy);
						//extraction input contenu
						
						String contenu = inputs[0][1].getText().trim();
						boolean isForm = controlesFormules.isForm(contenu, "moy");
						boolean isForm2 = controlesFormules.isForm(contenu, "Moy");
						boolean isForm3 = controlesFormules.isForm(contenu, "somme");
						boolean isForm4 = controlesFormules.isForm(contenu, "Somme");
						boolean isRelative = controlesFormules.isRelative(contenu);
						boolean isCalc = false;
						
						
						if (isRelative == true) {
							//ajout dans liste des formules (utile pour passer en mode litéral à numérique)
							Formats.setListeFormule(contenu, x, y);
							//traitement pour formule type $(x,x) */-+ n
							ArrayList<String> listeCoordonneesRelative = controlesFormules.extractInputFormuleOperator(contenu);
							String premier = listeCoordonneesRelative.get(0);
							String deuxieme = listeCoordonneesRelative.get(1);
							String troisieme = listeCoordonneesRelative.get(2);
							String quatrieme = listeCoordonneesRelative.get(3);
							//modifier contenu cellule pointée
							int ligneRelative = Integer.parseInt(listeCoordonneesRelative.get(0));
							int colonneRelative = Integer.parseInt(listeCoordonneesRelative.get(1));
							int xReference = x - ligneRelative;
							int yReference = y - colonneRelative;
							premier = String.valueOf(xReference);
							deuxieme = String.valueOf(yReference);
							try {
							resultat = (float) fenetreAccueil.calculArithmétique(premier, deuxieme, troisieme, quatrieme);
							} catch (ArithmeticException exception) {
								notEvaluable = true;
							}
							//conversion
							String result = "";
							if (Float.isNaN(resultat) || notEvaluable == false) {
								result = String.valueOf(resultat);
							} else {
								result = "?";
							}
							Formats.setListeResultats(result, x, y);
							fenetreAccueil.afficheString(result, x, y);
							isCalc = true;
						} else {
							boolean isFormOperator1 = controlesFormules.isOperator(contenu, "*");
							boolean isFormOperator2 = controlesFormules.isOperator(contenu, "/");
							boolean isFormOperator3 = controlesFormules.isOperator(contenu, "-");
							boolean isFormOperator4 = controlesFormules.isOperator(contenu, "+");
							
							if (isRelative == false && isFormOperator1 == true || isFormOperator2 == true || isFormOperator3 == true ||isFormOperator4 == true) {
								//ajout dans liste des fomrules (utile pour passer mode litéral à numérique)
								Formats.setListeFormule(contenu, x, y);
								//traitement pour formule type (x,x) +/-* n
								ArrayList<String> listeCoordonneesOperator = controlesFormules.extractInputFormuleOperator(contenu);
								String premier = listeCoordonneesOperator.get(0);
								String deuxieme = listeCoordonneesOperator.get(1);
								String troisieme = listeCoordonneesOperator.get(2);
								String quatrieme = listeCoordonneesOperator.get(3);
								//envoyer dans fonction de calcul
								try {
								resultat = (float) fenetreAccueil.calculArithmétique(premier, deuxieme, troisieme, quatrieme);
								} catch ( ArithmeticException exception) {
									notEvaluable = true;
								}
								//conversion resultat en String
								String result = "";
								if (Float.isNaN(resultat) || notEvaluable == false) {
									result = String.valueOf(resultat);
								} else {
									result = "?";
								}
								//ajout résultat dans mes résultats
								Formats.setListeResultats(result, x, y);
								//afficher dans le tableau le résultat de la formule
								fenetreAccueil.afficheString(result, x, y);
								isCalc = true;
							} 
						}
						if (isForm == true || isForm2 == true || isForm3 == true || isForm4 == true) {
								//ajout dans mes formules (utile pour passer mode litéral à numérique)
								Formats.setListeFormule(contenu, x, y);
								//traitement pour formule type somme ou moyenne
								ArrayList<Integer> listeCoordonnees = controlesFormules.extractInputFormule(contenu);
								if (listeCoordonnees != null) {
									int premier = listeCoordonnees.get(0);
									int deuxieme = listeCoordonnees.get(1);
									int troisieme = listeCoordonnees.get(2);
									int quatrieme = listeCoordonnees.get(3);
									if (isForm == true || isForm2 == true) {
										try {
											resultat = fenetreAccueil.calculMoyenne(premier,deuxieme,troisieme,quatrieme);
										} catch (ArithmeticException exception) {
											notEvaluable = true;
										}
									} else if (isForm3 == true || isForm4 == true) {
										try {
											resultat = fenetreAccueil.calculSomme(premier, deuxieme, troisieme, quatrieme);
										} catch (ArithmeticException exception) {
											notEvaluable = true;
										}
									}
									//conversion du resultat en String pour être compatible avec la fonction 'afficheString'
									String result;
									if (notEvaluable == false) {
										result = String.valueOf(resultat);
									} else {
										result = "?";
									}
									//ajout résultat dans mesresultats
									//ajout dans mes résultats (utile pour passer mode litéral à numérique)
									Formats.setListeResultats(result, x, y);
									//afficher dans le tableau le résultat de la formule
									fenetreAccueil.afficheString(result, x, y);
									isCalc = true;
								}
							}  
						if (isCalc == false)
						{
						//affichage dans le tableur (quand ce n'est pas une forme type somme ou moyenne)
							fenetreAccueil.afficheString(contenu, x, y);
						}
						notEvaluable = false;
					}
				});
				
				/**
				 * Gestion événement lorsque l'utilisateur clique sur "supprimer le contenu d'une cellule" depuis la page d'accueil
				 */
				fenetreAccueil.btnSupp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fs.setVisible(true);
					}
				});
				
				/**
				 * Gestion de l'événement lorsque l'utilisateur clique sur "Modifier ou supprimer le contenu d'une cellule"
				 */
				fenetreAccueil.btnSaisie.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fc.setVisible(true);
					}
				});
				
				/**
				 * Gestion de l'événement lorsque l'utilisateur clique sur "Afficher la table sous un format littéral"
				 */
				fenetreAccueil.btnFormatLit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						HashMap<Integer, String> formules = Formats.getListeFormule();
						Iterator it = formules.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry m = (Map.Entry) it.next();
							Integer n = (Integer) m.getKey();
							String str = n.toString();
							String coordonneesx = str.substring(0,1);
							String coordonneesy = str.substring(1,2);
							int x = Integer.parseInt(coordonneesx);
							int y = Integer.parseInt(coordonneesy);
							String s = (String) m.getValue();
							fenetreAccueil.afficheString(s, x, y);
							
						}
					}
				});
				
				/**
				 * Gestion de l'événement lorsque l'utilisateur clique sur "Afficher la table sous un format numérique"
				 */
				fenetreAccueil.btnFormatNum.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						HashMap<Integer, String> resultats = Formats.getListeResultats();
						Iterator it2 = resultats.entrySet().iterator();
						while (it2.hasNext()) {
							Map.Entry m2 = (Map.Entry) it2.next();
							Integer n = (Integer) m2.getKey();
							String str = n.toString();
							String coordonneesx = str.substring(0,1);
							String coordonneesy = str.substring(1,2);
							int x = Integer.parseInt(coordonneesx);
							int y = Integer.parseInt(coordonneesy);
							String s = (String) m2.getValue();
							fenetreAccueil.afficheString(s, x, y);
							
						}
					}
				});
				
				/**
				 * Gestion de l'événement lorsque l'utilisateur clique sur "valider" dans la fenetre pour supprimer le contenu d'une cellule
				 */
				fs.boutonValidation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//tableau JTextField retournant les inputs avec leur contenu
						JTextField[][] contenu = fs.getTin();
						String coordonneesx;
						String coordonneesy;
						//extraction input coordonnées 
						String coordonnees = contenu[0][0].getText().trim();
						if (coordonnees.length() == 3) {
							coordonneesx = coordonnees.substring(0,1);
							coordonneesy = coordonnees.substring(2,3);
						} else {
							coordonneesx = coordonnees.substring(0,2);
							coordonneesy = coordonnees.substring(3,4);
						}
						//conversion des string en int
						int x = Integer.parseInt(coordonneesx);
						int y = Integer.parseInt(coordonneesy);
						//suppression dans le tableur
						fenetreAccueil.supprimeString(x, y);
					}
				});
			}
				
		});	
	}
}