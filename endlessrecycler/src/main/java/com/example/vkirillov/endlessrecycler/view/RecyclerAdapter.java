package com.example.vkirillov.endlessrecycler.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vkirillov.endlessrecycler.R;
import com.example.vkirillov.endlessrecycler.model.Model;

import java.util.List;

/**
 * Adapter for endless recycler adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.EndlessViewHolder> {

    /**Reference to model*/
    private final List<Model> mModel;
    private boolean mLoading;
    /**Then the difference between item total count and current visible item position
     * reaches the threshold the new data page loading must be launched (5 by default)*/
    private int mVisibleThreshold = 5;
    private ILoadListener mOnLoadListener;
    private int mCurrentPage;

    /**
     * Constructor
     * @param recyclerView recyclerViewer
     * @param model data model
     */
    public RecyclerAdapter(RecyclerView recyclerView, List<Model> model) {
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
    public EndlessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new EndlessViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    @Override
    public void onBindViewHolder(EndlessViewHolder holder, int position) {
        EndlessViewHolder evh = holder;
        Model entry = mModel.get(position);
        evh.title.setText(entry.getTitle());
        evh.content.setText(entry.getContent());
    }

    /**
     * Notifies adapter about loading completion
     */
    public void setLoaded(){
        mLoading = false;
    }

    public static class EndlessViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;

        public EndlessViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
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

            if(!mLoading && (totalItemCount <= lastVisibleItemPos + mVisibleThreshold)){
                mLoading = true;
                if(mOnLoadListener != null){
                    //Load data
                    mOnLoadListener.onLoad(mCurrentPage++);
                }
            }
        }

    }
}
