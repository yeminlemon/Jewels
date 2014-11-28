import java.awt.*;
import javax.swing.*;

/**
 * This is the Instructions class. It extends JFrame. 
 * It is a pop up that will appear whenever the user wants to read the instructions for the Bejeweled game.
 * 
 * @author Yemin Shou
 */
public class Instructions extends JFrame
{
	/**
	 * Constructor method builds Instructions frame
	 */
	public Instructions()
	{
		super("Bejeweled - Instructions");
		
		setSize(450, 750);
		setResizable(false);
		setLayout(null);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.BLACK);
		
		//sets the panels used in the tabbedPane
		JLabel howToPlay = new JLabel();
		howToPlay.setIcon(new ImageIcon("How to play.gif"));
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0,50,450,700);
		panel1.setBackground(new Color(60,23,27));
		panel1.add(howToPlay);
		
		JLabel scoring = new JLabel();
		scoring.setIcon(new ImageIcon("scoring.gif"));
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(0,50,450,700);
		panel2.setBackground(new Color(0,82,118));
		panel2.add(scoring);
		
		//sets tabbedPane
		JTabbedPane tab = new JTabbedPane();
		tab.setBounds(getBounds());
		tab.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
		tab.setForeground(Color.WHITE);
		tab.setBackground(Color.BLACK);
		tab.addTab("How to Play", panel1);
		tab.addTab("Scoring", panel2);
		tab.setBackgroundAt(0, new Color(60,23,27));
		tab.setBackgroundAt(1, new Color(0,82,118));	
		c.add(tab);
		
		setVisible(true);
	}//end constructor
}//end class
