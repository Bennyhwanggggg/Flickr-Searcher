package benny.dev.flickerbrowser;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

public class SearchActivity extends BaseActivity {

    private SearchView mSearchView;
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activiateToolbar(true); // activateToolbar from BaseActivity Class
        Log.d(TAG, "onCreate: end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: start");
        getMenuInflater().inflate(R.menu.menu_search, menu); // inflating an xml layout creates the view from them

        // The search manager provides access to get system search service using the Context.SEACH_SERVICE function.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // retrieve the item using the function below.
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // retrieve searchable info from xml using the function below. It wants an component name instead of activity.
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);

        mSearchView.setIconified(false); // make it envoke straightaway without being an icon.

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: called");
                // SharedPreferences object get using PreferenceManager with the application context. we use application context because many activity will get it
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                // edit method to make it into an editable state
                sharedPreferences.edit().putString(FLICKR_QUERY, query).apply();
                mSearchView.clearFocus(); // Ensure we go back to main activity
                finish(); // closes activity that launched it
                return true; // true to indicate we are dealing with the event
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // let system deal with this using default behaviour
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });

        Log.d(TAG, "onCreateOptionsMenu: returned true");
        return true;
    }
}
