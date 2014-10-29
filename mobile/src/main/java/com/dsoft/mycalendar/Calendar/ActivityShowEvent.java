package com.dsoft.mycalendar.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dsoft.mycalendar.Objects.EventItem;
import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by enrique on 26/10/14.
 */
public class ActivityShowEvent extends Activity {

    FloatingActionButton btn_edit_event;
    protected static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mostrar_evento);

        Bundle reicieveParams = getIntent().getExtras();
        final String calendario = reicieveParams.getString("calendario");
        final Long idEvent = reicieveParams.getLong("idEvent");

        EventItem eventSelected = QuerysCalendar.getEventByID(getApplicationContext(),
                calendario,idEvent);

        Toast.makeText(this,"Color:" + eventSelected.getColorEvent(),Toast.LENGTH_SHORT).show();

        btn_edit_event = (FloatingActionButton) findViewById(R.id.fab_editar);
        btn_edit_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityEvento.class);
                i.putExtra("idEvent",idEvent);
                i.putExtra("calendar",calendario);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        if(eventSelected!=null) {
            TextView tx_titulo = (TextView) findViewById(R.id.me_titulo_evento);
            tx_titulo.setText(eventSelected.getTitle());

            TextView tx_fecha = (TextView) findViewById(R.id.me_fecha_evento);
            tx_fecha.setText(getDate(eventSelected.getDtStart()) + "  -  " + getDate(eventSelected.getDtEnd()));

            TextView tx_calendario = (TextView) findViewById(R.id.mv_correo_calendario);
            tx_calendario.setText(eventSelected.getCalendar());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                finish();
            }
        }catch(Exception exp) {

        }
    }

    public String getDate(Long date)
    {
        return new SimpleDateFormat("h:mm a").format(new Date(date));
    }
}
