import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.micromanager.api.MMPlugin;
import org.micromanager.api.ScriptInterface;

/**
 *
 * @author admin
 */
public class MicroIrradiation implements MMPlugin {

    /**
     *
     */
    public static String menuName = "Micro Irradiation";

    /**
     *
     */
    public static String tooltipDescription = "This Micro-Manager plugin is used to perform microbeam irradiation. ";
   private CMMCore core_;
   private ScriptInterface gui_;
   private IrradiationFrame myFrame_;

    /**
     *
     * @param app
     */
    public void setApp(ScriptInterface app) {
      gui_ = app;                                        
      core_ = app.getMMCore();
      if (myFrame_ == null)
        try {
            //myFrame_ = new SetupFrame(gui_);
            myFrame_ = new IrradiationFrame(gui_);
        } catch (IOException ex) {
        Logger.getLogger(MicroIrradiation.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      myFrame_.setVisible(true);
      
      // Used to change the background layout of the form.  Does not work on Windows
      gui_.addMMBackgroundListener(myFrame_);
   }

    /**
     *
     */
    @Override
   public void dispose() {
      myFrame_.exit();
   }

    /**
     *
     */
    @Override
    public void show() {
   }

    /**
     *
     */
    public void configurationChanged() {
   }

    /**
     *
     * @return
     */
    @Override
   public String getInfo () {
      return "Micro Irradiation plugin";
   }

    /**
     *
     * @return
     */
    @Override
   public String getDescription() {
      return tooltipDescription;
   }
   
    /**
     *
     * @return
     */
    @Override
   public String getVersion() {
      return "1.0";
   }
   
    /**
     *
     * @return
     */
    @Override
   public String getCopyright() {
      return "University of Bordeaux, 2018-2021";
   }
    
}
