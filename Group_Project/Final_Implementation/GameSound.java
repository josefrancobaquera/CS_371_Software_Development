/**

   New Mexico State University: CS 371 - Software Development
   Team Number: 5
   Team Members: Jose Franco Baquera, Andrew Phillips, Diondra Silva
   Due Date: November 30, 2018
   
   Project Purpose: Create an interactive, 2-D game that provides an entertaining
   and stress-free environment to game players. 
   
   File Name: GameSound.java
   Purpose of Class: In essence, this class will play sounds 
   when certian functions are called. 

**/ 

// Import the necessary classes needed for this file.  
import java.net.URL;
import javax.sound.sampled.*;

// GameSounds class.  
public class GameSound{
    
   // Declare several sounds used throughout the game.  
   private Clip thunderStorm;
   private Clip backgroundMusic;
   private Clip pickUpSound;
   private Clip fruitPickupSound;
   private Clip splashSound;
   
   // GameSound constructor. This constructor is responsible for
   // inizializing the sound clips.     
   public GameSound(){
   
      URL url;
      AudioInputStream audioIn;
      
      try{
            
         // Opening the thunderstorm audio file.
         url = this.getClass().getClassLoader().getResource("sounds/ThunderStorm.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         thunderStorm = AudioSystem.getClip();
         thunderStorm.open(audioIn);
         
         // Opening the background music file. 
         url = this.getClass().getClassLoader().getResource("sounds/BackgroundMusic.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         backgroundMusic = AudioSystem.getClip();
         backgroundMusic.open(audioIn);
         
         // Opening the pick up sound music file. 
         url = this.getClass().getClassLoader().getResource("sounds/PickUpSound.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         pickUpSound = AudioSystem.getClip();
         pickUpSound.open(audioIn);
         
         // Opening the "eating the fruit" music file. 
         url = this.getClass().getClassLoader().getResource("sounds/Slurp.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         fruitPickupSound = AudioSystem.getClip();
         fruitPickupSound.open(audioIn);
         
         // Opening the "falling in the water" music file. 
         url = this.getClass().getClassLoader().getResource("sounds/Splash.wav");
         audioIn = AudioSystem.getAudioInputStream(url);
         splashSound = AudioSystem.getClip();
         splashSound.open(audioIn);
         
      } // end try. 
      
      catch( Exception e ){ }
    
   } // end constructor. 
   
   // Invoking this function will play the thunderstorm sound.
   public void thunderStorm( ){
    
      thunderStorm.stop();
      thunderStorm.setFramePosition(0);
      thunderStorm.start();
        
   } // end thunderStorm function. 
   
   // Invoking this function will play game's background music.
   // NOTE: This is the only music file that will loop forever until
   // the applet is closed.
   public void backgroundMusic( ){
    
      // Loop for the entire game.
      backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        
   } // end backgroundMusic function. 
   
   // Invoking this function will play game's pick up sound.
   public void playPickUpSound( ){
    
      pickUpSound.stop();
      pickUpSound.setFramePosition(0);
      pickUpSound.start();
        
   } // end playPickUpSound function. 
   
   // Invoking this function will play game's fruit eating sound.
   public void playAteFruitSound( ){
    
      fruitPickupSound.stop();
      fruitPickupSound.setFramePosition(0);
      fruitPickupSound.start();
        
   } // end playAteFruitSound function. 
   
   // Invoking this function will play game's splash sound.
   public void playSplashSound( ){
    
      splashSound.stop();
      splashSound.setFramePosition(0);
      splashSound.start();
        
   } // end playSplashSound function. 
  
} // end GameSounds class.