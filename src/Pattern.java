import ij.IJ;
import java.util.Scanner;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Point;


/**
 *
 * @author deves
 */
public class Pattern {
    ArrayList<Point> pattern = new ArrayList<Point>();
    ArrayList<Short> dosePattern = new ArrayList<Short>();
    String path;
    
    
   
   //Constructors 
   public Pattern (String path){
       this.path=path;
       initialize(path);
   }
   public Pattern (String path, int size){
       this.path=path;
       initialize(path, size);
   }
   
   /**
    * Initialize pattern
    * @param path , path to the pattern file
    */
   private void initialize(String path){
       readFile(path);
       
   }
   private void initialize(String path, int size){
       readFile(path, size);
       
   }
   /**
    * Read a pattern file and build a centered pattern
    * @param path, path to the pattern file
    */
   public void readFile (String path){
       try{
           int column=0;
           int ligne=0;
           Scanner scanner = new Scanner(new File(path));
           
           while (scanner.hasNext()){
               column=0;
               ligne+=1;
               String line=  scanner.nextLine();
               Scanner lineScanner = new Scanner (line);
               while (lineScanner.hasNext()){
                   column+=1;
                   dosePattern.add(lineScanner.nextShort());
                   pattern.add(new Point(ligne, column));
               }
           }
           centerPattern();
                               
       } catch (Exception e){
           logMessage(e.toString());
         }    
   }
   /**
    * Read a pattern file and build a centered pattern, then scale it according to size
    * @param path, path to the pattern file
    * @param distance, distance between points
    */
   public void readFile (String path, int distance){
       try{
           int column=0;
           int ligne=0;
           Scanner scanner = new Scanner(new File(path));
           
           while (scanner.hasNext()){
               column=0;
               ligne+=1;
               String line=  scanner.nextLine();
               Scanner lineScanner = new Scanner (line);
               while (lineScanner.hasNext()){
                   column+=1;
                   dosePattern.add(lineScanner.nextShort());
                   pattern.add(new Point(ligne, column));
               }
           }
           centerPattern();
           scalePattern(distance);
                               
       } catch (Exception e){
           logMessage(e.toString());
         }    
   }
   
   public ArrayList<Short> getDosePattern(){
       return dosePattern;
   }
   /**
    * Apply the pattern to any point to calculate the surrounding points to be irradiated
    * @param p, position of the irradiation center
    * @return 
    */
   public ArrayList<Point> applyPattern (Point p){
       ArrayList<Point> pointList = new ArrayList<Point>();
       for (int index=0;index<pattern.size();index++){
           pointList.add(index, new Point((int)(pattern.get(index).getX()+p.getX()),(int)(pattern.get(index).getY()+p.getY())));
       }
       return pointList;
   }
   /**
    * Apply the pattern to a list of points
    * @param pointList
    * @return a list of point to be irradiated
    */
   public ArrayList<Point> applyPattern (ArrayList<Point> pointList){
       ArrayList<Point> p = new ArrayList<Point>();
       for (int pointIndex=0;pointIndex<pointList.size();pointIndex++){
            for (int index=0;index<pattern.size();index++){
                if (dosePattern.get(index)>0) p.add( new Point((int)(pattern.get(index).getX()+pointList.get(pointIndex).getX()),(int)(pattern.get(index).getY()+pointList.get(pointIndex).getY())));
            }
       }
       print(p);
       return p;
   }
   public ArrayList<Point> getPattern(){
       return pattern;
   }
   /**
    * Scale the pattern
    * @param size 
    */
   public void scalePattern(int size){
       for (int index=0;index<pattern.size();index++){
           pattern.set(index, new Point((int)pattern.get(index).getX()*size,(int)pattern.get(index).getY()*size));
       }
       print();
   }
   /**
    * Center the pattern
    */
   public void centerPattern(){
       double shiftX=0;
       double shiftY=0;
       for (int index=0;index<pattern.size();index++){
           shiftX +=pattern.get(index).getX()/pattern.size();
           shiftY +=pattern.get(index).getY()/pattern.size();
       }
       
       ShiftXY((int)Math.round(-shiftX),(int)Math.round(-shiftY));
       print();
   }
   /**
    * Shift the pattern along the x axis
    * @param dx 
    */
   public void ShiftX (int dx){
       for (int index=0;index<pattern.size();index++){
           pattern.get(index).translate(dx, 0);
       }
   }
   /**
    * Shift the pattern along y axis
    * @param dy 
    */
   public void ShiftY (int dy){
       for (int index=0;index<pattern.size();index++){
           pattern.get(index).translate(0, dy);
       }
   }
   /**
    * Shift the pattern along x and y axis
    * @param dx
    * @param dy 
    */
   public void ShiftXY (int dx, int dy){
       for (int index=0;index<pattern.size();index++){
           pattern.get(index).translate(dx, dy);
       }
   }
   /**
    * Print the pattern 
    */
   public void print(){
       for (int index=0;index<pattern.size();index++){
           logMessage("Point " + String.valueOf(index) + ": " + String.valueOf(pattern.get(index)));
       } 
   }
   public void print(ArrayList<Point> list){
       for (int index=0;index<list.size();index++){
           logMessage("Point " + String.valueOf(index) + ": " + String.valueOf(list.get(index)));
       } 
   }
   
   /**
     * Print message with time in the ImageJ log window
     * @param message 
     */
    private void logMessage(String message){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        IJ.log(df.format(dateobj) + " : " + message);
    }
}
