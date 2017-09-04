/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
    //private int x,y;
    //private Point p = new Point(0,0);
    public Charset charset;
    public Buffer byteArray;
    public DataOutputStream out;
    private int inputPort=6300;
    private int outputPort=6400;
    private String IP="10.13.0.29";
    private int beamCoefficient = 29;
    public Prefs prefs;
    
    
    
    
    /**
     * Initialize connexion with the compact RIO using defaut parameters
     * @return true if initialization is with no exception thrown
     * @throws IOException 
     */
    public Boolean initialize() throws IOException {
	try {	
            //Stored values
            
                Prefs.set("crio.inputPort", (int)(6300));
               
                IJ.log("test prefs " + String.valueOf(Prefs.getInt("crio.inputPort",0)) + "fin");
            
            
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
         * Format the message to be sent to compact rio
         * @param value
         * @param instruction
         * @return the string message to be sent
         */
        private String formatMessage(int value, String instruction){
           String message = "0000"+ String.valueOf((instruction+String.valueOf(value)).length()) + instruction+String.valueOf(value);
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
         * Get the defined parameters
         */
        public void getParameters() {
            try {
                sendMessage("000014GET SCAN PARAM");
            } catch (Exception e) {
        }
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
        private String read(int size) throws IOException{
            int stream;
            byte[] b = new byte[size];
            stream = reader.read(b);
            String response = new String(b, 0, stream);
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
            } catch(Exception e) {   
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
              catch(IOException e) {   
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
    */
    public void sendPositionList(List<Point> positionsList) {
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
            for(int index=0; index < positionsList.size(); index++) {
                byte dx = 0;
                byte dy = 0;
                short p;
                if (index==positionsList.size()-1)
                    p = -32768;
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


            dx = 0; //(byte) ((x+32768)/256);
            dy = 0;
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
                reader = null;
                reader = new BufferedInputStream(socketOut.getInputStream());
                String response = read(6);         
                response = response.replace(" ", "");            
                int taille = Integer.parseInt(response); 
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
        
        

   
