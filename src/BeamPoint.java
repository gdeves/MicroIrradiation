
import java.awt.Point;

/*
* BeamPoint.java
* Models a point to irradiate
*/



public class BeamPoint {
    Point p;
    
    // Constructs a randomly placed point
    public BeamPoint(){
        this.p = new Point((int)(Math.random()*256),(int)(Math.random()*256));
        
    }
    
    // Constructs a point at chosen x, y location
    public BeamPoint(int x, int y){
        this.p = new Point(x,y);
    }
    public BeamPoint(Point p){
        this.p=p;
    }
    
    // Gets Point x coordinate
    public int getX(){
        return (int)this.p.getX();
    }
    
    // Gets point y coordinate
    public int getY(){
        return (int)this.p.getY();
    }
    public Point get(){
        return this.p;
    }
    
    // Gets the distance to given point
    public double distanceTo(BeamPoint point){
        int xDistance = Math.abs(getX() - (int)point.getX());
        int yDistance = Math.abs(getY() - (int)point.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}
