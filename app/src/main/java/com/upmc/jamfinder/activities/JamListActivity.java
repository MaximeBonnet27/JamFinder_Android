package com.upmc.jamfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.customLayouts.JamListRecyclerAdapter;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

public class JamListActivity extends AppCompatActivity {

    private User mUser;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mRecyclerManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jam_list);

        mUser = UserTools.getLoggedInUser(this);

        mRecyclerView =(RecyclerView)findViewById(R.id.jam_list_recycler);

        mRecyclerManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerManager);

        mRecyclerAdapter = new JamListRecyclerAdapter(mUser.getCreatedJams());
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }
}
