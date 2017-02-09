package com.htlconline.sm.classmate.LoginModule.KYC;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.interfaces.Communication;

/**
 * Created by Shikhar Garg on 28-12-2016.
 */
public class KYC_School extends Fragment {
    private Communication communication;
    private Button mNextButton;

    public KYC_School(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kyc_school_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNextButton=(Button)view.findViewById(R.id.next_kyc_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communication.clicked_next(2);
            }
        });

    }
}
