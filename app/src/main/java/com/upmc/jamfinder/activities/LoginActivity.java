package com.upmc.jamfinder.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mNameEditText;
    private EditText mPasswordEditText;

    private Button mSubmitButton;

    private String mName;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNameEditText = (EditText) findViewById(R.id.sign_in_name_edit);
        mPasswordEditText = (EditText) findViewById(R.id.sign_in_password_edit);

        mSubmitButton = (Button) findViewById(R.id.sign_in_submit_button);
        mSubmitButton.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        return CheckFormResult.VALID;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mSubmitButton.getId()){
            if(checkForm() == CheckFormResult.VALID){
                User loggingIn = new User(mName, mPassword);
                UserTools.logUserIn(this, loggingIn);
                goToMainActivity();
            }
        }
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
