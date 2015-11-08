package com.upmc.jamfinder.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileActivity extends AppCompatActivity {

    private final static String TAG = "PROFILE_ACTIVITY";

    // User loggé
    private User mUser;

    // Widgets
    @Bind(R.id.profile_name_edit) EditText mNameEditText;
    @Bind(R.id.profile_password_edit) EditText mPasswordEditText;
    @Bind(R.id.profile_passwordConfirm_edit) EditText mConfirmPasswordEditText;
    @Bind(R.id.profile_mail_edit) EditText mMailEditText;
    @Bind(R.id.profile_edit_button) FloatingActionButton mEditButton;
    @Bind(R.id.profile_passwordConfirm_layout) LinearLayout mlayoutPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mUser = UserTools.getLoggedInUser(this);

        mNameEditText.setText(mUser.getName());
        mPasswordEditText.setText(mUser.getPassword());
        mConfirmPasswordEditText.setText(mUser.getPassword());
        mMailEditText.setText(mUser.getMail());

        mNameEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        mConfirmPasswordEditText.setEnabled(false);
        mMailEditText.setEnabled(false);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @OnClick(R.id.profile_edit_button)
    public void editAction(){
        if(saveParameters()) {
            boolean isEnabled = mNameEditText.isEnabled();
            mNameEditText.setEnabled(!isEnabled);
            mPasswordEditText.setEnabled(!isEnabled);
            mMailEditText.setEnabled(!isEnabled);
            mConfirmPasswordEditText.setEnabled(!isEnabled);

            if (!isEnabled) {
                mlayoutPass.setVisibility(View.VISIBLE);
                mEditButton.setImageResource(R.drawable.ic_done_white_48dp);
            } else {
                mlayoutPass.setVisibility(View.INVISIBLE);
                mEditButton.setImageResource(R.drawable.ic_create_white_48dp);
            }
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

    private boolean saveParameters(){
        if(checkForm()==CheckFormResult.VALID) {
            Log.d(TAG, "SAVE PARAMETERS");

            String newName = mNameEditText.getText().toString();
            String newPassword = mPasswordEditText.getText().toString();
            String newMail = mMailEditText.getText().toString();
            mUser.setName(newName);
            mUser.setPassword(newPassword);
            mUser.setMail(newMail);
            UserTools.logUserIn(this, mUser);
            return true;
        }else{
            Log.d(TAG, "CANT SAVE PARAMETERS");
        }

        return false;
    }

    // Checks if the form is valid
    private CheckFormResult checkForm() {
        String newName = mNameEditText.getText().toString();
        if (newName.isEmpty()) {
            mNameEditText.setError("Empty pseudo");
            return CheckFormResult.EMPTY_NAME;
        }

        String newMail=mMailEditText.getText().toString();
        if(newMail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(newMail).matches()){
            mMailEditText.setError("invalid email address");
            return CheckFormResult.INCORRECT_MAIL;
        }
        String newPassword = mPasswordEditText.getText().toString();
        if (newPassword.isEmpty()) {
            mPasswordEditText.setError("Empty password");
            return CheckFormResult.EMPTY_PASSWORD;
        }
        String confirmPassword = mConfirmPasswordEditText.getText().toString();
        if (!confirmPassword.equals(newPassword)) {
            mConfirmPasswordEditText.setError("Not same Password");
            return CheckFormResult.INCORRECT_CONFIRMATION;
        }
        return CheckFormResult.VALID;
    }
}
