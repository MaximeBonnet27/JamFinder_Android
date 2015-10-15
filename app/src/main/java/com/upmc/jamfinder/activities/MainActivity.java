package com.upmc.jamfinder.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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


public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String TAG = "MAIN_ACTIVITY";

    private MapFragment mMapFragment;
    private DrawerLayout mDrawer;
    private Button mMenuButton;

    private GoogleMap mMap;
    private Marker mMarker;
    private LinearLayout mDrawerContent;

    private ListView mDrawerList;

    private String[] mDrawerMenuEntries;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.main_map);
        mMapFragment.getMapAsync(this);
        mMap = mMapFragment.getMap();

        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerContent = (LinearLayout) findViewById(R.id.main_left_drawer);

        mMenuButton = (Button) findViewById(R.id.main_menu_button);
        mMenuButton.setOnClickListener(this);

        mDrawerMenuEntries = getResources().getStringArray(R.array.main_drawer_menu_entries);

        mDrawerList = (ListView) findViewById(R.id.main_drawer_list);
        mDrawerList.setAdapter(new DrawerPanelListAdapter(this, mDrawerMenuEntries));

        buildGoogleApiClient();

    }
    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void updateUI(){
        LatLng loc = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        //mMarker = mMap.addMarker(new MarkerOptions().position(loc));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }


    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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
        setUpMap();
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mMenuButton.getId()) {
            mDrawer.openDrawer(mDrawerContent);
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        if(mCurrentLocation == null){
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mCurrentLocation != null) {
                updateUI();
            }
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "CONNECTION FAILED");
        Log.e(TAG, connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Toast.makeText(this, "Location changée",
                Toast.LENGTH_SHORT).show();
        updateUI();
    }
}
