package com.example.vkirillov.animatedrecycler.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vkirillov.animatedrecycler.R;
import com.example.vkirillov.animatedrecycler.model.Model;

import java.util.List;

/**
 * Adapter for endless recycler adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private Context mContext;
    /**Reference to model*/
    private final List<Model> mModel;

    /**
     * Constructor
     * @param model data model
     */
    public RecyclerAdapter(Context context, List<Model> model) {
        mContext = context;
        mModel = model;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Model entry = mModel.get(position);
        holder.image.setImageDrawable(
                ContextCompat.getDrawable(mContext, R.drawable.ic_settings_white_36dp));
        holder.title.setText(entry.getTitle());
    }

    /**
     * Item view holder
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
