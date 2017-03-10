package com.htlconline.sm.classmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M82061 on 2/10/2017.
 */

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_layout, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View toggleButton = getActivity().findViewById(R.id.toolbar_toggle_frame);
        View toolbarTitle = getActivity().findViewById(R.id.toolBarTitle);

        toolbarTitle.setVisibility(View.VISIBLE);
        toggleButton.setVisibility(View.GONE);
    }
}
