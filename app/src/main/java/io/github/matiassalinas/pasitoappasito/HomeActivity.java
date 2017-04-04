package io.github.matiassalinas.pasitoappasito;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //startActivity(i);
                    return true;
                case R.id.navigation_favorite:
                    //startActivity(i);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getName();
        setList();

    }

    protected void getName(){
        storage = getIntent().getExtras();
        if(storage!=null){

            name = storage.getString("NOMBRE");
            TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
            Log.d("NOMBRE",name);
            txtNombre.setText("Hola, " + name);

        }
    }

    protected void setList(){
        gridView = (GridView) findViewById(R.id.gridViewAct);
        SearchView search = (SearchView) findViewById(R.id.searchView1);
        if(getList("actividades.xml")){
            Log.d("ACTIVIDADESXML","EXISTE");
            adapter = new Adapter(this,items);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),items.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }
            });

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return false;
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
                    ArrayList<Paso> pasos = new ArrayList<>();
                    NodeList nList2 = element2.getElementsByTagName("paso");
                    for(int j=0;j<nList2.getLength();j++){
                        Node node2 = nList2.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE){
                            Element element3 = (Element) node;
                            Paso paso = new Paso(Integer.parseInt(getValue("id",element3)),
                                                getValue("texto",element3),
                                                getValue("img",element3),
                                                getValue("sonido",element3));
                            pasos.add(paso);
                        }
                    }
                    Actividad act = new Actividad(Integer.parseInt(getValue("id",element2)),
                                                getValue("nombre",element2),
                                                pasos);
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
