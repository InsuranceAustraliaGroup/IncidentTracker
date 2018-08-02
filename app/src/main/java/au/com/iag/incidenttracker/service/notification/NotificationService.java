package au.com.iag.incidenttracker.service.notification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.model.Feature;
import au.com.iag.incidenttracker.model.FeatureCollection;
import au.com.iag.incidenttracker.service.transport.HazardManager;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceCallback;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceHelper;
import au.com.iag.incidenttracker.ui.MapsActivity;

public class NotificationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = NotificationService.class.getSimpleName();

    private Timer timer;
    private LiveTrafficHazardServiceHelper liveTrafficHazardServiceHelper;
    private NotificationManager notificationManager;

    private LocationRequest locationRequest;
    private Location currentLocation;
    private GoogleApiClient googleApiClient;

    // Foreground notification id
    private static final int NOTIFICATION_ID = 1;

    // Service binder
    private final IBinder serviceBinder = new RunServiceBinder();

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected" + bundle);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (l != null) {
            Log.i(TAG, "lat " + l.getLatitude());
            Log.i(TAG, "lng " + l.getLongitude());

        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended " + i);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "lat " + location.getLatitude());
        Log.i(TAG, "lng " + location.getLongitude());
        currentLocation = location;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed ");

    }

    @SuppressLint("RestrictedApi")
    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

    }

    public class RunServiceBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Override
    public void onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service");
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new NotificationTask(), 60000, 60000);
        liveTrafficHazardServiceHelper = new LiveTrafficHazardServiceHelper();
        notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service");
        }
        if (!googleApiClient.isConnected())
            googleApiClient.connect();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Binding service");
        }
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Destroying service");
        }
    }

    /**
     * Creates a notification for placing the service into the foreground
     *
     * @return a notification for interacting with the service when in the foreground
     */
    private Notification createNotification(Feature feature) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(feature.getProperties().getHeadline())
                .setContentText(feature.getProperties().getAdviceA())
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent resultIntent = new Intent(this, MapsActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);

        return builder.build();
    }

    class NotificationTask extends TimerTask implements LiveTrafficHazardServiceCallback {

        private int next = 0;

        private int notificationDistance =0;
        private boolean notificationsEnabled = true;
        private boolean fireNotificationsEnabled = true;
        private boolean floodNotificationsEnabled = true;
        private boolean majorEventNotificationsEnabled = true;
        private boolean incidentNotificationsEnabled = true;
        private boolean alpineNotificationsEnabled = true;
        private boolean roadworkNotificationsEnabled = true;

        private void callNext() {
            switch (next) {
                case 0:
                    if (incidentNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callIncidentHazardService(this);
                        return;
                    }
                    break;
                case 1:
                    if (fireNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callFireHazardService(this);
                        return;
                    }
                    break;
                case 2:
                    if (floodNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callFloodHazardService(this);
                        return;
                    }
                    break;
                case 3:
                    if (roadworkNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callRoadworkHazardService(this);
                        return;
                    }
                    break;
                case 4:
                    if (majorEventNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callMajorEventHazardService(this);
                        return;
                    }
                    break;
                case 5:
                    if (alpineNotificationsEnabled) {
                        liveTrafficHazardServiceHelper.callAlpineHazardService(this);
                        return;
                    }
                    break;
            }
            next++;
            if (next < 6) {
                callNext();
            }
        }

        @Override
        public void run() {

            next = 0;
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            notificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_notifications), true);
            fireNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_fire_notifications), true);
            floodNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_flood_notifications), true);
            majorEventNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_major_event_notifications), true);
            incidentNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_incident_notifications), true);
            alpineNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_alpine_notifications), true);
            roadworkNotificationsEnabled = prefs.getBoolean(getApplicationContext().getString(R.string.enable_roadwork_notifications), true);
            notificationDistance = Integer.parseInt(prefs.getString(getApplicationContext().getString(R.string.notification_distance), "100"));

            if (notificationsEnabled)
                callNext();
        }

        @Override
        public void onOpenAlpineHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        @Override
        public void onOpenFireHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        @Override
        public void onOpenFloodHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        @Override
        public void onOpenIncidentHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        @Override
        public void onOpenMajorEventHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        @Override
        public void onOpenRoadworkHazardResponse(FeatureCollection featureCollection) {
            if (!notifyHazards(featureCollection))
                callNext();
        }

        private boolean notifyHazards(FeatureCollection featureCollection) {

            for (Feature feature : featureCollection.getFeatures()) {
                if (HazardManager.shouldNotifyHazard(feature, notificationDistance, currentLocation)) {
                    notificationManager.notify(feature.getId(), createNotification(feature));
                    HazardManager.hazardNotified(feature);
                    return true;
                }
            }
            return false;
        }
    }
}