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
import com.projectx.main.Application.AppController;
import com.projectx.main.R;
import com.projectx.main.RestUtil.APIClient;
import com.projectx.main.RestUtil.APIInterfacesRest;
import com.projectx.main.adapter.AdapterGridShopProductCard;
import com.projectx.main.modelservice.User.UserMobile;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Merchandise_Table;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingProductGrid extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;

    List<Merchandise> items, listItems;

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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.toolbarx);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        listItems =  (ArrayList) SQLite.select().from(Merchandise.class)
                .where(Merchandise_Table.vendorId.eq(dataVendor.getId()))
                     .queryList();

        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(this, listItems);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Merchandise obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
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
        if (dataVendor.isHasChild()){
            child ="1";
        }else{
            child="0";
        }

        String nophone = "";
        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();



        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }
        Call<List<Merchandise>> merchantCall = apiInterface.getListMerchant1(dataVendor.getId(),dataVendor.getCategoryId(),child,Tools.getDateNow(),Tools.latitude,Tools.longitude,nophone);
        merchantCall.enqueue(new Callback<List<Merchandise>>() {
            @Override
            public void onResponse(Call<List<Merchandise>> call, Response<List<Merchandise>> response) {
                progressDialog.dismiss();
                items = response.body();

                if (items !=null) {



                       savedb();



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


    public void savedb(){

        FlowManager.getDatabase(AppController.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Merchandise>() {
                            @Override
                            public void processModel(Merchandise orderItem, DatabaseWrapper wrapper) {

                                orderItem.save();


                            }

                        }).addAll(items).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        Toast.makeText(getApplicationContext(),"Data Tersimpan",Toast.LENGTH_LONG).show();
                        initToolbar();
                        initComponent();
                    }
                }).build().execute();


    }



}

