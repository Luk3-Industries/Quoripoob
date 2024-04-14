package domain;    

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import java.util.logging.Level;

/**
 * 
 */
public class Log{
    public static String name="Project";
    
    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(name+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
            JOptionPane.showMessageDialog(null, "Se ha presentado un error");
        }catch (Exception oe){
            oe.printStackTrace();
            System.exit(0);
        }
    }
}
    
