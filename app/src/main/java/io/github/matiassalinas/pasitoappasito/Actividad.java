package io.github.matiassalinas.pasitoappasito;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by matias on 31-03-17.
 */

public class Actividad implements Parcelable {
    private int id;
    private String nombre;
    private ArrayList<Paso> pasos;

    public Actividad(int id, String nombre, ArrayList pasos){
        this.id = id;
        this.nombre = nombre;
        this.pasos = pasos;
    }

    protected Actividad(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
    }

    public static final Creator<Actividad> CREATOR = new Creator<Actividad>() {
        @Override
        public Actividad createFromParcel(Parcel in) {
            return new Actividad(in);
        }

        @Override
        public Actividad[] newArray(int size) {
            return new Actividad[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
    }
}
