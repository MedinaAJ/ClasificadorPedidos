/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifyorders;

import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class GestorRetales {

    private ArrayList<String> retalesMemoria;
    private final Conectar c;
    
    public GestorRetales(Conectar c) {
        retalesMemoria = new ArrayList();
        this.c = c;
    }

    String mejorRetal(ArrayList<String> posibles_retales, String pieza) {

        int ancho = Integer.parseInt(pieza.split(",")[0]);
        int largo = Integer.parseInt(pieza.split(",")[1]);
        int grosor = Integer.parseInt(pieza.split(",")[2]); // Grosor de la pieza actual
        String densidad = pieza.split(",")[3]; // Densidad de la pieza actual

        int i_ancho, i_largo;
        int mejor_ancho = Integer.MAX_VALUE, mejor_largo = Integer.MAX_VALUE;
        int mejorDif = Integer.MAX_VALUE;
        int actDif;
        String carroRetal = "";
        String IDmejorRetal = "";
        String carro = "";
        String id_retal = "";
        String aux;

        // Datos que necesitamos del nuevo metodo: 
        //    booleano que diga si hemos encontrado un retal o no
        //    String similar a registro estandar segun mi criterio: (Ancho,Alto--Carro--id); en el que se almacena el retal
        Tupla t = buscarMemoria(ancho, largo, grosor, densidad);

        if (!t.isEncontrado()) {
            // Buscamos un retal en base de datos
            for (int i = 0; i < posibles_retales.size(); i++) {
                try {
                    //System.out.println(posibles_retales.get(i));
                    carro = posibles_retales.get(i).split("--")[1];
                    id_retal = posibles_retales.get(i).split("--")[2];
                    aux = posibles_retales.get(i).split("--")[0];

                    i_ancho = Integer.parseInt(aux.split(",")[0]);
                    i_largo = Integer.parseInt(aux.split(",")[1]);

                    actDif = (i_largo - largo) + (i_ancho - ancho);
                    if (actDif < mejorDif) {
                        mejorDif = actDif;
                        carroRetal = carro;
                        IDmejorRetal = id_retal;
                        mejor_ancho = i_ancho;
                        mejor_largo = i_largo;
                    }
                } catch (Exception e) {

                }
            }
            if(!id_retal.equals(""))
                if(c.eliminarRetal(id_retal,ancho, largo, grosor, densidad))
                    System.out.println("Retal utilizado");
                else
                    System.out.println("No se pudo borrar el retal");
        } else {
            System.out.println("Entra a metodo mejorRetal la pieza:");
            System.out.println(pieza);

            System.out.println("Piezas disponibles en memoria: ");
            System.out.println("--------------");
            for (String retal : retalesMemoria) {
                System.out.println(retal);
            }
            System.out.println("--------------");
            System.out.println("Recogemos la pieza de memoria: " + t.getDatosRetal());

            String datos_retal = t.getDatosRetal();
            carroRetal = datos_retal.split("--")[1];
            IDmejorRetal = datos_retal.split("--")[2];
            mejor_ancho = Integer.parseInt(datos_retal.split("--")[0].split(",")[0]);
            mejor_largo = Integer.parseInt(datos_retal.split("--")[0].split(",")[1]);
        }

        // Anyadimos dos nuevas piezas a memoria que mas nos interesen
        if (mejor_ancho != Integer.MAX_VALUE && mejor_largo != Integer.MAX_VALUE) {
            anyadirRestosMemoria(ancho, largo, mejor_ancho, mejor_largo, grosor, densidad, IDmejorRetal, carroRetal);
        }

        return (IDmejorRetal + "-" + carroRetal); // Devuelve el id-carro
    }

    private void anyadirRestosMemoria(int a, int b, int A, int B, int grosor, String densidad, String id_retal, String carro) {
        if (A >= a && B >= b) {
            // Alternativa 1
            int Ap11 = A - a;
            int Bp11 = B;
            String pieza11 = (Ap11 + "," + Bp11 + "," + grosor + "," + densidad + "--" + carro + "--" + id_retal);

            int Ap21 = a;
            int Bp21 = B - b;
            String pieza21 = (Ap21 + "," + Bp21 + "," + grosor + "," + densidad + "--" + carro + "--" + id_retal);

            // Alternativa 2
            int Ap12 = A - a;
            int Bp12 = b;
            String pieza12 = (Ap12 + "," + Bp12 + "," + grosor + "," + densidad + "--" + carro + "--" + id_retal);

            int Ap22 = A;
            int Bp22 = B - b;
            String pieza22 = (Ap22 + "," + Bp22 + "," + grosor + "," + densidad + "--" + carro + "--" + id_retal);

            int aP11 = Ap11 * Bp11;
            int aP21 = Ap21 * Bp21;
            int aP12 = Ap12 * Bp12;
            int aP22 = Ap22 * Bp22;

            //System.out.println("Areas alternativa 1:");
            //System.out.println("Pieza 1 : " + aP11 + "pieza11 " + pieza11);
            //System.out.println("Pieza 2 : " + aP21 + "pieza21 " + pieza21);
            //System.out.println("\nAreas alternativa 2:");
            //System.out.println("Pieza 1 : " + aP12 + "pieza12 " + pieza12);
            //System.out.println("Pieza 2 : " + aP22 + "pieza22 " + pieza22);

            int mejor = aP11;
            boolean alt1 = true;

            if (mejor <= aP21) {
                mejor = aP21;
            } else if (mejor <= aP12) {
                alt1 = false;
                mejor = aP12;
            } else if (mejor <= aP22) {
                alt1 = false;
                mejor = aP22;
            }

            if (alt1) {
                //System.out.println("ALTERNATIVA 1 ELEGIDA");
                if (Ap11 >= 6 && Bp11 >= 6) {
                    System.out.println("Se añade a memoria la pieza1 : "+pieza11);
                    retalesMemoria.add(pieza11);
                }
                if (Ap21 >= 6 && Bp21 >= 6) {
                    System.out.println("Se añade a memoria la pieza2 : "+pieza21);
                    retalesMemoria.add(pieza21);
                }
            } else {
                //System.out.println("ALTERNATIVA 2 ELEGIDA");
                if (Ap12 >= 6 && Bp12 >= 6) {
                    System.out.println("Se añade a memoria la pieza1 : "+pieza12);
                    retalesMemoria.add(pieza12);
                }
                if (Ap22 >= 6 && Bp22 >= 6) {
                    System.out.println("Se añade a memoria la pieza2 : "+pieza22);
                    retalesMemoria.add(pieza22);
                }
            }
        }
    }

    private Tupla buscarMemoria(int ancho, int largo, int grosor, String densidad) {
        // (Ap22 + "," + Bp22 + "," + grosor + "," + densidad + "--" + carro + "--" + id_retal) ;; ArrayList<String> retalesMemoria
        Tupla t = new Tupla(false, "");

        String densidad_retal, id_retal;
        int ancho_retal, largo_retal, grosor_retal;
        int mejorDif = Integer.MAX_VALUE;

        String mejorRetal = "", IDmejorRetal = "";
        int mejor_ancho = 0, mejor_largo = 0;
        boolean encontrado = false;

        String aux = "0,0--0--0";

        for (String retal : retalesMemoria) {
            densidad_retal = retal.split("--")[0].split(",")[3];
            grosor_retal = Integer.parseInt(retal.split("--")[0].split(",")[2]);
            if (grosor_retal == grosor && densidad.equals(densidad_retal)) {
                ancho_retal = Integer.parseInt(retal.split("--")[0].split(",")[0]);
                largo_retal = Integer.parseInt(retal.split("--")[0].split(",")[1]);

                if (ancho_retal >= ancho && largo_retal >= largo) {
                    int actDif = (largo_retal - largo) + (ancho_retal - ancho);
                    if (actDif < mejorDif) {
                        mejorDif = actDif;
                        mejorRetal = retal.split("--")[1];
                        IDmejorRetal = retal.split("--")[2];
                        mejor_ancho = ancho_retal;
                        mejor_largo = largo_retal;
                        encontrado = true;
                        aux = retal;
                    }
                }
            }
        }
        if (encontrado) {
            // (Ancho,Alto--Carro--id)
            String pieza = (mejor_ancho + "," + mejor_largo + "--" + mejorRetal + "--" + IDmejorRetal);
            retalesMemoria.remove(aux);
            t = new Tupla(true, pieza);
        }

        return t;
    }

}
