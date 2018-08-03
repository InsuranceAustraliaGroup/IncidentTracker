package au.com.iag.incidenttracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Utils {

    /**
     * Demonstrates converting a {@link Drawable} to a {@link BitmapDescriptor},
     * for use as a marker icon.
     */
    public static BitmapDescriptor vectorToBitmap(Activity activity, @DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(activity.getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static void show201to388Route(GoogleMap mMap) {
        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-33.872991, 151.204110),
                        new LatLng(-33.872808, 151.204501),
                        new LatLng(-33.872732, 151.205064),
                        new LatLng(-33.870978, 151.204840),
                        new LatLng(-33.870920, 151.205152),
                        new LatLng(-33.870880, 151.205507),
                        new LatLng(-33.870755, 151.206950),
                        new LatLng(-33.868685, 151.206906)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.870920, 151.20515), 12f));
    }

    public static void show201toCentralStationRoute(GoogleMap mMap) {
        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-33.872964, 151.204114),
                        new LatLng(-33.874089, 151.204244),
                        new LatLng(-33.874208, 151.205210),
                        new LatLng(-33.876255, 151.205504),
                        new LatLng(-33.876375, 151.206097),
                        new LatLng(-33.877756, 151.205727),
                        new LatLng(-33.879319, 151.205400),
                        new LatLng(-33.879675, 151.205271),
                        new LatLng(-33.881082, 151.204756),
                        new LatLng(-33.881478, 151.205507),
                        new LatLng(-33.881585, 151.205673),
                        new LatLng(-33.881643, 151.205866),
                        new LatLng(-33.881781, 151.206290),
                        new LatLng(-33.882289, 151.207197)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.879675, 151.205271), 12f));
    }
}
