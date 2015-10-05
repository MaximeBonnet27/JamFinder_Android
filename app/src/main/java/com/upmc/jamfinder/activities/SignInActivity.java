package com.upmc.jamfinder.activities;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.enums.CheckFormResult;

import org.apache.http.client.HttpClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SignInActivity extends ActionBarActivity {

    private URL serverURL;
    private final String serverAddress = "http://192.168.1.88:8080";
    // Widgets

    private EditText nameEditText;
    private EditText mailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    // Widgets' values

    private String name;
    private String mail;
    private String password;
    private String confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Get Widgets from id's
        nameEditText = (EditText) findViewById(R.id.sign_in_name_edit);
        mailEditText = (EditText) findViewById(R.id.sign_in_mail_edit);
        passwordEditText = (EditText) findViewById(R.id.sign_in_password_edit);
        confirmPasswordEditText = (EditText) findViewById(R.id.sign_in_confirm_password_edit);

        try {
            serverURL = new URL(serverAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
        name = nameEditText.getText().toString();
        if (name.isEmpty()) {
            return CheckFormResult.EMPTY_NAME;
        }
        mail = mailEditText.getText().toString();
        if (mail.isEmpty()) {
            return CheckFormResult.EMPTY_MAIL;
        }
        password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            return CheckFormResult.EMPTY_PASSWORD;
        }
        confirmation = confirmPasswordEditText.getText().toString();
        if (confirmation.isEmpty()) {
            return CheckFormResult.EMPTY_CONFIRMATION;
        }
        if (!password.equals(confirmation)) {
            return CheckFormResult.INCORRECT_CONFIRMATION;
        }
        return CheckFormResult.VALID;
    }

    // Send the form information to the server
    public void sendPostRequest() {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) serverURL.openConnection();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("name", name)
                .appendQueryParameter("mail", mail)
                .appendQueryParameter("password", password);
        String query = builder.build().getEncodedQuery();
        try {
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            connection.connect();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // Submit Button listener
    public void onSubmitForm(View view) {
        CheckFormResult validForm = checkForm();
        switch (validForm) {
            case VALID:
                sendPostRequest();
                break;
            default:
                return;
        }

    }
}
