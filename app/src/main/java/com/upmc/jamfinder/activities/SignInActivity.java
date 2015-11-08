package com.upmc.jamfinder.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity{


    @Bind(R.id.sign_in_name_edit) EditText mNameEditText;
    @Bind(R.id.sign_in_mail_edit) EditText mMailEditText;
    @Bind(R.id.sign_in_password_edit) EditText mPasswordEditText;
    @Bind(R.id.sign_in_confirm_password_edit) EditText mConfirmPasswordEditText;
    @Bind(R.id.sign_in_submit_button) Button mSubmitbutton;
    @Bind(R.id.signin_login) TextView mLinkLogin;

    // Widgets' values
    private String mName;
    private String mMail;
    private String mPassword;
    private String mConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
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

    @OnClick(R.id.sign_in_submit_button)
    public void singin(){
        if(checkForm() == CheckFormResult.VALID){
            mSubmitbutton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getString(R.string.sign_in_creating));
            progressDialog.show();

            creating();


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
                mSubmitbutton.setEnabled(true);
                goToMainMenuActivity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean creating(){
        User newUser = new User(mName, mPassword,mMail);
        UserTools.logUserIn(this, newUser);
        return true;
    }

    // Checks if the form is valid
    private CheckFormResult checkForm() {
        mName = mNameEditText.getText().toString();
        if (mName.isEmpty()) {
            mNameEditText.setError("Empty pseudo");
            return CheckFormResult.EMPTY_NAME;
        }

        mMail=mMailEditText.getText().toString();
        if(mMail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mMail).matches()){
            mMailEditText.setError("invalid email address");
            return CheckFormResult.INCORRECT_MAIL;
        }
        mPassword = mPasswordEditText.getText().toString();
        if (mPassword.isEmpty()) {
            mPasswordEditText.setError("Empty password");
            return CheckFormResult.EMPTY_PASSWORD;
        }
        mConfirmation = mConfirmPasswordEditText.getText().toString();

        if (!mPassword.equals(mConfirmation)) {
            mConfirmPasswordEditText.setError("Not same Password");
            return CheckFormResult.INCORRECT_CONFIRMATION;
        }
        return CheckFormResult.VALID;
    }


    private void goToMainMenuActivity(){
        goToActivity(MainActivity.class);
    }
    @OnClick(R.id.signin_login)
    public void goToLoginActivity(){
        goToActivity(LoginActivity.class);
    }

    private void goToActivity(Class target){
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}
