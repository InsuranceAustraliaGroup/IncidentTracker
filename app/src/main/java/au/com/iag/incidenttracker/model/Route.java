package au.com.iag.incidenttracker.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private Integer routeId;

    private String name;

    private List<Location> locations = new ArrayList<>();

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
