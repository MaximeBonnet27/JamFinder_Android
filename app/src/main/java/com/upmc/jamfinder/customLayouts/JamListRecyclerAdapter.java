package com.upmc.jamfinder.customLayouts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upmc.jamfinder.R;
import com.upmc.jamfinder.model.Jam;

import java.util.ArrayList;

/**
 * Created by maxime on 20/10/15.
 */
public class JamListRecyclerAdapter extends RecyclerView.Adapter<JamListRecyclerAdapter.ViewHolder> implements OnMapReadyCallback {


    private ArrayList<Jam> mJamList;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        MapView mMapView;
        TextView mTextView;
        Button mEditButton;
        Button mDeleteButton;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.recycler_card_jam_name);
            mEditButton = (Button) v.findViewById(R.id.recycler_card_jam_edit_button);
            mDeleteButton = (Button) v.findViewById(R.id.recycler_card_jam_delete_button);
            mMapView = (MapView) v.findViewById(R.id.recycler_card_jam_map_view);
        }
    }

    public JamListRecyclerAdapter(ArrayList<Jam> mJamList) {
        this.mJamList = mJamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_card_jam, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mJamList.get(position).getName());

        holder.mMapView.onCreate(null);
        holder.mMapView.getMapAsync(this);

        holder.mMapView.getMap()
                .addMarker(new MarkerOptions().position(mJamList.get(position).getLocation()));
        holder.mMapView.getMap()
                .animateCamera(CameraUpdateFactory.newLatLngZoom(mJamList.get(position).getLocation(), 13.0f));
    }

    @Override
    public int getItemCount() {
        return mJamList.size();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

}
