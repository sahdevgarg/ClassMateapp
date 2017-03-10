package com.htlconline.sm.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htlconline.sm.classmate.interfaces.*;


/**
 * Created by M82061 on 3/8/2017.
 */

public class PtmFragment extends Fragment implements com.htlconline.sm.classmate.interfaces.OnRecyclerItemClick {

    private View rootView;
    private RecyclerView ptmRecyclerView;
    private PtmListingAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ptm_layout, container, false);

        ptmRecyclerView = (RecyclerView)rootView.findViewById(R.id.ptm_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ptmRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PtmListingAdapter(getActivity(), this);
        ptmRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ptmRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int position, String title, String id) {

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(),PtmDetailActivity.class);
        startActivity(detailIntent);
        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
}
