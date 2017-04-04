package io.github.matiassalinas.pasitoappasito;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by matias on 31-03-17.
 */

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<Actividad> actividad;
    ArrayList<Actividad> allActividad;

    public Adapter(Context context, ArrayList<Actividad> actividad){
        this.context = context;
        this.actividad = actividad;
        this.allActividad = actividad;
    }

    @Override
    public int getCount() {
        return this.actividad.size();
    }

    @Override
    public Object getItem(int position) {
        return actividad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.model_grid_view, parent, false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.imgModel);
        TextView textView = (TextView) convertView.findViewById(R.id.txtViewModel);
        InputStream inputstream= null;
        try {
            inputstream = context.getAssets().open("images/"
                    +actividad.get(position).getPrincipalImage());
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            img.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setText(actividad.get(position).getNombre());

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        actividad.clear();
        if (charText.length() == 0) {
            actividad.addAll(allActividad);
        } else {
            for (Actividad act : allActividad) {
                if (act.getNombre().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    actividad.add(act);
                }
            }
        }
        notifyDataSetChanged();
    }
}
