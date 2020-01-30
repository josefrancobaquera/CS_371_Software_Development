/**

   Name: Jose Franco Baquera
   Date: Novemeber 29, 2018
   CS371 - Software Development
   File Name: Circle1Test.java
   Purpose of Class: Example JUnit testing class for Circle1 (and Circle)
   NOTE: Circle1Test and Circle2Test have the same test cases!

**/

// Import all assertions and all annotations - see docs for lists and descriptions.
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

// Starting Circle1Test class.
public class Circle1Test {

   // Data you will need for each test case.
   private Circle1 circle1;
   private Circle1 circle2;
   private Circle1 circle3;
   private Circle1 circle4;
 
   /**
    *Stuff you want to do before each test case:
   **/
   @BeforeEach
   public void setup( ) {

      System.out.println("\nTest starting...");
      circle1 = new Circle1(1,2,3);
      circle2 = new Circle1(2,2,4);
      circle3 = new Circle1(8,8,1);
      circle4= new Circle1(2,2,1);
   
   } // end setup function.

   /**
    *Stuff you want to do after each test case:
   **/
   @AfterEach
   public void teardown() {
   
      System.out.println("\nTest finished.");
   
   } // end teardown function.

   /**
    *Test a simple positive move. That is, this test case 
    *will test if a circle can move in the 
    *"positive" direction.
   **/
   @Test
   public void simpleMove() {
   
      Point p;
      System.out.println("Running test simpleMove.");
      p = circle1.moveBy(5, 6);
      assertTrue(p.x == 6 && p.y == 8);
   
   } // end simpleMove function.
 
   /**
    *Test a simple negative move. That is, this test case 
    *will test if a circle can move in the 
    *"negative" direction.
   **/
   @Test
   public void simpleMoveNeg() {
   
      Point p;
      System.out.println("Running test simpleMoveNeg.");
      p = circle1.moveBy(-5,-6);
      assertTrue(p.x == -4 && p.y == -4 );
   
   } // end simpleMoveNeg function.
   
   // NOTE: Because both positive and negative directions work, we can 
   // assume that moving "zero" places also works. Therefore, no test case 
   // is needed for this scenerio. 
   
   /**
    * Test a positive scaling. That is, we want to test 
    * whether or not we can scale the circle more than 100%. 
    * The resulting new radius of the circle should be
    * greater than its original. 
   **/
   @Test
   public void scalePositive( ) {
      
      System.out.println("Running test scalePositive.");
      assertTrue(circle1.scale(2) == 6);
      
   } // end scalePositive function.
   
   /**
    *Test negative scaling. That is, we want to test whether or not we 
    *can scale the circle by less than 100%. The resulting new
    *radius of the circle should be less than its original. 
   **/
   @Test
   public void scaleNegative() {
   
      System.out.println("Running test scaleNegative.");
      assertTrue(circle2.scale(0.5) == 2);
   
   } // end scaleNegative function. 

   /**
    *Test that two circles intersect by using boundary
    *testing (i.e. circles intersect on at least one point).
   **/
   @Test
   public void simpleIntersect() {
	
      System.out.println("Running simpleIntersect test.");
	   assertTrue(circle1.intersects(circle2));
   
   } // end simpleIntersect function.

   /**
    *Test that two circles do not intersect (i.e. one circle 
    *does not contain the other circle). That is, one circle is not 
    *"inside" the other circle, meaning that no intersection occurs.  
   **/
   @Test
   public void simpleNoIntersect()  {
   
	   System.out.println("Running simpleNoIntersect test");
	   assertFalse(circle1.intersects(circle3));
   
   } // end simpleNoIntersect function.
   
   /**
    *Test that two circles do not intersect (i.e. one 
    *circle contains the other circle and their edges
    *do not intersect). If one circle contains another 
    *circle in its totality, then no edges should intersect.
   **/
   @Test
   public void containedNoIntersect()  {
   
	   System.out.println("Running containedNoIntersect test");
	   assertFalse(circle1.intersects(circle4));
   
   } // end containedNoIntersect function.

} // end class.
