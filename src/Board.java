import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is the Board class for the Bejeweled Game. It extends JPanel and implements ActionListener.
 * It will contain all of the Jewels in a grid and will regulate most of the game mechanics in actionPerformed as Jewels are being pressed.
 * 
 * @author Yemin Shou
 */
public class Board extends JPanel implements ActionListener
{
	//private instance variables
	private Jewel grid[][] = new Jewel[10][10];	//builds the grid
	
	private Jewel pressed = new Jewel();	//used in actionPerformed and is initialized to whatever Jewel is pressed
	private boolean firstPressed = true;	//used in actionPerformed to indicate if it's first Jewel pressed
	
	//static constant variables
	//positioning
	final static int TOP = 1;		//The numbers 1,2,4,8 were chosen to represent the different sides so that when combining
	final static int BOTTOM = 2;	//	the sides together in the checkThree method, the sum does not equal a different already existing constant variable
	final static int LEFT = 4;
	final static int RIGHT = 8;
	
	/**
	 * Constructor method builds Board panel
	 */
	public Board()
	{
		super(); //calls super to construct as JPanel
		
		setLayout(null);
		setBounds(300, 90, 535,500);
		setBackground(Color.BLACK);
		
		int size = 50;	//the size of the Jewel, since it is a square it will be one length for all sides
		
		for(int x = 0; x < grid.length; x++)	//goes through the columns
		{
			for(int y = 0; y < grid.length; y++)	//goes through the rows
			{
				grid[x][y] = new Jewel();
				grid[x][y].setBounds(x*size + ((getWidth()-grid.length*size)/2), y*size, size,size); //centers the grid of Jewels in the Board JPanel
				
				while(checkThree(grid[x][y]) > 0)	//while the newly created Jewel is in a group of 3 already
					grid[x][y].setType((int)(Math.random()*7+1));	//the type of the Jewel will change to ensure that no groups of 3 will already be present at the start of the game
				
				add(grid[x][y]);
			}
		}//end for loop
		
		
	}// end constructor
	
	/**
	 * Returns which Jewel is beside the given Jewel
	 * @param j A Jewel being checked which Jewel is beside it
	 * @param side The side of the Jewel being checked
	 * @return The Jewel that is beside that checked Jewel
	 */
	public Jewel jewelBeside(Jewel j, int side)
	{
		Rectangle bounds = new Rectangle();	//Rectangle bounds is used to set bounds to the Jewel that is beside the target Jewel for comparison
		
		if(side == TOP)
			bounds.setBounds(j.getX(), j.getY()-j.getHeight(), j.getWidth(), j.getHeight());
		else if(side == BOTTOM)
			bounds.setBounds(j.getX(), j.getY()+j.getHeight(), j.getWidth(), j.getHeight());
		else if(side == RIGHT)
			bounds.setBounds(j.getX()+j.getWidth(), j.getY(), j.getWidth(), j.getHeight());
		else if(side == LEFT)
			bounds.setBounds(j.getX()-j.getWidth(), j.getY(), j.getWidth(), j.getHeight());
		
		//checks all of the Jewels in the grid for one that matches the bounds
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				try	//try catches NullPointerException so that during the constructor, when it checks all of the Jewels (some of which not initialized), it will not crash
				{
					if(grid[x][y].getBounds().equals(bounds))
						return grid[x][y];
				}
				catch(NullPointerException e)
				{}
			}
		}//end for loop
		
		return new Jewel(-1); //if there is nothing beside that Jewel, returns an empty Jewel instead of null to avoid NullPointerException
	}//end getBeside method
	
	/**
	 * Checks if the Jewel is in a group of 3
	 * @param j The Jewel being checked
	 * @return The positioning of the Jewel in the group of 3
	 */
	public int checkThree(Jewel j)
	{
		int position = 0;	// will return 0 if not in a group of 3
		
		if(jewelBeside(j,TOP).equals(j))
		{
			if(jewelBeside(j,BOTTOM).equals(j))	//if Jewel on top and bottom are the same
			{
				position += TOP;
				position += BOTTOM;
			}
			else if(jewelBeside(jewelBeside(j,TOP),TOP).equals(j))	//if two Jewels on top are the same
				position += TOP;
		}
		else if(jewelBeside(j,BOTTOM).equals(j) && jewelBeside(jewelBeside(j,BOTTOM),BOTTOM).equals(j))	//if two Jewel on bottom are the same
				position += BOTTOM;
		
		if(jewelBeside(j,RIGHT).equals(j))
		{
			if(jewelBeside(j,LEFT).equals(j))	//if Jewel left and right are the same
			{
				position += RIGHT;
				position += LEFT;
			}
			else if(jewelBeside(jewelBeside(j,RIGHT),RIGHT).equals(j))	//if two Jewels to the right are the same
				position += RIGHT;
		}
		else if(jewelBeside(j,LEFT).equals(j) && jewelBeside(jewelBeside(j,LEFT),LEFT).equals(j))	//if two Jewels to the left are the same
				position += LEFT;
		
		return position;	//returns the positioning which will be a number calculated by the different positions of the Jewel; will return 0 if it is not in a group of 3
	}// end positionThree method
	
	/**
	 * If the target Jewel is in a group of 3 already, this method checks
	 * to see if there are more than 3 Jewels in the line and marks them.
	 * Usually used with checkThree returning the position of the group of 3.
	 * @param j The target Jewel being checked
	 * @param position The position of the target Jewel in the group of 3
	 */
	public void markLine(Jewel j, int position)
	{
		Jewel target;	//the target Jewel
		int numCleared = 0;	//the number of Jewels cleared/marked 
		int direction1 = -1, direction2 = -1, direction3 = -1; //the three possible directions to check
			
		if(position > 0) //if the position is greater than 0, then there is a group of 3 to check
		{
			//sets the different directions according to the positioning of the Jewel
			if(position == TOP || position == BOTTOM || position == LEFT || position == RIGHT)
			{
				direction1 = position;
			}
			else if(position == TOP+BOTTOM || position == TOP+RIGHT || position == TOP+LEFT)
			{
				direction1 = TOP;
				direction2 = position - TOP;
			}
			else if(position == BOTTOM+RIGHT || position == BOTTOM+LEFT)
			{
				direction1 = BOTTOM;
				direction2 = position - BOTTOM;
			}
			else if(position == RIGHT+LEFT)
			{
				direction1 = RIGHT;
				direction2 = LEFT;
			}
			else if(position == TOP+BOTTOM+RIGHT || position == TOP+BOTTOM+LEFT)
			{
				direction1 = TOP;
				direction2 = BOTTOM;
				direction3 = position - TOP - BOTTOM;
			}
			else if(position == RIGHT+LEFT+TOP || position == RIGHT+LEFT+BOTTOM)
			{
				direction1 = RIGHT;
				direction2 = LEFT;
				direction3 = position - RIGHT - LEFT;
			}
			
			j.setEnabled(false);	//marks target Jewel
			numCleared++;	//increases to include the target Jewel being cleared
			
			//checks if the Jewel group of 3 has more than just 3 of the same type of Jewel
			target = j;
			while(jewelBeside(target,direction1).equals(j))	//while the Jewel beside the target Jewel in the first direction is the same type
			{
				numCleared++;	//increases with each Jewel being cleared/marked
				jewelBeside(target,direction1).setEnabled(false);	//marks the Jewel that is beside the target Jewel
				target = jewelBeside(target,direction1);	//target Jewel becomes the marked Jewel that's beside it
			}
			
			//if the target Jewel is in the middle of a group and not at the end, there is a second direction to check for extra same Jewels
			target = j;
			while(jewelBeside(target,direction2).equals(j))	
			{
				numCleared++;	//increases with each Jewel being cleared/marked
				jewelBeside(target,direction2).setEnabled(false);
				target = jewelBeside(target,direction2);
			}
			
			//there is a possibility of the target Jewel being in a group where there are 3 directions to check beyond the target Jewel
			target = j;
			while(jewelBeside(target,direction3).equals(j))	
			{
				numCleared++;	//increases with each Jewel being cleared/marked
				jewelBeside(target,direction3).setEnabled(false);
				target = jewelBeside(target,direction3);
			}
			
			//this is a type of if else statement
			Game.points += (numCleared == 3)? 250 : (numCleared == 4)? 500 : 1000;	//increases number of points depending on number of Jewels cleared
			
			//a delay will occur so that the Jewels that are marked can be seen before they are cleared
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e){}
		}//end if statement
		
	}// end checkLine method
	
	/**
	 * Checks the entire grid of Jewels for a group of the same type of Jewels and marks them
	 * @return A boolean true if there was a group of Jewels marked, false if there are none on the grid
	 */
	public boolean checkGrid()
	{
		boolean check = false;	//will stay false if there are no groups of 3 on the grid
		
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				if(checkThree(grid[x][y]) > 0 && grid[x][y].isEnabled())	//if there is a group of three and the Jewel is not already marked (so that it does not mark twice, and does not count double points)
				{
					check = true;	//will become true once there is a group of 3 detected
					markLine(grid[x][y], checkThree(grid[x][y]));	//group of Jewels will be marked
				}
			}
		}//end for loop
		
		return check;
	}//end checkGrid method
	
	/**
	 * Checks the entire grid and moves all of the marked Jewels to the top of their column
	 * Usually used after checkGrid or markLine is used because the Jewels needed to be cleared will be marked
	 */
	public void clearGrid()
	{
		Jewel target;
		
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				if(!grid[x][y].isEnabled())	//if the Jewel is marked
				{
					target = grid[x][y];	//target will become this marked Jewel
					
					//While the Jewel on top of the marked Jewel exists (has a type that is not -1) 
					//and while the Jewel on top is an unmarked Jewel (this allows a set of marked Jewels in one column to all move up to the top without re-enabling them) 
					while(jewelBeside(target,TOP).getType() > 0 && jewelBeside(target,TOP).isEnabled())
					{
						target.swapType(jewelBeside(target,TOP));	//target Jewel swaps with Jewel on top
						target.setEnabled(true);	//target Jewel becomes unmarked
						target = jewelBeside(target,TOP);	//target Jewel becomes the top Jewel
						target.setEnabled(false);	//the target top Jewel becomes marked
					}			
				}//end if statement
			}//end for loop
		}//end for loop
	}//end clearGrid method
	
	/**
	 * Checks the grid for all marked Jewels and replaces them with a new random type Jewel
	 * Should be used after clearGrid method when all of the marked Jewels are moved to the top of their column
	 */
	public void refillGrid()
	{
		for(int y = grid.length-1; y > -1; y--)
		{
			for(int x = grid.length-1; x > -1; x--)
			{
				if(!grid[x][y].isEnabled())	//if Jewel is marked
				{
					grid[x][y].setType((int)(Math.random()*7+1));	//sets a new type
					grid[x][y].setEnabled(true);	//unmarks the Jewel
				}
			}
		}//end for loop
	}//end refillGrid method
	
	/**
	 * Implemented from ActionListener 
	 * This method will wait for an event to occur (ex.button being pressed) and will react to that action occurring
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(firstPressed)	// if it is the first Jewel pressed
		{
			pressed = (Jewel)e.getSource();	//pressed will become the first Jewel pressed
			pressed.setGlow(true);	//Jewel will be marked to signify it having been selected
			firstPressed = false;
		}	
		else	//if it is second Jewel pressed
		{
			if(pressed.isTouching((Jewel)e.getSource()))	//if second pressed Jewel is touching first pressed Jewel
			{
				pressed.swapType((Jewel)e.getSource());	//then the two Jewels will swap types
			
				if(checkThree(pressed) <= 0 && checkThree((Jewel)e.getSource()) <= 0) //if the two Jewels are not in a group of 3
					pressed.swapType((Jewel)e.getSource());	//then the two Jewels will switch types back to original
				else	//if either of the two Jewels are in a group of 3
					Timer.gridFunctions((Jewel)e.getSource(),pressed);	//all of the functions that occur with the Jewels in the grid will happen through the Timer class so that the thread.sleep will work and we can see the Jewels that are being cleared
				
				firstPressed = true;	//firstPressed is set back to true
			}	
			else if(e.getSource() == pressed)	//if the second pressed Jewel is the same Jewel pressed again
			{
				pressed.setGlow(false);	//glow will be turned off
				firstPressed = true;	//firstPressed will become true
			}
			else	//if the second Jewel is not touching the first and is not the same Jewel pressed again
			{
				pressed.setGlow(false);	//unmarks the first pressed Jewel
				pressed = (Jewel)e.getSource();	//sets the second pressed Jewel as the first pressed
				pressed.setGlow(true);	//marks the new pressed Jewel
				//firstPressed does not turn back to true
			}
		}// end else
		
	}// end actionPerformed method
	
	/**
	 * Adds actionListener to all of the Jewels in the grid
	 */
	public void addActionListener()
	{
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				grid[x][y].addActionListener(this);
			}
		}//end for loop
	}//end addActionListener method
	
	/**
	 * Removes actionListener from all of the Jewels in the grid and resets the look back to the default look
	 */
	public void removeActionListener()
	{
		for(int x = 0; x < grid.length; x++)
		{
			for(int y = 0; y < grid.length; y++)
			{
				grid[x][y].removeActionListener(this);
				grid[x][y].setType(grid[x][y].getType());
			}
		}//end for loop
	}//end removeActionListener method
	
}// end class
