/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: Player.java
   Purpose of Class: In essence, this class will represent the game character 
   itself. It is important to note that our game will only have two game characters:
   one female and one male. Futhermore, there will only be ONE player object
   intanziated per game.

**/ 

// Import the necessary classes needed for this file.  
import java.awt.*;

// Player class. The game character object will be instantiated from 
// this class.
public class Player {

   // Declare variables that will help us determine where the character is.
   private int locationX;
   private int locationY;
   
   // Each character has a name.
   private String name;
   
   // Each player will also a corresponding image.
   private Image gameCharacter;
   
   // There are two types of characters. 1 represents the male character
   // and 2 represents the woman character. 
   private int gameCharacterSelection;

   // Player constructor.
   public Player ( ) {
   
      // Initialize the location of the player at the center of the board.
      locationX = 300;
      locationY = 330;
      
      // Initialize the character image to option "1." NOTE: This is the 
      // "generic" inizalization. 
      gameCharacterSelection = 1;
      gameCharacter = Toolkit.getDefaultToolkit().getImage("img/RunningGuy.gif"); 
      
      // Initialize the name of the player to the empty string
      name = "";

   } // end Player constructor. 
   
   // The purpose of this function is to return the name of the 
   // calling Player object. 
   public String getName() {
   
	   return name;
      
   } // end getName function.
   
   // The purpose of this function is to "update" the name of the 
   // calling Player object. 
   public void setName(String newName) {
   
	   name = newName;
	   return;
      
   } // end setName function
   
   // The purpose of this function is to move the character to the left a total of 5 pixels.
   // NOTE: We must check that the game character doesn't move "off scree" first.
   public void leftMovement( ) {

      // Block left movement if at left side.
      if ( locationX <= -10 ) {
            return;
      } // end if.
      
      locationX = locationX - 5;

      return;
       
   } // end leftMovement function.
   
   // The purpose of this function is to move the character up a total of 5 pixels.
   // NOTE: We must check that the game character doesn't move "off scree" first.
   public void upMovement( ) {

      // Block up movement if at top.
      if ( locationY <= -10 ) {
            return;
      } // end if.
      
      locationY = locationY - 5;

      return;
       
   } // end upMovement function.
   
   // The purpose of this function is to move the character down a total of 5 pixels.
   // NOTE: We must check that the game character doesn't move "off scree" first.
   public void downMovement( ) {

      // Block down movement if at bottom.
      if ( locationY >= 595 ) {
            return;
      } // end if.
      
      locationY = locationY + 5;

      return;
       
   } // end downMovement function.
  
   // The purpose of this function is to move the character to the right a total of 5 pixels.
   // NOTE: We must check that the game character doesn't move "off screen" first.
   public void rightMovement( ) {

      // Block right movement if at right side.
      if ( locationX >= 965 ) {
            return;
      } // end if.
      
      locationX = locationX + 5;

      return;
       
   } // end rightMovement function.

   // The purpose of this function is to return the image of the character. 
   public Image getGameCharacter ( ) {
   
      return gameCharacter;

   } // end getGameCharacter function.
   
   // The purpose of this function is to set the image of the character.
   // This function will be called once the user "chooses" which game 
   // character he or she wants. 
   public void setGameCharacter ( int characterChoice ) {
	   
      // Update the numerical character selection
	   gameCharacterSelection = characterChoice;
	   	  
	   // Use the numerical character selection to set the corresponding image
	   if ( gameCharacterSelection == 1 ) {
	      gameCharacter = Toolkit.getDefaultToolkit().getImage("img/RunningGuy.gif"); 
	   } // end if.
	      
      else if ( gameCharacterSelection == 2 ) {
	      gameCharacter = Toolkit.getDefaultToolkit().getImage("img/RunningGirl.gif"); 
	   } // end else if.
	      
      return;

   } // end setGameCharacter function.
   
   // The purpose of this function is to return the X location of the character. 
   public int getLocationX( ) {
   
      return locationX;
       
   } // end getLocationX function.
   
   // The purpose of this function is to return the Y location of the character. 
   public int getLocationY( ) {
   
      return locationY;
       
   } // end getLocationY function.
   
   // The purpose of this function is to return the game character choosen (i.e. 1 or 2).
   public int getGameCharacterSelection( ) {
	   
	   return gameCharacterSelection;
	   
   } // end get GameCharacterSelection.
   
   // The purpose of this function is to set the new X coordinate of the game character.
   public void setXCoordinate ( int tempX ) {
   
      locationX = tempX;
      return;  
      
   } // end setXCoordinate
   
   // The purpose of this function is to set the new Y coordinate of the game character.
   public void setYCoordinate ( int tempY ) {
   
      locationY = tempY;
      return;  
      
   } // end setXCoordinate

} // end Player class.
