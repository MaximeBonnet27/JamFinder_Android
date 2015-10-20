package com.upmc.jamfinder.customLayouts;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.User;

import java.util.ArrayList;

/**
 * Created by maxime on 20/10/15.
 */
public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder> {

    private ArrayList<User> mFriendsList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.recycler_item_friends_name);
            mImageView = (ImageView) v.findViewById(R.id.recycler_item_friends_picture);
        }
    }

    public FriendsRecyclerAdapter(ArrayList<User> friends){
        mFriendsList = friends;
    }


    @Override
    public FriendsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_friends, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(FriendsRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mFriendsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size();
    }
}
