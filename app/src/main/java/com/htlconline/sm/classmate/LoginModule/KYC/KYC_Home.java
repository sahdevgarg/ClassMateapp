package com.htlconline.sm.classmate.LoginModule.KYC;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.interfaces.Communication;

/**
 * Created by Shikhar Garg on 28-12-2016.
 */
public class KYC_Home extends Fragment {
    private Communication communication;
    private Button mNextButton,mShowMapButton;

    public KYC_Home(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kyc_home_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNextButton=(Button)view.findViewById(R.id.next_kyc_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communication.clicked_next(3);
            }
        });
        mShowMapButton=(Button) view.findViewById(R.id.showmap_kyc);
        mShowMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start=new Intent (getActivity(),MapsActivityKyc.class);
                startActivityForResult(start,0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("INtent", "intent1");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode== Activity.RESULT_OK){
                Log.i("INtent","intent11");
            }
        }
    }
}
