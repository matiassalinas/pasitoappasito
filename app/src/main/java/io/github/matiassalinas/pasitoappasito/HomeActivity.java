package io.github.matiassalinas.pasitoappasito;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HomeActivity extends AppCompatActivity {

    private static Bundle storage;
    private static String name;
    private static ArrayList<Actividad> items;
    private static GridView gridView;
    private static Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getName();
        setList();

    }

    protected void getName(){
        storage = getIntent().getExtras();
        if(storage!=null){

            name = storage.getString("NOMBRE");
            TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
            Log.d("NOMBRE",name);
            txtNombre.setText(getString(R.string.hola2) + " " + name);

        }
    }

    protected void setList(){
        gridView = (GridView) findViewById(R.id.gridViewAct);
        if(getList("actividades.xml")){
            Log.d("ACTIVIDADESXML","EXISTE");
            adapter = new Adapter(this,items);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(),String.valueOf(items.get(position).getPasos().size()), Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(HomeActivity.this, PasosActivity.class);
                    //mIntent.putExtra("actividad", items.get(position));
                    //mIntent.putExtra("pasos", items.get(position).getPasos());
                    mIntent.putExtra("ID",items.get(position).getId());
                    startActivity(mIntent);
                }
            });


        }
        else{
            Log.e("SIN ACTIVIDADES","NO EXISTEN ACTIVIDADES");
        }

    }

    protected boolean getList(String xml){
        try {
            items = new ArrayList<>();
            InputStream is = getAssets().open(xml);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("actividad");
            if(nList.getLength()==0) return false;
            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    /*
                    ArrayList<Paso> pasos = new ArrayList<>();
                    NodeList nList2 = element2.getElementsByTagName("paso");
                    //Log.d("PASOS", String.valueOf(nList2.getLength()));
                    for(int j=0;j<nList2.getLength();j++){
                        Node node2 = nList2.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE){
                            Element element3 = (Element) node2;
                            Paso paso = new Paso(Integer.parseInt(getValue("id",element3)),
                                                getValue("texto",element3),
                                                getValue("img",element3),
                                                getValue("sonido",element3));
                            pasos.add(paso);
                            //Log.d("TEXTO",getValue("texto",element3));
                        }
                    }
                    */
                    Actividad act = new Actividad(Integer.parseInt(getValue("id",element2)),
                                                getValue("nombre",element2),
                                                getValue("img",element2));
                    items.add(act);

                }

            }
            /*
                Ordenar la lista de actividades según el nombre
             */
            Comparator<Actividad> comparator = new Comparator<Actividad>() {
                @Override
                public int compare(Actividad left, Actividad right) {
                    return left.getNombre().compareTo(right.getNombre()); // use your logic
                }
            };
            Collections.sort(items,comparator);
            return true;

        } catch (Exception e) {e.printStackTrace(); return false;}
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}
