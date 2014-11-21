package com.dsoft.mycalendar.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import com.dsoft.mycalendar.MyActivity;
import com.dsoft.mycalendar.R;

/**
 * Created by enrique on 7/11/14.
 */
public class CalendarReminderReceiver extends BroadcastReceiver {

    private static final String TAG = "MyServiceNotification";

    @Override
    public void onReceive(Context c, Intent event) {
        if (event.getAction().equalsIgnoreCase(CalendarContract.ACTION_EVENT_REMINDER)) {
            //Do Something Here to get EVENT ID


            Long id = (Long) event.getExtras().get(CalendarContract.Events.DTEND);
            String calendar = event.getStringExtra(CalendarContract.Events.OWNER_ACCOUNT);
            String title = event.getStringExtra("title");
            Intent i = new Intent(c, MyActivity.class);
            i.putExtra("idEvent",id);
            i.putExtra("calendario",calendar);
            PendingIntent pIntent = PendingIntent.getActivity(c, 0,i, 0);

            // Build notification
            // Actions are just fake
            Notification noti = new Notification.Builder(c)
                    .setContentTitle("Recordatorio MyCalendar " + id)
                    .setContentText("Evento Proximo").setSmallIcon(R.drawable.ic_evento_nuevo)
                    .setContentIntent(pIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) c.getApplicationContext().getSystemService(c.NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);


        }
    }
}
