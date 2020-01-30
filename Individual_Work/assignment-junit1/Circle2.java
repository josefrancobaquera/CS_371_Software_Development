
/**

   Name: Jose Franco Baquera
   Date: November 29, 2018
   CS371 - Software Development
   File Name: Circle2.java
   Purpose of File: Implements the intersects
   function differently than Circle1.java.

**/

public class Circle2 extends Circle
{

public Circle2(double x, double y, double radius)
{
   super(x,y,radius); // Error found on this line. Changed by Jose Franco Baquera.
}

public boolean intersects(Circle other)
{
   double d;
   d = Math.pow(center.x - other.center.x, 2) + // Error found on this line. Changed by Jose Franco Baquera. 
          Math.pow(center.y - other.center.y, 2);
   if ( (Math.pow(radius - other.radius, 2) <= d) &&  (d <= Math.pow(radius+other.radius, 2))) // Error found on this line. Changed by Jose Franco Baquera.
      return true;
   else
      return false;
}

}
