package com.projectx.main.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projectx.main.R;
import com.projectx.main.RestUtil.APIClient;
import com.projectx.main.RestUtil.APIInterfacesRest;
import com.projectx.main.modelservice.mapsvendor.MapsVendor;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.utils.Tools;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetMap extends AppCompatActivity {

    private GoogleMap mMap;
    private BottomSheetBehavior bottomSheetBehavior;

    private Vendor dataVendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_map);

        dataVendor =  getIntent().getParcelableExtra("data");

        if (dataVendor !=null) {
        getData();
        }
        Toast.makeText(this, "Swipe up bottom sheet", Toast.LENGTH_SHORT).show();
    }


    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    List<MapsVendor> items;



    private void getData(){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(BottomSheetMap.this);
        }
        progressDialog.setTitle("Loading");
        progressDialog.show();
        String child = "0";
        if (dataVendor.isHasChild()){
            child ="1";
        }else{
            child="0";
        }
        Call<List<MapsVendor>> merchantCall = apiInterface.getMapVendorLocation(dataVendor.getId(),dataVendor.getCategoryId(),child,dataVendor.getLat().toString(),dataVendor.getLon().toString());
        merchantCall.enqueue(new Callback<List<MapsVendor>>() {
            @Override
            public void onResponse(Call<List<MapsVendor>> call, Response<List<MapsVendor>> response) {
                progressDialog.dismiss();
                items = response.body();

                if (items !=null) {


                    initMapFragment();
                    initComponent();



                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(BottomSheetMap.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(BottomSheetMap.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<MapsVendor>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }

    private void initComponent() {

        TextView txtAlamat = (TextView)findViewById(R.id.txtAlamat);
        txtAlamat.setText(dataVendor.getName());

        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_directions)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                try {
                    mMap.animateCamera(zoomingLocation());
                } catch (Exception e) {
                }
            }
        });


    }

    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = Tools.configActivityMaps(googleMap);
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(dataVendor.getLat(),dataVendor.getLon()));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(zoomingLocation());
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        try {
                            mMap.animateCamera(zoomingLocation());
                        } catch (Exception e) {
                        }
                        return true;
                    }
                });
            }
        });
    }

    private CameraUpdate zoomingLocation() {
        return CameraUpdateFactory.newLatLngZoom(new LatLng(dataVendor.getLat(),dataVendor.getLon()), 13);
    }

}
