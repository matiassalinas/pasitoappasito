package io.github.matiassalinas.pasitoappasito;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by matias on 31-03-17.
 */

public class Paso implements Serializable {
    private int id;
    private String texto;
    private String imagen;
    private String sonido;

    public Paso(int id, String texto, String imagen, String sonido){
        this.id = id;
        this.texto = texto;
        this.imagen = imagen;
        this.sonido = sonido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Drawable getImagen(Context context) throws IOException {
        InputStream inputstream= null;
        String img = "images/"+imagen;
        inputstream = context.getAssets().open(img);
        Drawable drawable = Drawable.createFromStream(inputstream, null);
        return drawable;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getSonido() {
        return sonido;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }

}
