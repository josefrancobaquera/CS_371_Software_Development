/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: Board.java
   Purpose of Class: In essence, this class will represent the game's
   "board." That is, anything that is "on top" of the board (e.g. rocks, game
   characters, etc.) will be handled with this class. 

**/ 

// Import the necessary classes needed for this file. 
import java.awt.*;
import javax.swing.JPanel;

// Board Class. Anything that exists "on top" of 
// our game's board will exist within this class.  
public class Board extends JPanel{
  
   // Each Board class will have a set of attributes related to the board. 
   
   // These attributes relate to the images displayed on the screen.
   private Image openingGifImage; 
   private Image titleScreenImage; 
   private Image wakeUpImage;
   private Image mapOne;
   private Image mapTwo;
   private Image mapThree;
   private Image characterChoiceOne;
   private Image characterChoiceTwo;
   private Image lightning;
   private Image closingGifImage;
   private Image closingPrompt;
   
   // Create 4 static objects that will be picked up by the game player.
   // Use an array for better usage.
   private StaticObject [ ] staticObjects = new StaticObject [ 4 ];
   
   // Create 2 static objects: One will represent a poisonous fruit 
   // while the other fruit will be the "antidote" to the poison fruit.
   private StaticObject poisonousFruit; 
   private StaticObject antidoteFruit; 
   
   // Create an array of deadly dynamic enemies. We decided to have a total 
   // of 17 dynamic objects in order for level 2 to be challenging, but also 
   // not too hard.
   private DynamicObject [ ] movingEnemies = new DynamicObject [ 17 ];
   
   // Create an array of deadly dynamic tracking enemies. We decided to have a total 
   // of 3 of these in order for level 3 to be challenging, but also 
   // not too hard.
   private DynamicObject [ ] trackingEnemies = new DynamicObject [ 3 ];
      
   // Create an array of boolean values that will keep track of the amount of static 
   // objects that the user collected.
   private boolean [ ] collectedStaticObjects = {false, false, false, false};
   
   // Create an array that will check whether or not we have to play 
   // a sound when one of the four rocks has been picked up.
   private boolean [ ] playSound = {true, true, true, true };
   
   // Create an array that will keep track of when to play the 
   // pickup food sound. This sound is the "slurp" sound.
   private boolean [ ] fruitPickUpSound = {true, true};
   
   // Create a variable that will keep track of the player's current level.
   private int currentGameLevel;
   
   // Declare a variable that will check whether or not the title screen has been displayed.
   private boolean titleScreenBoolean;
      
   // Font used to display messages.
   private Font messageFont; 
   
   // All Board objects will have ONE Player object.
   private Player userPlayer;
   
   // Create a static object that will represent the flare gun that must be
   // picked by the user on level 3.
   private StaticObject flareGun;
   
   // Create a variable that will keep track if the user ate the poisonous fruit.
   // When this variable is set to true, all controls will be switched up. 
   private boolean crazyMode;
   
   // Keep track of the number of items picked up by the game character
   private int levelOneObjectsNumber;
   
   // Create a new GameSound object that will play sounds whenever the user picks up 
   // a static object.
   GameSound pickUpSound;
   
   // Polygon array that will keep track of the water zones of level 2.
   private Polygon [] level2WaterZones;

   // Board constructor. 
   public Board( ) {
      
      // Inizialize the Player object.
      userPlayer = new Player ( );
      
      // Inizalize crazyMode to false.
      crazyMode = false;
      
      // Inizalize the pickUpSound.
      pickUpSound = new GameSound ( );
      
      // Assume that the user will ALWAYS start with the 
      // title screen as the first "screen."
      titleScreenBoolean = true;
      
      // Assume that a player starts with 0 collected items.
      levelOneObjectsNumber = 0;
      
      // Game's images. Open the images. Assume that the images will always 
      // exist and will not be corrupted/will always be found.
      openingGifImage = Toolkit.getDefaultToolkit().getImage("img/AirplaneMove.gif"); 
      titleScreenImage = Toolkit.getDefaultToolkit().getImage("img/TitleScreen.png");
      wakeUpImage = Toolkit.getDefaultToolkit().getImage("img/WakeUp.png");
      mapOne = Toolkit.getDefaultToolkit().getImage("img/level1.png"); 
      mapTwo = Toolkit.getDefaultToolkit().getImage("img/level2.png");
      mapThree = Toolkit.getDefaultToolkit().getImage("img/level3.png");
      characterChoiceOne = Toolkit.getDefaultToolkit().getImage("img/RunningGuyBig.gif"); 
      characterChoiceTwo = Toolkit.getDefaultToolkit().getImage("img/RunningGirlBig.gif"); 
      lightning = Toolkit.getDefaultToolkit().getImage("img/Lightning.gif");
      closingGifImage = Toolkit.getDefaultToolkit().getImage("img/FinalCutscene.gif");
      closingPrompt = Toolkit.getDefaultToolkit().getImage("img/EndingPrompt.gif");
      
      // Message fonts. Use Monospaced Bold as default. 
      messageFont = new Font("Monospaced Bold", Font.BOLD, 12 ); 
      
      // Each player will start on level "-3" but will only get to 
      // "control" levels 1 to 3.
      currentGameLevel = -3;
      
      // Initialize the four objects that will need to be picked up by the user.
      // All the objects will be type rock. Make them appear at "random" locations.
      for (int i = 0; i < 4; i++) {
          staticObjects[ i ] = new StaticObject ( "rock", (int) ( ( Math.random()*( ( 940 - 50 ) + 1 ) ) + 50 ), 
        	         (int) ( ( Math.random()*( ( 600 - 40 ) + 1 ) ) + 40 ) ); 
      }
      
      // Initialize the food that the user will be able to eat.
      poisonousFruit = new StaticObject( "poisonousFruit", 900, 80 );
      antidoteFruit = new StaticObject( "antidoteFruit", 80, 585 );
      
      // Initialize the flare gun that will be picked up by the user 
      // on level 3.
      flareGun = new StaticObject( "flareGun", 100, 450 );
      
      // Initialize the dynamic object(s) that the user must avoid during level 2.
      // Note that these will move "randomly" during level 2 but will be initialized 
      // at a fixed location.
      for ( int i = 0; i < 17; i++) {
    	  movingEnemies[i] = new DynamicObject( "spider", (int) ( ( Math.random()*( ( 700 - 10 ) + 1 ) ) + 10 ), 
    			  (int) ( ( Math.random()*( ( 700 - 10 ) + 1 ) ) + 10 ) );
      }
      
      // Initialize level 2 water zones.
      Polygon p1 = new Polygon(new int[] {0,590,610,415,390,150,135,0}, new int[] {0,0,130,145,290,305,560,560}, 8);
      Polygon p2 = new Polygon(new int[] {1000,185,185,370,385,630,640,885,900,1000}, new int[] {700,700,630,615,460,440,270,260,315,315}, 10);
      level2WaterZones = new Polygon[] {p1, p2};
      
      // Initialize the dynamic object(s) that the user must avoid during level 3.
      // Note that these will track the Player object during level 3 and will be initialized 
      // random locations on the left side of the board.
      for ( int i = 0; i < 3; i++) {
    	  trackingEnemies[i] = new DynamicObject( "tracker", 100, (int) ( ( Math.random()*( ( 500 - 100 ) + 1 ) ) + 100 ) );
      }

   } // end constructor.
   
   // paint function. This is the "main" function that draws one entire 
   // frame of the game every 30 seconds. NOTE: Java draws images 
   // "on top" of each other.
   public void paint(Graphics g) {
   
      // Level -3 corresponds to the title screen of the game. 
      if( currentGameLevel == -3  ){
    	   paintTitleScreen(g);
         return;
      } // end else if.
      
      // Level -2 corresponds to the "airplane gif" screen.  
      else if ( currentGameLevel == -2 ) {
    	   paintOpeningCutscene(g);
    	   return;
      } // end if.
      
      // Level -1 corresponds to the "enter your name" screen. 
      else if( currentGameLevel == -1 ){
         paintNameEntry(g);
         return;   
      } // end else if.
  
      // Level 0 corresponds to the "choose your character" screen.
      else if( currentGameLevel == 0 ){
         paintCharacterSelect(g);
         return;
      } // end else if.
      
      // Level 1 corresponds to the actual level 1 gameplay where the user can move the 
      // character around the board to complete the objective. 
      else if( currentGameLevel == 1 ) {
         paintLevel1(g);
         return;
      } // end else if.
      
      // Level 2 corresponds to the actual level 2 gameplay where the user can move the 
      // character around the board to complete the objective.
      else if ( currentGameLevel == 2 ) {
         paintLevel2(g);
         return;
      } // end else if.
      
      // Level 3 corresponds to the last gameplay level where the user can move the 
      // character around the board to complete the objective.
      else if ( currentGameLevel == 3 ) {
         paintLevel3(g);
         return;
      } // end else if.
      
      // Level 4 corresponds to the ending cutscene that will inform the user
      // the he or she has sucessfully left the island. 
      else if ( currentGameLevel == 4 ) {
    	  paintExitingCutscene(g);
    	  return;
      } // end else if.
      
      // Generic return.
      return;
       
   } // end paint.
   
   // This function will "reset" all the enemies around the center of the map. Only this 
   // class should use this function, so make it private.
   private static void resetEnemies ( DynamicObject [ ] tempMovingObjects ) {
   
      for ( int i = 0; i < tempMovingObjects.length; i++ ) {
      
         tempMovingObjects[i].resetLocation( );
            
      } // end for. 
   
   } // end resetEnemies function.
   
   // This function will "reset" all the tracker dynamic objects. Only this 
   // class should use this function, so make it private.
   private static void resetTrackers ( DynamicObject [ ] tempMovingObjects ) {
   
      for ( int i = 0; i < tempMovingObjects.length; i++ ) {
      
         tempMovingObjects[i].resetTracker( );
            
      } // end for. 
   
   } // end resetTrackers function.
   
   // If the user pressed the up keyboard key, update the position 
   // of the Player object. 
   public void moveUp ( ) {
   
      // "Switch" the controls if we are in crazy mode,
      // else move the character normally.
      if( crazyMode )
         userPlayer.downMovement( );
         
      else  
         userPlayer.upMovement( ); 
         
      return;
  
   } // end moveUp. 
  
   // If the user pressed the down keyboard key, update the position of the 
   // Player object.
   public void moveDown ( ) {
   
      // "Switch" the controls if we are in crazy mode,
      // else move the character normally.
      if( crazyMode ) 
         userPlayer.leftMovement( );
         
      else
         userPlayer.downMovement( );
         
      return;
  
   } // end moveDown.
  
   // If the user pressed the left keyboard key, update the position of the 
   // Player object.
   public void moveLeft ( ) {
   
      // "Switch" the controls if we are in crazy mode,
      // else move the character normally.
      if( crazyMode )
         userPlayer.rightMovement( );
         
      else 
         userPlayer.leftMovement( ); 
         
      return;
        
   } // end moveLeft.
   
   // If the user pressed the right keyboard key, update the position of the 
   // Player object.
   public void moveRight ( ) {
   
      // "Switch" the controls if we are in crazy mode,
      // else move the character normally.
      if ( crazyMode )
         userPlayer.upMovement( );
         
      else
         userPlayer.rightMovement( ); 
         
      return;
  
   } // end moveRight.
   
   // This function is responsible for updating the name of the Player object. 
   public void setPlayerName(String newName) {
   
	   userPlayer.setName(newName);
      
	   return;
      
   } // end setPlayerName method.
   
   // This function is responsible for getting the name of the Player object. 
   public String getPlayerName() {
   
	   return userPlayer.getName();
      
   } // end getPlayerName method.
   
   // This function is responsible for returning the titleScreenBoolean variable.
   public boolean getTitleScreenBoolean ( ) {
   
      return titleScreenBoolean;
   
   } // end getTitleScreenBoolean function.
   
   // This function is responsible for setting the titleScreenBoolean variable.
   public void setTitleScreenBoolean( boolean tempBoolean ) {
   
      titleScreenBoolean = tempBoolean;
      return;
      
   } // end setTitleScreenBoolean function.
   
   // This function is responsible for incrementing the currentGameLevel variable.
   public void incrementCurrentGameLevel ( ) {
   
      currentGameLevel++;
      return;
   
   } // end incrementCurrentGameLevel function.
   
   // This function is responsible for selecting the character image for the Player object
   // by using user input. 
   public void selectCharacter ( int characterChoice ) {
	   
	   userPlayer.setGameCharacter( characterChoice );
	   return;
	   
   } // end selectCharacter function.
   
   // This function is responsible for returning the current level of the game. 
   public int getCurrentGameLevel( ) {
   
      return currentGameLevel;
   
   } // end getGameLevel function.
   
   // Displays the title screen of the game.
   private void paintTitleScreen (Graphics g) {
	   
      // Display the title screen. 
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(titleScreenImage,-120,-5,Color.BLACK,null);
     
      // Return to the caller.
      return;
	   
   } // end paintTitleScreen function.
   
   // Displays the opening cutscene.
   private void paintOpeningCutscene (Graphics g) {
	   
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      // Display the lightNing gifs.
      g.drawImage(lightning,0,0,Color.BLACK,null);
      g.drawImage(lightning,320,0,Color.BLACK,null);
      // Display the actual airplane gif on top.
      g.drawImage(openingGifImage,160,20,Color.BLACK,null);
      
      // Return to the caller. 
      return;
	   
   } // end paintOpeningCutscene function.
   
   // Dispays the prompt for users to enter their names.
   private void paintNameEntry (Graphics g) {
	   
      // Display the crash landing screen. 
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(wakeUpImage,0,0,Color.BLACK,null);
      g.setFont( messageFont );
        
      // Display the character name prompt.
      g.setColor(Color.WHITE);
      g.drawString("Enter your name. Press ENTER to advance.", 700, 170);

      // Display the character name as it is being entered.
      g.drawString(userPlayer.getName(), 790, 190);
        
      // Return to the caller. 
      return;
	   
   } // end paintNameEntry function.
   
   // Displays the character being choosen.
   private void paintCharacterSelect (Graphics g) {
	   
      // Display the crash landing screen. 
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(wakeUpImage,0,0,Color.BLACK,null);
      g.setFont( messageFont );

      // Display the available characters.
      g.setColor(Color.WHITE);
      g.drawString( "Now choose your character. Press 1",700, 170 );
      g.drawString( "for character 1 or press 2 for",700, 190 );  
      g.drawString( "character 2.",700, 210 );
      g.drawString("Press the enter key to continue..", 700, 230);  
      g.drawString("1", 768, 260);
      g.drawString("2", 858, 260);
      g.drawImage(characterChoiceOne, 720, 285,null ,null);
      g.drawImage(characterChoiceTwo, 820, 285,null ,null);
        
      // Draw an enclosure around the selected character image.
      if ( userPlayer.getGameCharacterSelection( ) == 1 ) {
         g.drawRoundRect(730, 280, 80, 120, 20, 20);
      } //end if.
      else if ( userPlayer.getGameCharacterSelection( ) == 2 ) {
         g.drawRoundRect(830, 280, 80, 120, 20, 20);
      } // end else if.
        
      // Return to the caller. 
      return;
	   
   } // end paintCharacterSelect function.
   
   // Paints everything related to level 1.
   private void paintLevel1 (Graphics g) {
	   
	   // Display the level 1 map, including the prompt on what the user needs to do, 
      // the static objects that have to be collected, and the game character.
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(mapOne,0,0,Color.BLACK,null);
        
      // Check if the objects have been picked up. If they have, do not display them.
      for ( int i = 0; i < staticObjects.length; i++ ) {
        
         if ( collectedStaticObjects[ i ] != true )
            g.drawImage(staticObjects[i].getStaticObjectImage( ), staticObjects[i].getLocationX( ), staticObjects[i].getLocationY( ), null, null );

      } // end for loop.
        
      // Display the character at the correct position within the map.
      g.drawImage(userPlayer.getGameCharacter( ), userPlayer.getLocationX( ), userPlayer.getLocationY( ),null ,null);
        
      // Check if the player is an top of the static object. If it is, the user has sucessfully picked it up.
      for ( int i = 0; i < staticObjects.length; i++ ) {
        
         if ( (userPlayer.getLocationX( ) <= staticObjects[ i ].getLocationX( ) + 15) && (userPlayer.getLocationX( ) >= staticObjects[ i ].getLocationX( ) - 15)
            && (userPlayer.getLocationY( ) <= staticObjects[ i ].getLocationY( ) + 15) && ( userPlayer.getLocationY( ) >= staticObjects[ i ].getLocationY( ) - 15) ) {
              
            // Play the pick up sound only once. This if conditional check will prevent 
            // the sound playing over and over again after the static object is picked.
            if( playSound[ i ] == true ) {
               pickUpSound.playPickUpSound( );
               playSound[ i ] = false;
            } // end if.  
              
            // Pick up the object. 
            collectedStaticObjects[ i ] = true; 
              
         } // end if.
        
      } // end for loop.
                  
      // Display a black square with the objective, the current level, and the user's name.
      g.setColor( Color.BLACK );
      g.fillRect( 0, 620, 1000, 30); 
      g.setColor( Color.WHITE );
      g.setFont( messageFont );
      g.drawString("Player: " + userPlayer.getName() + ". Level 1 Objective: Collect all 4 rocks (by walking on top of them) to make an SOS sign.", 15, 638);
        
      // Count the number of rocks collected by the user. 
      for ( int i = 0; i < collectedStaticObjects.length; i++ ) {
           
         if ( collectedStaticObjects[i] == true )
            levelOneObjectsNumber++;
              
      } // end for. 
        
      // If the user collected all the required objects, increment the level variable, 
      // else reset the counter. 
      if( levelOneObjectsNumber == collectedStaticObjects.length ) {
           
         // Set the current level to 2.
         currentGameLevel = 2;
           
         // Update the character location so that the player starts at a specific location
         // for level 2.
         userPlayer.setXCoordinate( 800 );
         userPlayer.setYCoordinate( 100 );
           
      } // end if.
        
      else 
         levelOneObjectsNumber = 0;
                              
      // Return to the caller. 
      return;
	   
   } // end paintLevel1 function.
   
   // Paints everything related to level 2.
   private void paintLevel2 (Graphics g) {
	   
	   // Display the level 2 map, including the prompt on what the user needs to do, 
      // the static objects that have to be collected, and the game character.
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(mapTwo,0,0,Color.BLACK,null);
       
      // Display the game character.
      g.drawImage(userPlayer.getGameCharacter( ), userPlayer.getLocationX( ), userPlayer.getLocationY( ),null ,null);
        
      // Draw the poisonous fruit if it has not been consumed by the user.
      if ( !crazyMode )
         g.drawImage( poisonousFruit.getStaticObjectImage( ), poisonousFruit.getLocationX( ), poisonousFruit.getLocationY( ), null ,null);
        
      // Draw the antidote fruit and the enemies that the character must avoid 
      // in random locations. 
      if ( crazyMode ) {
      	   
         g.drawImage( antidoteFruit.getStaticObjectImage( ), antidoteFruit.getLocationX( ), antidoteFruit.getLocationY( ), null ,null);
          
         for ( int i = 0; i < movingEnemies.length; i++ ) {
             
            // Move the enemies at random locations and draw them on the board. 
            movingEnemies[i].randomMovement( ); 
            g.drawImage(movingEnemies[i].getDynamicObjectImage( ), movingEnemies[i].getLocationX( ), movingEnemies[i].getLocationY( ), null, null );

         } // end for loop.
           
      } // end if.         
 
      // Check if the user came in contact with the moving enemies. If true, we "reset" the level. 
      for ( int i = 0; i < movingEnemies.length; i++ ) {
        
         if ( ( movingEnemies[i].getLocationX() <= userPlayer.getLocationX() + 15 ) && ( movingEnemies[i].getLocationX() >= userPlayer.getLocationX() - 15 )
          && ( movingEnemies[i].getLocationY() <= userPlayer.getLocationY() + 15 ) && ( movingEnemies[i].getLocationY() >= userPlayer.getLocationY() - 15 ) ) {
          	 
            // "Reset" the level by turning off crazy mode and placing the game character at the strarting position.
            crazyMode = false;
            userPlayer.setXCoordinate( 800 );
            userPlayer.setYCoordinate( 100 );
              
            // "Reset" the position of the bees. 
            resetEnemies( movingEnemies );   
            fruitPickUpSound[0] = true;
               
         } // end if.
           
      } // end for loop. 
       
      // Check if the user came in contact with the water. If true, we "reset" the level. 
      for ( Polygon p : level2WaterZones ) {
    	   
         if ( p.contains( userPlayer.getLocationX() + 15 , userPlayer.getLocationY() + 30) ) {
          	 
       	   // Play the splash sound
        	   pickUpSound.playSplashSound();
        	  
            // "Reset" the level by turning off crazy mode and placing the game character at the strarting position.
            crazyMode = false;
            userPlayer.setXCoordinate( 800 );
            userPlayer.setYCoordinate( 100 );
              
            // "Reset" the position of the bees. 
            resetEnemies( movingEnemies );   
            fruitPickUpSound[0] = true;
               
         } // end if.
           
      } // end for loop. 
                  
      // If the user eats the poisonous fruit, change crazyMode to true and play the slurping sound.
      if ( ( userPlayer.getLocationX( ) <= poisonousFruit.getLocationX( ) + 15 ) && ( userPlayer.getLocationX( ) >= poisonousFruit.getLocationX( ) - 15 )
        && ( userPlayer.getLocationY( ) <= poisonousFruit.getLocationY( ) + 15 ) && ( userPlayer.getLocationY( ) >= poisonousFruit.getLocationY( ) - 15 ) ) {
           
         crazyMode = true;
         if( fruitPickUpSound[0] == true ) {
           
            pickUpSound.playAteFruitSound( );
            fruitPickUpSound[0] = false;

         } // end inner if.          
           
      } // end if.
        
      // Display a black square with the objective, the current level, and the user's name. 
      g.setColor( Color.BLACK );
      g.fillRect( 0, 620, 1000, 30); 
      g.setColor( Color.WHITE );
      g.setFont( messageFont );
        
      // Display a different prompt depending on whether or not the character ate the poisonous fruit.
      if ( !crazyMode ) 
         g.drawString("Player: " + userPlayer.getName() + ". Level 2 Objective: You are HUNGRY! Eat the fruit right next to you!", 15, 638);
      else 
         g.drawString("Oh no! You ate posionous fruit, which messed up your controls! Find the antidote fruit! Beware of the enemies and avoid the water!", 15, 638);
        
      // If the game character picked up the antidote fruit, increase the level and reset the crazyMode variable. The player
      // must have gone through crazy mode in order to advance into the next level. 
      if ( ( userPlayer.getLocationX( ) <= antidoteFruit.getLocationX( ) + 15 ) && ( userPlayer.getLocationX( ) >= antidoteFruit.getLocationX( ) - 15 )
        && ( userPlayer.getLocationY( ) <= antidoteFruit.getLocationY( ) + 15) && ( userPlayer.getLocationY( ) >= antidoteFruit.getLocationY( ) - 15) && crazyMode ) {
        
         currentGameLevel = 3;
         crazyMode = false;
          
         // Update the character location so that the player starts at a specific location
         // for level 3.
         userPlayer.setXCoordinate( 800 );
         userPlayer.setYCoordinate( 100 );
           
         // Play the sound if the user ate the antidote fruit.
         if( fruitPickUpSound[1] == true ) {
           
            pickUpSound.playAteFruitSound( );
            fruitPickUpSound[1] = false;

         } // end inner if.    
        
      } // end if.
        
      // Return to the caller. 
      return;
	   
   } // end paintLevel3 function.
   
   // Paints everything related to level 3.
   private void paintLevel3 (Graphics g) {
	   
	   // Display the level 3 map, including the prompt on what the user needs to do.
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(mapThree,0,0,Color.BLACK,null);
          
      for ( int i = 0; i < trackingEnemies.length; i++ ) {
             
         // Use the tracker algorithm to move the enemies and draw them on the board. 
         trackingEnemies[i].trackingMovement( userPlayer.getLocationX() , userPlayer.getLocationY() ); 
         g.drawImage(trackingEnemies[i].getDynamicObjectImage( ), trackingEnemies[i].getLocationX( ), trackingEnemies[i].getLocationY( ), null, null );

      } // end for loop.      
 
      // Check if the user came in contact with the moving enemies. If true, we "reset" the level. 
      for ( int i = 0; i < trackingEnemies.length; i++ ) {
        
         if ( ( trackingEnemies[i].getLocationX() <= userPlayer.getLocationX() + 15 ) && ( trackingEnemies[i].getLocationX() >= userPlayer.getLocationX() - 15 )
           && ( trackingEnemies[i].getLocationY() <= userPlayer.getLocationY() + 15 ) && ( trackingEnemies[i].getLocationY() >= userPlayer.getLocationY() - 15 ) ) {
          	 
            // "Reset" the level by placing the game character at the starting position.
            userPlayer.setXCoordinate( 800 );
            userPlayer.setYCoordinate( 100 );
              
            // "Reset" the position of the trackers. 
            resetTrackers( trackingEnemies );
               
         } // end if.
           
      } // end for loop. 
       
      // Display the character.
      g.drawImage(userPlayer.getGameCharacter( ), userPlayer.getLocationX( ), userPlayer.getLocationY( ),null ,null);

      // Display a black square with the objective, the current level, and the user's name. 
      g.setColor( Color.BLACK );
      g.fillRect( 0, 620, 1000, 30); 
      g.setColor( Color.WHITE );
      g.setFont( messageFont );
      g.drawString("Player: " + userPlayer.getName() + ". Level 3 Objective: A plane is passing by but your SOS sign is too small! Find the flaregun! Beware of enemies!", 15, 638);
       
      // Display the flare gun that must be collected by the user to get off the island.
      g.drawImage(flareGun.getStaticObjectImage( ), flareGun.getLocationX( ), flareGun.getLocationY( ),null ,null);

      // If the game character picked up the flare gun, increase the level. 
      if ( ( userPlayer.getLocationX( ) <= flareGun.getLocationX( ) + 15 ) && ( userPlayer.getLocationX( ) >= flareGun.getLocationX( ) - 15 )
        && ( userPlayer.getLocationY( ) <= flareGun.getLocationY( ) + 15 ) && ( userPlayer.getLocationY( ) >= flareGun.getLocationY( ) - 15 ) ) {
        
         currentGameLevel = 4;

      } // end if.
       
      // Return to the caller. 
      return;
	   
   } // end paintLevel3 function.
   
   // Paints the closing cutscene once the user completes the game.   
   private void paintExitingCutscene (Graphics g) {
	   
      // Display the closing cutscene with the user prompt/message on top.
      g.setColor(Color.BLACK);
      g.fillRect(0,0,1000,680);
      g.drawImage(closingGifImage,0,0,null,null);
      g.drawImage(closingPrompt,340,230,null,null);
      
      // Return to the caller. 
      return;
	   
   } // end paintExistingCutscene function.
   
} // end class Board.
