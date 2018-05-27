package com.projectx.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.projectx.main.R;
import com.projectx.main.RestUtil.AppUtil;
import com.projectx.main.activity.MainMenu;
import com.projectx.main.activity.PhoneActivation;
import com.projectx.main.modelservice.User.FirebaseFav;
import com.projectx.main.modelservice.User.UserMobile;
import com.projectx.main.modelservice.User.UserMobile_Table;
import com.projectx.main.modelservice.favourite.Cart;
import com.projectx.main.modelservice.favourite.Favourite;
import com.projectx.main.modelservice.favourite.Favourite_Table;
import com.projectx.main.modelservice.favourite.Star;
import com.projectx.main.modelservice.favourite.Star_Table;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.utils.ImageUtil;
import com.projectx.main.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class AdapterGridShopProductCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Merchandise> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }



    public AdapterGridShopProductCard(Context context, List<Merchandise> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;

        public View lyt_parent;
        public ImageButton imgStar,imgShop;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);

            imgStar = (ImageButton) v.findViewById(R.id.imgStar);
            imgShop = (ImageButton) v.findViewById(R.id.imgShop);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_product_card, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final Merchandise p = items.get(position);
            view.title.setText(p.getName());
            view.price.setText("Rp."+ AppUtil.formatCurrency(p.getPrice()));
            view.price.setPaintFlags(view.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
          //  Tools.displayImageOriginal(ctx, view.image, p.image);
            ImageUtil.displayImage(view.image,p.getThumbnailUrl(),null);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            List<Star> lstFav =  (ArrayList) SQLite.select().from(Star.class)
                    .where(Star_Table.merchantId.eq(items.get(position).getId()))
                    .queryList();

            if (lstFav.size()>0){
                view.imgStar.setImageResource(R.drawable.ic_star);
                view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100)));

            }

            view.imgShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart cart = new Cart(items.get(position).getId());
                    cart.save();
                }
            });

            view.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewx) {
                 if(view.imgStar.getImageTintList() == ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A200))){
                        view.imgStar.setImageResource(R.drawable.ic_star_border);
                        view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100)));
                     Star star = new Star(items.get(position).getId());
                     star.delete();
                    }else{
                     view.imgStar.setImageResource(R.drawable.ic_star);
                     view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A200)));
                     Star star = new Star(items.get(position).getId());
                     star.save();
                 }

                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Merchandise obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, Merchandise obj, MenuItem item);
    }



}