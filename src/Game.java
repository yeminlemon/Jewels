import java.awt.*;
import javax.swing.*;

/**
 * This is the Game class. It extends JFrame.
 * This class contains all of the contents of the GUI and is the main window of the Bejeweled program.
 * 
 * @author Yemin Shou
 */
public class Game extends JFrame
{
	//sets static components of the game, static because they need to be accessed throughout the program
	static Board board;
	static Control control;
	static Menu menu;
	
	//static points will be kept here
	static int points;
	
	static JLabel countdown;
	
	/**
	 * Constructor method builds Game frame
	 */
	public Game()
	{
		super("Bejeweled");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(880, 700);
		setResizable(false);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.BLACK);
		
		//menu is set first
		menu = new Menu();
		c.add(menu);
		
		//board is invisible until game starts
		board = new Board();
		board.setVisible(false);
		c.add(board);
		
		JLabel topBorder =  new JLabel();
		topBorder.setBounds(board.getX()+(board.getWidth()-550)/2, board.getY() - 30, 535, 30);
		topBorder.setIcon(new ImageIcon("Images/Border/grid.gif"));
		c.add(topBorder);
		
		JLabel timerBorder = new JLabel();
		timerBorder.setBounds(board.getX()+(board.getWidth()-550)/2, board.getY()+board.getHeight(), 550, 40);
		timerBorder.setIcon(new ImageIcon("Images/Border/timer.gif"));
		c.add(timerBorder);
		
		//sets timer bar
		Timer.setBar(60);
		Timer.bar.setVisible(false);
		c.add(Timer.bar);
		
		//control is also invisible until game starts
		control = new Control();
		control.setVisible(false);
		c.add(control);
		
		//countdown is set and invisible until game starts
		countdown = new JLabel("3");
		countdown.setBounds(board.getX()+(board.getWidth()-300)/2, board.getY()+(board.getHeight()-150)/2, 300,150);
		countdown.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 130));
		countdown.setForeground(new Color(225,225,150));
		countdown.setHorizontalAlignment(JLabel.CENTER);
		countdown.setVisible(false);
		c.add(countdown);

		setVisible(true);
	}//end constructor

	/**
	 * Starts the game
	 */
	public static void start()
	{
		//timer will start, features of the game will become visible, the menu will become invisible
		board.addActionListener();
		menu.setVisible(false);
		control.setVisible(true);
		Timer.bar.setVisible(true);
		Timer.start();
	}//end start method
	
	/**
	 * Game over when timer drops to 0
	 */
	public static void over()
	{
		//Jewels actionListener's are removed
		board.removeActionListener();
		control.submitScore();	//prompts user to submit their high score
		new Leaderboard();	//Leaderboard will show
	}//end over method
	
}//end class
