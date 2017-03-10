package com.htlconline.sm.classmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M82061 on 3/2/2017.
 */

public class AnnouncementFragment extends Fragment implements OnRecyclerItemClick {

    private View rootView;
    private RecyclerView announcementRecyclerView;
    private AnnouncementRecyclerViewAdapter mAdapter;
    private RecyclerView reportCardRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.announcement_layout, container, false);

        announcementRecyclerView = (RecyclerView)rootView.findViewById(R.id.announcementRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        announcementRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AnnouncementRecyclerViewAdapter(getActivity(), this);
        announcementRecyclerView.setItemAnimator(new DefaultItemAnimator());
        announcementRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onItemClick(int position) {

    }
}
