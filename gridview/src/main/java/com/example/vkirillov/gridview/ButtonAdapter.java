package com.example.vkirillov.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by vkirillov on 02.10.2015.
 */
public class ButtonAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Product> mProducts;

    public ButtonAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //Old view does not exist
        if(convertView == null){
            //Create or inflate view
            ButtonWithPreservedSize button = new ButtonWithPreservedSize(mContext);
            convertView = button;
            //Create holder
            holder = new ViewHolder();
            //Init holder with view controls
            //Viewholder keeps a reference to the convertView control
            holder.button = button;
            //Bind holder to the view
            convertView.setTag(holder);
        }else{
            //Reuse the existing view
            holder = (ViewHolder) convertView.getTag();
        }

        //Set holder data
        holder.button.setText(mProducts.get(position).getText());
        holder.button.setId(position);

        return convertView;
    }

    static class ViewHolder{
        public ButtonWithPreservedSize button;
    }
}
