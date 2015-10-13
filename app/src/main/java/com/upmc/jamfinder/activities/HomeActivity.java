package com.upmc.jamfinder.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.tools.UserTools;

public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    private String TAG = "HOME ACTIVITY";

    private boolean mIsLoggedIn;

    private Button mLogIn;
    private Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mIsLoggedIn = checkUserLoggedIn();


        if(mIsLoggedIn){
            Log.d(TAG, "OUAIS OUAIS");
            goToMainMenuActivity();
        }

        mLogIn = (Button) findViewById(R.id.home_login_button);
        mSignIn = (Button) findViewById(R.id.home_sign_in_button);

        mLogIn.setOnClickListener(this);
        mSignIn.setOnClickListener(this);

    }

    private boolean checkUserLoggedIn(){
        return UserTools.getLoggedInUser(this) != null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
    public void onClick(View view) {
        if(view.getId() == mLogIn.getId()){
            goToLoginActivity();
        }
        else if(view.getId() == mSignIn.getId()){
            goToSignInActivity();
        }

    }

    private void goToMainMenuActivity(){
        goToActivity(MainActivity.class);
    }

    private void goToLoginActivity(){
        goToActivity(LoginActivity.class);
    }

    private void goToSignInActivity(){
        goToActivity(SignInActivity.class);
    }

    private void goToActivity(Class target){
        Intent intent = new Intent(this, target);

        startActivity(intent);
    }
}
