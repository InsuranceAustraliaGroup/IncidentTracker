package au.com.iag.incidenttracker.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import au.com.iag.incidenttracker.R;
import au.com.iag.incidenttracker.model.Feature;
import au.com.iag.incidenttracker.model.FeatureCollection;
import au.com.iag.incidenttracker.service.transport.HazardManager;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceCallback;
import au.com.iag.incidenttracker.service.transport.LiveTrafficHazardServiceHelper;
import au.com.iag.incidenttracker.ui.MapsActivity;

public class NotificationService extends Service {

    private static final String TAG = NotificationService.class.getSimpleName();

    private Timer timer;
    private LiveTrafficHazardServiceHelper liveTrafficHazardServiceHelper;
    private NotificationManager notificationManager;

    // Foreground notification id
    private static final int NOTIFICATION_ID = 1;

    // Service binder
    private final IBinder serviceBinder = new RunServiceBinder();

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service");
        }
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

        @Override
        public void run() {

            liveTrafficHazardServiceHelper.callIncidentHazardService(this);
        }

        @Override
        public void onOpenAlpineHazardResponse(FeatureCollection featureCollection) {
            if (notifyHazards(featureCollection))
                liveTrafficHazardServiceHelper.callFireHazardService(this);
        }

        @Override
        public void onOpenFireHazardResponse(FeatureCollection featureCollection) {
            if (notifyHazards(featureCollection))
                liveTrafficHazardServiceHelper.callFloodHazardService(this);
        }

        @Override
        public void onOpenFloodHazardResponse(FeatureCollection featureCollection) {
            if (notifyHazards(featureCollection))
                liveTrafficHazardServiceHelper.callMajorEventHazardService(this);
        }

        @Override
        public void onOpenIncidentHazardResponse(FeatureCollection featureCollection) {
            if (notifyHazards(featureCollection))
                liveTrafficHazardServiceHelper.callAlpineHazardService(this);
        }

        @Override
        public void onOpenMajorEventHazardResponse(FeatureCollection featureCollection) {
            if (notifyHazards(featureCollection))
                liveTrafficHazardServiceHelper.callRoadworkHazardService(this);
        }

        @Override
        public void onOpenRoadworkHazardResponse(FeatureCollection featureCollection) {
            if (!featureCollection.getFeatures().isEmpty())
                notificationManager.notify(NOTIFICATION_ID, createNotification(featureCollection.getFeatures().get(0)));
        }

        private boolean notifyHazards(FeatureCollection featureCollection) {

            for (Feature feature : featureCollection.getFeatures()) {
                if (HazardManager.shouldNotifyHazard(feature)) {
                    notificationManager.notify(feature.getId(), createNotification(feature));
                    HazardManager.hazardNotified(feature);
                    return false;
                }
            }
            return true;
        }
    }
}