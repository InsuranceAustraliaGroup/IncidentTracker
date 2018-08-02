package au.com.iag.incidenttracker;

import com.google.gson.Gson;

import au.com.iag.incidenttracker.service.transport.APIClientGenerator;
import au.com.iag.incidenttracker.service.transport.GSONFactory;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardService;

public class InjectionBase {

    private static LiveTrafficHazardService liveTrafficHazardService;

    private static Gson gsonInstance;

    public static Gson getGSONInstance() {
        if (gsonInstance == null) {
            gsonInstance = GSONFactory.getGsonInstance();
        }
        return gsonInstance;
    }

    public static LiveTrafficHazardService getLiveTrafficHazardService() {
        if (liveTrafficHazardService == null)
            liveTrafficHazardService = APIClientGenerator.createService(getGSONInstance(), LiveTrafficHazardService.class, "https://api.transport.nsw.gov.au/v1/");
        return liveTrafficHazardService;
    }
}
