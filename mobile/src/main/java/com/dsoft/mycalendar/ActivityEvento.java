package com.dsoft.mycalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dsoft.mycalendar.Interfaces.OnDateSelected;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

/**
 * Created by Marco Barrios on 12/10/2014.
 */
public class ActivityEvento extends Activity implements OnDateSelected {
    private ActionBar supportActionBar;
    FloatingActionButton btn;
    Button inicio, fin;
    private static final int OK_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_evento);
        ActionBar actionbar = this.getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //Bundle reicieveParams = getIntent().getExtras();
        //TextView texto = (TextView)findViewById(R.id.texto_fecha);
        //texto.setText(reicieveParams.getString("fecha"));

        btn = (FloatingActionButton)findViewById(R.id.retornar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnParams();
            }
        });

        inicio = (Button)findViewById(R.id.fecha_inicio);
        fin = (Button)findViewById(R.id.fecha_fin);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    protected void returnParams() {
        Intent intent = new Intent();
        intent.putExtra("result", "Evento Guardado");
        setResult(OK_RESULT_CODE, intent);
        finish();
    }

    @Override
    public void onDateSelected(String date) {
        ////Retorno de la fecha
        Toast.makeText(this,date,Toast.LENGTH_SHORT).show();
    }
}
