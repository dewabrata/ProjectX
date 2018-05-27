package com.projectx.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.projectx.main.R;
import com.projectx.main.adapter.AdapterGridShopProductCard;
import com.projectx.main.modelservice.favourite.Cart;
import com.projectx.main.modelservice.favourite.Star;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;

import java.util.ArrayList;
import java.util.List;


public class CardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Merchandise> lstMerchant;

    List<Cart> lstStar;
    private View myFragment;



    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;
    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
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
        myFragment = inflater.inflate(R.layout.fragment_card, container, false);

        initComponent();

        return myFragment;
    }

    // TODO: Rename method, update argument and hook method into UI event


    private void initComponent() {
        recyclerView = (RecyclerView) myFragment.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 8)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        lstStar = (ArrayList) SQLite.select().from(Cart.class)
                .queryList();

        String dataFav ="";
        for (int x = 0 ; x < lstStar.size() ;x ++){
            dataFav += "'"+lstStar.get(x).getMerchantId()+"',";

        }


        String datanya ="";
        if (dataFav.length()>0) {
            datanya = dataFav.substring(0, dataFav.length() - 1);
        }
        /*lstVendor =  (ArrayList) SQLite.select().from(Vendor.class)
                .where(Vendor_Table.id.in(datanya))
                .queryList();
*/
        String rawQuery = "SELECT * FROM Merchandise WHERE id in ( "+datanya+")";
        StringQuery<Merchandise> stringQuery = new StringQuery<>(Merchandise.class, rawQuery);
        lstMerchant = stringQuery.queryList();

        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(getContext(), lstMerchant);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Merchandise obj, int position) {
                Snackbar.make(myFragment, "Item " + obj.getName() + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });



    }





}
