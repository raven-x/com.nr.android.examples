package com.example.vkirillov.endlessrecycler.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vkirillov.endlessrecycler.R;
import com.example.vkirillov.endlessrecycler.model.Model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Adapter for endless recycler adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    private static final int VIEW_ITEM = 0;
    private static final int PROGRESS_ITEM = 1;

    /**Reference to model*/
    private final List<Model> mModel;
    private AtomicBoolean mLoading;
    /**Then the difference between model_item total count and current visible model_item position
     * reaches the threshold the new data page loading must be launched (5 by default)*/
    private int mVisibleThreshold = 5;
    private ILoadListener mOnLoadListener;
    private int mCurrentPage;

    @Override
    public int getItemViewType(int position) {
        return mModel.get(position) == null ? PROGRESS_ITEM : VIEW_ITEM;
    }

    /**
     * Constructor
     * @param recyclerView recyclerViewer
     * @param model data model
     */
    public RecyclerAdapter(RecyclerView recyclerView, List<Model> model) {
        mLoading = new AtomicBoolean();
        mModel = model;
        recyclerView.addOnScrollListener(new EndlessScrollListener());
    }

    public void setVisibleThreshold(int visibleThreshold){
        mVisibleThreshold = visibleThreshold;
    }

    public void setOnLoadListener(ILoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
            return new ItemViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            return new ProgressBarViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            ItemViewHolder evh = (ItemViewHolder) holder;
            Model entry = mModel.get(position);
            evh.title.setText(entry.getTitle());
            evh.content.setText(entry.getContent());
        }else{
            ProgressBarViewHolder pbvh = (ProgressBarViewHolder) holder;
            pbvh.progressBar.setIndeterminate(true);
        }
    }

    /**
     * Notifies adapter about loading completion
     */
    public void setLoaded(){
        mLoading.set(false);
    }

    /**
     * Item view holder
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    /**
     * Progress bar view holder
     */
    public static class ProgressBarViewHolder extends RecyclerView.ViewHolder{
        public final ProgressBar progressBar;

        public ProgressBarViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private int lastVisibleItemPos;
        private int totalItemCount;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            totalItemCount = layoutManager.getItemCount();
            lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();

            if(!mLoading.get() && (totalItemCount <= lastVisibleItemPos + mVisibleThreshold)){
                mLoading.set(true);
                if(mOnLoadListener != null){
                    //Load data
                    mOnLoadListener.onLoad(mCurrentPage++);
                }
            }
        }

    }
}
