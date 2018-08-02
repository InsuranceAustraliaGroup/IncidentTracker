package au.com.iag.incidenttracker.service.transport;

import android.util.Log;

import au.com.iag.incidenttracker.InjectionBase;
import au.com.iag.incidenttracker.model.FeatureCollection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveTrafficHazardServiceHelper {

    private LiveTrafficHazardServiceCallback liveTrafficHazardServiceCallback;
    private FeatureCollection featureCollection = new FeatureCollection();

    public void callTrafficHazardServices(LiveTrafficHazardServiceCallback callback) {
        liveTrafficHazardServiceCallback = callback;

        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenFireHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenFireHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting fire hazards");
            }
        });

        call = InjectionBase.getLiveTrafficHazardService().getOpenAlpineHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenAlpineHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting alpine hazards");
            }
        });

        call = InjectionBase.getLiveTrafficHazardService().getOpenFloodHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenFloodHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting flood hazards");
            }
        });

        call = InjectionBase.getLiveTrafficHazardService().getOpenIncidentHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenIncidentHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting incident hazards");
            }
        });

        call = InjectionBase.getLiveTrafficHazardService().getOpenMajorEventHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenMajorEventHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting major event hazards");
            }
        });

        call = InjectionBase.getLiveTrafficHazardService().getOpenRoadworkHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                liveTrafficHazardServiceCallback.onOpenRoadworkHazardResponse(response.body());
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting roadwork hazards");
            }
        });
    }
}
