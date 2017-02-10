package com.htlconline.sm.classmate.Batch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.htlconline.sm.classmate.Batch.BatchFragments.BatchFilterFragment;
import com.htlconline.sm.classmate.Batch.BatchFragments.BatchListFragment;
import com.htlconline.sm.classmate.Batch.Data.BatchFilterData;
import com.htlconline.sm.classmate.Batch.Data.BatchListData;
import com.htlconline.sm.classmate.R;

/**
 * Created by Anurag on 15-01-2017.
 */

public class BatchFilterListAdapter extends ArrayAdapter {
    BatchFilterData[] dataItems = null;
    Context context;

    public BatchFilterListAdapter(Context context, BatchFilterData[] resource) {
        super(context, R.layout.batch_filter_row, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.dataItems = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.batch_filter_row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.choices);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
        name.setText(dataItems[position].getName());

        if (dataItems[position].getValue() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb.isChecked()) {
                    int index = BatchFilterFragment.map.get(dataItems[position].getName());
                    BatchFilterFragment.batchFilterData[index].setValue(1);


                } else {
                    int index = BatchFilterFragment.map.get(dataItems[position].getName());
                    BatchFilterFragment.batchFilterData[index].setValue(0);

                }

            }
        });

        return convertView;
    }
}
