package com.htlconline.sm.classmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by M82061 on 2/21/2017.
 */

public class ProgramFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.program_layout, container, false);

        return rootView;
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
