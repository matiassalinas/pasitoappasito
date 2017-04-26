package io.github.matiassalinas.pasitoappasito;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PasosActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private int idActividad;

    private static ArrayList<Paso> pasos;

    private static boolean firstPage;

    private int posActual;

    private static MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);
        firstPage = false;
        idActividad = (int) getIntent().getExtras().get("ID");
        if(getPasos("actividades.xml")){
            Log.d("OK","OKK "+ pasos.size());
            for(int i=0;i<pasos.size();i++){
                Log.d("Paso",pasos.get(i).getTexto());
            }
        }

        Log.d("PASOS", String.valueOf(pasos.size()));
        //Log.d("actividad", String.valueOf(actividad.getPasos().size()));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                posActual = position;
                setSound(position,getBaseContext());
            }
        });
        posActual = 0;
        ImageButton soundButton = (ImageButton) findViewById(R.id.soundButton);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLIIICK","CLIIICK");
                setSound(posActual,getBaseContext());
            }
        });








    }

    public boolean getPasos(String xml){
        try {
            pasos = new ArrayList<>();
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
                    if(Integer.parseInt(getValue("id",element2)) == idActividad){
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
                        return true;
                    }

                }

            }

            return false;

        } catch (Exception e) {e.printStackTrace(); return false;}
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public static void setSound(int pos, Context context){
        Log.d("PLAYERSOUND", String.valueOf(player));
        if(player!=null){
            player.release();
            player = null;
        }
        player = new MediaPlayer();
        try {
            AssetFileDescriptor afd = pasos.get(pos).getSonido(context);
            Log.d("POS", String.valueOf(pos));
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pasos, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.txtPaso);
            int pos = getArguments().getInt(ARG_SECTION_NUMBER)-1;
            textView.setText(pasos.get(pos).getTexto());
            ImageView imgView = (ImageView) rootView.findViewById(R.id.imgPaso);
            try {
                imgView.setImageDrawable(pasos.get(pos).getImagen(this.getContext()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!firstPage){
                firstPage = true;
                setSound(pos,this.getContext());
            }

            return rootView;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {
                Log.d("HOLAA","HOLAA");
            }
            else {
            }
        }
    }





    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return pasos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
