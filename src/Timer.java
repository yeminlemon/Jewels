import java.awt.*;
import javax.swing.*;
import java.util.concurrent.*; //this is imported to allow the thread.sleep to work

/**
 * This is the Timer class. All aspects within this class are static.
 * This is where the mechanics of the timer in the Bejeweled game will happen.
 * 
 * @author Yemin Shou
 */
public class Timer
{
	//static variables necessary to allow Thread.sleep to work, this set is used for the actual timer in the start method
	private static ExecutorService gameExecutor = Executors.newSingleThreadExecutor();
	private static Future<?> gameTask;
	
	//a second set of these variables were made so that we can have two delays working at the same time for two different purposes, this set is used to be able to see which Jewels on the grid were cleared before they are cleared in the gridFunctions method
	private static ExecutorService gameExecutor2 = Executors.newSingleThreadExecutor();
	private static Future<?> gameTask2;
	
	//static JProgressBar displays a visual representation of the timer ticking down
	public static JProgressBar bar; //static to allow Game JFrame to add it and for other classes to access it
	
	//boolean to regulate whether the timer will continue or not
	public static boolean on = true;
	
	//static Jewels are used in the gridFunctions method
	private static Jewel source, pressed;
	
	/**
	 * Sets up the bar JProgressBar
	 * @param seconds Sets the number of seconds the timer will count down from
	 */
	public static void setBar(int seconds)
	{
		on = true;	//every time you reset the bar, on becomes true again so that timer will work
		
		bar = new JProgressBar(0,seconds);	//sets minimum and maximum value of the bar
		bar.setValue(bar.getMaximum());	//sets the starting value to the maximum
		bar.setStringPainted(true);
		
		String zero = "";
		if(bar.getValue()%60 < 10)
			zero = "0";	//adds a zero if the number of seconds (disregarding whole minutes) is less than 10
		
		bar.setString((bar.getValue()/60) + ":" + zero + (bar.getValue()%60));	//sets the bar to show time left which would be the maximum
		
		bar.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 22));
		bar.setBackground(new Color(60,23,27));
		bar.setForeground(new Color(0,82,118));
		
		bar.setBorderPainted(false);
		
		Timer.bar.setBounds(Game.board.getX()+(Game.board.getWidth()-465)/2, Game.board.getY()+Game.board.getHeight()+7, 465, 26);
	}//end setBar method
	
	/**
	 * Starts the timer to start counting down
	 */
	public static void start() 
	{
		//This little part is required to allow thread.sleep to work properly, although honestly speaking, I am not sure what it does that allows thread.sleep to work
		//The Internet is credited for this little section of code that allows thread.sleep to work!
		if (gameTask != null) 
			gameTask.cancel(true);

		//the new Runnable class is necessary for thread.sleep to work
		gameTask = gameExecutor.submit(new Runnable() 
		{
			//Whatever is inside this run method is what will happen when this Timer.start method is called, thread.sleep has to be in here to work properly
			public void run() 
			{
				Game.countdown.setVisible(true);	//the countdown becomes visible
				
				for(int i = 0; i < 4; i++)	// loops 3 times
				{
					try
					{
						Thread.sleep(1000);	//delays 1 second
					}
					catch(InterruptedException e){}
					
					String text = (i == 0)? "2" : (i == 1)? "1" : "GO!";	//this is sort of a simplified if-else statement where if i == 0 then text will be "2" and if i == 1 then text is "1", else text is "GO!"
					
					Game.countdown.setText(text);	//sets the countdown to the text
				}
				
				Game.board.setVisible(true);	//after the countdown is over, board is visible and the game will start
				
				while(bar.getValue() > 0 && on)	//while the bar value is greater than 0 
				{
					bar.setValue(bar.getValue() - 1);	//bar value will decrease by 1
					
					String zero = "";
					if(bar.getValue()%60 < 10)
						zero = "0";
					
					bar.setString((bar.getValue()/60) + ":" + zero + (bar.getValue()%60));	//shows a string on the bar depicting the time left
					
					if(bar.getValue() < 11)
						bar.setForeground(Color.RED);
					
					try
					{
						Thread.sleep(1000);	//a delay of one second (1000 milliseconds) will occur
					}
					catch(InterruptedException e){}
				}
				
				if(on)	//if the boolean is still on when the while loop is over
				{
					Game.over();	//game is over because the game was played until timer reached 0
					on = false;		//otherwise, the user may have quit the game
				}
									
			}//end run method
		});//end new Runnable
	}// end start method

	/**
	 * Goes through checking the grid of Jewels, marks the groups, clears, and refills them.
	 * This method is necessary so that the thread.sleep that is in the markLine method will work.
	 * The thread.sleep will only work if it goes through this method and the Runnable class that is inside it first.
	 * @param s The source Jewel from the actionPerformed in the Board class
	 * @param p The pressed Jewel from the Board class
	 */
	public static void gridFunctions(Jewel s, Jewel p)
	{
		source = s;	//this is necessary because in the new Runnable class below, it can only access the static Jewels in the Timer class and can't access s and p
		pressed = p;
		
		//this is the little piece of code again that allows thread.sleep to work
		if (gameTask2 != null) 
			gameTask2.cancel(true);
		
		//the new Runnable that is necessary for thread.sleep to work
		gameTask2 = gameExecutor2.submit(new Runnable() 
		{
			public void run()
			{
				Game.board.markLine(source,Game.board.checkThree(source));	//marks the group of 3 or more if it is the second source Jewel
				Game.board.markLine(pressed,Game.board.checkThree(pressed));	//marks the group of 3 or more if it is the firstPressed Jewel
		
				Game.board.clearGrid();	//clears the marked group
				Game.board.refillGrid();	//refills the cleared Jewels with new ones
				Game.control.updatePoints();
				
				while(Game.board.checkGrid())	//checks the entire grid after the Jewels have been moved and while there is a new group of 3 on the grid, it will mark it
				{
					Game.points += 500; //if there are extra groups on the grid after the swapped ones were cleared, cascade bonus points are given
					Game.board.clearGrid();	//the grid will be cleared
					Game.board.refillGrid();	//the grid will be refilled with new Jewels 
					Game.control.updatePoints();
				}
			}//end run method
		});//end new Runnable
	}//end gridFunctions method
	
}// end class
