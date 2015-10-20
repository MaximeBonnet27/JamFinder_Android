package com.upmc.jamfinder.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.Jam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxime on 20/10/15.
 */
public class JamTools {
    public static void saveJam(Context context, Jam jam){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String formerList = preferences.getString(context.getString(R.string.jam_list_preferences_key), "");

        ArrayList<Jam> jams = null;
        Gson jsonParser = new Gson();

        if(formerList.isEmpty()){
            jams = new ArrayList<>();
        }
        else{
            jams = jsonParser.fromJson(formerList, new TypeToken<List<Jam>>(){}.getType());
        }
        jams.add(jam);

        String json = jsonParser.toJson(jams);

        editor.putString(context.getString(R.string.jam_list_preferences_key), json);
        editor.commit();

    }

    public static ArrayList<Jam> getJamsList(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        String formerList = preferences.getString(context.getString(R.string.jam_list_preferences_key), "");
        Gson jsonParser = new Gson();
        return jsonParser.fromJson(formerList, new TypeToken<List<Jam>>(){}.getType());

    }

}
