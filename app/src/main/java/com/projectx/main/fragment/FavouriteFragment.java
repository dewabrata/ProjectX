package com.projectx.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projectx.main.R;
import com.projectx.main.adapter.AdapterGridShopProductCard;
import com.projectx.main.adapter.AdapterGridSingleLine;
import com.projectx.main.adapter.AdapterGridSingleLineVendor;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.modelservice.favourite.Favourite;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Merchandise_Table;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.modelservice.vendor.Vendor_Table;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Vendor> lstVendor;

    List<Favourite> lstFav;


    private RecyclerView recyclerView;
    private AdapterGridSingleLineVendor mAdapter;

    private List<String> listImage;


    private View myFragment;

   public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_favourite, container, false);

        initComponent();

        return myFragment;

    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();
    }

    private void initComponent() {
        recyclerView = (RecyclerView) myFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 8)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        lstFav = (ArrayList) SQLite.select().from(Favourite.class)
                .queryList();

        String dataFav ="";
        for (int x = 0 ; x < lstFav.size() ;x ++){
            dataFav += "'"+lstFav.get(x).getVendorId()+"',";

        }


        String datanya ="";
        if (dataFav.length()>0) {
           datanya = dataFav.substring(0, dataFav.length() - 1);
        }
        /*lstVendor =  (ArrayList) SQLite.select().from(Vendor.class)
                .where(Vendor_Table.id.in(datanya))
                .queryList();
*/
        String rawQuery = "SELECT * FROM Vendor WHERE id in ( "+datanya+")";
        StringQuery<Vendor> stringQuery = new StringQuery<>(Vendor.class, rawQuery);
        lstVendor = stringQuery.queryList();
        //set data and list adapter
        mAdapter = new AdapterGridSingleLineVendor(getContext(), lstVendor);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridSingleLineVendor.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Vendor obj, int position) {
                Snackbar.make(myFragment, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });



    }



}
