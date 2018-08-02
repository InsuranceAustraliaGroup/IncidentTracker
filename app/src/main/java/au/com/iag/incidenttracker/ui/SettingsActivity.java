package au.com.iag.incidenttracker.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import au.com.iag.incidenttracker.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}