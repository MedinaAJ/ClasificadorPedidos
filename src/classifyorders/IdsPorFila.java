/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifyorders;

/**
 *
 * @author Alejandro
 */
public class IdsPorFila {
    private Double id_pedido;
    private String filas;
    
    public IdsPorFila(Double id_pedido){
        this.id_pedido = id_pedido;
        this.filas = "";
    }
    
    public void addFila(int f){
        if(this.filas.equals("")){
            this.filas += f + " ";
        }else{
            this.filas += ", "+f;
        }
    }
    
    public String getFilas(){
        return this.filas;
    }
    
    public Double getId(){
        return this.id_pedido;
    }
    
    public String gestorID(){
        if(filas.split(",").length > 1)
            return filas;
        else
            return "";
    }
    
    public String toString(){
        return "ID pedido: " + this.id_pedido + " filas: " + this.filas;
    }
}
