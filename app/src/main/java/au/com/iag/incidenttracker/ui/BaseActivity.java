package au.com.iag.incidenttracker.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import au.com.iag.incidenttracker.R;

public class BaseActivity extends AppCompatActivity {

    protected void setupToolbar(String title, boolean isMain) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(!isMain);
    }
}
