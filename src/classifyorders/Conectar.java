/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifyorders;

/**
 *
 * @author Alejandro Medina Jimenez
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane; 
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultListModel;


public class Conectar { 
    Connection conexion; 
    Statement sentencia;
    //private final String NOMBRE_BASE_DE_DATOS = "C:\\Users\\9alej\\Documents\\NetBeansProjects\\IPO\\MaGesft\\MaGesft\\BD\\BBDDIPO.accdb";
    private final String CLAVE_BASE_DE_DATOS = "";
    private final String USUARIO_BASE_DE_DATOS = "admin";
    private String NOMBRE_BASE_DE_DATOS;
   // private final String USUARIO_BASE_DE_DATOS = "admin";


 public Conectar(){
     NOMBRE_BASE_DE_DATOS = new File("database.accdb").getAbsolutePath();
     System.out.println("RUTA: "+NOMBRE_BASE_DE_DATOS);
 }
 public String getPath(){
     return NOMBRE_BASE_DE_DATOS;
 }
 public void PrepararBaseDatos() { 
        try{ 
            conexion=DriverManager.getConnection("jdbc:ucanaccess://"+this.NOMBRE_BASE_DE_DATOS,this.USUARIO_BASE_DE_DATOS,this.CLAVE_BASE_DE_DATOS);
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null,"Error al realizar la conexion "+e);
        } 
        try { 
            sentencia=conexion.createStatement( 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY); 
            
            System.out.println("Se ha establecido la conexión correctamente");
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null,"Error al crear el objeto sentencia "+e);
        } 
    }
 
 public void desconectar(){
        try {
            conexion.close();            
            System.out.println("La conexion a la base de datos a terminado");
        } catch (SQLException ex) {
            System.out.println( ex.getMessage() );
        }       
   }

    ArrayList<String> searchRetal(String densidad, int ancho, int largo, int grosor) {
        ArrayList<String> posibilidades = new ArrayList();
        String sql = "SELECT * FROM amj_retales WHERE Densidad='"+densidad+"' AND Alto >= "+largo+" AND Ancho >= "+ancho+" AND Grosor = "+grosor+" ;" ;
        
        try{
            PreparedStatement pstm = conexion.prepareStatement( sql );
            ResultSet res = pstm.executeQuery();            
             while(res.next())
             {            
                posibilidades.add((res.getString("Ancho")+","+res.getString("Alto")+"--"+res.getString("Carro")+"--"+res.getString("id")));
             }
            res.close();         
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
        
        return posibilidades;
    }

    boolean eliminarRetal(String id, int ancho, int largo, int grosor, String densidad) {      
        boolean correcto = false;
        boolean eliminado = false;
        String sql = "DELETE FROM amj_retales WHERE id=" + id + ";";
        try {
            PreparedStatement pstm = conexion.prepareCall(sql);
            int res = pstm.executeUpdate();
            pstm.close();
            eliminado = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (eliminado) {
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);

            String d = año + "-" + (mes + 1) + "-" + dia;

            sql = "INSERT INTO amj_retales_borrados_automatico (id_retal, fecha_eliminacion, Info)"
                    + " VALUES (" + id + ", DATE '" + d + "', '"+ancho+"x"+largo+"x"+grosor+"-"+densidad+"');";

            try {
                PreparedStatement pstm = conexion.prepareCall(sql);
                int res1 = pstm.executeUpdate();
                pstm.close();

                correcto =  true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                correcto = false;
            }
        }
        
        return correcto;
    }
}