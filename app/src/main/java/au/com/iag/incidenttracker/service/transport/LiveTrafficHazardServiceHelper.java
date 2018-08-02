package au.com.iag.incidenttracker.service.transport;

import android.util.Log;

import au.com.iag.incidenttracker.InjectionBase;
import au.com.iag.incidenttracker.model.Feature;
import au.com.iag.incidenttracker.model.FeatureCollection;
import au.com.iag.incidenttracker.model.FeatureType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveTrafficHazardServiceHelper {

    private FeatureCollection featureCollection = new FeatureCollection();

    public void callAlpineHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenAlpineHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.ALPINE);
                    callback.onOpenAlpineHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting alpine hazards");
            }
        });
    }

    public void callFireHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenFireHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.FIRE);
                    callback.onOpenFireHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting fire hazards");
            }
        });
    }

    public void callFloodHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenFloodHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.FLOOD);
                    callback.onOpenFloodHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting flood hazards");
            }
        });
    }

    public void callIncidentHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenIncidentHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.INCIDENT);
                    callback.onOpenIncidentHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting incident hazards");
            }
        });
    }

    public void callMajorEventHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenMajorEventHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.MAJOR_EVENT);
                    callback.onOpenMajorEventHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting major event hazards");
            }
        });
    }

    public void callRoadworkHazardService(final LiveTrafficHazardServiceCallback callback) {
        Call<FeatureCollection> call = InjectionBase.getLiveTrafficHazardService().getOpenRoadworkHazards();

        call.enqueue(new Callback<FeatureCollection>() {
            @Override
            public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                if (response.body() != null) {
                    for (Feature feature : response.body().getFeatures())
                        feature.setFeatureType(FeatureType.ROADWORK);
                    callback.onOpenRoadworkHazardResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {
                Log.e("Hazard", "error getting roadwork hazards");
            }
        });
    }

    public void callTrafficHazardServices(final LiveTrafficHazardServiceCallback callback) {
        callAlpineHazardService(callback);
        callFireHazardService(callback);
        callFloodHazardService(callback);
        callIncidentHazardService(callback);
        callMajorEventHazardService(callback);
        callRoadworkHazardService(callback);
    }
}
