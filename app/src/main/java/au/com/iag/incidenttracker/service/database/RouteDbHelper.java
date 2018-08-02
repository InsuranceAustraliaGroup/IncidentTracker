package au.com.iag.incidenttracker.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RouteDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RouteContract.RouteEntry.TABLE_NAME + " (" +
                    RouteContract.RouteEntry._ID + " INTEGER PRIMARY KEY," +
                    RouteContract.RouteEntry.COLUMN_NAME_ROUTE_NAME + " VARCHAR," +
                    RouteContract.RouteEntry.COLUMN_NAME_STEP + " INTEGER," +
                    RouteContract.RouteEntry.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    RouteContract.RouteEntry.COLUMN_NAME_LONGITUDE + " DOUBLE)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RouteContract.RouteEntry.TABLE_NAME;

    public RouteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}