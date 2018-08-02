package au.com.iag.incidenttracker.model;

import java.util.ArrayList;
import java.util.List;

public class FeatureCollection {

    List<Feature> features = new ArrayList<>();

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
