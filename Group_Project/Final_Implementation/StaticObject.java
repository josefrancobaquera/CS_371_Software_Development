/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: StaticObject.java
   Purpose of Class: In essence, this class will represent the game's
   "static objects." That is, a static object is defined as an object that will NOT 
   move around the board. A player object however can "pick up" a static object.

**/ 

// Import the necessary classes needed for this file.  
import java.awt.*;

// StaticObject class. All objects that NOT move will be instanziated 
// from this class. 
public class StaticObject {
    
   // A static object is represented by a specific image.  
   private Image staticObject;
   
   // Declare variables that will help us determine where the static object is within the map.
   private int locationX;
   private int locationY;
    
   // Inizialize the object depending on what type of static object we want.     
   public StaticObject( String typeOfObject, int copyLocationX, int copyLocationY ){
  
      if( typeOfObject.equals( "rock" ) )
         staticObject = Toolkit.getDefaultToolkit().getImage("img/Rock.png");
      else if ( typeOfObject.equals( "poisonousFruit" ) )
         staticObject = Toolkit.getDefaultToolkit().getImage("img/PoisonousFruit.png");
      else if ( typeOfObject.equals( "antidoteFruit" ) )
         staticObject = Toolkit.getDefaultToolkit().getImage("img/AntidoteFruit.png");
      else if ( typeOfObject.equals( "flareGun") )
         staticObject = Toolkit.getDefaultToolkit().getImage("img/FlareGun.png");
         
      // Copy the locations sent as parameters whenever we are intanziating an object 
      // of this class.   
      locationX = copyLocationX;
      locationY = copyLocationY;
              
   } // end constructor. 
   
   // The purpose of this function is to return the image that represents the 
   // calling object. 
   public Image getStaticObjectImage ( ) {
   
      return staticObject;
   
   } // end getImage function.
   
   // The purpose of this function is to return the Y location of the calling object. 
   public int getLocationY( ) {
   
      return locationY;
       
   } // end getLocationY function.
   
   // The purpose of this function is to return the X location of the calling object.
   public int getLocationX( ) {
   
      return locationX;
       
   } // end getLocationX function.
    
} // end StaticObject class.