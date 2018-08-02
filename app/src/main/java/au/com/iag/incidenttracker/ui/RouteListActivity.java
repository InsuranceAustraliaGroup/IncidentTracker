package au.com.iag.incidenttracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.model.Route;
import au.com.iag.incidenttracker.service.database.RouteQueryHelper;

public class RouteListActivity extends BaseActivity {

    private RouteQueryHelper routeQueryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        setupToolbar("Routes", true);

        routeQueryHelper = new RouteQueryHelper(this);

        //generate list
        List<Route> list = routeQueryHelper.getRoutes();

        //instantiate custom adapter
        RouteListAdapter adapter = new RouteListAdapter(list, this, routeQueryHelper);

        //handle listview and assign adapter
        ListView listView = findViewById(R.id.route_list);
        listView.setAdapter(adapter);
    }
}
