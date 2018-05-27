package com.projectx.main.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.github.florent37.rxgps.RxGps;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.projectx.main.Application.AppController;
import com.projectx.main.R;
import com.projectx.main.adapter.AdapterGridSingleLine;
import com.projectx.main.adapter.AdapterGridSingleLineVendor;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Merchandise_Table;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.modelservice.vendor.Vendor_Table;
import com.projectx.main.utils.ImageUtil;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class VendorMenu extends AppCompatActivity {

    private static final String TAG ="Test" ;
    List<Vendor> lstCategory;
    FirebaseFirestore db;


    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterGridSingleLineVendor mAdapter;

    private List<String> listImage;


    private Runnable runnable = null;
    private Handler handler = new Handler();

    private  Category dataCategory;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_category_vendor);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);

        db = FirebaseFirestore.getInstance();

        parent_view = findViewById(R.id.parent_view);

        dataCategory =  getIntent().getParcelableExtra("data");

        if (dataCategory !=null){

            initToolbar();
            initComponent();
            getDataList();


        }else{


        }





    }

    private void getDataList(){
        db.collection("vendors")
                .whereEqualTo("categoryId", dataCategory.getId())
                .whereEqualTo("parentId","")
                .whereEqualTo("active",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            lstCategory = task.getResult().toObjects(Vendor.class);

                            savedb();



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }




    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.toolbarx);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(), 3)));
        recyclerView.setHasFixedSize(true);






    }

    List<Vendor> listItems;

    public void savedb(){

        FlowManager.getDatabase(AppController.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Vendor>() {
                            @Override
                            public void processModel(Vendor orderItem, DatabaseWrapper wrapper) {

                                orderItem.save();


                            }

                        }).addAll(lstCategory).build())  // add elements (can also handle multiple)
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
                        //set data and list adapter


                        listItems =  (ArrayList) SQLite.select().from(Vendor.class)
                                .where(Vendor_Table.categoryId.eq(dataCategory.getId()))
                                .queryList();


                        mAdapter = new AdapterGridSingleLineVendor(VendorMenu.this, listItems);
                        recyclerView.setAdapter(mAdapter);

                        // on item list clicked
                        mAdapter.setOnItemClickListener(new AdapterGridSingleLineVendor.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, Vendor obj, int position) {

                            }

                        });

                    }
                }).build().execute();


    }







}
