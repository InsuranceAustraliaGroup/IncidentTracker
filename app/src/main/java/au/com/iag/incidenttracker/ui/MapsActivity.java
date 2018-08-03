package au.com.iag.incidenttracker.ui;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import au.com.iag.incidenttracker.IncidentTrackerApplication;
import au.com.iag.incidenttracker.PermissionUtils;
import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.Utils;
import au.com.iag.incidenttracker.model.Feature;
import au.com.iag.incidenttracker.model.FeatureCollection;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceCallback;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceHelper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapsActivity extends BaseActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    public static final String EXTRA_LOCATION = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LatLng intentLocation = getIntent().getParcelableExtra(EXTRA_LOCATION);
        if (intentLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(intentLocation, 12f));
            setUpClusterer();
            getFeatures();
        }
        else mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12f));
                        }

                        setUpClusterer();
                        getFeatures();
                    }
                });
    }

    private void getFeatures() {
        LiveTrafficHazardServiceHelper liveTrafficHazardServiceHelper = new LiveTrafficHazardServiceHelper();
        liveTrafficHazardServiceHelper.callTrafficHazardServices(new LiveTrafficHazardServiceCallback() {

            @Override
            public void onOpenAlpineHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }

            @Override
            public void onOpenFireHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }

            @Override
            public void onOpenFloodHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }

            @Override
            public void onOpenIncidentHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }

            @Override
            public void onOpenMajorEventHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }

            @Override
            public void onOpenRoadworkHazardResponse(FeatureCollection featureCollection) {
//                addFeatures(featureCollection.getFeatures());
                addClusterItems(featureCollection.getFeatures());
            }
        });
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

//    private void addFeatures(List<Feature> features) {
//        for(Feature feature : features) {
//
//            BitmapDescriptor icon = feature.getFeatureType().getIcon(this);
//
//            Marker marker = mMap.addMarker(new MarkerOptions()
//                    .position(feature.getGeometry().getLatLong())
//                    .title(feature.getFeatureType().name())
//                    .snippet(feature.getProperties().getHeadline())
//                    .icon(icon));
//        }
//    }

    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);



        final CustomClusterRenderer renderer = new CustomClusterRenderer(MapsActivity.this, mMap, mClusterManager);
        mClusterManager.setRenderer(renderer);
    }

    private void addClusterItems(List<Feature> features) {

        for(Feature feature : features) {
            MyItem offsetItem = new MyItem(feature);
            mClusterManager.addItem(offsetItem);
        }
    }

    public class CustomClusterRenderer extends DefaultClusterRenderer<MyItem> {

        private final Context mContext;

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);

            mContext = context;
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            BitmapDescriptor icon = item.getFeature().getFeatureType().getIcon(MapsActivity.this);
            markerOptions.icon(icon);
            markerOptions.icon(icon).title(item.getTitle()).snippet(item.getSnippet());
        }
    }
}
