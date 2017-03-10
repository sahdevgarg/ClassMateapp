package com.htlconline.sm.classmate;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.Data.BatchListData;
import com.htlconline.sm.classmate.Schedule.widget.Model;
import com.htlconline.sm.classmate.interfaces.OnRecyclerItemClick;

import java.util.List;

import static com.htlconline.sm.classmate.R.id.academic_year;


/**
 * Created by Anurag on 03-01-2017.
 */

public class PtmListingAdapter extends RecyclerView.Adapter<PtmListingAdapter.ListHolder> {


    private OnRecyclerItemClick onRecyclerItemClick;
    private Context context;
   // private OnClickListItem clickListener;
    private View view;
    private LayoutInflater inflator;

    public PtmListingAdapter(FragmentActivity activity, OnRecyclerItemClick onRecyclerItemClick) {
        this.context = activity;
        this.onRecyclerItemClick = onRecyclerItemClick;
        this.inflator = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public PtmListingAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflator.inflate(R.layout.ptm_recycler_item, parent, false);
        PtmListingAdapter.ListHolder viewHolder = new PtmListingAdapter.ListHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PtmListingAdapter.ListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

//    public void setClickListener(BatchListFragment batchListFragment) {
//        this.clickListener = batchListFragment;
//    }


    public class ListHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView subjectName;
        private TextView ptmDate;
        private TextView ptmType;
        private TextView ptmTime;
        private TextView createdBy;
        private TextView batchName;
        private TextView description;

        public ListHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            subjectName = (TextView) itemView.findViewById(R.id.subjectName);
//            ptmDate = (TextView) itemView.findViewById(R.id.ptmDate);
//            ptmType = (TextView) itemView.findViewById(R.id.ptmType);
            ptmTime = (TextView) itemView.findViewById(R.id.ptmTime);
            createdBy = (TextView) itemView.findViewById(R.id.ptmCreatedBy);
            batchName = (TextView) itemView.findViewById(R.id.batchId);
            description = (TextView) itemView.findViewById(R.id.ptmDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    BatchListData.Results result = results.get(getAdapterPosition());
//                    String title = result.getDisplay_name();
//                    Model.setBatch_id(String.valueOf(result.getId()));
//                    //clickListener.onClick(view, title);
                    onRecyclerItemClick.onItemClick(getAdapterPosition());
                }
            });
        }

    }

//    public interface OnClickListItem {
//        void onClick(View view, String title);
//    }
}
