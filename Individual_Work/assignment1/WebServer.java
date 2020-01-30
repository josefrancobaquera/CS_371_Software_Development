// Name: Jose Franco Baquera
// Date: August 24, 2018
// Class: CS - 371 Software Development
// File: WebServer.java
// Assignment: Program 1

// (NOTE: I did not changed anything in this file). I only changed
// things in the WebWorker.java file.

// Purpose of this code: 
/**
* A simple web server: it creates a new WebWorker for each new client
* connection, so all the WebServer object does is listen on the port
* for incoming client connection requests.
*
* This class contains the application "main()" (see below). At startup, 
* main() creates an object of this class (WebServer) and invokes its
* start() method. Since servers run continually, the start() method 
* never returns. It uses socket programming to listen for client network
* connection requests. When one happens, it creates a new object of
* the WebWorker class and hands that client connection off to the WebWorker
* object. The WebServer object then just keeps listening for new client
* connections. See the WebWorker source for more information about it.
* 
**/

// Import the important java class. 
import java.net.*;

public class WebServer {

// WebServer class' attributes. 
private ServerSocket socket;
private boolean running;

/**
* Constructor
**/

private WebServer() {
   running = false;
} // end constructor

/**
* Web server starting point. This method does not return until
* the server is finished, so perhaps it should be named "runServer"
* or something like that.
* @param port is the TCP port number to accept connections on
**/

private boolean start( int port ) {
   
   // Create a new Socket and WebWorker objects. 
   Socket workerSocket;
   WebWorker worker;
   
   // Use a try and catch to try to open the socket with the given port number.    
   try {
      socket = new ServerSocket(port);
   } // end try. 
   
   // If the socket could not be opened with the given port number,
   // print out an error message. 
   catch (Exception e) {
      System.err.println("Error binding to port "+port+": "+e);
      return false;
   } // end catch. 
   
   // Use a while loop to check when the socket is no longer being 
   // able to open. 
   while (true) {
      
      // Try and see if the socket is opened. 
      try {
         // Wait and listen for new client connection.
         workerSocket = socket.accept();
      } // end try.
      
      // Catch if the socket is no longer accepting the given port. 
      catch (Exception e) {
         System.err.println("No longer accepting: " + e );
         break;
      } // end catch. 
      
      worker = new WebWorker(workerSocket);
      new Thread(worker).start();
      
   } // end while. 
   
   // Return true. 
   return true;
   
} // end start.

/**
* Does not do anything, since start() never returns.
**/
private boolean stop() {
   return true;
} // end stop.

/**
* Application main: process command line and start web server; default
* port number is 8080 if not given on command line.
**/
public static void main( String args[] ) {
   
   // Make port 8080 the default port number. 
   int port = 8080;
   
   // Check the paramters passed by the user when the program was runned.  
   if (args.length > 1) {
   
      System.err.println("Usage: java Webserver <portNumber>");
      return;
      
   } // end if. 
   
   else if (args.length == 1) {
      
      // Use a try and catch that will check the parameters passed by the user 
      // whenever the program was originally runned. 
      try {
         port = Integer.parseInt(args[0]);
      } // end try.  
      
      catch (Exception e) {
         System.err.println("Argument must be an int ("+e+")");
         return;
      } // end catch. 
      
   } // end else if.
   
   WebServer server = new WebServer();
   
   // If the socket could not opened, print an error message. 
   // NOTE: The .start will start the connection. 
   if (!server.start(port)) {
      System.err.println("Execution failed!");
   } // end if. 
   
} // end main

} // end class

