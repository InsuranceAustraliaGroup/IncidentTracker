package au.com.iag.incidenttracker.service.transport;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import au.com.iag.incidenttracker.model.Feature;

public class HazardManager {

    private static List<Integer> hazardIds = new ArrayList<>();

    public static boolean shouldNotifyHazard(Feature feature, int distance, Location currentLocation) {
        if (hazardIds.contains(feature.getId()))
            return false;
        Location incidentLocation = new Location("");
        incidentLocation.setLongitude(feature.getGeometry().getCoordinates().get(0));
        incidentLocation.setLatitude(feature.getGeometry().getCoordinates().get(1));
        float incidentDistance = currentLocation.distanceTo(incidentLocation);
        if (incidentDistance > distance * 1000)
            return false;
        return true;
    }

    public static void hazardNotified(Feature feature) {
        hazardIds.add(feature.getId());
    }
}