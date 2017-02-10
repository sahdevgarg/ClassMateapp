package com.htlconline.sm.classmate.Batch;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchListFragment;
import com.htlconline.sm.classmate.R;

import java.util.List;



/**
 * Created by Anurag on 03-01-2017.
 */

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.ListHolder> {

    private Context context;
    private OnClickListItem clickListener;
    private List<TestClass.Results> results;
    private View view;
    private LayoutInflater inflator;
    public BatchListAdapter(FragmentActivity activity, List<TestClass.Results> results) {
        this.context = activity;
        this.results = results;
        this.inflator = LayoutInflater.from(activity);

    }

    @Override
    public BatchListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflator.inflate(R.layout.batch_list_content,parent,false);
        BatchListAdapter.ListHolder viewHolder = new BatchListAdapter.ListHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BatchListAdapter.ListHolder holder, int position) {

        for(int i=0;i<results.size();i++)
        {
            //Log.d("Test pos",position+"");
            TestClass.Results result = results.get(position);
            holder.batchList.setText(result.getDisplay_name());
        }

    }

    @Override
    public int getItemCount() {
        //Log.d("Test",results.size()+"");
        return results.size();
    }

    public void setClickListener(OnClickListItem batchListFragment) {
         this.clickListener =batchListFragment;
    }


    public class ListHolder extends RecyclerView.ViewHolder
    {
        private TextView batchList;
        public ListHolder(View itemView) {
            super(itemView);
            batchList = (TextView) itemView.findViewById(R.id.batch_list_name);
            batchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      TestClass.Results result= results.get(getAdapterPosition());

                      String title = result.getDisplay_name();

                      clickListener.onClick(view, title);
                }
            });
        }

    }

    public interface OnClickListItem
    {
        void onClick(View view, String title);
    }
}
