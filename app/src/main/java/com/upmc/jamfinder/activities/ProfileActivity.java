package com.upmc.jamfinder.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;


public class ProfileActivity extends AppCompatActivity {

    private final static String TAG = "PROFILE_ACTIVITY";

    // User loggé
    private User mUser;

    // Widgets
    private EditText mNameEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mNameEditText = (EditText) findViewById(R.id.profile_name_edit);
        mPasswordEditText = (EditText) findViewById(R.id.profile_password_edit);

        mUser = UserTools.getLoggedInUser(this);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home){
            saveParameters();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveParameters();
        super.onBackPressed();
    }

    private void saveParameters(){

        Log.d(TAG, "SAVE PARAMETERS");

        String newName =mNameEditText.getText().toString();
        String newPassword =mPasswordEditText.getText().toString();

        if(!newName.isEmpty()){
            mUser.setName(newName);
        }
        if(!newPassword.isEmpty()){
            mUser.setPassword(newPassword);
        }
        UserTools.logUserIn(this, mUser);
    }
}
