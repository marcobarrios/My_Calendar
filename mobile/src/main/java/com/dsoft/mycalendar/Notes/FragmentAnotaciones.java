package com.dsoft.mycalendar.Notes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentAnotaciones extends Fragment {
    View v;
    FloatingActionButton btn;
    private EditText txtTitulo, txtDescripcion;
    protected static final int REQUEST_CODE = 10;

    private ArrayAdapter<Anotacion> adapter;
    private ListView notesListView;
    private AnotacionReceiver receiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_anotaciones, container, false);
        inicializarComponentes();
        inicializarListView();
        inicializarTabs();
        return v;
    }

    public void inicializarComponentes() {
        txtTitulo = (EditText)v.findViewById(R.id.cmpTitulo);
        txtDescripcion = (EditText)v.findViewById(R.id.cmpDescripcion);
        notesListView = (ListView)v.findViewById(R.id.listView);

        btn = (FloatingActionButton)v.findViewById(R.id.fab_anotacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i =  new Intent(getActivity().getApplicationContext(), ActivityAnotacion.class);
                //i.putExtra("fecha", String.valueOf(calendario.getDate()));
                //startActivity(i);
                agregarContacto(
                        txtTitulo.getText().toString(),
                        txtDescripcion.getText().toString(),
                        "29/10/2014"
                );
                String msj = "Se ha agregado a la lista: " + txtTitulo.getText();
                Toast.makeText(view.getContext(), msj, Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        });
    }

    private void agregarContacto(String titulo, String descripcion, String fecha) {
        Anotacion nuevo = new Anotacion(titulo, descripcion, fecha);
        adapter.add(nuevo);
    }

    private void inicializarListView() {
        adapter = new AnotacionListAdapter(getActivity(), new ArrayList<Anotacion>());
        notesListView.setAdapter(adapter);
    }

    private void inicializarTabs() {
        TabHost tabHost = (TabHost)v.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec  spec = tabHost.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Nueva");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Notas");
        tabHost.addTab(spec);
    }

    private void limpiarCampos() {
        txtTitulo.getText().clear();
        txtDescripcion.getText().clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                String result = data.getStringExtra("result");
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception exp) {

        }
    }
}
