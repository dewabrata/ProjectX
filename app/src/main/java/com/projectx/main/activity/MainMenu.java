package com.projectx.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.projectx.main.R;
import com.projectx.main.adapter.AdapterGridSingleLine;
import com.projectx.main.fragment.CardFragment;
import com.projectx.main.fragment.CategoryFragment;
import com.projectx.main.fragment.FavouriteFragment;
import com.projectx.main.fragment.StarFragment;
import com.projectx.main.modelservice.User.UserMobile;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.utils.ImageUtil;
import com.projectx.main.utils.Messagebox;
import com.projectx.main.utils.Tools;
import com.projectx.main.utils.ViewAnimation;
import com.raizlabs.android.dbflow.sql.language.SQLite;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainMenu extends AppCompatActivity {

    private static final String TAG ="Test" ;
    List<Category> lstCategory;
    FirebaseFirestore db;

    private View parent_view;


    private AdapterGridSingleLine mAdapter;

    private ViewPager viewPagerx;


    private List<String> listImage;


    private Runnable runnable = null;
    private Handler handler = new Handler();

    private TabLayout tab_layout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_category_image);

        db = FirebaseFirestore.getInstance();
        viewPagerx = (ViewPager) findViewById(R.id.viewpager);
        setGPS();
        setupViewPager(viewPagerx);
        initToolbar();
        initTab();




        parent_view = findViewById(R.id.parent_view);


        getCommercialList();

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CategoryFragment(), "Category");
        adapter.addFrag(new FavouriteFragment(), "Fav");
        adapter.addFrag(new CardFragment(), "Cart");
        adapter.addFrag(new StarFragment(), "Star");
        viewPagerx.setAdapter(adapter);
    }

    private void getCommercialList(){
        db.collection("commercial")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listImage = new ArrayList<String>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listImage.add(document.getData().get("imageUrl").toString());
                            }

                            if(listImage.size()>0){


                                setHeaderSlide();


                            }





                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

    private ActionBar actionBar;
    Toolbar toolbar;
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home);
      //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Categories");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.toolbarx);
    }



    private ViewPager viewPager;

    private AdapterImageSlider adapterImageSlider;
    public void setHeaderSlide(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(MainMenu.this, listImage);


        adapterImageSlider.setItems(listImage);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());
    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<String> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, String obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<String> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public String getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            ImageUtil.displayImage(image,items.get(position),null);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, items.get(position));
                    }
                }
            });

            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }

    @Override
    public void onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }


    public void initTab(){
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(viewPagerx);

        tab_layout.getTabAt(0).setIcon(R.drawable.ic_home);
        tab_layout.getTabAt(1).setIcon(R.drawable.ic_favorites);
        tab_layout.getTabAt(2).setIcon(R.drawable.ic_shopping_cart);
        tab_layout.getTabAt(3).setIcon(R.drawable.ic_star);



        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);


        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.deep_orange_500), PorterDuff.Mode.SRC_IN);
                //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
                switch (tab.getPosition()) {
                    case 0:
                        actionBar.setTitle("Categories");
                        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case 1:
                        actionBar.setTitle("Favourites");
                        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case 2:
                        actionBar.setTitle("Cart");
                        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case 3:
                        actionBar.setTitle("Wishlist");
                        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.overlay_light_90), PorterDuff.Mode.SRC_ATOP);
                        break;

                }

                ViewAnimation.fadeOutIn(viewPagerx);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
          //  return mFragmentTitleList.get(position);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Messagebox.showDialog(MainMenu.this, "Exit Application?", new String[]{"Yes", "No "}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();

                if (i == 0) {

                   updateStatus(false);
                }
            }
        });
    }


    public void updateStatus(boolean online){

        String nophone = "";
        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();



        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }

        DocumentReference updateStatus = db.collection("mobileUsers").document(nophone);


        updateStatus
                .update("online", online)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Update status failed",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
    }



    public void setGPS(){
        final RxGps rxGps = new RxGps(this);


    }

    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
