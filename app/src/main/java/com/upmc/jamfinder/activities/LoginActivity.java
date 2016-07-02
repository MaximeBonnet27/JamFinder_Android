package com.upmc.jamfinder.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity{

    @Bind(R.id.login_name_edit)  EditText mNameEditText;
    @Bind(R.id.login_password_edit)  EditText mPasswordEditText;
    @Bind(R.id.login_submit_button)  AppCompatButton mSubmitButton;
    @Bind(R.id.login_signin)  TextView mLinkSigin;

    private String mName;
    private String mPassword;
    private boolean mIsLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mIsLoggedIn = checkUserLoggedIn();


        if(mIsLoggedIn){
            goToMainMenuActivity();
        }
        mNameEditText.setSelected(false);
        mPasswordEditText.setSelected(false);
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
            mNameEditText.setError("Empty pseudo");
            return CheckFormResult.EMPTY_NAME;
        }

        mPassword = mPasswordEditText.getText().toString();
        if (mPassword.isEmpty()) {
            mPasswordEditText.setError("Empty password");
            return CheckFormResult.EMPTY_PASSWORD;
        }
        return CheckFormResult.VALID;
    }

    @OnClick(R.id.login_submit_button)
    public void login(){
        if(checkForm() == CheckFormResult.VALID){
            mSubmitButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.login_authenticating));


            boolean isGood=authentificating();
            if(isGood)
                progressDialog.show();
            else
                mPasswordEditText.setError("wrong password");

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
                if(isGood)
                    goToMainMenuActivity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean authentificating(){
        return UserTools.authentificating(this,mName,mPassword);
    }

    private void goToMainMenuActivity(){
        goToActivity(MainActivity.class);
    }

    @OnClick(R.id.login_signin)
    public void goToSignInActivity(){
        goToActivity(SignInActivity.class);
    }

    private void goToActivity(Class target){
        Intent intent = new Intent(this, target);

        startActivity(intent);
    }

    private boolean checkUserLoggedIn(){
        return UserTools.getLoggedInUser(this) != null;
    }
}
