package au.com.iag.incidenttracker.service.transport;

import au.com.iag.incidenttracker.model.FeatureCollection;

public interface LiveTrafficHazardServiceCallback {

    void onOpenAlpineHazardResponse(FeatureCollection featureCollection);

    void onOpenFireHazardResponse(FeatureCollection featureCollection);

    void onOpenFloodHazardResponse(FeatureCollection featureCollection);

    void onOpenIncidentHazardResponse(FeatureCollection featureCollection);

    void onOpenMajorEventHazardResponse(FeatureCollection featureCollection);

    void onOpenRoadworkHazardResponse(FeatureCollection featureCollection);

}
