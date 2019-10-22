/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifyorders;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 *
 * @author Alejandro
 */
public class ClassifyOrders {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*try{
            
            m.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            
        }catch(Exception ex){
        } */ 
        try{
           JFrame.setDefaultLookAndFeelDecorated(true); //que nos permite dejar a Substance la decoracion ( por asi decirlo) 
           SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.MistAquaSkin"); // Setencia que aplica el skin Creme de Substance
           
           SubstanceLookAndFeel.setCurrentTheme( "org.jvnet.substance.theme.SubstanceAquaTheme" ); // Se aplica el tema Aqui de Substance
           SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermar");//Ejemplo de aplicacion de un watermark de Substance
       } catch (Exception e){
           e.printStackTrace();
       }
        
        Main m = new Main();
        m.show(true);
    }
    
}
