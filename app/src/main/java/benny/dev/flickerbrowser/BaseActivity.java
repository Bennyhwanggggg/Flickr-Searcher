package benny.dev.flickerbrowser;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    static final String FLICKR_QUERY = "FLICKER_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activiateToolbar (boolean enableHome){
        Log.d(TAG, "activiateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar != null){
                setSupportActionBar(toolbar); // put the toolbar in place
                actionBar = getSupportActionBar(); // get the reference to the new actionbar
            }
        }
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enableHome); // enable home button
        }
    }
}
