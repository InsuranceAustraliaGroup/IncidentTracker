package au.com.iag.incidenttracker.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

public class LocationDbHelper {

    FeedReaderDbHelper dbHelper;

    public LocationDbHelper(Context context) {
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void saveLocation(Integer routeId, Location location) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ROUTE_ID, routeId);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE, location.getLongitude());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUDE, location.getLatitude());

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    public void clearRoute(Integer routeId) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = { "" + routeId };
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "WHERE ROUTE_ID = ? " + routeId, args);
    }
}
