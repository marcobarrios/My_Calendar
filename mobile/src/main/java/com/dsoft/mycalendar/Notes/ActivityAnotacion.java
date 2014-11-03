package com.dsoft.mycalendar.Notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dsoft.mycalendar.R;

/**
 * Created by Marco Barrios on 15/10/2014.
 */
public class ActivityAnotacion extends Activity {

    EditText txtTitulo, txtDescripcion;
    Button btnAceptar, btnCancelar;
    private static final int OK_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_nota);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        txtTitulo = (EditText)findViewById(R.id.titulo_nota);
        txtDescripcion = (EditText)findViewById(R.id.descripcion_nota);

        btnAceptar = (Button)findViewById(R.id.btn_aceptar_nota);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnParams();
            }
        });

        btnCancelar = (Button)findViewById(R.id.btn_cancelar_nota);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void returnParams() {
        Intent intent = new Intent();
        intent.putExtra("titulo", txtTitulo.getText().toString());
        intent.putExtra("descripcion",txtDescripcion.getText().toString());
        setResult(OK_RESULT_CODE, intent);
        finish();
    }
}
