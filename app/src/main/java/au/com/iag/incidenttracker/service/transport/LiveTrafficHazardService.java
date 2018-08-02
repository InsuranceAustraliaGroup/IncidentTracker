package au.com.iag.incidenttracker.service.transport;

import au.com.iag.incidenttracker.model.FeatureCollection;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LiveTrafficHazardService {

    @GET("live/hazards/alpine/open")
    Call<FeatureCollection> getOpenAlpineHazards();

    @GET("live/hazards/fire/open")
    Call<FeatureCollection> getOpenFireHazards();

    @GET("live/hazards/flood/open")
    Call<FeatureCollection> getOpenFloodHazards();

    @GET("live/hazards/incident/open")
    Call<FeatureCollection> getOpenIncidentHazards();

    @GET("live/hazards/majorevent/open")
    Call<FeatureCollection> getOpenMajorEventHazards();

    @GET("live/hazards/roadwork/open")
    Call<FeatureCollection> getOpenRoadworkHazards();
}
