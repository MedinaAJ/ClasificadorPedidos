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
public class Tupla {
    private boolean encontrado;
    private String id_carro;

    public Tupla(boolean encontrado, String id_carro) {
        this.encontrado = encontrado;
        this.id_carro = id_carro;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public String getDatosRetal() {
        return id_carro;
    }

    public void setDatosRetal(String id_carro) {
        this.id_carro = id_carro;
    }
    
    
}
