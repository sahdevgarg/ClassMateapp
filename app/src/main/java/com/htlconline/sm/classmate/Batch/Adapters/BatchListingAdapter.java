package com.htlconline.sm.classmate.Batch.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchListFragment;
import com.htlconline.sm.classmate.Batch.Data.BatchListData;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.interfaces.OnRecyclerItemClick;

import java.util.List;


/**
 * Created by Anurag on 03-01-2017.
 */

public class BatchListingAdapter extends RecyclerView.Adapter<BatchListingAdapter.ListHolder> {


    private OnRecyclerItemClick onRecyclerItemClick;
    private Context context;
   // private OnClickListItem clickListener;
    private List<BatchListData.Results> results;
    private View view;
    private LayoutInflater inflator;

    public BatchListingAdapter(FragmentActivity activity, OnRecyclerItemClick onRecyclerItemClick, List<BatchListData.Results> results) {
        this.context = activity;
        this.results = results;
        this.onRecyclerItemClick = onRecyclerItemClick;
        this.inflator = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public BatchListingAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflator.inflate(R.layout.batch_list_content, parent, false);
        BatchListingAdapter.ListHolder viewHolder = new BatchListingAdapter.ListHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BatchListingAdapter.ListHolder holder, int position) {
        BatchListData.Results result = results.get(position);
        holder.batchList.setText(result.getDisplay_name());
        holder.batch_centre.setText(result.getCentre());
        holder.academic_year.setText(result.getAcademic_year());
        holder.subject.setText(result.getSubject());
        holder.batch_product.setText(result.getProduct());
        holder.next_class.setText(result.getNext_class());
        holder.instructor.setText(result.getInstructor());


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

//    public void setClickListener(BatchListFragment batchListFragment) {
//        this.clickListener = batchListFragment;
//    }


    public class ListHolder extends RecyclerView.ViewHolder {
        private TextView instructor;
        private TextView batchList;
        private TextView batch_centre;
        private TextView batch_product, next_class;
        private CardView cardView;
        private TextView academic_year;
        private TextView subject;

        public ListHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            batch_product = (TextView) itemView.findViewById(R.id.batch_list_product);
            batchList = (TextView) itemView.findViewById(R.id.batch_list_name);
            batch_centre = (TextView) itemView.findViewById(R.id.batch_list_centre);
            next_class = (TextView) itemView.findViewById(R.id.batch_list_next_class);
            academic_year = (TextView) itemView.findViewById(R.id.academic_year);
            subject = (TextView) itemView.findViewById(R.id.batch_list_subject);
            instructor = (TextView) itemView.findViewById(R.id.batch_list_instructor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BatchListData.Results result = results.get(getAdapterPosition());
                    String title = result.getDisplay_name();
                    Model.setBatch_id(String.valueOf(result.getId()));
                    //clickListener.onClick(view, title);
                    onRecyclerItemClick.onItemClick(getAdapterPosition(),result.getDisplay_name(),result.getId());
                }
            });
        }

    }

//    public interface OnClickListItem {
//        void onClick(View view, String title);
//    }
}
