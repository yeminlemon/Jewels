import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is the Menu class. It extends JPanel.
 * It contains the basic elements of the start screen at the beginning of the game.
 * 
 * @author Yemin Shou
 */
public class Menu extends JPanel implements ActionListener
{
	//private buttons
	private JButton start, instructions, leaderboard;
	
	/**
	 * Constructor method builds Menu panel
	 */
	public Menu()
	{
		super();
		
		setLayout(null);
		setBounds(0, 0, 880,700);
		setBackground(new Color(88,60,100));
		
		//title is set
		JLabel title = new JLabel();
		title.setBounds((getWidth()-600)/2, 20, 600, 250);
		title.setIcon(new ImageIcon("logo.gif"));
		add(title);
		
		//buttons are set
		start = new JButton();
		start.setBounds((getWidth()-350)/2,280, 350, 300);
		start.setIcon(new ImageIcon("play.gif"));
		start.setBorder(null);
		start.addActionListener(this);
		add(start);
		
		instructions = new JButton();
		instructions.setBounds(50,380,200,160);
		instructions.setIcon(new ImageIcon("instructionstart.gif"));
		instructions.setBorder(null);
		instructions.addActionListener(this);
		add(instructions);
		
		leaderboard = new JButton();
		leaderboard.setBounds(630,380,200,160);
		leaderboard.setIcon(new ImageIcon("leaderboard.gif"));
		leaderboard.setBorder(null);
		leaderboard.addActionListener(this);
		add(leaderboard);
	}//end constructor
	
	/**
	 * Implemented from ActionListener 
	 * This method will wait for an event to occur (ex.button being pressed) and will react to that action occurring
	 */
	public void actionPerformed(ActionEvent e)
	{
		//functions that will occur when the buttons are pressed
		if(e.getSource() == start)
			Game.start();
		if(e.getSource() == instructions)
			new Instructions();
		if(e.getSource() == leaderboard)
			new Leaderboard();
	}//end actionPerformed
}//end class
