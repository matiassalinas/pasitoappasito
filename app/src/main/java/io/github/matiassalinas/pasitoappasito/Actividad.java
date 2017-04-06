package io.github.matiassalinas.pasitoappasito;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by matias on 31-03-17.
 */

public class Actividad{
    private int id;
    private String nombre;
    private String img;

    public Actividad(int id, String nombre, String img){
        this.id = id;
        this.nombre = nombre;
        this.img = img;
    }

    protected Actividad(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
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

    public String getImg(){ return img; }

}
