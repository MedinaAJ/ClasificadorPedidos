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
public class Retal {
    private String densidad;
    private int alto;
    private int ancho;
    private int grosor;

    public Retal(String densidad, int alto, int ancho, int grosor) {
        this.densidad = densidad;
        this.alto = alto;
        this.ancho = ancho;
        this.grosor = grosor;
    }

    public String getDensidad() {
        return densidad;
    }

    public void setDensidad(String densidad) {
        this.densidad = densidad;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getGrosor() {
        return grosor;
    }

    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }

    @Override
    public String toString() {
        return "Retal{" + "densidad=" + densidad + ", alto=" + alto + ", ancho=" + ancho + ", grosor=" + grosor + '}';
    }
}
