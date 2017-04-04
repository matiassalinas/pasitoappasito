package io.github.matiassalinas.pasitoappasito;

import java.util.ArrayList;

/**
 * Created by matias on 31-03-17.
 */

public class Actividad {
    private int id;
    private String nombre;
    private ArrayList<Paso> pasos;

    public Actividad(int id, String nombre, ArrayList pasos){
        this.id = id;
        this.nombre = nombre;
        this.pasos = pasos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<Paso> pasos) {
        this.pasos = pasos;
    }

    public String getPrincipalImage(){
        String img = new String();
        if(pasos.size()!=0){
            img = pasos.get(pasos.size()-1).getImagen(); //La última imagen es cuando ya está listo, por lo que representa a la actividad.
        }
        return img;
    }


}
