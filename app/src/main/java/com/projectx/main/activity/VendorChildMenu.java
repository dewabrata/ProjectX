package com.projectx.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.projectx.main.R;
import com.projectx.main.adapter.AdapterGridSingleLineVendor;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.utils.Tools;

import java.util.List;


public class VendorChildMenu extends AppCompatActivity {

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
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            lstCategory = task.getResult().toObjects(Vendor.class);
                            //set data and list adapter
                            mAdapter = new AdapterGridSingleLineVendor(VendorChildMenu.this, lstCategory);
                            recyclerView.setAdapter(mAdapter);

                            // on item list clicked
                            mAdapter.setOnItemClickListener(new AdapterGridSingleLineVendor.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, Integer obj, int position) {

                                }

                        });




                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }




    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(), 3)));
        recyclerView.setHasFixedSize(true);






    }





}
