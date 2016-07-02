package com.upmc.jamfinder.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.User;

/**
 * Created by maxime on 13/10/15.
 */
public class UserTools {

    public static User getLoggedInUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);

        String json = preferences.getString(context.getString(R.string.user_logged_in_preferences_key), "");

        if(json.isEmpty()) {
            return null;
        }

        Gson jsonParser = new Gson();

        return jsonParser.fromJson(json, User.class);

    }

    public static void logUserIn(Context context, User newUser) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson jsonParser = new Gson();
        String json = jsonParser.toJson(newUser);

        editor.putString(context.getString(R.string.user_logged_in_preferences_key), json);
        editor.commit();
    }

    private static User mainUser=new User("admin","admin","JamFinder@gmail.com");

    public static boolean authentificating(Context context,String pseudo, String password){
        if(mainUser.getName().equals(pseudo) && mainUser.getPassword().equals(password)) {
            logUserIn(context,mainUser);
            return true;
        }
        return false;
    }

}
