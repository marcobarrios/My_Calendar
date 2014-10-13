package com.dsoft.mycalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.faizmalkani.floatingactionbutton.FloatingActionButton;

/**
 * Created by Marco Barrios on 12/10/2014.
 */
public class ActivityEvento extends Activity {
    private ActionBar supportActionBar;
    FloatingActionButton btn;
    private static final int OK_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_evento);
        ActionBar actionbar = this.getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Bundle reicieveParams = getIntent().getExtras();
        TextView texto = (TextView)findViewById(R.id.texto_fecha);
        texto.setText(reicieveParams.getString("fecha"));

        btn = (FloatingActionButton)findViewById(R.id.retornar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnParams();
            }
        });
    }

    protected void returnParams() {
        Intent intent = new Intent();
        intent.putExtra("result", "Retornar Fecha");
        setResult(OK_RESULT_CODE, intent);
        finish();
    }
}
