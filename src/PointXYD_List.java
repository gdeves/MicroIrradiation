import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author deves
 */
public class PointXYD_List {
  ArrayList<Point> pointList = new ArrayList<Point>();
  ArrayList<Integer> doseList = new ArrayList <Integer>();
  


public PointXYD_List (Point p, int dose){
  pointList.add(p);
  doseList.add(dose);
}

public PointXYD_List(Point p){
  pointList.add(p);
  doseList.add(1);
}

//Setter
public void setPoint (Point p, int index){
    pointList.set(index, p);
}
public void setDose(int dose, int index){
    doseList.set(index, dose);
}

//Getter
public Point GetPoint(int index){
  return pointList.get(index);
}

public int GetDose(int index){
  return doseList.get(index);
}
public int size(){
    return pointList.size();
}

public double distanceBetween(int from, int to){
    Point p1=GetPoint(from);
    Point p2=GetPoint(to);
    double distance=Math.sqrt(Math.pow(p1.getX()-p2.getX(),2))+Math.sqrt(Math.pow(p1.getY()-p2.getY(),2));
    return distance;    
}
public int getNearest(int pointIndex){
    double distance=1e6;
    int nearest=0;
    
    for (int index=0;index<size();index++){
        if (distanceBetween(pointIndex,index)>0) {
            if (distanceBetween(pointIndex,index)<distance){
            distance=Math.min(distance, distanceBetween(pointIndex,index));
            nearest=index;
            }
        }
    }
    return nearest;
}

public void sortByNearest(){
   ArrayList<Point>clone=(ArrayList<Point>)pointList.clone();
   ArrayList<Point>sortedList=new ArrayList<Point>();
   ArrayList<Integer> sortedDoses = new ArrayList<Integer>();
   
   sortedList.add(pointList.get(0));
   sortedDoses.add(doseList.get(0));
   
   int start=0;
    while (clone.size()>1){
       int next = getNearest(start);
       sortedList.add(pointList.get(next));
       sortedDoses.add(doseList.get(next));
       clone.remove(start);
       start=next;
   }
   pointList.clear();
   doseList.clear();
   pointList=(ArrayList<Point>)sortedList.clone();
   doseList=(ArrayList<Integer>)sortedDoses.clone();
}
public void sortByNearest(int first){
   ArrayList<Point>clone=(ArrayList<Point>)pointList.clone();
   ArrayList<Point>sortedList=new ArrayList<Point>();
   ArrayList<Integer> sortedDoses = new ArrayList<Integer>();
   
   sortedList.add(pointList.get(0));
   sortedDoses.add(doseList.get(0));
   
   int start=first;
    while (clone.size()>1){
       int next = getNearest(start);
       sortedList.add(pointList.get(next));
       sortedDoses.add(doseList.get(next));
       clone.remove(start);
       start=next;
   }
   pointList.clear();
   doseList.clear();
   pointList=(ArrayList<Point>)sortedList.clone();
   doseList=(ArrayList<Integer>)sortedDoses.clone();
}
public int getClosestPointToCenter(){
    double nearest=1e6;
    int centerIndex=0;
    
    for (int index=0;index<size();index++){
        double distance=Math.pow(GetPoint(index).getX(), 2.0)+Math.pow(GetPoint(index).getY(), 2.0);
        if (distance<nearest){
            nearest=distance;
            centerIndex=index;
        }
    }
    return centerIndex;
}
}
