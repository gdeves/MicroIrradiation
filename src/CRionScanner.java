
import ij.IJ;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Point;
import java.io.DataOutputStream;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import ij.Prefs;


/**
 * contient toutes les fonctions dont on pourrait avoir besoin et qu'on enverra au CRionScanner
 * @author sao, deves, barberet
 */

 public class CRionScanner { 
    
    private BufferedInputStream reader = null;
    public Socket socketIn;
    public Socket socketOut;
    public Charset charset;
    public Buffer byteArray;
    public DataOutputStream out;
    private int inputPort;
    private int outputPort;
    private String IP;
    private int beamCoefficient;
    private double a,b,c,d,e,f;
    
    
    
    
    /**
     * Initialize connexion with the compact RIO using defaut parameters
     * @return true if initialization is with no exception thrown
     * @throws IOException 
     */
    public Boolean initialize() throws IOException {
	try {	
            //Stored values
            inputPort= Prefs.getInt(".crio.inputPort",6300);
            outputPort= Prefs.getInt(".crio.outputPort",6400);
            IP=Prefs.getString(".crio.IP", "10.13.0.29");
            beamCoefficient=Prefs.getInt(".crio.beamCoefficient", 7);
            a=Prefs.getDouble(".crio.calib.a",0);
            b=Prefs.getDouble(".crio.calib.b",0);
            c=Prefs.getDouble(".crio.calib.c", 0);
            d=Prefs.getDouble(".crio.calib.d", 0);
            e=Prefs.getDouble(".crio.calib.e", 0);
            f=Prefs.getDouble(".crio.calib.f", 0);
            
            // Server connexion
            try {socketIn = new Socket(IP, inputPort); 
                socketOut = new Socket(IP, outputPort);
            }
            catch (Exception e){
                logMessage("No Server found");
            }
            logMessage(" Starting Micro-irradiation Plugin");
            logMessage(" Connexion: " + IP);
            logMessage(" Input Port : " + Integer.toString(inputPort));
            logMessage(" Output Port : " + Integer.toString(outputPort));
            logMessage("Beam calibration: ("+ String.valueOf(a) + ","+ String.valueOf(b) + ","+ String.valueOf(c) + ","+ String.valueOf(d) + ","+ String.valueOf(e) + ","+ String.valueOf(f) + ")");
            
            out = new DataOutputStream(socketIn.getOutputStream());
            
	} catch (IOException e) {
            logMessage(" Error : " + e.toString());
            logMessage(" No connection found to CRIO");
            return false;
        } 
        return true;
    }
    public Boolean initialize(String IP, Integer inputPort, Integer outputPort) throws IOException {
	try {	
            // Server connexion   
            socketIn = new Socket(IP, inputPort); 
            socketOut = new Socket(IP, outputPort);
            logMessage(" Starting Micro-irradiation Plugin");
            logMessage(" Connexion: " + IP);
            logMessage(" Input Port : " + Integer.toString(inputPort));
            logMessage(" Output Port : " + Integer.toString(outputPort));
            
            out = new DataOutputStream(socketIn.getOutputStream());
            
	} catch (IOException e) {
            logMessage(" Error : " + e.toString());
            logMessage(" No connection found to CRIO");
            return false;
        } 
        return true;
    }
    public void exit(){
        try{
            stopScan();
            out.close();
            socketIn.close();
            socketOut.close();
            
        }
        catch (Exception e){
            
        }
    }
    private void loadPrefs(){
        
    }
    //-----------------------------------------------
    //Setters
    //-----------------------------------------------
    
    /**
     * Set the beam coefficient (/µm/MeV)
     * @param beamCoefficient 
     */
    public void setcoefficient(int beamCoefficient){
        this.beamCoefficient=beamCoefficient;
    }
    /**
     * Set input Port
     * @param inputPort 
     */
    public void setInputPort(int inputPort){
        this.inputPort=inputPort;
    }
    /**
     * Set output port for compact RIO
     * @param outputPort 
     */
    public void setOutputPort(int outputPort){
        this.outputPort=outputPort;
    }
    /**
     * Set IP for compact RIO: IP shall be given using quotes eg "IP"
     * @param IP 
     */
    public void setIP(String IP){
        this.IP=IP;
    }
    /**
     * CRIO calibration
     * @param a 
     */
    public void set_calib_a(double a){
        this.a=a;
    }
        /**
     * CRIO calibration
     * @param b 
     */
    public void set_calib_b(double b){
        this.b=b;
    }
        /**
     * CRIO calibration
     * @param c 
     */
    public void set_calib_c(double c){
        this.c=c;
    }
        /**
     * CRIO calibration
     * @param d 
     */
    public void set_calib_d(double d){
        this.d=d;
    }
        /**
     * CRIO calibration
     * @param e 
     */
    public void set_calib_e(double e){
        this.e=e;
    }
        /**
     * CRIO calibration
     * @param f 
     */
    public void set_calib_f(double f){
        this.f=f;
    }
    /**
     * CRIO calibration
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     */
    public void set_calib(double a,double b,double c,double d,double e,double f){
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;
    }
    public Point GetBeamOrigin(){
        Point p = new Point(0,0);
        return p;
    }
    /**
     * Transform (x,y) image coordinate to beam position
     * @param x
     * @param y
     * @return 
     */
    public Point MicroscopeToBeam (double x, double y){
       Point p= new Point((int)(a*x+b*y+e), (int)(c*x+d*y+f));
       return p;
    }
    public ArrayList<Point> MicroscopeToBeam(ArrayList<Point> pointList){
        for (int index=0;index<pointList.size();index++){
            Point p=MicroscopeToBeam(pointList.get(index).getX(),pointList.get(index).getY());
            pointList.set(index,p);
        }
        return pointList;
    }
    public ArrayList<Point> MicroscopeToBeam(ArrayList<Point> pointList, int offsetX, int offsetY){
        for (int index=0;index<pointList.size();index++){
            Point p=MicroscopeToBeam(pointList.get(index).getX()+offsetX,pointList.get(index).getY()+offsetY);
            pointList.set(index,p);
        }
        return pointList;
    }
    public int MicroscopeToBeam (int size){
       int p= (int)(a*size+e);
       return p;
    }
    /**
     * Get the correxponding x position in image reference from a beam position
     * @param p, a beam position
     * @return 
     */
    public double BeamToMicroscopeX (Point p){
        double x=(int)((p.getX()-b*p.getY()-c)/a);
        return x;
    }
     /**
     * Get the correxponding Y position in image reference from a beam position
     * @param p, a beam position
     * @return 
     */
    public double BeamToMicroscopeY (Point p){
        double y=(int)((p.getX()-e*p.getY()-f)/d);
        return y;
    }
    /**
     * 
     * @param p
     * @return 
     */
    
    
    public Point BeamToMicroscope (Point p){
        double x=(int)((p.getX()-e*p.getY()-f)/d);
        double y=(int)((p.getX()-e*p.getY()-f)/d);
        Point o= new Point((int)x,(int)y);
        return o;
    }
    
    /**
    * Setting the scanning speed (ms/pt)
    * @param speed 
    */
    public void setScanSpeed(int speed) {
        try {
            String message=formatMessage(speed,"SET SCAN SPEED = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    /**
    * Setting the number of scan
    * @param scan 
    */
    public void setnumberOfScan(int scan) {
        try {
            String message=formatMessage(scan,"SET NbrSCAN = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    /**
    * Keeping the beam always open even after scan
    **/
    public void setBeamOpenAfterScan() {
        try {
            sendMessage("000015SET No DeadTime");
        } catch (Exception e) {      
        }
    }
    /**
    * Closing the beam after each scan
    **/
    public void setBeamClosedAfterScan() {
        try {
            sendMessage("000016SET DeadTime = 0");
        } catch (Exception e) {      
        }
    }
    /**
    * Closing the beam after each point during a certain period
    * @param period
    **/
    public void setDeadTime(int period) {
        try {
            String message=formatMessage(period,"SET DeadTime = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    /**
    * Setting the counter to be used for the counting of ions
    * @param counter
    **/
    public void setCounter(int counter) {
        try {
            String message=formatMessage(counter,"SET EXTTRIG COUNTER = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    
    /**
    * Setting the timeout used during irradiation with a defined number of ions per point
    * When timeout is reached CR switches to next point and send "000008TimedOut" message
    * @param timeout
    **/
    public void setTimeOut(int timeout) {
        try {
            String message=formatMessage(timeout,"SET Timeout = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    
    /**
    * Setting the counter to be used to make an image
    * @param counter
    **/
    public void setMapCounter(int counter) {
        try {
            String message=formatMessage(counter,"SET IMA COUNTER = ");
            sendMessage(message);
        } catch (Exception e) {      
        }
    }
    
    /**
    * Setting the counter to be used to make an image
    * @param unit, ms or µs
    **/
    public void setSpeedUnit(String unit) {
        try {
            if ("ms".equals(unit)) sendMessage("000019SET VarSpeedUnit ms");
            else sendMessage("000019SET VarSpeedUnit µs");
        } catch (Exception e) {      
        }
    }
    
    
    /**
    * Format the message to be sent to compact rio
    * @param value
    * @param instruction
    * @return the string message to be sent
    */
    private String formatMessage(int value, String instruction){
       String message = "0000"+ String.valueOf((instruction+String.valueOf(value)).length()) + instruction+String.valueOf(value);
       return message;
    }
    /**
    * Format the message to be sent to compact rio
    * @param instruction
    * @return the string message to be sent
    */
    public String formatMessage(String instruction){
       String message = "0000"+ String.valueOf((instruction).length()) + instruction;
       return message;
    }
    //------------------------------------------------
    //Getters
    //------------------------------------------------
    
    public int getCRIOcoefficient(){
        return beamCoefficient;
    }    
        
        
        /**
         * get the compact RIO status
         */       
        public void getStatus() {
            try {
                sendMessage("000018GET SCANNER STATUS");
            } catch (Exception e) {
            }
        }
        /**
         * get the compact RIO status
         */       
        public void getMap() {
            try {
                sendMessage("000009GET IMAGE");
            } catch (Exception e) {
            }
        }
        /**
         * Get the defined parameters
         */
        public void getParameters() {
            try {
                sendMessage("000014GET SCAN PARAM");
            } catch (Exception e) {
        }
        }
        /**
         * Converts a beam position into digital value for display
         * @param x
         * @param y
         * @return 
         */
        public Point makeDigital(double x, double y){
            x=  (254*x/1023) +1;
            y=  (254*y/1023)+1;
            Point p=new Point((byte)x,(byte)y);
            return p;
        }
        /**
         * Starts the beam scanning
         */
        public void startScan() {
            try {
                sendMessage("000010START SCAN");
            } catch (Exception e) {
        }
        }
        /**
         * Stops the beam scanning
         */
        public void stopScan() {
            try {
                sendMessage("000009STOP SCAN");
            } catch (Exception e) {
        }
        }
        /**
         * Opens the beam with a defined deadTime
         * @param deadTime 
         */
        public void openBeam(int deadTime) { // pas de message reçu; à lancer avant 'startScan'
            try {
                switch (deadTime) {
                    case -1:
                        // laisse le faisceau toujours ouvert, même après fin de scan
                        sendMessage("000015SET No DeadTime");
                        break;
                    case 0:
                        // ouvre le faisceau, le laisse ouvert pendant le scan et le ferme en fin de scan
                        sendMessage("000016SET DeadTime = 0"); 
                        break;
                    case 1:
                        // ouvre le faisceau, coupe un certain temps (ici 500 µs) entre chaque point pdt le scan, le ferme a la fin du scan
                        sendMessage("000018SET DeadTime = 500"); 
                        break;
                    default:
                        String message=formatMessage(deadTime,"SET DeadTime = ");
                        sendMessage(message);
                        break;
                }
            } catch (Exception e) {
        }
        }
        
       
        /**
         * Lecture des caractères envoyés par le compactRIO
         * Conseil - Appliquer la méthode read dans un thread séparé des autres (tourne en continu)
         * @param size, taille du buffer de lecture
         * @return le message reçu
         * @throws IOException 
         */
        public String read(int size) throws IOException{
            int stream;
            byte[] word = new byte[size];
            stream = reader.read(word);
            String response = new String(word, 0, stream);
            
            return response;
        }
        /**
         * Send a message to the compact RIO
         * @param message to be sent
         */
        public void sendMessage(String message) {
            try {
                logMessage(" >> " + message);
                sendMessage(message.getBytes(),message.length());
            } catch(Exception sendMessageException) {   
            }
        }
        /**
         * Send a message to the compactRIo 
         * @param table, tableau d'octets à envoyer
         * @param tableLength, longueur du tableau
         */
        public void sendMessage(byte[] table, int tableLength) {
             try {
             out.write(table,0,tableLength);
             out.flush();
             }
             //writer.flush();
              catch(IOException outException) {   
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
    /**
    * Send a list of beam position to the compact RIO
    * @param positionsList, liste des positions à envoyer
     * @param digitalList, digital coordinates for display of pattern on web interface
    */
    public void sendPositionList(List<Point> positionsList, List<Point> digitalList) {
            // Création d'un bytebuffer de 8 octets que l'on remplit avec x, y , dx
            ByteBuffer positionsBuffer = ByteBuffer.allocate(8*positionsList.size());
            logMessage("N points : " + positionsList.size());
            
            //Envoi de toutes les valeurs analogiques des points
            for(int index=0; index < positionsList.size(); index++){
                short x = (short) positionsList.get(index).x;
                short y = (short) positionsList.get(index).y;
                positionsBuffer.putShort(x).putShort(y);
            }
            
            //Envoi de toutes les valeurs digitales des points
            for(int index=0; index < digitalList.size(); index++) {
                byte dx = (byte) digitalList.get(index).x;
                byte dy = (byte) digitalList.get(index).y;
                logMessage("dx: " + String.valueOf(dx));
                logMessage("dy: " + String.valueOf(dy));
                short p;
                //Si c'est le dernier point de la liste, bit 15=1
                if (index==positionsList.size()-1)
                    p = -32768;
                //si bit 14 =0 alors mode temps par point
                else 
                    p = 0;
                positionsBuffer.put(dx).put(dy).putShort(p);
            }   
                
            String message=formatMessage(positionsList.size()*8,"SCAN DATA = ");
            logMessage(message);
            
            sendMessage(message.getBytes(),message.length());
            sendMessage(positionsBuffer.array(),positionsBuffer.capacity());
  }
  
    /**
    * Send a list of beam position to the compact RIO, same dose for each point defined by value
    * @param positionsList, liste des positions à envoyer
     * @param digitalList, list of digital coordinates to display pattern on web interface
     * @param TimeMode, timemode between ion per point mode (timemode=0) or time per point (timemode=1)
     * @param dose, ion or time per point. Identical for each irradiated point
    */
    public void sendPositionList(List<Point> positionsList, List<Point> digitalList,int TimeMode, short dose) {
        
        
        // Création d'un bytebuffer de 8 octets que l'on remplit avec x, y , dx
        ByteBuffer positionsBuffer = ByteBuffer.allocate(8*positionsList.size());
        logMessage("N points : " + positionsList.size());
        try {
            if (positionsList.size()>1 & TimeMode == 1) sendMessage("000011SET NO WAIT");
            if (TimeMode==0) sendMessage("000011SET NO WAIT");
            if (TimeMode == 1 & positionsList.size()==1) sendMessage("000008SET WAIT");
        } catch (Exception e) {
        }
        
        //Envoi de toutes les valeurs analogiques des points
        for(int index=0; index < positionsList.size(); index++){
        short x = (short) positionsList.get(index).x;
        short y = (short) positionsList.get(index).y;
        positionsBuffer.putShort(x).putShort(y);
        }
        switch (TimeMode){
            //Ion per point mode
            case 0:
                //Envoi de toutes les valeurs digitales des points
                for(int index=0; index < digitalList.size(); index++) {
                    byte dx = (byte) digitalList.get(index).x;
                    byte dy = (byte) digitalList.get(index).y;
                    short p;
                    //si bit15=0 et bit 14 =1 alors mode temps par point
                    //16384 = 0x0100 0000 0000 0000
                    //Si c'est le dernier point de la liste, bit 15=1 et bit14=1
                    //-16384 = 0x1100 0000 0000 0000
                    if (index==positionsList.size()-1)
                        p = (short)(dose | -16384);
                    
                    else 
                        p = (short)(dose | 16384);
                    //logMessage("Ions par point: " + Integer.toString(p));
                    positionsBuffer.put(dx).put(dy).putShort(p);
                }
                break;
            //Time per point mode    
            case 1:
                //Envoi de toutes les valeurs digitales des points
                for(int index=0; index < digitalList.size(); index++) {
                    byte dx = (byte) digitalList.get(index).x;
                    byte dy = (byte) digitalList.get(index).y;
                    short p;
                    //Si c'est le dernier point de la liste, bit 15=1 et bit14=0
                    //-32768 = 0x1000 0000 0000 0000
                    if (index==positionsList.size()-1)
                        p = (short)(dose | -32768);
                    //si bit15=0 et bit 14 =0 alors mode temps par point
                    //16383 = 0x0011 1111 1111 1111
                    else 
                        p = (short)(dose | 16383);
                    //logMessage("Time per point" + Integer.toString(p));
                    positionsBuffer.put(dx).put(dy).putShort(p);
                }
                
            
                
        }       
            String message=formatMessage(positionsList.size()*8,"SCAN DATA = ");
            logMessage(message);
            
            sendMessage(message.getBytes(),message.length());
            sendMessage(positionsBuffer.array(),positionsBuffer.capacity());
  }
    /**
     * Sending position list to CRIO with a dose pattern
     * @param positionsList
     * @param digitalList
     * @param TimeMode
     * @param dose list of doses for irradiated points
     */
  public void sendPositionList(List<Point> positionsList, List<Point> digitalList,int TimeMode, List<Integer> dose) {
        
        
        // Création d'un bytebuffer de 8 octets que l'on remplit avec x, y , dx
        ByteBuffer positionsBuffer = ByteBuffer.allocate(8*positionsList.size());
        logMessage("N points : " + positionsList.size());
        try {
            if (positionsList.size()>1 & TimeMode == 1) sendMessage("000011SET NO WAIT");
            if (TimeMode==0) sendMessage("000011SET NO WAIT");
            if (TimeMode == 1 & positionsList.size()==1) sendMessage("000008SET WAIT");
        } catch (Exception e) {
        }
        
        //Envoi de toutes les valeurs analogiques des points
        for(int index=0; index < positionsList.size(); index++){
        short x = (short) positionsList.get(index).x;
        short y = (short) positionsList.get(index).y;
        positionsBuffer.putShort(x).putShort(y);
        }
        switch (TimeMode){
            //Ion per point mode
            case 0:
                //Envoi de toutes les valeurs digitales des points
                for(int index=0; index < digitalList.size(); index++) {
                    byte dx = (byte) digitalList.get(index).x;
                    byte dy = (byte) digitalList.get(index).y;
                    short p;
                    //si bit15=0 et bit 14 =1 alors mode temps par point
                    //16384 = 0x0100 0000 0000 0000
                    //Si c'est le dernier point de la liste, bit 15=1 et bit14=1
                    //-16384 = 0x1100 0000 0000 0000
                    if (index==positionsList.size()-1)
                        p = (short)(dose.get(index) | -16384);
                    
                    else 
                        p = (short)(dose.get(index) | 16384);
                    //logMessage("Ions par point: " + Integer.toString(p));
                    positionsBuffer.put(dx).put(dy).putShort(p);
                    //logMessage(Short.toString(p));
                }
                break;
            //Time per point mode    
            case 1:
                //Envoi de toutes les valeurs digitales des points
                for(int index=0; index < digitalList.size(); index++) {
                    byte dx = (byte) digitalList.get(index).x;
                    byte dy = (byte) digitalList.get(index).y;
                    short p;
                    //Si c'est le dernier point de la liste, bit 15=1 et bit14=0
                    //-32768 = 0x1000 0000 0000 0000
                    if (index==positionsList.size()-1){
                        p = (short)(dose.get(index) | -32768);
                    //si bit15=0 et bit 14 =0 alors mode temps par point
                    //16383 = 0x0011 1111 1111 1111
                    }
                    else {
                        p = (short)(dose.get(index) & 16383);
                    //logMessage("Time per point" + Integer.toString(p));
                    }
                    positionsBuffer.put(dx).put(dy).putShort(p);
                    logMessage(Short.toString(p));
                    //logMessage("Dose sent: " + Integer.toString(dose.get(index)));
                }
                
            
                
        }       
            String message=formatMessage(positionsList.size()*8,"SCAN DATA = ");
            logMessage(message);
            
            sendMessage(message.getBytes(),message.length());
            sendMessage(positionsBuffer.array(),positionsBuffer.capacity());
  }
/**
     * Sending position list to CRIO with a dose pattern
     * @param positionsList
     * @param digitalList
     * @param TimeMode
     * @param dose list of doses for irradiated points
     * @param bufferSize number of points to sent to CRIO at once
     */
  public void sendPositionList(List<Point> positionsList, List<Point> digitalList,int TimeMode, List<Integer> dose, int bufferSize) {
        
        int pointToSend=positionsList.size();
        ByteBuffer positionsBuffer=ByteBuffer.allocate(1);
        int start=0;
        int stop=0;
        
        while (pointToSend>0){
        
            // Création d'un bytebuffer de 8 octets que l'on remplit avec x, y , dx
            
            if (pointToSend>=bufferSize) {
                positionsBuffer = ByteBuffer.allocate(8*positionsList.size());
                logMessage("Sending N points : " + bufferSize);
                start=positionsList.size()-pointToSend;
                stop=start+bufferSize;
            }
            else if (pointToSend<bufferSize) {
                positionsBuffer = ByteBuffer.allocate(8*pointToSend);
                logMessage("Sending N points : " + pointToSend);
                start=positionsList.size()-pointToSend;
                stop=start+pointToSend;
            }
            
            try {
                if (pointToSend>1 & TimeMode == 1) sendMessage("000011SET NO WAIT");
                if (TimeMode==0) sendMessage("000011SET NO WAIT");
                if (TimeMode == 1 & pointToSend==1) sendMessage("000008SET WAIT");
            } catch (Exception e) {
            }

            //Envoi de toutes les valeurs analogiques des points
            for(int index=start; index < stop; index++){
            short x = (short) positionsList.get(index).x;
            short y = (short) positionsList.get(index).y;
            positionsBuffer.putShort(x).putShort(y);
            }
            switch (TimeMode){
                //Ion per point mode
                case 0:
                    //Envoi de toutes les valeurs digitales des points
                    for(int index=start; index < stop; index++) {
                        byte dx = (byte) digitalList.get(index).x;
                        byte dy = (byte) digitalList.get(index).y;
                        short p;
                        //si bit15=0 et bit 14 =1 alors mode temps par point
                        //16384 = 0x0100 0000 0000 0000
                        //Si c'est le dernier point de la liste, bit 15=1 et bit14=1
                        //-16384 = 0x1100 0000 0000 0000
                        if (index==stop-1)
                            p = (short)(dose.get(index) | -16384);

                        else 
                            p = (short)(dose.get(index) | 16384);
                        //logMessage("Ions par point: " + Integer.toString(p));
                        positionsBuffer.put(dx).put(dy).putShort(p);
                        //logMessage(Short.toString(p));
                    }
                    break;
                //Time per point mode    
                case 1:
                    //Envoi de toutes les valeurs digitales des points
                    for(int index=start; index < stop; index++) {
                        byte dx = (byte) digitalList.get(index).x;
                        byte dy = (byte) digitalList.get(index).y;
                        short p;
                        //Si c'est le dernier point de la liste, bit 15=1 et bit14=0
                        //-32768 = 0x1000 0000 0000 0000
                        if (index==stop-1){
                            p = (short)(dose.get(index) | -32768);
                        //si bit15=0 et bit 14 =0 alors mode temps par point
                        //16383 = 0x0011 1111 1111 1111
                        }
                        else {
                            p = (short)(dose.get(index) & 16383);
                        //logMessage("Time per point" + Integer.toString(p));
                        }
                        positionsBuffer.put(dx).put(dy).putShort(p);
                        logMessage(Short.toString(p));
                        //logMessage("Dose sent: " + Integer.toString(dose.get(index)));
                    }



            }
                String message="";
                if (pointToSend>bufferSize) {
                    message=formatMessage(bufferSize*8,"SCAN DATA = ");
                    logMessage(message);
                }
                else if (pointToSend<bufferSize){
                    message=formatMessage(pointToSend*8,"SCAN DATA = ");
                    logMessage(message);
                }

                sendMessage(message.getBytes(),message.length());
                sendMessage(positionsBuffer.array(),positionsBuffer.capacity());
                
                pointToSend-=bufferSize;
        }
  }  
        /**
         * Send a single beam position to the compact RIO
         * @param pt 
         */
        public void sendPosition(Point pt) {
            ByteBuffer buff = ByteBuffer.allocate(8);
            short x ;
            short y;
            byte dx;
            byte dy;
            short p;
  
            x = (short) pt.x;
            y = (short) pt.y;              
            buff.putShort(x).putShort(y);


            dx = (byte)makeDigital((double)x,(double)y).getX();
            dy = (byte)makeDigital((double)x,(double)y).getY();
            p = -32768;

            buff.put(dx).put(dy).putShort(p);
            
            String message=formatMessage(8,"SCAN DATA = ");
            logMessage(message);
            
            sendMessage("000008SET WAIT");
            sendMessage(message.getBytes(),message.length());
            sendMessage(buff.array(),buff.capacity());
 }
     

   
        public String readMessage() {
            try {
                int taille=0;
                reader = null;
                reader = new BufferedInputStream(socketOut.getInputStream());
                String response = read(6);

                response = response.replace(" ", "");            
                taille = Integer.parseInt(response);
                response = read(taille);
                
                return response;
                
            } catch (IOException e) {
                String response;
                response = "Fail to read message: IO exception";
                return response;
            } catch (NumberFormatException e) {      
                String response = "Fail to read message: wrong format";
                return response;
        }      
        }
 }
        
        

   
