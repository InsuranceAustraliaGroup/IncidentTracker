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

public class RecordRouteActivity extends BaseActivity {

    private static final String TAG = "Main";

    private boolean recording = false;

    private RouteRecordService routeRecordService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_route);
        setupToolbar("Record Route", false);

        final TextView routeNameTextView = findViewById(R.id.route_name_edit_text);
        final Button recordButton = findViewById(R.id.route_record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recordButton.setText("Record route");
                    Intent intent = new Intent(RecordRouteActivity.this, RouteRecordService.class);
                    intent.setAction(RouteRecordService.ACTION_STOP_FOREGROUND_SERVICE);
                    startService(intent);
                    recording = false;

                }
                else {
                    Intent intent = new Intent(RecordRouteActivity.this, RouteRecordService.class);
                    intent.setAction(RouteRecordService.ACTION_START_FOREGROUND_SERVICE);
                    intent.putExtra("ROUTE_NAME", routeNameTextView.getText().toString());
                    startService(intent);
                    bindService(intent, mConnection, 0);
                    recording = true;
                    recordButton.setText("Stop recording");
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    startActivity(i);
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
