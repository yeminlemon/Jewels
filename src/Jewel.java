import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * This is the Jewel class. It extends JButton.
 * These are the Jewels pressed in the Bejeweled Game.
 * It will store extra information about the jewel, such as type and power.
 * 
 * @author Yemin Shou
 */
public class Jewel extends JButton
{
	// private instance variables
	private int type;

	// static constant variables
	// types of Jewels
	final static int RED = 1;
	final static int GREEN = 2;
	final static int BLUE = 3;
	final static int YELLOW = 4;
	final static int PURPLE = 5;
	final static int ORANGE = 6;
	final static int WHITE = 7;
	
	/**
	 * Constructor method will randomly set a type 1-7
	 * Sets default no power of -1
	 */
	public Jewel()
	{
		this((int)(Math.random()*7+1), -1);
	}// end constructor
	
	/**
	 * Constructor method sets type
	 * Sets default no power of -1
	 * @param t Sets type of jewel
	 */
	public Jewel(int t)
	{
		this(t, -1);
	}// end constructor
	
	/**
	 * Constructor method sets type and power
	 * @param t Sets type of jewel
	 * @param p Sets power of jewel
	 */
	public Jewel(int t, int p)
	{
		super(); //calls super to construct as JButton

		setType(t);
		
		setBackground(Color.BLACK);
		setBorder(null);
	}// end constructor
	
	/**
	 * Accessor method
	 * @return An int describing type of jewel
	 */
	public int getType()
	{
		return type;
	}// end getType method
	
	/**
	 * Mutator method
	 * @param t Sets type of jewel and changes appearance of jewel
	 */
	public void setType(int t)
	{
		type = t;
		
		//changes the image file depending on the type of jewel; using if else statements
		String image = "Images/";
		if(type == RED)
			image += "Red/jewel.gif";
		else if(type == GREEN)
			image += "Green/jewel.gif";
		else if(type == BLUE)
			image += "Blue/jewel.gif";
		else if(type == YELLOW)
			image += "Yellow/jewel.gif";
		else if(type == PURPLE)
			image += "Purple/jewel.gif";
		else if(type == ORANGE)
			image += "Orange/jewel.gif";
		else if(type == WHITE)
			image += "White/jewel.gif";
		
		setIcon(new ImageIcon(image));
		
		//changes the disabled icon image depending on type of Jewel; using this style sort of like if/else statements which I later learned
		//disabled is used to mark the jewel if it is being cleared
		String disabled = "Images/";
		disabled += (type == RED)? "Red/mark.gif": (type == GREEN)? "Green/mark.gif": (type == BLUE)? "Blue/mark.gif": (type == YELLOW)? "Yellow/mark.gif":
			(type == PURPLE)? "Purple/mark.gif": (type == ORANGE)? "Orange/mark.gif": (type == WHITE)? "White/mark.gif": "";
		
		setDisabledIcon(new ImageIcon(disabled));
		
	}// end setType method
	
	/**
	 * Sets the image of the Jewel to have a marked glow to them
	 * @param on A boolean for switching the glow on or off
	 */
	public void setGlow(boolean on)
	{
		if(on)	//if glow is turned on, then the image will change for the Jewel
		{
			String image = "Images/";
			if(type == RED)
				image += "Red/glow.gif";
			else if(type == GREEN)
				image += "Green/glow.gif";
			else if(type == BLUE)
				image += "Blue/glow.gif";
			else if(type == YELLOW)
				image += "Yellow/glow.gif";
			else if(type == PURPLE)
				image += "Purple/glow.gif";
			else if(type == ORANGE)
				image += "Orange/glow.gif";
			else if(type == WHITE)
				image += "White/glow.gif";
			
			setIcon(new ImageIcon(image));
		}
		else	//if glow is turned off, then it will default back to original image
			setType(type);
	}//end setGlow method
	
	/**
	 * Swaps the type of the two jewels
	 * @param j Swaps the type with this Jewel
	 */
	public void swapType(Jewel j)
	{
		int temp = j.getType();
		j.setType(type);
		setType(temp);
	}// end swapType method
	
	/**
	 * Checks to see if the second Jewel is touching this Jewel
	 * @param j A Jewel being checked to see if it is touching
	 * @return A boolean true if the two Jewels are touching
	 */
	public boolean isTouching(Jewel j)
	{		
		return ((getX()+getWidth() == j.getX() && getY() == j.getY())		//checks if second Jewel is to right of this Jewel
				||(getX()-getWidth() == j.getX() && getY() == j.getY())		//checks if second Jewel is to left of this Jewel
				||(getX() == j.getX() && getY()+getHeight() == j.getY())	//checks if second Jewel is above this Jewel
				||(getX() == j.getX() && getY()-getHeight() == j.getY()));	//checks if second Jewel is below this Jewel
	}// end isTouching method
	
	/**
	 * @return A string containing the type of jewel
	 */
	public String toString()	//only used for testing purposes
	{
		String jewelType = "";
		
		if(type == RED)
			jewelType = "red";
		else if(type == GREEN)
			jewelType = "green";
		else if(type == BLUE)
			jewelType = "blue";
		else if(type == YELLOW)
			jewelType = "yellow";
		else if(type == PURPLE)
			jewelType = "purple";
		else if(type == ORANGE)
			jewelType = "orange";
		else if(type == WHITE)
			jewelType = "white";
		
		return jewelType;
	}// end toString method
	
	/**
	 * @param j Another Jewel being compared
	 * @return A boolean true when the two Jewel types are the same
	 */
	public boolean equals(Jewel j)
	{
		return(j.getType() == type);
	}// end equals method
	
} // end class
