package com.dsoft.mycalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Marco Barrios on 12/10/2014.
 */
public class ActivityEvento extends Activity {
    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_evento);

        ActionBar actionbar = this.getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
}
