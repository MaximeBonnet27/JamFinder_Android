package com.upmc.jamfinder.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.activities.SignInActivity;
import com.upmc.jamfinder.model.User;

/**
 * Created by maxime on 13/10/15.
 */
public class UserTools {

    public static User getLoggedInUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);

        String json = preferences.getString(context.getString(R.string.user_logged_in_preferences_key), "");

        if(json == "") {
            return null;
        }

        Gson jsonParser = new Gson();

        User user = jsonParser.fromJson(json, User.class);

        return user;
    }

    public static void logUserIn(Context context, User newUser) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson jsonParser = new Gson();
        String json = jsonParser.toJson(newUser);

        editor.putString(context.getString(R.string.user_logged_in_preferences_key), json);
        editor.commit();
    }
}
