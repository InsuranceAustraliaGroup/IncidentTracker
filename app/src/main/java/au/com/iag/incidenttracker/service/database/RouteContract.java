package au.com.iag.incidenttracker.service.database;

import android.provider.BaseColumns;

public final class RouteContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RouteContract() {}

    /* Inner class that defines the table contents */
    public static class RouteEntry implements BaseColumns {
        public static final String TABLE_NAME = "route";
        public static final String COLUMN_NAME_ROUTE_NAME = "route_name";
        public static final String COLUMN_NAME_STEP = "step";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
    }
}