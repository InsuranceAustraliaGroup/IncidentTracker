package au.com.iag.incidenttracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.model.Route;
import au.com.iag.incidenttracker.service.database.RouteQueryHelper;

import static au.com.iag.incidenttracker.ui.MapsActivity.EXTRA_SHOW_ROUTE;

public class RouteListActivity extends BaseActivity {

    private RouteQueryHelper routeQueryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        setupToolbar("Routes", false);

        routeQueryHelper = new RouteQueryHelper(this);

        //generate list
        List<Route> list = routeQueryHelper.getRoutes();

        //handle listview and assign adapter
        ListView listView = findViewById(R.id.route_list);
        TextView textView = findViewById(R.id.route_text_view);

        if (list.isEmpty()) {
            listView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
            //instantiate custom adapter
            RouteListAdapter adapter = new RouteListAdapter(list, this, routeQueryHelper);
            listView.setAdapter(adapter);
        }
    }
}
