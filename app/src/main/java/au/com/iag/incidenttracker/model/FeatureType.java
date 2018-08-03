package au.com.iag.incidenttracker.model;

import android.app.Activity;
import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.Utils;

public enum FeatureType {
    FIRE(R.drawable.icon_fire, Color.parseColor("#ff6600")),
    FLOOD(R.drawable.icon_flood, Color.parseColor("#0000ff")),
    INCIDENT(R.drawable.icon_car, Color.parseColor("#990000")),
    MAJOR_EVENT(R.drawable.icon_majorevent, Color.parseColor("#000000")),
    ALPINE(R.drawable.icon_alpine, Color.parseColor("#5c85d6")),
    ROADWORK(R.drawable.icon_roadwork, Color.parseColor("#996633"));


    private int icon;
    private int color;
    private BitmapDescriptor bitmap;

    FeatureType(int icon, int color) {
        this.icon = icon;
        this.color = color;
    }

    public int getIconRessource() {
        return icon;
    }

    public BitmapDescriptor getIcon(Activity activity) {
        if(bitmap == null) {
            bitmap = Utils.vectorToBitmap(activity, icon, color);
        }
        return bitmap;
    }
}
