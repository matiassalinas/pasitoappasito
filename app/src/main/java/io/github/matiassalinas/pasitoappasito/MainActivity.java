package io.github.matiassalinas.pasitoappasito;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static io.github.matiassalinas.pasitoappasito.R.string.ingresaNombreVacio;

public class MainActivity extends AppCompatActivity {
    private static TextView txtV;
    private static EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtV=(TextView)findViewById(R.id.txtHello);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Leaner_Regular.ttf");
        txtV.setTypeface(face);

        Button btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editNombre = (EditText) findViewById(R.id.editNombre);
                //editNombre.getText();
                String nombreUsuario = editNombre.getText().toString();
                Log.i("TEXT",nombreUsuario);
                if(nombreUsuario.length() == 0){
                    editNombre.setError(getString(R.string.ingresaNombreVacio));
                }
                else if(nombreUsuario.length() < 3){
                    editNombre.setError(getString(R.string.ingresaNombreCorto));
                }
                else{
                    Log.d("OK","HA INGRESADO CORRECTAMENTE EL NOMBRE " + nombreUsuario);
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.putExtra("NOMBRE",nombreUsuario);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
