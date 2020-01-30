/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: DynamicObject.java
   Purpose of Class: In essence, this class will represent the game's
   "dynamic objects." That is, a dynamic object is defined as an object that will move
   around the board and interact with the game character.

**/ 

// Import the necessary classes needed for this file.  
import java.awt.*;

// StaticObject class. All objects that move will be instanziated 
// from this class. 
public class DynamicObject {
    
   // A dynamic object is represented by a specific image.  
   private Image dynamicObject;
   
   // Declare variables that will help us determine where the dynamic object is within the map.
   private int locationX;
   private int locationY;
   
   // Variables used to implement movement for tracker dynamic objects
   private double momentumX;
   private double momentumY;
   private int previousDirX;
   private int previousDirY;
   
   // Variable that will help us move the dynamic object randomly.
   private int randomNumber = 0;
    
   // Constructor. Inizialize the object depending on what type of object we want.     
   public DynamicObject( String typeOfObject, int copyLocationX, int copyLocationY ){
  
      if( typeOfObject.equals( "spider" ) )
         dynamicObject = Toolkit.getDefaultToolkit().getImage("img/SpiderEnemy.gif");
      else if( typeOfObject.equals( "tracker" ) )
    	   dynamicObject = Toolkit.getDefaultToolkit().getImage("img/Monkey.gif");
       
      // Copy the locations sent as parameters whenever we are intanziating an object 
      // of this class.   
      locationX = copyLocationX;
      locationY = copyLocationY;
              
   } // end constructor. 
   
   // The purpose of this function is to return the image that represents the 
   // calling object. 
   public Image getDynamicObjectImage ( ) {
   
      return dynamicObject;
   
   } // end getImage function.
   
   // The purpose of this function is to return the Y location of the calling object. 
   public int getLocationY( ) {
   
      return locationY;
       
   } // end getLocationY function.
   
   // The purpose of this function is to return the X location of the calling object.
   public int getLocationX( ) {
   
      return locationX;
       
   } // end getLocationX function.
   
   // Sets the location of the dynamic object. 
   public void setLocation( int x, int y ) {
	   
      locationX = x;
	   locationY = y;
      
   } // end setLocation function.
   
   // The purpose of this function is to set the locations of the dynamic object 
   // to random locations within the map.
   public void resetLocation( ) {
   
      locationX = (int) ( ( Math.random()*( ( 700 - 10 ) + 1 ) ) + 10 ); 
      locationY = (int) ( ( Math.random()*( ( 700 - 10 ) + 1 ) ) + 10 ); 
       
   } // end resetLocation function.
   
   // The purpose of this function is to reset the tracker dynamic object 
   // locations and tracking data.
   public void resetTracker( ) {
   
      locationX = 100;
      locationY = (int) ( ( Math.random()*( ( 500 - 100 ) + 1 ) ) + 100 );
      momentumX = momentumY = previousDirX = previousDirY = 0;
       
   } // end resetTracker function.
   
   // The purpose of this function is to change the coordinate location of the 
   // calling dynamic object to a random location with the given contraints. 
   public void randomMovement( ) { 
   
      // There are four different directions that the dynamic object can move to.
      // Choose one direction at randomly.
      randomNumber = (int) (Math.random( )*4 + 1);
      
      // Check boundaries so that the dynamic object has not moved too far.
      // That is: 1 = up, 2 = right, 3 = down, and 4 = left. That is, the purpose 
      // of these if statements is to check that the enemies do not go 
      // outside the applet. 
      if( randomNumber == 1 ) {
      
         if ( ( locationY - 8 ) >= 0 )
            locationY = locationY - 8;
      
      } // end if.
      
      else if ( randomNumber == 2 ) {
      
         if( (locationX + 8) <= 1000 ) 
            locationX = locationX + 8;
      
      } // end else if.
      
      else if ( randomNumber == 3 ) {
      
         if( (locationY + 8 ) <= 680 )
            locationY = locationY + 8;
      
      } // end else if.
      
      else {
      
         if( (locationX - 8) >= 0 )
            locationX = locationX - 8;
      
      } // end else.
      
   } // end randomMovement.
   
   // The purpose of this function is to change the coordinate location of the 
   // calling dynamic object to a location closer to the Player object. 
   public void trackingMovement( int playerX, int playerY ) { 
   
      // Defines the upper limit of the dynamic object momentum.
      // Increasing this number increases the tracking ability of
      // the dynamic object, and vice versa.
	   double difficulty = 2.5;
	   
      // Treat movement along the x-direction.
      
      // Allows the dynamic object to change x-direction only when
      // the movement in one direction has slowed to a stop. 
	   if ( momentumX == 0 ) {
		  
         // If the dynamic object is to the left of the Player object, move right.
	      if ( locationX < playerX ) {
         
	    	   previousDirX = 1;
            
	      } // end if.
         
         // If the dynamic object is to the right of the Player object, move left.
	      else if ( locationX > playerX ) {
         
	         previousDirX = -1;
            
	      } // end else if.
	      
      } // end if.
	  
      // If movement direction and necessary direction are the same, increase momentum.
      if ( (previousDirX == 1 && locationX < playerX)  || (previousDirX == -1 && locationX > playerX) ) {
      
    	   momentumX += 0.1;
      
      } // end if.
      
      // If movement direction and necessary direction are opposite, decrease momentum.
      else if ( (previousDirX == 1 && locationX > playerX)  || (previousDirX == -1 && locationX < playerX) ) {
      
    	  momentumX -= 0.1;
        
      } // end else if.
      
      // Maintain an upper limit on the dynamic object momentum in the x-direction.
      if ( momentumX > difficulty ) {
      
    	   momentumX = difficulty;
         
      } // end if.
      
      // Move the dynamic object in the specified x-direction by the specified momentum.
      if (previousDirX == 1)
      
         locationX = locationX + (int) (momentumX);
         
      else if (previousDirX == -1)
      
    	   locationX = locationX - (int) (momentumX);
	  
	   // Treat movement along the y-direction.
      
      // Allows the dynamic object to change y-direction only when
      // the movement in one direction has slowed to a stop. 
	   if ( momentumY == 0 ) {
		  
         // If the dynamic object is below the Player object, move up.
	      if ( locationY < playerY ) {
         
	    	  previousDirY = 1;
           
	      } // end if.
         
         // If the dynamic object is above the Player object, move down.
	      else if ( locationY > playerY ) {
         
	    	  previousDirY = -1;
           
	      } // end else if.
	      
      } // end if.
	  
      // If movement direction and necessary direction are the same, increase momentum.
      if ( (previousDirY == 1 && locationY < playerY)  || (previousDirY == -1 && locationY > playerY) ) {
      
    	   momentumY += 0.1;
         
      } // end if.
      
      // If movement direction and necessary direction are opposite, decrease momentum.
      else if ( (previousDirY == 1 && locationY > playerY)  || (previousDirY == -1 && locationY < playerY) ) {
      
    	   momentumY -= 0.1;
         
      } // end else if.
      
      // Maintain an upper limit on the dynamic object momentum in the y-direction.
      if ( momentumY > difficulty ) {
      
    	   momentumY = difficulty;
         
      } // end if.
      
      // Move the dynamic object in the specified y-direction by the specified momentum.
      if (previousDirY == 1)
      
         locationY = locationY + (int) (momentumY);
         
      else if (previousDirY == -1)
      
    	   locationY = locationY - (int) (momentumY);
      
   } // end trackingMovement.
    
} // end StaticObject class.
