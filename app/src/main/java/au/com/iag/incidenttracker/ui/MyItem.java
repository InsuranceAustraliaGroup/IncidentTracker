package au.com.iag.incidenttracker.ui;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import au.com.iag.incidenttracker.model.Feature;

public class MyItem implements ClusterItem {
    private Feature feature;

    public MyItem(Feature feature) {
        this.feature = feature;
    }

    @Override
    public LatLng getPosition() {
        return feature.getGeometry().getLatLong();
    }

    @Override
    public String getTitle() {
        return feature.getFeatureType().name();
    }

    @Override
    public String getSnippet() {
        return feature.getProperties().getHeadline();
    }

    public Feature getFeature() {
        return feature;
    }
}
