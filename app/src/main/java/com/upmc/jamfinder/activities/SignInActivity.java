package com.upmc.jamfinder.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;

    private Button mSubmitbutton;

    // Widgets' values

    private String mName;
    private String mPassword;
    private String mConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Get Widgets from id's
        mNameEditText = (EditText) findViewById(R.id.sign_in_name_edit);
        mPasswordEditText = (EditText) findViewById(R.id.sign_in_password_edit);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.sign_in_confirm_password_edit);

        mSubmitbutton = (Button) findViewById(R.id.sign_in_submit_button);
        mSubmitbutton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

    // Checks if the form is valid
    private CheckFormResult checkForm() {
        mName = mNameEditText.getText().toString();
        if (mName.isEmpty()) {
            return CheckFormResult.EMPTY_NAME;
        }

        mPassword = mPasswordEditText.getText().toString();
        if (mPassword.isEmpty()) {
            return CheckFormResult.EMPTY_PASSWORD;
        }
        mConfirmation = mConfirmPasswordEditText.getText().toString();
        if (mConfirmation.isEmpty()) {
            return CheckFormResult.EMPTY_CONFIRMATION;
        }
        if (!mPassword.equals(mConfirmation)) {
            return CheckFormResult.INCORRECT_CONFIRMATION;
        }
        return CheckFormResult.VALID;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == mSubmitbutton.getId()){
            if(checkForm() == CheckFormResult.VALID){
                User newUser = new User(mName, mPassword);
                UserTools.logUserIn(this, newUser);
                goToMainActivity();
            }
        }
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
