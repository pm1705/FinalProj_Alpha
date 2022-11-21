package com.example.finalproj_alpha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class notificationPractice extends AppCompatActivity {

    EditText hours_et, minutes_et;
    int hrs, mns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_practice);

        hours_et = (EditText) findViewById(R.id.hours_in);
        minutes_et = (EditText) findViewById(R.id.minutes_in);
    }

    public void notify_now(View view) {

        // Intent
        Intent intent = new Intent(this, AlarmReciever.class);
        intent.putExtra("notificationId", 1);
        intent.putExtra("message", "nope");

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        hrs = Integer.parseInt(hours_et.getText().toString());
        mns = Integer.parseInt(minutes_et.getText().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hrs);
        cal.set(Calendar.MINUTE , mns);
        cal.set(Calendar.SECOND, 0);
        long alarmStartTime = cal.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
        Toast.makeText(this, "Done! " + hrs + ":" + mns, Toast.LENGTH_SHORT).show();
        Log.d("0", "Notification");
    }
}