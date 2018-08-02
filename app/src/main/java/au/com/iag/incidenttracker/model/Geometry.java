package au.com.iag.incidenttracker.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Geometry {

    List<Double> coordinates;

    public LatLng getLatLong() {
        return new LatLng(coordinates.get(1), coordinates.get(0));
    }
}
