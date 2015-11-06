package com.upmc.jamfinder.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ProfileActivity extends AppCompatActivity {

    private final static String TAG = "PROFILE_ACTIVITY";

    // User loggé
    private User mUser;

    // Widgets
    @Bind(R.id.profile_name_edit) EditText mNameEditText;
    @Bind(R.id.profile_password_edit) EditText mPasswordEditText;
    @Bind(R.id.profile_edit_button) FloatingActionButton mEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mUser = UserTools.getLoggedInUser(this);

        mNameEditText.setText(mUser.getName());
        mPasswordEditText.setText(mUser.getPassword());

        mNameEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEnabled=mNameEditText.isEnabled();
                mNameEditText.setEnabled(!isEnabled);
                mPasswordEditText.setEnabled(!isEnabled);

                if(!isEnabled) {
                    mEditButton.setImageResource(R.drawable.ic_done_white_48dp);
                }else{
                    mEditButton.setImageResource(R.drawable.ic_create_white_48dp);
                }
            }
        });
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
