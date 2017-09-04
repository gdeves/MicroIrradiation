
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.process.ImageProcessor;
import ij.ImageStack;
import ij.process.ShortProcessor;
import java.awt.Point;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ij.Prefs;
import java.awt.Color;
import mmcorej.CMMCore;
import mmcorej.TaggedImage;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.micromanager.api.ScriptInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class IrradiationFrame extends javax.swing.JFrame {
   private final ScriptInterface gui_;
   private final CMMCore core_;
   //private Preferences prefs_;
   public CRionScanner crio;
   //private ImageProcessor imProc;
   private final NumberFormat nf_;
   public double a,b,c,d,e,f;
   //public List<Point>  pointList = new ArrayList<Point>();
   //public List<Point> pointList2 = new ArrayList<Point>();
   
   private Prefs preferences;
   private volatile boolean statusThreadIsOn =false;
   private volatile boolean isBeamOn=false;
    /**
     * Creates new form IrradiationFrame
     * @param gui
     * @throws java.io.IOException
     */
    public IrradiationFrame (ScriptInterface gui) throws IOException{
       gui_ = gui;
       core_ = gui_.getMMCore();
       nf_ = NumberFormat.getInstance();
       //prefs_ = Preferences.userNodeForPackage(this.getClass());
       initComponents();
       
       IJ.log("Preferences are saved in " + Prefs.getPrefsDir());
       // CRionScanner        
       crio = new CRionScanner();
       Boolean isInitialized=crio.initialize();
       if (isInitialized) getStatus();
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jP_Calibration_ = new javax.swing.JPanel();
        jLab_Beam_Energy_ = new javax.swing.JLabel();
        jTF_BeamEnergy_ = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTF_ImageSize_ = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTF_ImageTreshold_ = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTF_Delay_ = new javax.swing.JTextField();
        jB_Acquire_ = new javax.swing.JButton();
        jB_Process_ = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jB_Move_ = new javax.swing.JButton();
        jRB_resultsTable = new javax.swing.JRadioButton();
        jRB_ROImanager = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jB_StartStop = new javax.swing.JButton();
        jPanel_Status = new javax.swing.JPanel();
        jLabel_status = new javax.swing.JLabel();
        jCheckBox_showFlux = new javax.swing.JCheckBox();
        jB_connectToRio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Microbeam irradiation");
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jP_Calibration_.setBorder(javax.swing.BorderFactory.createTitledBorder("Calibration"));
        jP_Calibration_.setName("jP_Calibration_"); // NOI18N

        jLab_Beam_Energy_.setText("Beam energy (MeV) : ");

        jTF_BeamEnergy_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_BeamEnergy_.setText("3.0");
        jTF_BeamEnergy_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_BeamEnergy_ActionPerformed(evt);
            }
        });

        jLabel2.setText("Field size (µm) : ");

        jTF_ImageSize_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_ImageSize_.setText("512");
        jTF_ImageSize_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_ImageSize_ActionPerformed(evt);
            }
        });

        jLabel3.setText("Image treshold");

        jTF_ImageTreshold_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_ImageTreshold_.setText("50");
        jTF_ImageTreshold_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_ImageTreshold_ActionPerformed(evt);
            }
        });

        jLabel4.setText("Delay (ms)");

        jTF_Delay_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_Delay_.setText("3000");
        jTF_Delay_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_Delay_ActionPerformed(evt);
            }
        });

        jB_Acquire_.setText("Acquire stack");
        jB_Acquire_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Acquire_ActionPerformed(evt);
            }
        });

        jB_Process_.setText("Calibrate stack");
        jB_Process_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Process_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jP_Calibration_Layout = new javax.swing.GroupLayout(jP_Calibration_);
        jP_Calibration_.setLayout(jP_Calibration_Layout);
        jP_Calibration_Layout.setHorizontalGroup(
            jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_Calibration_Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_Calibration_Layout.createSequentialGroup()
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jP_Calibration_Layout.createSequentialGroup()
                        .addComponent(jLab_Beam_Energy_)
                        .addGap(18, 18, 18)))
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTF_BeamEnergy_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_Delay_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_ImageTreshold_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_ImageSize_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jB_Acquire_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jB_Process_, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        jP_Calibration_Layout.setVerticalGroup(
            jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_Calibration_Layout.createSequentialGroup()
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTF_Delay_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jB_Acquire_))
                .addGap(18, 18, 18)
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_Calibration_Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTF_ImageSize_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTF_ImageTreshold_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTF_BeamEnergy_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jB_Process_)
                        .addComponent(jLab_Beam_Energy_)))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Irradiation"));

        jB_Move_.setText("Get positions & send to RIO");
        jB_Move_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Move_ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRB_resultsTable);
        jRB_resultsTable.setSelected(true);
        jRB_resultsTable.setText("...from results table");
        jRB_resultsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_resultsTableActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRB_ROImanager);
        jRB_ROImanager.setText("...from ROI manager");

        jLabel1.setText("Get positions ");

        jB_StartStop.setText("Start");
        jB_StartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_StartStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jB_Move_, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jB_StartStop))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jRB_resultsTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRB_ROImanager)))
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRB_resultsTable)
                    .addComponent(jRB_ROImanager)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Move_)
                    .addComponent(jB_StartStop))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel_status.setText("Status : ??");

        jCheckBox_showFlux.setText("show data flux");

        jB_connectToRio.setText("connect to RIO");
        jB_connectToRio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_connectToRioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_StatusLayout = new javax.swing.GroupLayout(jPanel_Status);
        jPanel_Status.setLayout(jPanel_StatusLayout);
        jPanel_StatusLayout.setHorizontalGroup(
            jPanel_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_status, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jB_connectToRio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox_showFlux)
                .addContainerGap())
        );
        jPanel_StatusLayout.setVerticalGroup(
            jPanel_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_status)
                    .addComponent(jCheckBox_showFlux)
                    .addComponent(jB_connectToRio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jP_Calibration_, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP_Calibration_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jP_Calibration_.getAccessibleContext().setAccessibleName("calibration");
        jP_Calibration_.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTF_BeamEnergy_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_BeamEnergy_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_BeamEnergy_ActionPerformed

    private void jTF_ImageSize_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_ImageSize_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_ImageSize_ActionPerformed

    private void jTF_ImageTreshold_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_ImageTreshold_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_ImageTreshold_ActionPerformed

    private void jTF_Delay_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_Delay_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_Delay_ActionPerformed

    private void jB_Acquire_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Acquire_ActionPerformed
        int fieldSize = Integer.parseInt(jTF_ImageSize_.getText()); // taille image ( 500 µm par defaut) 
        float energy = Float.parseFloat(jTF_BeamEnergy_.getText()); // energie ( 3 MeV par defaut)
        List<Point>  pointList = new ArrayList<Point>();
        
        try {
            pointList.clear();           
            float gain = crio.getCRIOcoefficient()*energy;
   
            pointList.add(new Point(-(int)(fieldSize*gain/2),(int)(fieldSize*gain/2))); 
            pointList.add(new Point((int)(fieldSize*gain/2),0));
            pointList.add(new Point(0,-(int)(fieldSize*gain/2)));
            logMessage(" Points: " + pointList.toString());
            
        } catch(Exception e){
            logMessage(" Error : "+ e.toString());
        }
        
        // envoi des pointList au CRio
        try {               
            // a changer pour emplacement plus accessible
            
            gui_.openAcquisition("Image", Prefs.getPrefsDir(), 3, 1, 1, 1, true, false);   
            core_.snapImage();
            int width=(int) core_.getImageWidth();
            int height=(int) core_.getImageHeight();
            ImageStack iStack=new ImageStack(width,height);
            
            for(int index=0; index < 3; index++) {               
                // envoi point par point a partir de la liste "pointList"
                crio.sendPosition(pointList.get(index)); 
                 
                // commence le scan
                crio.startScan();
                 
                /* temps (en ms) de pause défini sur interface avant
                if(Integer.parseInt(jTF_Delay_.getText()) > 0){
                   Thread.sleep(Integer.parseInt(jTF_Delay_.getText()));
                }*/
                
                //Wait for the beam before going further
                while (!isBeamOn){                   
                }
                
                core_.snapImage();
                //------------------------------------------
                short[] img = (short[])core_.getImage();
                width=(int) core_.getImageWidth();
                height=(int) core_.getImageHeight();
                
                ShortProcessor ip = new ShortProcessor(width, height);
		ip.setPixels(img);
                iStack.addSlice(Integer.toString(index),ip,index);
                //------------------------------------------
                
                
                /*TaggedImage img; /code de mariama
                img = core_.getTaggedImage();*/                    
                //gui_.addImageToAcquisition("Image", index, 0, 0, 0, img);                                
                    
                // arret scan
                crio.stopScan();                  
            }       
            ImagePlus imp = new ImagePlus("Calibration", iStack);
            imp.show();
            
        } catch (Exception e) {
            logMessage(" Acquisition error : " + e.toString());
            crio.stopScan();
        }
    }//GEN-LAST:event_jB_Acquire_ActionPerformed

    private void jB_Process_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Process_ActionPerformed
        try{
        // recupere un stack d'image existantes, applique traitement basique (threshold, "analyze particles", recupere coordonnees X Y)
        //jTF_ImageTreshold_ActionPerformed(evt);
        
        //IJ.open(); // ouvre une fenetre pour acceder au dossier d'images
        ImagePlus imp = IJ.getImage(); // ouvre l'image
        IJ.run(imp, "8-bit", ""); // conversion 8 bit
        
        // application seuillage 
        int minVal = Integer.parseInt(jTF_ImageTreshold_.getText());    // affiche la valeur min du seuil choisie   
        IJ.setThreshold(minVal,255);
        
        // application fonction 'Analyse Particles' (imageJ) pour recuperer coordonnees X et Y
        Analyzer.setMeasurement(32,true);
        IJ.run(imp, "Analyze Particles...", "size=1-Infinity show=Outlines display clear add stack"); 
        ResultsTable rt = Analyzer.getResultsTable();
            
        
        int nResults = rt.getCounter();
        
        for (int i=0; i<nResults; i++) {
            double x = rt.getValue("X",i); //affiche la valeur de x
            double y = rt.getValue("Y",i); // affiche la valeur de y
            int w = i+1;
            logMessage("Point # " +w + ", X = " +x+ ", Y = " +y); // affiche  les coordonnees X et Y dans la console
        }
        
            // etalonnage
            int fieldSize = Integer.parseInt(jTF_ImageSize_.getText()); // taille image ( 500 µm par defaut) 
            float energy = Float.parseFloat(jTF_BeamEnergy_.getText()); // energie ( 3 MeV par defaut)
            float gain = crio.getCRIOcoefficient()*energy;
            int step = (int)(fieldSize*gain/2);
        
            RealMatrix coefficients = new Array2DRowRealMatrix(new double[][] { {rt.getValue("X",0), rt.getValue("Y",0),1}, {rt.getValue("X",1),rt.getValue("Y",1),1}, {rt.getValue("X",2), rt.getValue("Y",2),1}},false);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        
            RealVector constants = new ArrayRealVector(new double[] { -step, step,-step}, false);
            RealVector solution = solver.solve(constants); // donne les 3 coefficients a, b et e (pour X)
            
            logMessage("Calibration results --------");
            logMessage("#a =" +solution.getEntry(0));        
            logMessage("#b = " +solution.getEntry(1));

            RealVector constantsY = new ArrayRealVector(new double[] { step, 0,-step}, false);
            RealVector solutionY = solver.solve(constantsY); // donne les 3 coefficients c, d et f (pour Y)
        
            logMessage("#c =" +solutionY.getEntry(0));
            logMessage("#d = " +solutionY.getEntry(1));
            logMessage("#e = " +solution.getEntry(2));
            logMessage("#f = " +solutionY.getEntry(2));
        
            a = solution.getEntry(0);
            b = solution.getEntry(1);
            c = solutionY.getEntry(0);
            d = solutionY.getEntry(1);
            e = solution.getEntry(2);
            f = solutionY.getEntry(2);
            
        } catch(Exception e){
            logMessage(" Error: " + e.toString());
        }
    }//GEN-LAST:event_jB_Process_ActionPerformed

    private void jB_Move_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Move_ActionPerformed
        
        if (jRB_resultsTable.isSelected()){
            List<Point>  pointList = new ArrayList<Point>();
            ResultsTable rt = Analyzer.getResultsTable();
            int nResults = rt.getCounter();
            
            pointList.clear();
            logMessage("Sending points to CRIO ------");
            for (int i=0; i< nResults; i++) {
                double x = rt.getValue("X", i); //affiche la valeur de x
                double y = rt.getValue("Y",i); // affiche la valeur de y
                logMessage("#X: " + String.valueOf(x) + " #Y: " + String.valueOf(y));
                Point p = new Point((int)(a*x+b*y+e), (int)(c*x+d*y+f));
                pointList.add(p);
            }
            crio.sendPositionList(pointList);
            logMessage("End-----");
        }
    }//GEN-LAST:event_jB_Move_ActionPerformed
    
    private void getStatus(){
        statusThreadIsOn = !statusThreadIsOn;
        
        Thread test = new Thread(new Runnable() { // bouton Start (thread)
            @Override public void run() {
                if (statusThreadIsOn) crio.sendMessage("000018GET SCANNER STATUS");
                while (statusThreadIsOn) {
                    jB_connectToRio.setVisible(false);
                    String s = crio.readMessage();
                    if (jCheckBox_showFlux.isSelected()) logMessage(" --Received -- ");
                    if (jCheckBox_showFlux.isSelected()) logMessage(s);
                    if (s.contains("SCANNING")) {
                        isBeamOn=true;
                        jLabel_status.setText("Status : ON");
                        jB_StartStop.setForeground(Color.red);
                        jB_StartStop.setText("Stop");
                    }
                    else if (s.contains("SCAN OFF")) {
                        isBeamOn=false;
                        jLabel_status.setText("Status : OFF");
                        jB_StartStop.setForeground(Color.black);
                        jB_StartStop.setText("Start");
                    }
                    else jLabel_status.setText("Status : ??");
                }
            }
        });
        test.start();
        
        if (statusThreadIsOn) logMessage("-- Demarrage du thread - Get Status");
        else {
            
            logMessage("-- Fin du Thread - Get Status");
            statusThreadIsOn=false;
        }
    }
    private void jRB_resultsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_resultsTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRB_resultsTableActionPerformed

    private void jB_StartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_StartStopActionPerformed
        if (jB_StartStop.getText().equals("Start")) {
            crio.startScan();
            jB_StartStop.setForeground(Color.red);
            jB_StartStop.setText("Stop");
        }
        else {
            crio.stopScan();
            jB_StartStop.setForeground(Color.black);
            jB_StartStop.setText("Start");
        }
    }//GEN-LAST:event_jB_StartStopActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jB_connectToRio.setVisible(true);
        crio.exit();
        statusThreadIsOn=false;
        isBeamOn=false;
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jB_connectToRioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_connectToRioActionPerformed
       try{
           
       Boolean isInitialized=crio.initialize();
       if (isInitialized) getStatus();
       }
       catch (IOException ioe){
           
       }
    }//GEN-LAST:event_jB_connectToRioActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jB_Acquire_;
    private javax.swing.JButton jB_Move_;
    private javax.swing.JButton jB_Process_;
    private javax.swing.JButton jB_StartStop;
    private javax.swing.JButton jB_connectToRio;
    private javax.swing.JCheckBox jCheckBox_showFlux;
    private javax.swing.JLabel jLab_Beam_Energy_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jP_Calibration_;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Status;
    private javax.swing.JRadioButton jRB_ROImanager;
    private javax.swing.JRadioButton jRB_resultsTable;
    private javax.swing.JTextField jTF_BeamEnergy_;
    private javax.swing.JTextField jTF_Delay_;
    private javax.swing.JTextField jTF_ImageSize_;
    private javax.swing.JTextField jTF_ImageTreshold_;
    // End of variables declaration//GEN-END:variables
}