package com.projectx.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectx.main.R;
import com.projectx.main.modelservice.category.Category;
import com.projectx.main.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterListShopCategoryImg extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Category obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListShopCategoryImg(Context context, List<Category> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_bg;
        public TextView title;
        public Button brief;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image_bg = (ImageView) v.findViewById(R.id.image_bg);
            title = (TextView) v.findViewById(R.id.title);
            brief = (Button) v.findViewById(R.id.brief);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_category_image, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Category p = items.get(position);
            view.title.setText(p.getName());
            ImageUtil.displayImage(view.image_bg,p.getImageUrl(),null);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}