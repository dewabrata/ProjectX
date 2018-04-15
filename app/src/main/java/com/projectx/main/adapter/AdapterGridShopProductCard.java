package com.projectx.main.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
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


import com.projectx.main.R;
import com.projectx.main.RestUtil.AppUtil;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterGridShopProductCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Merchandise> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterGridShopProductCard(Context context, List<Merchandise> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton more;
        public View lyt_parent;
        public ImageButton imgStar;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);
            more = (ImageButton) v.findViewById(R.id.more);
            imgStar = (ImageButton) v.findViewById(R.id.imgStar);
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

            view.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMoreButtonClickListener == null) return;
                    onMoreButtonClick(view, p);
                }
            });

            view.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewx) {
                 if(view.imgStar.getImageTintList() == ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100))){
                        view.imgStar.setImageResource(R.drawable.ic_favorite_border);
                        view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A200)));
                    }else{
                     view.imgStar.setImageResource(R.drawable.ic_favorites);
                     view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100)));
                 }

                }
            });
        }
    }

    private void onMoreButtonClick(final View view, final Merchandise p) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, p, item);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_product_more);
        popupMenu.show();
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