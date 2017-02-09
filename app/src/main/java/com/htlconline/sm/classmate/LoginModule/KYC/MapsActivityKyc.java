package com.htlconline.sm.classmate.LoginModule.KYC;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.htlconline.sm.classmate.R;

public class MapsActivityKyc extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener,GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_kyc);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient=new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();
        PlaceAutocompleteFragment mPlaceAutocompleteFragment=(PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("KYC_place",""+place.getLatLng());
                add_marker(mMap,place.getLatLng());
            }

            @Override
            public void onError(Status status) {

            }
        });
        confirm=(Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)  {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        add_marker(mMap,sydney);
    }

    public void add_marker(GoogleMap mMap,LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setOnMarkerDragListener(this);
    }
     @Override
    public void onMarkerDragStart(Marker marker) {
        Log.i("KYC_status:","Drag started");
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng mLatLng=null;
        Log.i("KYC_status","Drag Ended");
        mLatLng=new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
        Log.i("KYC_latlng",""+mLatLng);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirm:
                Intent data=new Intent();
                //data.putExtra()
                Log.i("INtent","intent2");
                setResult(Activity.RESULT_OK, data);
                finish();
                break;
        }
    }
}
