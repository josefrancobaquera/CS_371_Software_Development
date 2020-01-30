
/**

   Name: Jose Franco Baquera
   Date: November 29, 2018
   CS371 - Software Development
   File Name: Circle1.java
   Purpose of File: Implements the intersects
   function differently than Circle2.java.

**/

public class Circle1 extends Circle
{

public Circle1(double x, double y, double radius)
{
   super(x,y,radius);
}

public boolean intersects(Circle other)
{
    // Error found on this if statement. Changed by Jose Franco Baquera.
    if ( (Math.pow(radius - other.radius, 2) <= Math.pow(center.x - other.center.x, 2) +  Math.pow(center.y - other.center.y, 2) )
       && (Math.pow(center.x - other.center.x, 2) + Math.pow(center.y - other.center.y, 2) <= Math.pow(radius+other.radius, 2) ) )
	      return true;
    return false;
}

}

