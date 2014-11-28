import java.awt.*;
import javax.swing.*;

/**
 * This is the Leaderboard class. It extends JFrame.
 * It is a popup that will appear whenever the user wants to view the high scores.
 * 
 * @author Yemin Shou
 */
public class Leaderboard extends JFrame
{
	//private variables 
	private static int[] scores;
	private static String[] names;
	private JLabel[] nameLabel = new JLabel[10];
	private JLabel[] scoreLabel = new JLabel[10];
	
	/**
	 * Constructor method builds Leaderboard frame
	 */
	public Leaderboard()
	{
		super();
		
		setSize(400, 650);
		setResizable(false);
		setLayout(null);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(60,23,27));
		
		//sets title
		JLabel highScores = new JLabel("High Scores");
		highScores.setBounds(getX(), 20, getWidth(), 50);
		highScores.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 36));
		highScores.setHorizontalAlignment(JLabel.CENTER);
		highScores.setForeground(Color.WHITE);
		c.add(highScores);
		
		//initializes the arrays with each person's high score
		getHighScore();
		
		int numScores = 10;		//by default, the leaderboard will only show top 10 high scores
		if(scores.length < 10)	//if there are less than 10, then numScores is set to that number to avoid array out of bounds exception below
			numScores = scores.length;
		
		for(int i = 0; i < numScores; i++)
		{
			//sets all of the labels with the name and placement
			nameLabel[i] = new JLabel(i + 1 + ".  " + names[names.length-1-i]);
			nameLabel[i].setBounds(50, 80 + i*30, 200, 50);
			nameLabel[i].setFont(new Font("Blue Highway", Font.PLAIN, 28));
			nameLabel[i].setForeground(Color.WHITE);
			c.add(nameLabel[i]);
			
			//sets all of the score labels
			scoreLabel[i] = new JLabel(stringScore(scores[scores.length-1-i]));
			scoreLabel[i].setBounds(200, 80 + i*30, 150, 50);
			scoreLabel[i].setFont(new Font("Blue Highway", Font.PLAIN, 28));
			scoreLabel[i].setForeground(Color.WHITE);
			scoreLabel[i].setHorizontalAlignment(JLabel.RIGHT);
			c.add(scoreLabel[i]);
		}
		
		setVisible(true);
	}//end constructor
	
	/**
	 * Returns the current top high score, also initializes all of the scores into arrays
	 * @return An int depicting the top high score
	 */
	public static int getHighScore()
	{
		int numLines = 0;
		IO.openInputFile("HighScores.txt");	//opens file

		while(IO.readLine() != null)	//determines how many lines are in file
			numLines++;
		
		IO.closeInputFile();
		
		scores = new int[numLines/2];	//declares arrays, numLines divided by 2 because score and name lines alternate
		names = new String[numLines/2];
		
		IO.openInputFile("HighScores.txt");
		
		for(int i = 0; i < scores.length; i++)	//initializes all arrays
		{
			names[i] = IO.readLine();
			scores[i] = Integer.parseInt(IO.readLine());
		}
		
		insertionSort(scores);	//sorts the arrays from lowest to highest high score
		
		return scores[scores.length-1]; //returns highest score
	}//end getHighScore method
	
	/**
	 * Turns a score into a formatted number with a comma
	 * @param score The score being formatted
	 * @return A String containing correctly formatted score
	 */
	public static String stringScore(int score)
	{
		String s = score + "";
		
		if(score > 999 && score < 1000000)
			s = s.substring(0,s.length()-3) + "," + s.substring(s.length()-3);	//adds comma
		
		return s;
	}// end stringScore method
	
	/**
	 * Sorts the int array from lowest to highest
	 * @param numbers An integer array being sorted
	 */
	public static void insertionSort(int numbers[])
	{
		int i, j, currentElement;
		String currentElement2;
		
		for (i = 1; i < numbers.length; i++)
		{
			j = i;
			// store the value of the "current" element
			currentElement = numbers[j];
			currentElement2 = names[j];
			// determine the correction location (in the sorted part
			// of the array) to insert the "current" element
			while ((j > 0) && (numbers[j-1] > currentElement))
			{
				numbers[j] = numbers[j-1];
				names[j] = names[j-1];
				j --;
			}
			// insert the element into its correct location
			numbers[j] = currentElement;
			names[j] = currentElement2;
		}
	}//end insertionSort method
}//end class