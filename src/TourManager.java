/*
* TourManager.java
* Holds the Points of a tour
*/



import java.util.ArrayList;

public class TourManager {

    // Holds our points
    private static ArrayList destinationPoints = new ArrayList<BeamPoint>();

    // Adds a destination point
    public static void addBeamPoint(BeamPoint point) {
        destinationPoints.add(point);
    }
    
    //Clear the list of destination points
    public static void reset(){
        destinationPoints.clear();
    }
    
    // Get a Point
    public static BeamPoint getPoint(int index){
        return (BeamPoint)destinationPoints.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfPoints(){
        return destinationPoints.size();
    }
}
