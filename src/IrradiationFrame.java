
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.WindowManager;
import ij.text.TextWindow;
import ij.plugin.frame.PlugInFrame;
import ij.plugin.frame.RoiManager;
import ij.process.ByteProcessor;
import ij.process.ShortProcessor;
import ij.ImageStack;
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
import java.io.File;
import java.util.Arrays;
import mmcorej.CMMCore;
import mmcorej.TaggedImage;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.micromanager.api.ScriptInterface;
import org.micromanager.utils.*;
import org.micromanager.SnapLiveManager;
import org.micromanager.MMStudio;
import java.math.BigInteger;

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
   private CMMCore core_;
   public CRionScanner crio;
   
   
  
   //private ImageProcessor imProc;
   private final NumberFormat nf_;
   public double a,b,c,d,e,f;
   //public List<Point>  pointList = new ArrayList<Point>();
   //public List<Point> pointList2 = new ArrayList<Point>();
   
   
   private volatile boolean statusThreadIsOn =false;
   private volatile boolean isBeamOn=false;
   private volatile boolean isMappingOn=false;
   
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
       try {
           crio = new CRionScanner();
           Boolean isInitialized=crio.initialize();
           if (isInitialized) getStatus();
       } catch (Exception e){
           logMessage("No crio " + e.toString());
       }
       
       
       initialize_pattern_list();
       
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
     * Exit the plugin properly
     */
    public void exit(){
        statusThreadIsOn=false;
        crio.stopScan();
        jB_StartStop.setForeground(Color.black);
        jB_StartStop.setText("Start");
        while (isBeamOn){                   
                }
        
    }
    /**
     * Access beam status
     * @return 
     */
    public void setMap(String message){
        char  sy=message.charAt(7);
        char  sx=message.charAt(8);
        int mapWidth=(int)sy;
        int mapHeight=(int)sx;
        logMessage("Map: " + Integer.toString(mapWidth) + " x " + Integer.toString(mapHeight));
        if (mapWidth*mapHeight>0){  
            ShortProcessor ip=new ShortProcessor(mapWidth,mapHeight);
            int index=9;
            for (int i=0;i<mapWidth;i++){
                for (int j=0;i<mapHeight;j++){
                    byte big=(byte)message.charAt(index);
                    byte little=(byte)message.charAt(index+1);
                    byte[] pixel_bytes={big,little};
                    BigInteger pixel=new BigInteger(1,pixel_bytes);
                    //logMessage("Pixel : " + pixel.toString());
                    ip.putPixel(i,j,pixel.intValue());
                    index+=2;
                }
            }
            ImagePlus image = new ImagePlus("Beam", ip);
            image.show();
        }
        
        
        
        
        }
    
    /**
     * Access beam status
     * @return 
     */
    public boolean getBeamStatus(){
        return isBeamOn;
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel_Status = new javax.swing.JPanel();
        jLabel_status = new javax.swing.JLabel();
        jB_connectToRio = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jP_Calibration_ = new javax.swing.JPanel();
        jLab_Beam_Energy_ = new javax.swing.JLabel();
        jTF_BeamEnergy_ = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTF_ImageSize_ = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTF_ImageTreshold_ = new javax.swing.JTextField();
        jB_Acquire_ = new javax.swing.JButton();
        jB_Process_ = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTF_minsize = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jRB_resultsTable = new javax.swing.JRadioButton();
        jRB_ROImanager = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jB_StartStop = new javax.swing.JButton();
        jCB_patterns = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jB_Reload = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTF_patternSizeX = new javax.swing.JTextField();
        jRB_ions = new javax.swing.JRadioButton();
        jRB_time = new javax.swing.JRadioButton();
        jTF_doseFactor = new javax.swing.JTextField();
        jTF_time = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRB_c1 = new javax.swing.JRadioButton();
        jRB_c2 = new javax.swing.JRadioButton();
        jRB_c3 = new javax.swing.JRadioButton();
        jRB_c4 = new javax.swing.JRadioButton();
        jRB_mus = new javax.swing.JRadioButton();
        jRB_ms = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jB_BuildAndSend_ = new javax.swing.JButton();
        jTF_NScans = new javax.swing.JTextField();
        jRB_Center = new javax.swing.JRadioButton();
        jTF_patternSizeY = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTF_offsetX = new javax.swing.JTextField();
        jTF_offsetY = new javax.swing.JTextField();
        jB_Irradiate = new javax.swing.JButton();
        jLabelNzones = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jB_resetNzones = new javax.swing.JButton();
        jRB_fixedTime = new javax.swing.JRadioButton();
        jRB_variableTime = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jTF_command = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jB_send = new javax.swing.JButton();
        jCheckBox_showFlux = new javax.swing.JCheckBox();
        jCB_TSP = new javax.swing.JCheckBox();
        jTF_TSPLoop = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jCB_basic = new javax.swing.JCheckBox();
        jB_GetImage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Microbeam irradiation v06-2021");
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel_status.setText("Status : ??");

        jB_connectToRio.setText("connect to RIO");
        jB_connectToRio.setNextFocusableComponent(jCheckBox_showFlux);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_StatusLayout.setVerticalGroup(
            jPanel_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_status)
                    .addComponent(jB_connectToRio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jP_Calibration_.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jP_Calibration_.setName("jP_Calibration_"); // NOI18N
        jP_Calibration_.setVerifyInputWhenFocusTarget(false);

        jLab_Beam_Energy_.setText("Beam energy (MeV) : ");

        jTF_BeamEnergy_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_BeamEnergy_.setText("3.0");
        jTF_BeamEnergy_.setNextFocusableComponent(jTF_ImageSize_);
        jTF_BeamEnergy_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_BeamEnergy_ActionPerformed(evt);
            }
        });

        jLabel2.setText("Field size (µm) : ");

        jTF_ImageSize_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_ImageSize_.setText("400");
        jTF_ImageSize_.setNextFocusableComponent(jTF_ImageTreshold_);
        jTF_ImageSize_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_ImageSize_ActionPerformed(evt);
            }
        });

        jLabel3.setText("Image treshold : ");

        jTF_ImageTreshold_.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_ImageTreshold_.setText("50");
        jTF_ImageTreshold_.setNextFocusableComponent(jB_Acquire_);
        jTF_ImageTreshold_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_ImageTreshold_ActionPerformed(evt);
            }
        });

        jB_Acquire_.setText("Acquire stack");
        jB_Acquire_.setNextFocusableComponent(jB_Process_);
        jB_Acquire_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Acquire_ActionPerformed(evt);
            }
        });

        jB_Process_.setText("Calibrate stack");
        jB_Process_.setNextFocusableComponent(jRB_resultsTable);
        jB_Process_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_Process_ActionPerformed(evt);
            }
        });

        jLabel8.setText("Min. Size");

        jTF_minsize.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_minsize.setText("20");
        jTF_minsize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_minsizeActionPerformed(evt);
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
                            .addComponent(jLab_Beam_Energy_))
                        .addGap(22, 22, 22)
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTF_BeamEnergy_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTF_ImageSize_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jP_Calibration_Layout.createSequentialGroup()
                                .addComponent(jTF_ImageTreshold_, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTF_minsize, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jP_Calibration_Layout.createSequentialGroup()
                        .addComponent(jB_Acquire_, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Process_, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jP_Calibration_Layout.setVerticalGroup(
            jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_Calibration_Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jP_Calibration_Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLab_Beam_Energy_)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTF_ImageSize_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTF_ImageTreshold_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jTF_minsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTF_BeamEnergy_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jP_Calibration_Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Acquire_)
                    .addComponent(jB_Process_))
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Calibration", jP_Calibration_);
        jP_Calibration_.getAccessibleContext().setAccessibleName("calibration");
        jP_Calibration_.getAccessibleContext().setAccessibleDescription("");
        jP_Calibration_.getAccessibleContext().setAccessibleParent(jTabbedPane1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        buttonGroup1.add(jRB_resultsTable);
        jRB_resultsTable.setSelected(true);
        jRB_resultsTable.setText("...from results table");
        jRB_resultsTable.setNextFocusableComponent(jRB_ROImanager);
        jRB_resultsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_resultsTableActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRB_ROImanager);
        jRB_ROImanager.setText("...from ROI manager");
        jRB_ROImanager.setNextFocusableComponent(jRB_Center);
        jRB_ROImanager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_ROImanagerActionPerformed(evt);
            }
        });

        jLabel1.setText("Get positions ");

        jB_StartStop.setText("Start");
        jB_StartStop.setNextFocusableComponent(jB_connectToRio);
        jB_StartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_StartStopActionPerformed(evt);
            }
        });

        jCB_patterns.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCB_patterns.setNextFocusableComponent(jTF_patternSizeX);
        jCB_patterns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_patternsActionPerformed(evt);
            }
        });

        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Use pattern");
        jRadioButton1.setEnabled(false);

        jB_Reload.setForeground(new java.awt.Color(255, 255, 255));
        jB_Reload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reload.png"))); // NOI18N
        jB_Reload.setBorder(null);
        jB_Reload.setBorderPainted(false);
        jB_Reload.setIconTextGap(0);
        jB_Reload.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jB_Reload.setOpaque(false);
        jB_Reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_ReloadActionPerformed(evt);
            }
        });

        jLabel5.setText("Pattern grid size XY (µm) :");

        jTF_patternSizeX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_patternSizeX.setText("5");
        jTF_patternSizeX.setMaximumSize(new java.awt.Dimension(6, 20));
        jTF_patternSizeX.setNextFocusableComponent(jB_BuildAndSend_);
        jTF_patternSizeX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_patternSizeXActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRB_ions);
        jRB_ions.setSelected(true);
        jRB_ions.setText("Ions / point");

        buttonGroup2.add(jRB_time);
        jRB_time.setText("Time");

        jTF_doseFactor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_doseFactor.setText("1");

        jTF_time.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_time.setText("1000");

        jLabel6.setText("Counter :");

        buttonGroup3.add(jRB_c1);
        jRB_c1.setText("1");

        buttonGroup3.add(jRB_c2);
        jRB_c2.setSelected(true);
        jRB_c2.setText("2");

        buttonGroup3.add(jRB_c3);
        jRB_c3.setText("3");

        buttonGroup3.add(jRB_c4);
        jRB_c4.setText("4");

        buttonGroup4.add(jRB_mus);
        jRB_mus.setSelected(true);
        jRB_mus.setText("µs");

        buttonGroup4.add(jRB_ms);
        jRB_ms.setText("ms");

        jLabel9.setText("Number of scans: ");

        jB_BuildAndSend_.setText("Build position list & send to RIO");
        jB_BuildAndSend_.setNextFocusableComponent(jB_StartStop);
        jB_BuildAndSend_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_BuildAndSend_ActionPerformed(evt);
            }
        });

        jTF_NScans.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_NScans.setText("1");

        buttonGroup1.add(jRB_Center);
        jRB_Center.setText("...center pattern");
        jRB_Center.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_CenterActionPerformed(evt);
            }
        });

        jTF_patternSizeY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_patternSizeY.setText("5");
        jTF_patternSizeY.setMaximumSize(new java.awt.Dimension(6, 20));
        jTF_patternSizeY.setNextFocusableComponent(jB_BuildAndSend_);
        jTF_patternSizeY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTF_patternSizeYActionPerformed(evt);
            }
        });

        jLabel10.setText("Offset XY (µm):");

        jTF_offsetX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_offsetX.setText("0");
        jTF_offsetX.setMaximumSize(new java.awt.Dimension(6, 20));

        jTF_offsetY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_offsetY.setText("0");
        jTF_offsetY.setMaximumSize(new java.awt.Dimension(6, 20));
        jTF_offsetY.setPreferredSize(new java.awt.Dimension(6, 20));

        jB_Irradiate.setText("Irradiate");
        jB_Irradiate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_IrradiateActionPerformed(evt);
            }
        });

        jLabelNzones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNzones.setText("0");

        jLabel11.setText("N irradiations : ");

        jB_resetNzones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reload.png"))); // NOI18N
        jB_resetNzones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_resetNzonesActionPerformed(evt);
            }
        });

        buttonGroup5.add(jRB_fixedTime);
        jRB_fixedTime.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jRB_fixedTime.setForeground(new java.awt.Color(100, 0, 0));
        jRB_fixedTime.setText("Constant speed");

        buttonGroup5.add(jRB_variableTime);
        jRB_variableTime.setSelected(true);
        jRB_variableTime.setText("Pattern multiplier");
        jRB_variableTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRB_variableTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jB_BuildAndSend_)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jB_StartStop))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jB_Irradiate)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelNzones, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_resetNzones, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                                .addGap(20, 20, 20))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRB_ions)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTF_doseFactor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addGap(7, 7, 7)
                                        .addComponent(jRB_c1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRB_c2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRB_c3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRB_c4))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRB_time)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTF_time, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(jRB_mus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRB_ms)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRB_fixedTime)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRB_variableTime))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(10, 10, 10)
                                        .addComponent(jTF_NScans, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRB_resultsTable))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCB_patterns, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_Reload, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRB_ROImanager)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRB_Center))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTF_patternSizeX, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTF_patternSizeY, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTF_offsetX, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTF_offsetY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jRB_resultsTable))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRB_ROImanager)
                        .addComponent(jRB_Center)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton1)
                        .addComponent(jCB_patterns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jB_Reload, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_patternSizeX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_patternSizeY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTF_offsetX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTF_offsetY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRB_ions)
                    .addComponent(jTF_doseFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jRB_c1)
                    .addComponent(jRB_c2)
                    .addComponent(jRB_c3)
                    .addComponent(jRB_c4))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRB_time)
                    .addComponent(jTF_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRB_mus)
                    .addComponent(jRB_ms)
                    .addComponent(jRB_fixedTime)
                    .addComponent(jRB_variableTime))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTF_NScans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jB_Irradiate)
                    .addComponent(jB_BuildAndSend_))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelNzones)
                    .addComponent(jB_resetNzones)
                    .addComponent(jLabel11))
                .addGap(9, 9, 9))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jB_StartStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );

        jRB_ions.getAccessibleContext().setAccessibleDescription("");
        jTF_time.getAccessibleContext().setAccessibleName("");
        jRB_Center.getAccessibleContext().setAccessibleName("jRB_Center");

        jTabbedPane1.addTab("Irradiation", jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel7.setText("Send command : ");

        jB_send.setText(">>");
        jB_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_sendActionPerformed(evt);
            }
        });

        jCheckBox_showFlux.setText("show data flux");

        jCB_TSP.setText("Optimize point List with TSP");
        jCB_TSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_TSPActionPerformed(evt);
            }
        });

        jTF_TSPLoop.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTF_TSPLoop.setText("1000");

        jLabel4.setText("Loop:");

        jCB_basic.setText("Basic mode (100 µs/pt)");
        jCB_basic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_basicActionPerformed(evt);
            }
        });

        jB_GetImage.setText("Get Image");
        jB_GetImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_GetImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCB_basic)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTF_command, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_send))
                    .addComponent(jCheckBox_showFlux)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCB_TSP)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_TSPLoop, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jB_GetImage))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTF_command, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jB_send))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCB_TSP)
                    .addComponent(jTF_TSPLoop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jB_GetImage)
                .addGap(31, 31, 31)
                .addComponent(jCheckBox_showFlux)
                .addGap(18, 18, 18)
                .addComponent(jCB_basic)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Parameters", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.setSelectedIndex(1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jPanel_Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Irradiation");

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
    /**
     * Acquire a stack for beam calibration
     * @param evt 
     */
    private void jB_Acquire_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_Acquire_ActionPerformed
        int fieldSize = Integer.parseInt(jTF_ImageSize_.getText()); // taille du champ d'irradiation ( 400 µm par defaut) 
        float energy = Float.parseFloat(jTF_BeamEnergy_.getText()); // energie ( 3 MeV par defaut)
        List<Point>  pointList = new ArrayList<Point>();
        
        try {
            pointList.clear();           
            float gain = crio.getCRIOcoefficient()*energy;
   
            pointList.add(new Point(-(int)(fieldSize*gain/2),(int)(fieldSize*gain/2))); 
            pointList.add(new Point((int)(fieldSize*gain/2),0));
            pointList.add(new Point(0,-(int)(fieldSize*gain/2)));
            logMessage(" Points: " + pointList.toString());
            
        } catch(Exception error){
            logMessage(" Error : "+ error.toString());
        }
        
        // envoi des pointList au CRio
        try {  
            
            //Take a snap to define the size of image and create a stack with true dimension
            core_.snapImage();
            int width=(int) core_.getImageWidth();
            int height=(int) core_.getImageHeight();
            ImageStack iStack=new ImageStack(width,height);
            
            for(int index=0; index < 3; index++) {               
                //Check if beam is really off
                //while (isBeamOn){                   
                //}
                
                // envoi point par point a partir de la liste "pointList"
                crio.sendPosition(pointList.get(index)); 
                 
                // commence le scan
                crio.startScan();
                 

                //Wait for the beam before going further
                //while (!isBeamOn){                   
                //}
                Thread.sleep(1000);
                core_.snapImage();
                long bitDepth=core_.getImageBitDepth();
                logMessage(Integer.toString((int)bitDepth));
                
                switch ((int)bitDepth) {
                    case 8:
                        byte[] img = (byte[])core_.getImage();
                        width=(int) core_.getImageWidth();
                        height=(int) core_.getImageHeight();
                        ByteProcessor bp = new ByteProcessor(width, height);
                        bp.setPixels(img);
                        iStack.addSlice(Integer.toString(index),bp,index);
                    default:
                        short[] img2 = (short[])core_.getImage();
                        width=(int) core_.getImageWidth();
                        height=(int) core_.getImageHeight();
                        ShortProcessor sp = new ShortProcessor(width, height);
                        sp.setPixels(img2);
                        iStack.addSlice(Integer.toString(index),sp,index);
                }
                // arret scan
                crio.stopScan();              
            }
            
            ImagePlus imp = new ImagePlus("Calibration", iStack);
            imp.show();
            
            
            
            
        } catch (Exception error) {
            logMessage(" Acquisition error : " + error.toString());
            crio.stopScan();
        }
        
    }//GEN-LAST:event_jB_Acquire_ActionPerformed
    /**
     * Process calibration from a stack of 3 beam position
     * @param evt 
     */
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
        String minSize = jTF_minsize.getText();
        
        // application fonction 'Analyse Particles' (imageJ) pour recuperer coordonnees X et Y
        Analyzer.setMeasurement(32,true);
        String sizeParam="size=" + minSize + "-Infinity show=Outlines display clear add stack";
        IJ.run(imp, "Analyze Particles...", sizeParam); 
        ResultsTable rt = Analyzer.getResultsTable();
            
        
        int nResults = rt.getCounter();
        
        for (int i=0; i<nResults; i++) {
            double x = rt.getValue("X",i); //affiche la valeur de x
            double y = rt.getValue("Y",i); // affiche la valeur de y
            int w = i+1;
            logMessage("Point # " +w + ", X = " +x+ ", Y = " +y); // affiche  les coordonnees X et Y dans la console
        }
        
            // etalonnage
            int fieldSize = Integer.parseInt(jTF_ImageSize_.getText()); // taille image ( 400 µm par defaut) 
            float energy = Float.parseFloat(jTF_BeamEnergy_.getText()); // energie ( 3 MeV par defaut)
            float gain = crio.getCRIOcoefficient()*energy;
            int step = (int)(fieldSize*gain/2);
            
        
            RealMatrix coefficients = new Array2DRowRealMatrix(new double[][] { {rt.getValue("X",0), rt.getValue("Y",0),1}, {rt.getValue("X",1),rt.getValue("Y",1),1}, {rt.getValue("X",2), rt.getValue("Y",2),1}},false);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        
            RealVector constants = new ArrayRealVector(new double[] { -step, step,0}, false);
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
            //Writing results in prefs file.
            try{
            Prefs.set("crio.calib.a",a);
            
            Prefs.set("crio.calib.b",b);
            Prefs.set("crio.calib.c",c);
            Prefs.set("crio.calib.d",d);
            Prefs.set("crio.calib.e",e);
            Prefs.set("crio.calib.f",f);
            Prefs.savePreferences();
            logMessage("saving prefs");
            } catch (Exception e){
                logMessage(e.toString());
            }
            crio.set_calib(a, b, c, d, e, f);
            
        } catch(NumberFormatException error){
            logMessage(" Error: " + error.toString());
        } catch (DimensionMismatchException error) {
            logMessage(" Error: " + error.toString());
       } catch (NoDataException error) {
           logMessage(" Error: " + error.toString());
       } catch (NullArgumentException error) {
           logMessage(" Error: " + error.toString());
       } catch (OutOfRangeException error) {
           logMessage(" Error: " + error.toString());
       } catch (SingularMatrixException error) {
           logMessage(" Error: " + error.toString());
       }
    }//GEN-LAST:event_jB_Process_ActionPerformed

    private void jB_BuildAndSend_ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int mode, doseFactor;
        ArrayList<Point>  pointList = new ArrayList<Point>();
        ArrayList<Point> digitalPointList = new ArrayList<Point>();
        
        String path=IJ.getDirectory("imagej")+"mmplugins\\MicroIrradiation\\Patterns\\"+jCB_patterns.getSelectedItem();
        int sizeX = Integer.valueOf(jTF_patternSizeX.getText());
        int sizeY = Integer.valueOf(jTF_patternSizeY.getText());
        int offsetX = Integer.valueOf(jTF_offsetX.getText());
        int offsetY = Integer.valueOf(jTF_offsetY.getText());
        
        //size=crio.MicroscopeToBeam(size);
        Pattern pattern=new Pattern(path,sizeX,sizeY);
        
        pointList.clear();
        digitalPointList.clear();
        
        //Setting scan speed to 0 in order to activate the mode dose/point
        //crio.setScanSpeed(9);
        crio.setnumberOfScan(Integer.valueOf(jTF_NScans.getText()));
        
        //If ion per point irradiation mode is selected
        if (jRB_ions.isSelected()){
            if (jRB_c1.isSelected()) crio.setCounter(1);
            if (jRB_c2.isSelected()) crio.setCounter(2);
            if (jRB_c3.isSelected()) crio.setCounter(3);
            if (jRB_c4.isSelected()) crio.setCounter(4);
            int timeout=Prefs.getInt(".crio.irrad.timeout.ms", 10000);
            crio.setTimeOut(timeout);
            doseFactor = Integer.valueOf(jTF_doseFactor.getText());
            mode=0;
        }
        
        //If time per point irradiation mode is selected
        else {
            if (jRB_ms.isSelected()) crio.setSpeedUnit("ms");
            else if (jRB_mus.isSelected()) crio.setSpeedUnit("µs");
            doseFactor = Integer.valueOf(jTF_time.getText());
            //Removing 9µs from expected time because FPGA requires a minimum of 9 µs for proessing
            if (jRB_mus.isSelected() & doseFactor<10){
                doseFactor=10;
                jTF_time.setText("10");
            }
            if (jRB_mus.isSelected() & doseFactor>9) doseFactor-=9;
            mode=1;
            
        }
        //Getting point list from result table
        
        if (jRB_resultsTable.isSelected()){
                           
            Analyzer analyser=new Analyzer(WindowManager.getCurrentImage());
            analyser.measure();
            //ResultsTable rt=ResultsTable.getActiveTable();
            ResultsTable rt = Analyzer.getResultsTable();
                        
            int nResults = rt.getCounter();
            int AlreadyShot=Integer.valueOf(jLabelNzones.getText());
            jLabelNzones.setText(String.valueOf(AlreadyShot+nResults));
            
            logMessage("Sending points to CRIO from TR------");
            logMessage("N irradiated zones: " + jLabelNzones.getText());
            
            for (int i=0; i< nResults; i++) {
                double x = rt.getValue("X", i); //affiche la valeur de x
                double y = rt.getValue("Y",i); // affiche la valeur de y
                logMessage("#X: " + String.valueOf(x) + " #Y: " + String.valueOf(y));
                Point p = new Point((int)x,(int)y);
                pointList.add(p);
                
               
                
            }
            
            // Sort the list using TSP algorythm
            if (jCB_TSP.isSelected()) pointList=SortUsingTSP(pointList);
            
            //Apply the pattern to the list (microscope coordinates)
            pointList=pattern.applyPattern(pointList);
            //Change to beam coordinates
            pointList=crio.MicroscopeToBeam(pointList, offsetX,offsetY);
            
            //Build a digital position list according to existing list
            //To be tested, not working so far
            for (int index=0;index<pointList.size();index++){
                digitalPointList.add(crio.makeDigital(pointList.get(index).getX(),pointList.get(index).getY()));
            }
            if (IJ.isResultsWindow()){ 
                IJ.selectWindow("Results");
                IJ.run("Close");
            }
            }
            
            
        
        //Getting the point list from the roi manager
        else if (jRB_ROImanager.isSelected()){
            
            RoiManager rm = RoiManager.getInstance();
            if (rm==null) rm = new RoiManager();
            
            rm.runCommand("Measure");
            ResultsTable rt = Analyzer.getResultsTable();
            int nResults = rt.getCounter();
            int AlreadyShot=Integer.valueOf(jLabelNzones.getText());
            jLabelNzones.setText(String.valueOf(AlreadyShot+nResults));
            
            logMessage("Sending points to CRIO from TR------");
            logMessage("N irradiated zones: " + jLabelNzones.getText());
            
            for (int i=0; i< nResults; i++) {
                double x = rt.getValue("X", i); //affiche la valeur de x
                double y = rt.getValue("Y",i); // affiche la valeur de y
                logMessage("#X: " + String.valueOf(x) + " #Y: " + String.valueOf(y));
                Point p = new Point((int)x,(int)y);
                pointList.add(p);
                
            }
            
            if (jCB_TSP.isSelected()) pointList=SortUsingTSP(pointList);
            //Apply the pattern to the list (microscope coordinates)
            logMessage("patterning...");
            pointList=pattern.applyPattern(pointList);
            //Change to beam coordinates
            pointList=crio.MicroscopeToBeam(pointList, offsetX,offsetY);
            
            //Sending digital pointlist - Not working at the moment
            for (int index=0;index<pointList.size();index++){
                digitalPointList.add(crio.makeDigital(pointList.get(index).getX(),pointList.get(index).getY()));
            }
            if (IJ.isResultsWindow()){ 
                IJ.selectWindow("Results");
                IJ.run("Close");
            }
        }
        //To center the pattern
        else if (jRB_Center.isSelected()){
            pointList.add(crio.BeamToMicroscope(crio.GetBeamOrigin()));
            pointList=pattern.applyPattern(pointList);
            pointList=crio.MicroscopeToBeam(pointList, offsetX,offsetY);
            int AlreadyShot=Integer.valueOf(jLabelNzones.getText());
            jLabelNzones.setText(String.valueOf(AlreadyShot+1));
            
            
            //Sending digital pointlist - Not working at the moment
            for (int index=0;index<pointList.size();index++){
                digitalPointList.add(crio.makeDigital(pointList.get(index).getX(),pointList.get(index).getY()));
            }
            
        }
        //if (!jCB_basic.isSelected()) crio.sendPositionList(pointList, digitalPointList,mode, (short)value);
        ArrayList <Integer> doseList=pattern.applyDosePattern(pointList, doseFactor);
        if (jRB_time.isSelected()&jRB_mus.isSelected()) {
            int last=doseList.get(doseList.size()-1)+9;
            doseList.set(doseList.size()-1, last);
        } 
        if (!jCB_basic.isSelected()) crio.sendPositionList(pointList, digitalPointList,mode, doseList);
        
        //Reduced mode, simple point list
        if (jCB_basic.isSelected()) {
            crio.setScanSpeed(100);
            crio.sendPositionList(pointList, digitalPointList);
        }
        //Fixed Time per point, variable time per point
        if (jRB_time.isSelected()&jRB_fixedTime.isSelected()) {
            crio.setScanSpeed(Integer.valueOf(jTF_time.getText()));
        }
        else if (jRB_time.isSelected()&jRB_variableTime.isSelected()) {
            crio.setScanSpeed(9);
        }
        logMessage("End-----");
    }
    public ArrayList<Point> sortLikeAHorse(ArrayList<Point> pointList){
        ArrayList<Point> sortedList=new ArrayList<Point>();
        
        if (pointList.size()>3){
            logMessage("Starting optimization");
            //sortedList.add(minDistanceFrom(0,0));
            
        }
        else logMessage("No optimisation: too few points.");
        return sortedList;
    }
    /**
     * Sort the points to be irradiated using TSP algorythm
     * @param pointList
     * @return a sorted pointList
     */
    public ArrayList<Point> SortUsingTSP(ArrayList<Point> pointList){
        if (pointList.size()>3){
            logMessage("Starting optimization");
        
        //Create and add points
        TourManager.reset();
        for (int index=0;index<pointList.size();index++){
            TourManager.addBeamPoint(new BeamPoint(pointList.get(index)));
        }
        // Initialize population
        Population pop = new Population(pointList.size(), true);
        logMessage("Initial distance: " + pop.getFittest().getDistance());
        
        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        int loop=Integer.valueOf(jTF_TSPLoop.getText());
        for (int i = 0; i < loop; i++) {
            pop = GA.evolvePopulation(pop);
        }
        logMessage("Finished");
        logMessage("Final distance: " + pop.getFittest().getDistance());
        logMessage("Solution:");
        logMessage(pop.getFittest().toString());
        pointList=pop.getFittest().getPointList();
        }
        else logMessage("Too few points to optimize: no TSP");
        return pointList;
    }        
    /**
     * Get live CRIO status
     */
    private void getStatus(){
        statusThreadIsOn = !statusThreadIsOn;
        
        Thread getStatusThread;
       getStatusThread = new Thread(new Runnable() { // bouton Start (thread)
           @Override public void run() {
               if (statusThreadIsOn) crio.sendMessage("000018GET SCANNER STATUS");
               while (statusThreadIsOn) {
                   jB_connectToRio.setVisible(false);
                   String s = crio.readMessage();
                   if (jCheckBox_showFlux.isSelected()) logMessage(" --Received -- ");
                   if (jCheckBox_showFlux.isSelected()) logMessage(s);
                   if (s.startsWith("Image =")) {
                       setMap(s);                      
                   }
                   else if (s.contains("SCANNING")) {
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
        getStatusThread.start();
        
        if (statusThreadIsOn) logMessage("-- Demarrage du thread - Get Status");
        else {
            
            logMessage("-- Fin du Thread - Get Status");
            statusThreadIsOn=false;
            getStatusThread.interrupt();
        }
    }
                                                  
        
    private void jB_StartStopActionPerformed(java.awt.event.ActionEvent evt) {                                             
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
    }                                            
   
    /**
     * Things done when closing the window
     * @param evt 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jB_connectToRio.setVisible(true);
        crio.exit();
        statusThreadIsOn=false;
        isBeamOn=false;
        dispose();
    }//GEN-LAST:event_formWindowClosing
    
    /**
     * Connect to CRIO when connexion was lost
     * Appearance of the button depends on the live status (eg. only when status is unknown)
     * @param evt 
     */
    private void jB_connectToRioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_connectToRioActionPerformed
       try{
           
       Boolean isInitialized=crio.initialize();
       if (isInitialized) getStatus();
       }
       catch (IOException ioe){
           
       }
    }//GEN-LAST:event_jB_connectToRioActionPerformed

    private void jB_ReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_ReloadActionPerformed
        initialize_pattern_list();
    }//GEN-LAST:event_jB_ReloadActionPerformed

    private void jCB_patternsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_patternsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_patternsActionPerformed

//GEN-FIRST:event_jB_StartStopActionPerformed
 
//GEN-LAST:event_jB_StartStopActionPerformed

    private void jRB_resultsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_resultsTableActionPerformed
        //dwfgqsdfg TODO add your handling code here:
    }//GEN-LAST:event_jRB_resultsTableActionPerformed

//GEN-FIRST:event_jB_Move_ActionPerformed
 
//GEN-LAST:event_jB_Move_ActionPerformed

    private void jTF_patternSizeXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_patternSizeXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_patternSizeXActionPerformed

    private void jRB_ROImanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_ROImanagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRB_ROImanagerActionPerformed

    private void jB_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_sendActionPerformed
       String message=jTF_command.getText();
       if (!"".equals(message)) crio.sendMessage(crio.formatMessage(message));
    }//GEN-LAST:event_jB_sendActionPerformed

    private void jTF_minsizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_minsizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_minsizeActionPerformed

    private void jCB_TSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_TSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_TSPActionPerformed

    private void jRB_CenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_CenterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRB_CenterActionPerformed

    private void jTF_patternSizeYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTF_patternSizeYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTF_patternSizeYActionPerformed

    private void jCB_basicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_basicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCB_basicActionPerformed

    private void jB_IrradiateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_IrradiateActionPerformed
        
        
        boolean isLiveOn = gui_.isLiveModeOn();
        if (isLiveOn) gui_.enableLiveMode(!isLiveOn);
        jB_BuildAndSend_.doClick();
        jB_StartStop.doClick();
        gui_.enableLiveMode(isLiveOn);
        

        
    }//GEN-LAST:event_jB_IrradiateActionPerformed

    private void jB_resetNzonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_resetNzonesActionPerformed
        jLabelNzones.setText("0");
    }//GEN-LAST:event_jB_resetNzonesActionPerformed

    private void jRB_variableTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRB_variableTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRB_variableTimeActionPerformed

    private void jB_GetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_GetImageActionPerformed
        crio.sendMessage("000009GET IMAGE");
        
       
       
                    
    }//GEN-LAST:event_jB_GetImageActionPerformed
    
    /**
     * Create the list of existing pattern stored in the folder
     * @param path of the folder
     * @return a list of pattern names
     */
    private ArrayList<String> patternList(String path) {
    ArrayList<String> patterns=new ArrayList<String>();
    File myDir=new File(path);
    File[] myList=myDir.listFiles();

    for(File elementListe : myList)
    {
        if(elementListe.isFile())
        {
           patterns.add(elementListe.getName());
        }
    }
    return patterns;
}
    /**
     * Create a list of available patterns
     */
    private void initialize_pattern_list(){
        String path=IJ.getDirectory("imagej")+"mmplugins\\MicroIrradiation\\Patterns\\";
        ArrayList<String> patterns=patternList(path);
        jCB_patterns.removeAllItems();
        for (int index=0;index<patterns.size();index++){
            jCB_patterns.addItem(patterns.get(index));
        }
        try{
            jCB_patterns.setSelectedItem("point.txt");
        }
        catch (Exception error){
            logMessage(error.toString());
        }
    }
    /**
     * Create the list of existing pattern stored in the folder
     * @param path of the folder
     * @return a list of pattern names
     */
    private ArrayList<String> doseList(String path) {
    ArrayList<String> doses=new ArrayList<String>();
    File myDir=new File(path);
    File[] myList=myDir.listFiles();

    for(File elementListe : myList)
    {
        if(elementListe.isFile())
        {
           doses.add(elementListe.getName());
        }
    }
    return doses;
}

    
    /**
     * Read the selected pattern and store values as an arraylist
     */
    private void readPattern(){
        
    }
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jB_Acquire_;
    private javax.swing.JButton jB_BuildAndSend_;
    private javax.swing.JButton jB_GetImage;
    private javax.swing.JButton jB_Irradiate;
    private javax.swing.JButton jB_Process_;
    private javax.swing.JButton jB_Reload;
    private javax.swing.JButton jB_StartStop;
    private javax.swing.JButton jB_connectToRio;
    private javax.swing.JButton jB_resetNzones;
    private javax.swing.JButton jB_send;
    private javax.swing.JCheckBox jCB_TSP;
    private javax.swing.JCheckBox jCB_basic;
    private javax.swing.JComboBox jCB_patterns;
    private javax.swing.JCheckBox jCheckBox_showFlux;
    private javax.swing.JLabel jLab_Beam_Energy_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNzones;
    private javax.swing.JLabel jLabel_status;
    private javax.swing.JPanel jP_Calibration_;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Status;
    private javax.swing.JRadioButton jRB_Center;
    private javax.swing.JRadioButton jRB_ROImanager;
    private javax.swing.JRadioButton jRB_c1;
    private javax.swing.JRadioButton jRB_c2;
    private javax.swing.JRadioButton jRB_c3;
    private javax.swing.JRadioButton jRB_c4;
    private javax.swing.JRadioButton jRB_fixedTime;
    private javax.swing.JRadioButton jRB_ions;
    private javax.swing.JRadioButton jRB_ms;
    private javax.swing.JRadioButton jRB_mus;
    private javax.swing.JRadioButton jRB_resultsTable;
    private javax.swing.JRadioButton jRB_time;
    private javax.swing.JRadioButton jRB_variableTime;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JTextField jTF_BeamEnergy_;
    private javax.swing.JTextField jTF_ImageSize_;
    private javax.swing.JTextField jTF_ImageTreshold_;
    private javax.swing.JTextField jTF_NScans;
    private javax.swing.JTextField jTF_TSPLoop;
    private javax.swing.JTextField jTF_command;
    private javax.swing.JTextField jTF_doseFactor;
    private javax.swing.JTextField jTF_minsize;
    private javax.swing.JTextField jTF_offsetX;
    private javax.swing.JTextField jTF_offsetY;
    private javax.swing.JTextField jTF_patternSizeX;
    private javax.swing.JTextField jTF_patternSizeY;
    private javax.swing.JTextField jTF_time;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
