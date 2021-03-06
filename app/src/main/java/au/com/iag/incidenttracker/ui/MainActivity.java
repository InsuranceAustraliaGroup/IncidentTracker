package au.com.iag.incidenttracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import au.com.iag.incidenttracker.R;

public class MainActivity extends BaseActivity {

    GridView gridView;
    GridViewMenuAdapter grisViewCustomeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setupToolbar("Menu", true);

        gridView=(GridView)findViewById(R.id.menu_grid_view);
        // Create the Custom Adapter Object
        grisViewCustomeAdapter = new GridViewMenuAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(grisViewCustomeAdapter);

        // Handling touch/click Event on GridView Item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, HazardHistoryActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, RecordRouteActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, RouteListActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, RouteAlarmActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
