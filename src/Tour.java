/*
* Tour.java
* Stores a candidate tour
*/



import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Holds our tour of cities
    private ArrayList<BeamPoint> tour = new ArrayList<BeamPoint>();
    // Cache
    private double fitness = 0;
    private int distance = 0;
    
    // Constructs a blank tour
    public Tour(){
        for (int i = 0; i < TourManager.numberOfPoints(); i++) {
            tour.add(null);
        }
    }
    
    public Tour(ArrayList tour){
        this.tour = tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int pointIndex = 0; pointIndex < TourManager.numberOfPoints(); pointIndex++) {
          setPoint(pointIndex, TourManager.getPoint(pointIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a point from the tour
    public BeamPoint getPoint(int tourPosition) {
        return (BeamPoint)tour.get(tourPosition);
    }
    public ArrayList<Point> getPointList(){
        ArrayList<Point> pointList = new ArrayList<Point>();
        for (int index=0;index<tour.size();index++){
            pointList.add(getPoint(index).get());
        }
        return pointList;
    }

    // Sets a point in a certain position within a tour
    public void setPoint(int tourPosition, BeamPoint point) {
        tour.set(tourPosition, point);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }
    
    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }
    
    // Gets the total distance of the tour
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int pointIndex=0; pointIndex < tourSize(); pointIndex++) {
                // Get point we're travelling from
                BeamPoint fromPoint = getPoint(pointIndex);
                // point we're travelling to
                BeamPoint destinationPoint;
                // Check we're not on our tour's last point, if we are set our
                // tour's final destination point to our starting point
                if(pointIndex+1 < tourSize()){
                    destinationPoint = getPoint(pointIndex+1);
                }
                else{
                    destinationPoint = getPoint(0);
                }
                // Get the distance between the two cities
                tourDistance += fromPoint.distanceTo(destinationPoint);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of points on our tour
    public int tourSize() {
        return tour.size();
    }
    
    // Check if the tour contains a point
    public boolean containsPoint(BeamPoint point){
        return tour.contains(point);
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getPoint(i)+"|";
        }
        return geneString;
    }
}
