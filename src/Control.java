import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is the Control class. It extends JPanel and implements ActionListener.
 * This will act as a control panel where information about the game will be shown.
 * For example, high scores, points, instructions, etc.
 * 
 * @author Yemin Shou
 */
public class Control extends JPanel implements ActionListener
{
	//private buttons and labels
	private JButton leaderboardButton, instructionButton, menuButton;
	
	private JLabel pointsLabel, highScoreLabel;
	
	/**
	 * Constructor method builds Control panel
	 */
	public Control()
	{
		super(); //calls super to construct as JPanel
		
		setLayout(null);
		setBounds(0, 0, 300,700);
		setBackground(Color.BLACK);
		
		//sets buttons
		leaderboardButton = new JButton();
		leaderboardButton.setBounds(getX()+(getWidth()-200)/2,330,200,63);
		leaderboardButton.setBackground(Color.BLACK);
		leaderboardButton.setBorder(null);
		leaderboardButton.setIcon(new ImageIcon("highscore.gif"));
		leaderboardButton.addActionListener(this);
		add(leaderboardButton);
		
		instructionButton = new JButton();
		instructionButton.setBounds(leaderboardButton.getX(),leaderboardButton.getY()+leaderboardButton.getHeight(),leaderboardButton.getWidth(),150);
		instructionButton.setBackground(Color.BLACK);
		instructionButton.setBorder(null);
		instructionButton.setIcon(new ImageIcon("instructions.gif"));
		instructionButton.addActionListener(this);
		add(instructionButton);
		
		menuButton = new JButton();
		menuButton.setBounds(leaderboardButton.getX(),instructionButton.getY()+instructionButton.getHeight(),leaderboardButton.getWidth(),93);
		menuButton.setBackground(Color.BLACK);
		menuButton.setBorder(null);
		menuButton.setIcon(new ImageIcon("menu.gif"));
		menuButton.addActionListener(this);
		add(menuButton);
		
		//sets points label and border
		pointsLabel = new JLabel(Game.points + "");
		pointsLabel.setBounds(getX()+(getWidth()-220)/2, 50,220,110);
		pointsLabel.setFont(new Font("Blue Highway", Font.PLAIN, 36));
		pointsLabel.setForeground(Color.WHITE);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		add(pointsLabel);
		
		JLabel pointsBorder = new JLabel();
		pointsBorder.setBounds(pointsLabel.getBounds());
		pointsBorder.setIcon(new ImageIcon("Images/Border/points.gif"));
		add(pointsBorder);
		
		//sets high score labels
		JLabel HSLabel = new JLabel("High Score");
		HSLabel.setBounds(getX()+(getWidth()-220)/2, 210,220,110);
		HSLabel.setFont(new Font("Blue Highway", Font.PLAIN, 28));
		HSLabel.setForeground(Color.WHITE);
		HSLabel.setHorizontalAlignment(JLabel.CENTER);
		add(HSLabel);

		highScoreLabel = new JLabel(Leaderboard.stringScore(Leaderboard.getHighScore()));
		highScoreLabel.setBounds(HSLabel.getX(), HSLabel.getY()+30,HSLabel.getWidth(),HSLabel.getHeight());
		highScoreLabel.setFont(new Font("Blue Highway", Font.PLAIN, 28));
		highScoreLabel.setForeground(Color.WHITE);
		highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		add(highScoreLabel);
	}//end constructor
	
	/**
	 * Updates the pointsLabel using correct formatting of the number
	 */
	public void updatePoints()
	{
		String points = Game.points + "";
		
		if(Game.points > 999 && Game.points < 1000000)
			points = points.substring(0,points.length()-3) + "," + points.substring(points.length()-3);	//adds a comma when appropriate
		
		pointsLabel.setText(points);
	}//end updatePoints
	
	/**
	 * Used at the end of the game, to prompt the user to submit their score
	 */
	public void submitScore()
	{
		IO.createOutputFile("HighScores.txt", true);
		
		String name = (String)JOptionPane.showInputDialog(null, "GAME OVER. \n You scored " + Game.points + " points. \nSubmit your score into the leaderboard to compare with other players!\nEnter your name to submit your score:", 
				"Submit Your Score", JOptionPane.PLAIN_MESSAGE, null, null, "John Smith");	//opens dialog 
		
		IO.println(name);				//prints name and points into the text file
		IO.println(Game.points + "");
		IO.closeOutputFile();
		
		if(Game.points > Leaderboard.getHighScore())	//if the user beat the high score, the new high score shows up
			highScoreLabel = new JLabel(Leaderboard.stringScore(Game.points));
	}//end submitScore
	
	/**
	 * Implemented from ActionListener 
	 * This method will wait for an event to occur (ex.button being pressed) and will react to that action occurring
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == instructionButton)
			new Instructions();
		if(e.getSource() == leaderboardButton)
			new Leaderboard();
		if(e.getSource() == menuButton)
		{
			int prompt = -1;
			if(Timer.on)	//if the timer is still on, then user will be prompted
				prompt = JOptionPane.showConfirmDialog(null,"Are you sure you would like to return to the menu?\nYour current game will end.", "Back to Menu", JOptionPane.YES_NO_OPTION); 	//confirms that user wants to return to menu
			if(prompt == 0 || !Timer.on)	//if timer is not on, or if user said yes, then it will return to the menu
			{
				Timer.on = false; //if player said yes, then timer will be turned off
				
				new Game();	//new game will be created
			}
			
			//else, nothing will happen, game will continue
		}
	}//end actionPerformed
}//end class
