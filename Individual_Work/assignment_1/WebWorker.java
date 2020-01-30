// Name: Jose Franco Baquera
// Date: August 24, 2018
// Class: CS - 371 Software Development
// File: WebWorker.java
// Assignment: Program 1

// Purpose of this code: 
/**
* Web worker: an object of this class executes in its own new thread
* to receive and respond to a single HTTP request. After the constructor
* the object executes on its "run" method, and leaves when it is done.
*
* One WebWorker object is only responsible for one client connection. 
* This code uses Java threads to parallelize the handling of clients:
* each WebWorker runs in its own thread. This means that you can essentially
* just think about what is happening on one client at a time, ignoring 
* the fact that the entirety of the webserver execution might be handling
* other clients, too. 
*
* This WebWorker class (i.e., an object of this class) is where all the
* client interaction is done. The "run()" method is the beginning -- think
* of it as the "main()" for a client interaction. It does three things in
* a row, invoking three methods in this class: it reads the incoming HTTP
* request; it writes out an HTTP header to begin its response, and then it
* writes out some HTML content for the response content. HTTP requests and
* responses are just lines of text (in a very particular format). 
**/

// Import several classes from Java.
import java.net.Socket;
import java.lang.Runnable;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.TimeZone;

// WebWorker class implementation. 
public class WebWorker implements Runnable {

// Private class attribute. 
private Socket socket;

/**
* Constructor: There must be a valid open socket.
**/

public WebWorker( Socket s ) {
   socket = s;
} // end constructor

/**
* Worker thread starting point. Each worker handles just one HTTP 
* request and then returns, which destroys the thread. This method
* assumes that whoever created the worker created it with a valid
* open socket object.
**/

public void run() {

   // Declare a string named "pathToContent" that will store the 
   // complete path of the html file. 
   String pathToContent = "";
   
   // For this assignment, we will be dealing with text only.
   // Create a String variable that will keep track of the type of content
   // that the program encounters. In this case, inizialize the variable 
   // to "text/html".
   String typeOfContent = "text/html";
   
   // Print a meaningful message that the connection is being handled.
   System.err.println("Handling connection...");
   
   try {
      
      // Get the output and intput stream. 
      InputStream  is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();
      
      // Get the entire path name from the "GET " location.
      pathToContent = readHTTPRequest(is); 
 
      // Call two methods with the os, typeOfContent, and pathToContent 
      // as the arguments to sucessfully "write out" the HTTP header
      // and content on the web page.  
      writeHTTPHeader( os, typeOfContent, pathToContent );
      writeContent( os, typeOfContent, pathToContent );
      
      // Flush the output stream and close the socket. 
      os.flush();
      socket.close();
      
   } // end try. 
   
   catch (Exception e) {
      System.err.println("Output error: "+e);
   } // end catch.
   
   System.err.println("Done handling connection.");
   
   // Return to the caller.
   return;
   
} // end run.

/**
* Read the HTTP request header.
**/

private String readHTTPRequest(InputStream is) {
   
   String line;
   BufferedReader r = new BufferedReader(new InputStreamReader(is));
   
   // Create a String variable that will store the entire path name 
   // to the desired file. In addition, declare a counting variable 
   // that will help us stop after we see a space after the
   // "GET " in the request.  
   String originalPathToFile = "";
   int count = 0;

   while ( true ) {
   
      try {
      
         while (!r.ready()) 
            
            Thread.sleep(1);
         
         line = r.readLine();        
         System.err.println("Request line: (" + line + ")" );
         
         // Check if the line contains "GET". If it does, we need to 
         // extract the file path name. 
         if ( line.contains("GET ") ) {
            
            // Ignore "GET " (i.e. INCLUDING the space).
            originalPathToFile = line.substring(4);
            
            // Use a while loop to ignore everything after 
            // the file path name.
            while ( !( originalPathToFile.charAt( count ) == ' ' ) ) {
               
               count++;
            
            } // end while. 
            
            // Update the original path to the html file. This will ignore everything 
            // after the original path name, including the space. 
            originalPathToFile = originalPathToFile.substring( 0, count );
          
         } // end if.

         if ( line.length()== 0 ) 
      
            break;
            
      } // end try. 
      
      catch (Exception e) {
   
         System.err.println("Request error: "+e);
         break;
         
      } // end catch. 
      
   } // end while.
   
   // Return the path of the file.
   return originalPathToFile;
   
} // end readHTTPRequest method.

/**
* Write the HTTP header lines to the client network connection.
* @param os is the OutputStream object to write to
* @param contentType is the string MIME content type (e.g. "text/html")
**/

private void writeHTTPHeader(OutputStream os, String contentType, String pathToFile ) throws Exception {
 
   // Get the current time and date.
   Date d = new Date();
   DateFormat df = DateFormat.getDateTimeInstance();
   df.setTimeZone(TimeZone.getTimeZone("MST7MDT"));
   
   // Inizialize a string variable that will allow
   // us to concatinate the CURRENT working directory 
   // with the path name given from HTTP GET.
   String pathCopy = "." + pathToFile.substring( 0, pathToFile.length( ) );
   
   // Try to read in the file using the path name. Use a try and catch
   // that will print an error if the file is not found or if
   // the path is incorrect. 
   try {
   
      FileReader inputFile = new FileReader ( pathCopy );
      BufferedReader bufferFile = new BufferedReader ( inputFile );
      
      // If the file is found and opened, the status should stay as 200. 
      os.write("HTTP/1.1 200 OK\n".getBytes());
       
   } // end try. 
        
   catch (FileNotFoundException e) {
   
      // If the file is not found, the status should change to 404. 
      os.write( "HTTP/1.1 404 Not Found \n".getBytes() );
      // Print an error message on the terminal. 
      System.err.println( "File not found: " + pathCopy + " Please check path or if file exists." );
                              
   } // end catch
   
   // Write the appropriate time and server on the HTTP header.
   os.write("Date: ".getBytes());
   os.write((df.format(d)).getBytes());
   os.write("\n".getBytes());
   os.write("Server: Jose's very own server!\n".getBytes());
   
   // Ignoring these two lines of codes. They were originally given to us.
   // os.write("Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n".getBytes());
   // os.write("Content-Length: 438\n".getBytes()); 
   
   os.write("Connection: close\n".getBytes());
   os.write("Content-Type: ".getBytes());
   os.write(contentType.getBytes());
   // HTTP header ends with 2 newlines
   os.write("\n\n".getBytes()); 
   
   // Return to the caller
   return;
   
} // end writeHTTPHeader

/**
* Write the data content to the client network connection. This MUST
* be done after the HTTP header has been written out.
* @param os is the OutputStream object to write to
**/

private void writeContent(OutputStream os, String contentType, String pathToFile ) throws Exception {

   // Declare a variable that will allow us to read in the content from the HTML file.
   String content = "";
   
   // Update the path name to include the current working directory.
   String pathCopy = "." + pathToFile.substring( 0, pathToFile.length( ) );
   
   // Create and update a new Date object.
   Date d = new Date();
   DateFormat df = DateFormat.getDateTimeInstance();
   df.setTimeZone(TimeZone.getTimeZone("MST7MDT"));
   
   // Try reading from the file and throw an exception if the file isn't found.
   try {
   
      File inputtedFile = new File ( pathCopy );         
      FileReader readerFile = new FileReader ( inputtedFile );
      BufferedReader bufferFile = new BufferedReader ( readerFile );
                 
      // Use a while loop that will read the the file until there is nothing to read. In other 
      // words, read the next line until we reach null.
      while ( (content = bufferFile.readLine()) != null) {  
      
         // If a date tag is encountered, replace it with the current date. 
         if ( content.contains( "{{cs371date}}") )
            content = content.replace( "{{cs371date}}", df.format(d) );
         
         // If a server tag is encountered, replace it with the current server.  
         if( content.contains("{{cs371server}}") )
            content = content.replace( "{{cs371server}}", "Jose's very own server!" );
         
         // Write the corresponding bytes to the out stream.          
         os.write( content.getBytes( ) );
         
         // End the current content with a newline. NOTE: This is only needed
         // to make the source code easier to read by users. 
         os.write( "\n".getBytes( ) );
                    
      }// end while
      
   } // end try.
   
   catch ( FileNotFoundException e) {             
         
      // Print an error message if the file did not exists and/or if the path given is incorrect. 
      System.err.println("File not found: " + pathToFile + " Please check path or if file exists." );
      
      // Send the corresponding bytes so that the user knows that the 404 error occured. 
      // NOTE: Extra messages were not needed for this assignment but adding extra context is 
      // important in the real world. 
      os.write ( "<html>\n".getBytes( ) );
      os.write ( "<head>\n<title>ERROR 404</title></head>\n".getBytes( ) );
      os.write ( "<body>\n".getBytes( ) );
      os.write ( "<h1>404 Not Found</h1>\n".getBytes() );
      os.write ( "<b>Please make sure that the correct path name was given and/or that the file exists! :)</b>\n".getBytes( ) ); 
      os.write ( "</body>\n".getBytes() );  
      os.write ( "</html>\n".getBytes( ) );               
                
   }// end catch. 
     
} // end writeContent method. 

} // end class
