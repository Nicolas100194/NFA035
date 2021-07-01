package projet_tableur;
import java.awt.*;
import javax.swing.*;

/**
 * Classe pour générer une fenêtre pour modifier, ajouter du contenu dans le tableur
 * @author AUDITEUR_GET473040
 */

public class FenetreCellule extends JFrame {
	private JTextField[][] tin = new JTextField[2][2];
	private static final long serialVersionUID = 1L;
	public JButton boutonValidation = new JButton("Valider");
	
	public FenetreCellule(String Title, int x, int y) {
		this.setTitle(Title);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel contentPane = (JPanel) this.getContentPane();

		//Création et ajout des composants GUI
		this.add(new JLabel("Quel cellule voulez vous remplir ?"));
		JLabel labelCel2 = new JLabel("Coordonnées de la cellule");
		contentPane.add(labelCel2);
		//tableau input
		for(int i=0; i<=0;i++) {
			JLabel labelCel = new JLabel("Contenu de la cellule");
			contentPane.add(labelCel);
			for(int j=0; j<=1;j++) {
				if (j==0) {
					tin[i][j] = new JTextField("ex : 5,4");
				}else {
					tin[i][j] = new JTextField();
				}
				tin[i][j].setPreferredSize(new Dimension(120,20));
				contentPane.add(tin[i][j]);
			}
		}
		//organisation des composants GUI
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		JPanel panelB = new JPanel();
		this.add(panelB);
		panelB.add(boutonValidation);
		this.pack();	
	}

	/**
	 * Getter pour récupérer le formulaire sous forme de tableau
	 * @return tin : tableau input à deux dimensions
	 */
	public JTextField[][] getTin() {
		return tin;
	}
}
