package com.dsoft.mycalendar.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marco Barrios on 12/10/2014.
 */
public class ActivityEvento extends Activity implements OnDateSelected, OnTimeSelected {

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

    Boolean is_edition;
    Long idEvent;

    private static final int OK_RESULT_CODE = 1;

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
                if(!is_edition) {
                    QuerysCalendar.addEvent(getApplicationContext(), (String) btn_mail_calendar.getTag(), event.getTitle(),
                            event.getDescription(), "", event.getDtStart(), event.getDtEnd());

                }else
                {
                    //Query Edición
                    QuerysCalendar.updateEvent(getApplicationContext(), (String) btn_mail_calendar.getTag(), idEvent, event.getTitle(),
                            event.getDescription(), "", event.getDtStart(), event.getDtEnd());
                }
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
                Bundle params = new Bundle();
                params.putInt("hour",0);
                params.putInt("minute",0);
                newFragment.setArguments(params);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        btn_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fly_btn_time = false;
                DialogFragment newFragment = new TimePickerFragment();
                Bundle params = new Bundle();
                params.putInt("hour",0);
                params.putInt("minute",0);
                newFragment.setArguments(params);
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
                } else {
                    btn_start_time.setEnabled(true);
                    btn_end_time.setEnabled(true);
                }
            }
        });

        edt_title = (EditText) findViewById(R.id.titulo_evento);
        edt_description = (EditText) findViewById(R.id.texto_evento);

        Long idEvent = null;
        String account_calendar = null;
        Bundle reicieveParams = getIntent().getExtras();
        if(reicieveParams!=null) {
            idEvent = reicieveParams.getLong("idEvent");
            account_calendar = reicieveParams.getString("calendar");
        }

        if(idEvent!=null)
        {
            initEdition(account_calendar,idEvent);
            this.idEvent = idEvent;
            is_edition = true;
        }
        else {
            iniciarPlantilla();
            is_edition = false;
        }
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



    /**
     * Get Date Selected into DatePickerDialog
     * @param date String format yyy-mm-dd
     */
    @Override
    public void onDateSelected(String date) {
        String data_month_day []= date.split("-");
        String list_months [] = getResources().getStringArray(R.array.months_of_year);

        if(fly_btn_date) {
            btn_start_date.setText(data_month_day[2].concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_start_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_start_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
            btn_end_date.setText((data_month_day[2]).concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_end_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_end_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
        }else {
            btn_end_date.setText((data_month_day[2]).concat(" de ").concat(list_months[Integer.parseInt(data_month_day[1]) - 1]));
            btn_end_date.setTag(R.id.tag_first,Integer.parseInt(data_month_day[1]) - 1);
            btn_end_date.setTag(R.id.tag_second,Integer.parseInt(data_month_day[2]));
        }
    }

    /**
     * Get Time Selected into timePickerDialog
     * @param time String format HH:MM
     */
    @Override
    public void onTimeSelected(String time) {

        String data [] = time.split(":");
        int hour = Integer.parseInt(data[0]);
        int minute = Integer.parseInt(data[1]);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.HOUR_OF_DAY,hour);

        if(fly_btn_time) {
            btn_start_time.setText(getDate(c.getTimeInMillis()));
            btn_start_time.setTag(R.id.tag_first,hour);
            btn_start_time.setTag(R.id.tag_second,minute);
            btn_end_time.setText(getDate(c.getTimeInMillis()));
            btn_end_time.setTag(R.id.tag_first,hour);
            btn_end_time.setTag(R.id.tag_second,minute);
        }else {
            btn_end_time.setText(getDate(c.getTimeInMillis()));
            btn_end_time.setTag(R.id.tag_first,hour);
            btn_end_time.setTag(R.id.tag_second,minute);
        }

    }

    /**
     * set All values on mode creation of Layout Activity Event
     */
    public void iniciarPlantilla()
    {
        int month;
        int day;
        int hour;
        String minute = "0";
        String list_months [];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        Time time = new Time();
        time.setToNow();
        time.minute = 0;
        hour = time.hour;
        list_months = getResources().getStringArray(R.array.months_of_year);

        btn_start_date.setText((String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_start_date.setTag(R.id.tag_first,month);
        btn_start_date.setTag(R.id.tag_second,day);

        btn_end_date.setText((String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_end_date.setTag(R.id.tag_first, month);
        btn_end_date.setTag(R.id.tag_second,day);


        btn_start_time.setText(getDate(calendar.getTimeInMillis()));
        btn_start_time.setTag(R.id.tag_first,hour);
        btn_start_time.setTag(R.id.tag_second,Integer.parseInt(minute));
        btn_end_time.setText(getDate(calendar.getTimeInMillis()));
        btn_end_time.setTag(R.id.tag_first,hour);
        btn_end_time.setTag(R.id.tag_second,Integer.parseInt(minute));

        btn_mail_calendar.setText(getAccounts());
        btn_mail_calendar.setTag(getAccounts());
    }

    /**
     * set All values on mode edition of Layout Activity Event
     * @param calendar field of calendar ACCOUNT_NAME
     * @param idEvent Event ID
     */

    public void initEdition(String calendar,Long idEvent)
    {
        EventItem event = QuerysCalendar.getEventByID(getApplicationContext(),calendar,idEvent);

        EditText txtTitle = (EditText) findViewById(R.id.titulo_evento);
        txtTitle.setText(event.getTitle());

        EditText txtUbication = (EditText) findViewById(R.id.titulo_ubicación);
        txtUbication.setText(event.getLocation());

        EditText txtDescription = (EditText) findViewById(R.id.texto_evento);
        txtDescription.setText(event.getDescription());


        Time t = new Time();
        t.set(event.getDtStart());
        int month = t.month;
        int day = t.monthDay;
        int hour = t.hour;
        int minute = t.minute;
        String list_months [] = getResources().getStringArray(R.array.months_of_year);

        btn_start_date.setText((String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_start_date.setTag(R.id.tag_first,month);
        btn_start_date.setTag(R.id.tag_second,day);
        btn_start_time.setText(getDate(event.getDtStart()));
        btn_start_time.setTag(R.id.tag_first,hour);
        btn_start_time.setTag(R.id.tag_second,minute);

        t.set(event.getDtEnd());
        month = t.month;
        day = t.monthDay;
        hour = t.hour;
        minute = t.minute;
        btn_end_date.setText((String.valueOf(day)).concat(" de ").concat(list_months[month]) );
        btn_end_date.setTag(R.id.tag_first, month);
        btn_end_date.setTag(R.id.tag_second,day);
        btn_end_time.setText(getDate(event.getDtEnd()));
        btn_end_time.setTag(R.id.tag_first,hour);
        btn_end_time.setTag(R.id.tag_second,minute);

        btn_mail_calendar.setText(event.getCalendar());
        btn_mail_calendar.setTag(event.getCalendar());


    }


    /**
     * Get ItemEvent with all fields
     * @return Event to saving
     */
    private EventItem getEvent()
    {
        long startMillis;
        long endMillis;

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2014, (Integer)btn_start_date.getTag(R.id.tag_first), (Integer)btn_start_date.getTag(R.id.tag_second), (Integer)btn_start_time.getTag(R.id.tag_first), (Integer)btn_start_time.getTag(R.id.tag_second));
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2014,(Integer)btn_end_date.getTag(R.id.tag_first), (Integer)btn_end_date.getTag(R.id.tag_second), (Integer)btn_end_time.getTag(R.id.tag_first), (Integer)btn_end_time.getTag(R.id.tag_second));
        endMillis = endTime.getTimeInMillis();
        return new EventItem(edt_title.getText().toString(),edt_description.getText().toString(),"Guatemala", (String)btn_mail_calendar.getTag(), startMillis,endMillis);
    }

    /**
     * Get All Account available in Phone and add into list
     */
    private String getAccounts() {
        final AccountManager accManager = AccountManager
                .get(this);
        final Account accounts[] = accManager.getAccountsByType("com.google");
        String temporal = accounts[0].name;
        for (Account account : accounts) {
            Toast.makeText(this, "Name " + account.name, Toast.LENGTH_SHORT).show();
        }
        return temporal;
    }

    public String getDate(Long date)
    {
        return new SimpleDateFormat("h:mm a").format(new Date(date));
    }
}
