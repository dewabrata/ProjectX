package com.projectx.main.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.projectx.main.R;
import com.projectx.main.RestUtil.APIClient;
import com.projectx.main.RestUtil.APIInterfacesRest;
import com.projectx.main.adapter.AdapterGridShopProductCard;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.utils.Tools;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingProductGrid extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;

    List<Merchandise> items;

    Vendor dataVendor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_product_grid);
        parent_view = findViewById(R.id.parent_view);

       // ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
       // com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);

        dataVendor =  getIntent().getParcelableExtra("data");
        if(dataVendor!=null) {
            getData();
        }

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);



        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Merchandise obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, Merchandise obj, MenuItem item) {
                Snackbar.make(parent_view, obj.getName() + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menu_cart_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }
        APIInterfacesRest apiInterface;
        ProgressDialog progressDialog;

    private void getData(){
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(ShoppingProductGrid.this);
        }
        progressDialog.setTitle("Loading");
        progressDialog.show();
        String child = "0";
        if (dataVendor.getHasChild()){
            child ="1";
        }else{
            child="0";
        }
        Call<List<Merchandise>> merchantCall = apiInterface.getListMerchant1(dataVendor.getId(),dataVendor.getCategoryId(),child,"20181303","0.0","0.0");
        merchantCall.enqueue(new Callback<List<Merchandise>>() {
            @Override
            public void onResponse(Call<List<Merchandise>> call, Response<List<Merchandise>> response) {
                progressDialog.dismiss();
               items = response.body();

                if (items !=null) {


                       initToolbar();
                       initComponent();


                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ShoppingProductGrid.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ShoppingProductGrid.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Merchandise>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}

