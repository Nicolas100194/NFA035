package projet_tableur;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe pour générer une fenêtre pour supprimer du contenu dans le tableur
 * @author  AUDITEUR_GET473040
 */

public class FenetreSuppression extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public JButton boutonValidation = new JButton("Valider");
	private JTextField[][] tin = new JTextField[2][2];
	
	public FenetreSuppression(String Title, int x, int y) {
		this.setTitle(Title);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel contentPane = (JPanel) this.getContentPane();
		
		//Création et ajout des composants GUI
		this.add(new JLabel("Quel sont les coordonnées de la cellule dont vous voulez supprimer le contenu ?"));
		
		for(int i=0; i<=0;i++) {
			JLabel labelCel = new JLabel("Coordonnées de la cellule");
			contentPane.add(labelCel);
			for(int j=0; j<=0;j++) {
				tin[i][j] = new JTextField();
				tin[i][j].setPreferredSize(new Dimension(120,20));
				contentPane.add(tin[i][j]);
			}
		}
		
		//Organisation des composants GUI
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