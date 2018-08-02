package au.com.iag.incidenttracker.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.service.notification.NotificationService;
import au.com.iag.incidenttracker.service.route.RouteRegistrationService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        findViewById(R.id.map_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.route_map_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PolylineDemoActivity.class);
                startActivity(intent);
            }
        });

        final Button recordButton = findViewById(R.id.route_record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setText("Record route");
                    recording = false;
                }
                else {
                    Intent i = new Intent(MainActivity.this, RouteRegistrationService.class);
                    startService(i);
                    bindService(i, mConnection, 0);
                    recording = true;
                    recordButton.setText("Stop recording");
                }
            }
        });

        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Callback for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound");
            }
            RouteRegistrationService.RunServiceBinder binder = (RouteRegistrationService.RunServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect");
            }
        }
    };
}
