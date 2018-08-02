package au.com.iag.incidenttracker.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.model.Route;
import au.com.iag.incidenttracker.service.database.RouteQueryHelper;
import au.com.iag.incidenttracker.service.route.RouteAlarmReceiver;
import au.com.iag.incidenttracker.service.route.RouteRecordService;

public class RouteAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private RouteQueryHelper routeQueryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_alarm);

        routeQueryHelper = new RouteQueryHelper(this);
        List<Route> routes = routeQueryHelper.getRoutes();
        List<String> routeNames = new ArrayList<>();
        for (Route route : routes) {
            routeNames.add(route.getName());
        }

        Spinner spinner = findViewById(R.id.route_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button alarmButton = findViewById(R.id.route_alarm_button);
        alarmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, RouteAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        TimePicker timePicker = findViewById(R.id.route_time_picker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmIntent);
    }
}
