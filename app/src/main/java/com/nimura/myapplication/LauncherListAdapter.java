package com.nimura.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nimura.model.AppEntry;

/**
 * Created by Nimura on 11.06.2015.
 */
public class LauncherListAdapter extends ArrayAdapter<AppEntry> {
    private final AppEntry[] model;
    private LayoutInflater inflater;

    public LauncherListAdapter(Activity activity, AppEntry[] model) {
        super(activity, R.layout.activity_app_launcher, model);
        this.model = model;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row = convertView;
        if(convertView == null){
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.grid_list_item_layout, parent, false);
            holder.image = (ImageView)row.findViewById(R.id.appImage);
            holder.text = (TextView) row.findViewById(R.id.appText);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }

        AppEntry entry = model[position];
        holder.image.setImageDrawable(entry.getIcon());
        holder.text.setText(entry.getLabel());

        return row;
    }

    private class ViewHolder{
        public ImageView image;
        public TextView text;
    }
}