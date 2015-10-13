package com.upmc.jamfinder.customLayouts;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.upmc.jamfinder.R;

/**
 * Created by jordhanleoture on 13/10/2015.
 */
public class DrawerPanelListAdapter extends BaseAdapter {

    private String[] mMenuEntries;
    private Context mContext;

    private static LayoutInflater mInflater;

    public DrawerPanelListAdapter(Context context, String[] menuEntries){
        mContext = context;
        mMenuEntries = menuEntries;

        mInflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return mMenuEntries.length;
    }

    @Override
    public Object getItem(int position) {
        return mMenuEntries[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = mInflater.inflate(R.layout.list_drawer_panel_main, null);

        TextView textView = (TextView) rowView.findViewById(R.id.list_drawer_panel_main_text);
        textView.setText(mMenuEntries[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_drawer_panel_main_image);
        imageView.setImageResource(R.mipmap.ic_launcher);

        return rowView;
    }
}
