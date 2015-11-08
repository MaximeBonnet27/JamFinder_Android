package com.upmc.jamfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.customLayouts.PlaceAutocompleteAdapter;
import com.upmc.jamfinder.model.Jam;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.JamTools;
import com.upmc.jamfinder.tools.UserTools;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateJamActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "CJACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private LatLng mLocation;

    @Bind(R.id.create_jam_address) AutoCompleteTextView mAutoCompleteTextView;
    @Bind(R.id.create_jam_name) EditText mNameEditText;
    @Bind(R.id.create_jam_details) EditText mDetailsEditText;
    @Bind(R.id.create_jam_button) Button mButton;

    private static final LatLngBounds BOUNDS_GREATER_PARIS = new LatLngBounds(
            new LatLng(48.658291, 2.08679), new LatLng(49.04694, 2.63791));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_jam);
        ButterKnife.bind(this);

        // Listeners
        mAutoCompleteTextView.setOnItemClickListener(autoCompleteClickListener);

        //Google api
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .build();

        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_PARIS,
                null);

        mAutoCompleteTextView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}


    private AdapterView.OnItemClickListener autoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            final CharSequence primaryText = item.getPrimaryText(null);

            Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId).setResultCallback(mGeoDataAPIResultCallback);

            mAutoCompleteTextView.setText(primaryText);
        }
    };

    @Override
    public void onBackPressed() {
        saveJam();
        super.onBackPressed();
    }

    private ResultCallback<? super PlaceBuffer> mGeoDataAPIResultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (places.getStatus().isSuccess()) {
                final Place myPlace = places.get(0);
                mLocation = myPlace.getLatLng();
            }
        }
    };

    @OnClick(R.id.create_jam_button)
    public void saveJam(){
        if(isValide()){
            Jam newJam = new Jam(mNameEditText.getText().toString(),
                    UserTools.getLoggedInUser(CreateJamActivity.this),
                    mLocation.latitude, mLocation.longitude, null, null);
            JamTools.saveJam(CreateJamActivity.this, newJam);
            User user =  UserTools.getLoggedInUser(CreateJamActivity.this);
            user.getCreatedJams().add(newJam);
            UserTools.logUserIn(CreateJamActivity.this, user);
            Toast.makeText(CreateJamActivity.this, R.string.create_jam_Created, Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isValide(){
        if(mLocation==null){
            mAutoCompleteTextView.setError("No location");
            return false;
        }

        if(mNameEditText.getText().toString().isEmpty()){
            mNameEditText.setError("Empty name");
            return false;
        }

        return true;
    }
}
