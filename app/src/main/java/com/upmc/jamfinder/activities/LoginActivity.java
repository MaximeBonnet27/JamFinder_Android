package com.upmc.jamfinder.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity{

    @Bind(R.id.login_name_edit)  EditText mNameEditText;
    @Bind(R.id.login_password_edit)  EditText mPasswordEditText;
    @Bind(R.id.login_submit_button)  AppCompatButton mSubmitButton;
    @Bind(R.id.login_signin)  TextView mLinkSigin;

    private String mName;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mLinkSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignInActivity();
            }
        });


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

    private void login(){
        if(checkForm() == CheckFormResult.VALID){
            mSubmitButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.login_authenticating));
            progressDialog.show();

            authenticating();

            Thread runnable=new Thread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });

            /*android.os.Handler handler=new android.os.Handler();
            handler.postDelayed(
                    runnable, 3000
            );*/

            try {
                runnable.join();
                mSubmitButton.setEnabled(true);
                goToMainMenuActivity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean authenticating(){
        User loggingIn = new User(mName, mPassword);
        UserTools.logUserIn(this, loggingIn);
        return true;
    }

    private void goToMainMenuActivity(){
        goToActivity(MainActivity.class);
    }
    private void goToSignInActivity(){
        goToActivity(SignInActivity.class);
    }

    private void goToActivity(Class target){
        Intent intent = new Intent(this, target);

        startActivity(intent);
    }
}
