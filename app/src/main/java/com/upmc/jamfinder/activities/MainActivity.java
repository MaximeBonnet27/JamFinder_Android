package com.upmc.jamfinder.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.customLayouts.DrawerPanelListAdapter;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private String TAG = "MAIN_ACTIVITY";

    private MapFragment mMapFragment;
    private DrawerLayout mDrawer;
    private Button mMenuButton;

    private GoogleMap mMap;
    private Marker mMarker;
    private ListView mDrawerContent;

    private String[] mDrawerMenuEntries;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.main_map);
        mMapFragment.getMapAsync(this);

        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerContent = (ListView) findViewById(R.id.main_left_drawer);

        mMenuButton = (Button) findViewById(R.id.main_menu_button);
        mMenuButton.setOnClickListener(this);

        mDrawerMenuEntries = getResources().getStringArray(R.array.main_drawer_menu_entries);

        mDrawerContent.setAdapter(new DrawerPanelListAdapter(this, mDrawerMenuEntries));

        mLastLocation = null;
        mMap = mMapFragment.getMap();

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*googleMap.addMarker(new MarkerOptions()
                .position(mLastLocation == null ? new LatLng(0, 0) : new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                .title("Ouais ouais"));
                */
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mMenuButton.getId()){
            mDrawer.openDrawer(mDrawerContent);
        }
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d(TAG,"Lat = " + location.getLatitude() + ", Long = " + location.getLongitude());
            mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };


}
