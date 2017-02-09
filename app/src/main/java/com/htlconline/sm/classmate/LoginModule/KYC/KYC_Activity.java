package com.htlconline.sm.classmate.LoginModule.KYC;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.htlconline.sm.classmate.CustomRequests.CustomGetRequest;
import com.htlconline.sm.classmate.MainActivity;
import com.htlconline.sm.classmate.R;
import com.htlconline.sm.classmate.Student.StudentListingModel;
import com.htlconline.sm.classmate.interfaces.Communication;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Shikhar Garg on 26-12-2016.
 */
public class KYC_Activity extends AppCompatActivity implements Communication,View.OnClickListener{
    private LinearLayout linearlayout;
    private ImageView dot1,dot2,dot3,dot4,dot5;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ImageView forward_header,previous_header;
    private HashMap<String,Integer> fragmentmap;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_activity);

        fragmentmap=new HashMap<String,Integer>();
        fill_fragmentmap();

        linearlayout = (LinearLayout) findViewById(R.id.l1);

        dot1=(ImageView)findViewById(R.id.c1);
        dot2=(ImageView)findViewById(R.id.c2);
        dot3=(ImageView)findViewById(R.id.c3);
        dot4=(ImageView)findViewById(R.id.c4);
        dot5=(ImageView)findViewById(R.id.c5);

        forward_header=(ImageView)findViewById(R.id.next_header_kyc);
        previous_header=(ImageView)findViewById(R.id.previous_header_kyc);

        forward_header.setOnClickListener(this);
        previous_header.setOnClickListener(this);

        fm = getFragmentManager();
        ft = fm.beginTransaction();


        KYC_Basic_fragment kbc = new KYC_Basic_fragment(this);
        ft.replace(R.id.l1, kbc).commit();
        currentFragment=kbc;

        request();
    }

    private void fill_fragmentmap() {
        fragmentmap.put(new KYC_Basic_fragment(this).getClass().getName(),1);
        fragmentmap.put(new KYC_School(this).getClass().getName(),2);
        fragmentmap.put(new KYC_Home(this).getClass().getName(),3);
        fragmentmap.put(new KYC_Father_Info(this).getClass().getName(),4);
        fragmentmap.put(new KYC_Mother_Info(this).getClass().getName(),5);
    }

    private void request() {
        String url = "http://www.htlconline.com/api/student_listing/";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        CustomGetRequest customGetRequest=new CustomGetRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                StudentListingModel slm=gson.fromJson(response.toString(),StudentListingModel.class);
                Log.i("gson_slm   ","count:"+slm.getCount());
                Log.i("gson_slm   ","total pages:"+slm.getTotal_pages());
                Log.i("gson_slm   ","next:"+slm.getNext());
                for(StudentListingModel.Results r:slm.getResults() ){
                    Log.i("name",r.getName());
                }
                }
            },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                },getApplicationContext());
        mRequestQueue.add(customGetRequest);

    }

    public void clicked_previous_header(int pos) {
        switch (pos) {
            case 1:
                break;
            case 2:
                dot2.setImageResource(R.drawable.not_filled_circle);
                KYC_Basic_fragment kycf = new KYC_Basic_fragment(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycf).commit();
                currentFragment = kycf;
                break;
            case 3:
                dot3.setImageResource(R.drawable.not_filled_circle);
                KYC_School kycs = new KYC_School(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycs).commit();
                currentFragment = kycs;
                break;
            case 4:
                dot4.setImageResource(R.drawable.not_filled_circle);
                KYC_Home kych = new KYC_Home(this);
                fm.beginTransaction().replace(currentFragment.getId(), kych).commit();
                currentFragment = kych;
                break;
            case 5:
                dot5.setImageResource(R.drawable.not_filled_circle);
                KYC_Father_Info kycfi = new KYC_Father_Info(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycfi).commit();
                currentFragment = kycfi;
                break;
        }
    }
    public void clicked_next_header(int pos) {
        switch(pos){
            case 1:
                dot1.setImageResource(R.drawable.filledcirclered);
                KYC_School kycs=new KYC_School(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycs).commit();
                currentFragment=kycs;
                break;
            case 2:
                dot2.setImageResource(R.drawable.filledcirclered);
                KYC_Home kych=new KYC_Home(this);
                fm.beginTransaction().replace(currentFragment.getId(), kych).commit();
                currentFragment=kych;
                break;
            case 3:
                dot3.setImageResource(R.drawable.filledcirclered);
                KYC_Father_Info kycf=new KYC_Father_Info(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycf).commit();
                currentFragment=kycf;
                break;
            case 4:
                dot4.setImageResource(R.drawable.filledcirclered);
                KYC_Mother_Info kycm=new KYC_Mother_Info(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycm).commit();
                currentFragment=kycm;
                break;
            case 5:break;
        }
    }

    @Override
    public void clicked_next(int pos) {
        switch(pos){
            case 1:
                dot1.setImageResource(R.drawable.filledcirclered);
                KYC_School kycs=new KYC_School(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycs).commit();
                currentFragment=kycs;
                break;
            case 2:
                dot2.setImageResource(R.drawable.filledcirclered);
                KYC_Home kych=new KYC_Home(this);
                fm.beginTransaction().replace(currentFragment.getId(), kych).commit();
                currentFragment=kych;
                break;
            case 3:
                dot3.setImageResource(R.drawable.filledcirclered);
                KYC_Father_Info kycf=new KYC_Father_Info(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycf).commit();
                currentFragment=kycf;
                break;
            case 4:
                dot4.setImageResource(R.drawable.filledcirclered);
                KYC_Mother_Info kycm=new KYC_Mother_Info(this);
                fm.beginTransaction().replace(currentFragment.getId(), kycm).commit();
                currentFragment=kycm;
                break;
            case 5:
                dot5.setImageResource(R.drawable.filledcirclered);
                startActivity(new Intent(KYC_Activity.this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.next_header_kyc:
                this.clicked_next_header(fragmentmap.get(currentFragment.getClass().getName()));
                break;
            case R.id.previous_header_kyc:
                this.clicked_previous_header(fragmentmap.get(currentFragment.getClass().getName()));
                break;
        }
    }
}
