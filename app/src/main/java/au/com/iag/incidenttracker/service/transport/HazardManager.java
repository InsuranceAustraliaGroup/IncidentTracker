package au.com.iag.incidenttracker.service.transport;

import java.util.ArrayList;
import java.util.List;

import au.com.iag.incidenttracker.model.Feature;

public class HazardManager {

    private static List<Integer> hazardIds = new ArrayList<>();

    public static boolean shouldNotifyHazard(Feature feature) {
        if (hazardIds.contains(feature.getId()))
            return false;
        return true;
    }

    public static void hazardNotified(Feature feature) {
        hazardIds.add(feature.getId());
    }
}