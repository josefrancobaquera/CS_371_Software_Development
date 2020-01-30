/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: SOSGame.java
   Purpose of Class: In essence, this is the class that will "listen"
   for any keys pressed by the user. This class will also have 
   the main "function", implying that the game must be run 
   using this class. Lastly, this particular class will be responsible
   for displaying the corresponding opening gifs and playing the 
   background loop music.

**/ 

// Import the necessary classes needed for this file. 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// SOSGame class. This class will essentially contain the "entire" game. 
public class SOSGame extends JApplet implements KeyListener { 

   // Declare a new variable that will serve as the game's "generic timer"
   // that will allow us to display gifs and/or other images for a set
   // period of time.  
   private long genericTimer;

   // Instanziate the required objects for this class. 
   private Board newBoard; 
   private GameSound sounds;

   // This timer is used to do request new frames to be drawn.
   private javax.swing.Timer frameTimer;
   
   // Create a new window frame.
   private JFrame frame; 
    
   // This constructor creates the entire game essentially.  
   public SOSGame() {
    
      // Every SOSGame object will also have a new Board object.
      newBoard = new Board();
      
      // Inizalize the GameSound object and play the background music
      // in a loop for the entire game. 
      sounds = new GameSound( );
      sounds.backgroundMusic( );
      
      // Inizialize timers to -1 since this is considered the "reset" 
      // value.
      genericTimer = -1;
      
      // Request the focus of the applet.
      newBoard.requestFocus();

      // Create and set up the window frame.
      frame = new JFrame(); 
    
      // Sets the size of the applet. For this particular project,
      // the game will be displayed on a 1000x680 java applet.
      frame.setSize(1000, 680);

      // Add the board to the center of the frame.
      frame.add(newBoard, BorderLayout.CENTER);
      
      // Add the keylistener to the board class. 
      newBoard.addKeyListener(this);  

      // Make frame visible, disable resizing.
      frame.setVisible(true);
      frame.setResizable(false);
      
      // Manually call the first frameStep to initialize the game.
      stepFrame( );
      
      // Create a timer that calls stepFrame every 30 milliseconds.
      // NOTE: This is an anonymous class!
      frameTimer = new javax.swing.Timer(30, new ActionListener( ) {
         
         public void actionPerformed(ActionEvent e) {
            
            stepFrame( );
         
         } // end actionPerformed function.
         
      });

      // Start the timer and request the applet's focus. 
      frameTimer.start();
      newBoard.requestFocus();
      
   } // end SOSGame constructor. 

   // stepFrame function. This function will step the screen foward one frame at a time. 
   // Furthermore, the function will be responsible for displaying the opening gif for a total of
   // 5 seconds.
   public void stepFrame( ) {
       
      // If the game level is equal to -2 (i.e. this level corresponds to 
      // the "level" after the title screen), display and play the 
      // airplane gif/thunderstorm sound for a total of 5 seconds.  
      if ( newBoard.getCurrentGameLevel( ) == -2 ) {
    
         // If the generic timer is equal to -1 (i.e. the "starting value", 
         // start the counter and play the thunderstorm sound.
         if ( genericTimer == -1 ) {
        
            sounds.thunderStorm( );        
            genericTimer = System.currentTimeMillis();
        
         } // end if. 

         long currTime = System.currentTimeMillis();
         
         // If 5 seconds have passed by, increment the current
         // game level and reset the timer.
         if ( currTime - genericTimer >= 5000 ) {
            
            // Update the game's logic and reset the timer.
            newBoard.incrementCurrentGameLevel( );
            genericTimer = -1;
            
         } // end if.
         
         // Repaint the board and return to the caller.
         newBoard.repaint( );
         return;

      } // end if.

      else {
      
         // "Generic" repaint and return
         newBoard.repaint( );
         return;
      
      } // end else
   
   } // end stepFrame function. 

   // keyPressed function. This function will handle any keys pressed by the user. 
   public void keyPressed(KeyEvent e) {
	  
	   // Call setUpName function to handle the KeyEvent when the 'enter name' screen
      // is being displayed. NOTE: This corresponds to "level" -1.
	   if ( newBoard.getCurrentGameLevel( ) == -1 ) {
		
         setUpName( e );
         return;
         
      } // end if.
      
      // Call setUpCharacter function to handle the KeyEvent when the 
      // 'choose your character' screen is being displayed. NOTE: This corresponds 
      // to "level" -1.
	   if ( newBoard.getCurrentGameLevel( ) == 0 ) {
		
         setUpCharacter( e );
		   return;
         
	   } // end if.
	   
      // If none of the two previous cases execute, then the user is on 
      // level 1 or above. Use a switch statement that will check what key was entered.
      // These key presses correlate to manipulating the game enviornment.
      switch( e.getKeyCode( ) ) {
      
         // The onle "ENTER" key press that matters if the first one.
         // If the user is on the title screen and presses enter, 
         // increment the game "level", else ignore.
         case KeyEvent.VK_ENTER:
         
            if ( newBoard.getTitleScreenBoolean( ) ) {
            
               newBoard.setTitleScreenBoolean( false );
               newBoard.incrementCurrentGameLevel( );
            
            } // end if.
            
            newBoard.repaint( );
            break; 
            
         // If the user pressed the left keyboard key, move the
         // character west 5 pixels.
         case KeyEvent.VK_LEFT:
            newBoard.moveLeft(); 
            newBoard.repaint( );
            break;
            
         // If the user pressed the escape keyboard key, quit the 
         // game.
         case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;
            
         // If the user pressed the right keyboard key, move the
         // character east 5 pixels.
         case KeyEvent.VK_RIGHT:
            newBoard.moveRight(); 
            newBoard.repaint( );
            break;
            
         // If the user pressed the up keyboard key, move the
         // character north 5 pixels.
         case KeyEvent.VK_UP:
            newBoard.moveUp(); 
            newBoard.repaint( );
            break;
            
         // If the user pressed the down keyboard key, move the
         // character south 5 pixels.
         case KeyEvent.VK_DOWN:
            newBoard.moveDown(); 
            newBoard.repaint( );
            break;
            
         // If the user pressed any other key, ignore it.
         default:
            break;
       
      } // end switch statement.
    
      // Return to the caller.
      return;
    
   } // end keyPressed function.
   
   // setUpName function. This function will handle any keys pressed by the user
   // during the "enter your name" screen.
   public void setUpName(KeyEvent e) {
	   		
	   // Retrieve current name of Player object. This will allow us to 
      // display the string of characters as the user is typing!
	   String currName = newBoard.getPlayerName();
	   	  
	   // Use a switch statement that will check what key was entered.
	   switch( e.getKeyCode() ) {
         
         // If the user pressed the escape keyboard key, quit the 
         // game.
         case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;
	      
	      // Pressing the "ENTER" key will make the game advance into 
         // the next "level." 
	      case KeyEvent.VK_ENTER:
            newBoard.incrementCurrentGameLevel( );
			   break;
	            
			// The "BACK_SPACE" key removes the latest addition to the Player object's name.
	      case KeyEvent.VK_BACK_SPACE:
	         if (currName.length() > 0) {
               
	            currName = currName.substring(0, currName.length() - 1);
	        		newBoard.repaint();
                  
	        	} // end if.
	         break;
	        	 
	      // Do not display the "SHIFT" key event
	      case KeyEvent.VK_SHIFT:
	        	break;
               
         // Do not display the "CAPSLOCK" key event
	      case KeyEvent.VK_CAPS_LOCK:
	        	break;

         // Do not display the LEFT character key.
         case KeyEvent.VK_LEFT:
            break;

         // Do not display the RIGHT character key.
         case KeyEvent.VK_RIGHT:
            break;
            
         // Do not display the UP character key.
         case KeyEvent.VK_UP:
            break;
            
         // Do not display the DOWN character key.
         case KeyEvent.VK_DOWN:
            break;
	         
         // Append any other keyboard selection to the Player object's name as long as it is less than 10 characters.
	      default:
	         
            if (currName.length() < 10) {
	            currName += e.getKeyChar();  
	        	} // end if.
	        	break;
            
	   } // end switch.
	      
	   // Update the Player object's name.
	   newBoard.setPlayerName( currName );
	   return;
	      
   } // end setupName function.
   
   // setUpCharacter function. This function will "set up" which 
   // game character was choosen by the user. 
   public void setUpCharacter(KeyEvent e) {
	   
	   // Use a switch statement that will check what key was entered.
      // NOTE: We only care about numbers "1" and "2". All other characters will
      // be ignored.
	   switch ( e.getKeyCode() ) {
	   
	      // "1" selects the first character
	      case KeyEvent.VK_1:
		      newBoard.selectCharacter( 1 );
		      newBoard.repaint();
		      break;

	      case KeyEvent.VK_NUMPAD1:
		      newBoard.selectCharacter( 1 );
		      newBoard.repaint();
		      break;
		   
	      // "2" selects the second character	   
	      case KeyEvent.VK_2:
		      newBoard.selectCharacter( 2 );
		      newBoard.repaint();
		      break;

	      case KeyEvent.VK_NUMPAD2:
		      newBoard.selectCharacter( 2 );
		      newBoard.repaint();
		      break;
		   
	      // The "ENTER" key takes users to level 1. NOTE: Level 1
         // is the "real" level that users will experience. That is, 
         // all levels <= 0 correspond to game set up.
	      case KeyEvent.VK_ENTER:
            newBoard.incrementCurrentGameLevel( );        
            newBoard.repaint();
            break;
           
          // If the user pressed the escape keyboard key, quit the 
          // game.
          case KeyEvent.VK_ESCAPE:
             System.exit(0);
             break;
           
      } // end switch statement.
	   
      // Return to the caller.
	   return;
	   
   } // end setUpCharacter function.poisonous
   
   // "Implement" the abstract functions. 
   public void keyReleased( KeyEvent e ){}
   public void keyTyped( KeyEvent e ){}
   
   // main function. The entire game will be created by calling this function. 
   public static void main(String [] args) {
     
      // Create a new game. 
      SOSGame newGame = new SOSGame();
       
   } // end main. 
     
} // end class. 
