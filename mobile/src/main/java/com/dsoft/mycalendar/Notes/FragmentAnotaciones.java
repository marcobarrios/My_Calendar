package com.dsoft.mycalendar.Notes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Marco Barrios on 08/10/2014.
 */
public class FragmentAnotaciones extends Fragment {
    View v;
    FloatingActionButton btn;
    protected static final int REQUEST_CODE = 1;

    private ArrayAdapter<Anotacion> adapter;
    private ListView notesListView;
    private AnotacionReceiver receiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_view_anotaciones, container, false);
        inicializarComponentes();
        inicializarListView();
        return v;
    }

    public void inicializarComponentes() {
        notesListView = (ListView)v.findViewById(R.id.listView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listEvents, View view, int position, long id) {

                }
            });

        btn = (FloatingActionButton)v.findViewById(R.id.fab_anotacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getActivity().getApplicationContext(), ActivityAnotacion.class);
                //i.putExtra("fecha", String.valueOf(calendario.getDate()));
                startActivityForResult(i, REQUEST_CODE);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                String title = data.getStringExtra("titulo");
                String descripcion = data.getStringExtra("descripcion");
                agregarContacto(title, descripcion, "20/10/2014");
            }
        }catch(Exception exp) {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_btn_eliminar);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
