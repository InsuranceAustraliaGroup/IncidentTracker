package au.com.iag.incidenttracker.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import au.com.iag.incidenttracker.model.Route;

import static au.com.iag.incidenttracker.service.database.RouteContract.RouteEntry.TABLE_NAME;

public class RouteQueryHelper {

    RouteDbHelper dbHelper;

    public RouteQueryHelper(Context context) {
        dbHelper = new RouteDbHelper(context);
    }

    public void saveLocation(String routeName, Integer step, Location location) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME, routeName);
        values.put(RouteContract.RouteEntry.COLUMN_NAME_STEP, step);
        values.put(RouteContract.RouteEntry.COLUMN_NAME_LONGITUDE, location.getLongitude());
        values.put(RouteContract.RouteEntry.COLUMN_NAME_LATITUDE, location.getLatitude());

// Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME, null, values);
    }

    public List<Route> getRoutes() {

        String[] columns = { RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME };

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, columns, null, null, null, null, null, null);
        List<Route> routes = new ArrayList<>();
        while(cursor.moveToNext()) {
            Route route = new Route();
            String routeName = cursor.getString(cursor.getColumnIndexOrThrow(RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME));
            route.setName(routeName);
            routes.add(route);
        }
        return routes;
    }

    public Route getRoute() {
        String[] columns = { RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME };

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, columns, null, null, null, null, null, null);
        List<Route> routes = new ArrayList<>();
        if (cursor.moveToNext()) {
            Route route = new Route();
            String routeName = cursor.getString(cursor.getColumnIndexOrThrow(RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME));
            route.setName(routeName);
            routes.add(route);
            String[] args = { routeName };
            Cursor locationCursor = db.query(true, TABLE_NAME, columns, "ROUTE_NAME = ?", args, null, null, "STEP", null);
            while (locationCursor.moveToNext()) {
                Location location = new Location("");
                location.setLatitude(locationCursor.getDouble(cursor.getColumnIndexOrThrow(RouteContract.RouteEntry.COLUMN_NAME_LATITUDE)));
                location.setLongitude(locationCursor.getDouble(cursor.getColumnIndexOrThrow(RouteContract.RouteEntry.COLUMN_NAME_LONGITUDE)));
                route.getLocations().add(location);
            }
            return route;
        }
        return null;
    }

    public void clearRoute(String routeName) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = { routeName };
        db.delete(TABLE_NAME, "ROUTE_NAME = ? ", args);
    }
}
