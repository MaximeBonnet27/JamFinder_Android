package com.upmc.jamfinder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.customLayouts.FriendsRecyclerAdapter;
import com.upmc.jamfinder.model.User;
import com.upmc.jamfinder.tools.UserTools;

public class FriendsActivity extends AppCompatActivity {

    private User mUser;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mRecyclerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mUser = UserTools.getLoggedInUser(this);

        mRecyclerView =(RecyclerView)findViewById(R.id.friends_recycler);

        mRecyclerManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerManager);

        mRecyclerAdapter = new FriendsRecyclerAdapter(mUser.getFriends());
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }
}
