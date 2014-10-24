package com.dsoft.mycalendar.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dsoft.mycalendar.Dialogs.DatePickerFragment;
import com.dsoft.mycalendar.Dialogs.TimePickerFragment;
import com.dsoft.mycalendar.Interfaces.OnDateSelected;
import com.dsoft.mycalendar.Interfaces.OnTimeSelected;
import com.dsoft.mycalendar.Objects.EventItem;
import com.dsoft.mycalendar.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Marco Barrios on 12/10/2014.
 */
public class ActivityEvento extends Activity implements OnDateSelected, OnTimeSelected {

    private ActionBar supportActionBar;

    FloatingActionButton btn_save_event;
    Button btn_start_date;
    Button btn_end_date;
    Button btn_start_time;
    Button btn_end_time;
    Button btn_mail_calendar;
    Switch swt_is_all_day;

    EditText edt_title;
    EditText edt_description;

    Boolean fly_btn_date;
    Boolean fly_btn_time;


    private static final int OK_RESULT_CODE = 1;
    private static final Uri EVENT_URI = CalendarContract.Events.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crear_evento);

        ActionBar actionbar = this.getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        fly_btn_date = true;
        fly_btn_time = true;

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.MyCalendar_color_darker);
        */

        //Bundle reicieveParams = getIntent().getExtras();
        //TextView texto = (TextView)findViewById(R.id.texto_fecha);
        //texto.setText(reicieveParams.getString("fecha"));

        btn_save_event = (FloatingActionButton) findViewById(R.id.retornar);
        btn_save_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventItem event = getEvent();
                addEvent(getApplicationContext(), event.getTitle(),event.getDescription(),"",event.getDtStart(),event.getDtEnd());
                returnParams();
            }
        });

        btn_start_date = (Button) findViewById(R.id.fecha_inicio);
        btn_end_date = (Button) findViewById(R.id.fecha_fin);

        btn_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                fly_btn_date = true;
            }
        });

        btn_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                fly_btn_date = false;
            }
        });

        btn_start_time = (Button) findViewById(R.id.tiempo_inicio);
        btn_end_time = (Button) findViewById(R.id.tiempo_fin);

        btn_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fly_btn_time = true;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        btn_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fly_btn_time = false;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        btn_mail_calendar = (Button) findViewById(R.id.btn_correo_calendario);
        btn_mail_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrar Dialog de cunetas asociadas.
                //DialogFragment newFragment = new TimePickerFragment();
                //newFragment.show(getFragmentManager(), "timePicker");
            }
        });


        swt_is_all_day = (Switch) findViewById(R.id.es_todo_el_dia);
        swt_is_all_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btn_start_time.setEnabled(false);
                    btn_end_time.setEnabled(false);
                    men("Activado");
                } else {
                    btn_start_time.setEnabled(true);
                    btn_end_time.setEnabled(true);
                    men("Desactivado");
                }
            }
        });

        edt_title = (EditText) findViewById(R.id.titulo_evento);
        edt_description = (EditText) findViewById(R.id.texto_evento);
        iniciarPlantilla();
    }


    public void men(String m) {
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void returnParams() {
        Intent intent = new Intent();
        intent.putExtra("result", "Evento Guardado");
        setResult(OK_RESULT_CODE, intent);
        finish();
    }

    /*
        Get Date Selected into DatePickerDialog
     */
    @Override
    public void onDateSelected(String date) {
        ////Retorno de la fecha
        String data_month_day []= date.split("-");
        String list_months [] = getResources().getStringArray(R.array.months_of_year);



        if(fly_btn_date) {
            btn_start_date.setText(data_month_day[2].concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_start_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_start_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
            btn_end_date.setText(new String(data_month_day[2]).concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_end_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_end_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
        }else {
            btn_end_date.setText(new String(data_month_day[2]).concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_end_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_end_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
        }
    }

    /*
        Get Time Selected into timePickerDialog
     */
    @Override
    public void onTimeSelected(String time) {

        String data [] = time.split(":");
        int hour = Integer.parseInt(data[0]);
        int minute = Integer.parseInt(data[1]);

        if(fly_btn_time) {
            btn_start_time.setText(time);
            btn_start_time.setTag(R.id.tag_first,hour);
            btn_start_time.setTag(R.id.tag_second,minute);
            btn_end_time.setText(time);
            btn_end_time.setTag(R.id.tag_first,hour);
            btn_end_time.setTag(R.id.tag_second,minute);
        }else {
            btn_end_time.setText(time);
            btn_end_time.setTag(R.id.tag_first,hour);
            btn_end_time.setTag(R.id.tag_second,minute);
        }

    }

    public void iniciarPlantilla()
    {
        int month = 0;
        int day = 0;
        int hour = 0;
        String minute = "0";
        String list_months [] = null;

        Calendar calendar = Calendar.getInstance();
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        Time time = new Time();
        time.setToNow();
        hour = time.hour;
        list_months = getResources().getStringArray(R.array.months_of_year);

        btn_start_date.setText(new String(String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_start_date.setTag(R.id.tag_first,month);
        btn_start_date.setTag(R.id.tag_second,day);

        btn_end_date.setText(new String(String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_end_date.setTag(R.id.tag_first, month);
        btn_end_date.setTag(R.id.tag_second,day);


        btn_start_time.setText(new String(String.valueOf(hour)).concat(" : ").concat(minute));
        btn_start_time.setTag(R.id.tag_first,hour);
        btn_start_time.setTag(R.id.tag_second,Integer.parseInt(minute));
        btn_end_time.setText(new String(String.valueOf(hour)).concat(" : ").concat(minute));
        btn_end_time.setTag(R.id.tag_first,hour);
        btn_end_time.setTag(R.id.tag_second,Integer.parseInt(minute));

        btn_mail_calendar.setText(getAccounts());
        btn_mail_calendar.setTag(getAccounts());
    }

    /**Create event - Add an event to our calendar
     * @param dtstart Event start time (in millis)
     * @param dtend Event end time (in millis)
     */
    public void addEvent(Context ctx, String title, String description, String location,
                                long dtstart, long dtend) {
        ContentResolver cr = ctx.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.TITLE, title);
        cv.put(CalendarContract.Events.DTSTART, dtstart);
        cv.put(CalendarContract.Events.DTEND, dtend);
        cv.put(CalendarContract.Events.EVENT_LOCATION, location);
        cv.put(CalendarContract.Events.DESCRIPTION, description);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Guatemala");
        cr.insert(buildEventUri(), cv);
    }

    /**Builds the Uri for events (as a Sync Adapter)*/
    private Uri buildEventUri() {
        return EVENT_URI
                .buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, (String) btn_mail_calendar.getTag())
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
                        CalendarContract.ACCOUNT_TYPE_LOCAL)
                .build();
    }

    private EventItem getEvent()
    {
        long startMillis = 0;
        long endMillis = 0;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2014, (Integer)btn_start_date.getTag(R.id.tag_first), (Integer)btn_start_date.getTag(R.id.tag_second), (Integer)btn_start_time.getTag(R.id.tag_first), (Integer)btn_start_time.getTag(R.id.tag_second));
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2014,(Integer)btn_end_date.getTag(R.id.tag_first), (Integer)btn_end_date.getTag(R.id.tag_second), (Integer)btn_end_time.getTag(R.id.tag_first), (Integer)btn_end_time.getTag(R.id.tag_second));
        endMillis = endTime.getTimeInMillis();
        return new EventItem(edt_title.getText().toString(),edt_description.getText().toString(),"Guatemala",startMillis,endMillis);
    }

    /**
     * Get All Account available in Phone and add into list
     */
    private String getAccounts() {
        int counter = 0;
        final AccountManager accManager = AccountManager
                .get(this);
        final Account accounts[] = accManager.getAccountsByType("com.google");
        String temporal = accounts[0].name;
        for (int i = 0; i < accounts.length; i++) {
            Toast.makeText(this, "Name " + accounts[i].name,Toast.LENGTH_SHORT).show();
        }
        return temporal;
    }


}
