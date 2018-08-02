package au.com.iag.incidenttracker.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.service.route.RouteRecordService;

public class RecordRouteActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_route);

        final TextView routeNameTextView = findViewById(R.id.route_name_edit_text);
        final Button recordButton = findViewById(R.id.route_record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setText("Record route");
                    recording = false;
                }
                else {
                    Intent i = new Intent(RecordRouteActivity.this, RouteRecordService.class);
                    i.putExtra("ROUTE_NAME", routeNameTextView.getText().toString());
                    startService(i);
                    bindService(i, mConnection, 0);
                    recording = true;
                    recordButton.setText("Stop recording");
                }
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
            RouteRecordService.RunServiceBinder binder = (RouteRecordService.RunServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect");
            }
        }
    };
}
