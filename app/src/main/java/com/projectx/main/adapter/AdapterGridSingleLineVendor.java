package com.projectx.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.projectx.main.R;

import com.projectx.main.RestUtil.AppUtil;
import com.projectx.main.activity.BottomSheetMap;
import com.projectx.main.activity.ShoppingProductGrid;
import com.projectx.main.activity.VendorMenu;
import com.projectx.main.modelservice.User.FirebaseFav;
import com.projectx.main.modelservice.User.UserMobile;
import com.projectx.main.modelservice.favourite.Favourite;
import com.projectx.main.modelservice.favourite.Favourite_Table;
import com.projectx.main.modelservice.vendor.Merchandise;
import com.projectx.main.modelservice.vendor.Vendor;
import com.projectx.main.modelservice.vendor.Vendor_Table;
import com.projectx.main.utils.ImageUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterGridSingleLineVendor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Vendor> items = new ArrayList<>();

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Vendor  obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterGridSingleLineVendor(Context context, List<Vendor> items) {
        this.items = items;
        ctx = context;
    }



    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;

        public View lyt_parent;
        public ImageButton imgStar;
         public ImageButton  pindrop;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);

            imgStar = (ImageButton) v.findViewById(R.id.imgShop);
            pindrop = (ImageButton) v.findViewById(R.id.pindrop);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendor_product_card, parent, false);
        vh = new AdapterGridSingleLineVendor.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterGridSingleLineVendor.OriginalViewHolder) {
            final AdapterGridSingleLineVendor.OriginalViewHolder view = (AdapterGridSingleLineVendor.OriginalViewHolder) holder;

            final Vendor p = items.get(position);
            view.title.setText(p.getName());

            view.pindrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent  = new Intent(ctx, BottomSheetMap.class);
                    intent.putExtra("data",(Parcelable)items.get(position));
                    ctx.startActivity(intent);
                }
            });

            ImageUtil.displayImage(view.image,p.getThumbnailUrl(),null);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                        Intent intent  = new Intent(ctx, ShoppingProductGrid.class);
                        intent.putExtra("data",(Parcelable)items.get(position));
                        ctx.startActivity(intent);
                    }
                }
            });


           List<Favourite> lstFav =  (ArrayList) SQLite.select().from(Favourite.class)
                    .where(Favourite_Table.vendorId.eq(items.get(position).getId()))
                    .queryList();

           if (lstFav.size()>0){
               view.imgStar.setImageResource(R.drawable.ic_favorites);
               view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100)));

           }

            view.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewx) {
                    if(view.imgStar.getImageTintList() == ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A200))){
                        view.imgStar.setImageResource(R.drawable.ic_favorites);
                        view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A100)));

                        Favourite fav = new Favourite(items.get(position).getId());
                        fav.save();

                        addFav(items.get(position));


                    }else{
                        view.imgStar.setImageResource(R.drawable.ic_favorite_border);
                        view.imgStar.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.red_A200)));
                        Favourite fav = new Favourite(items.get(position).getId());
                        fav.delete();
                        deleteFav(items.get(position));
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }


    public void addFav(final Vendor vendor , boolean increment){

        FirebaseFav ff = new FirebaseFav(vendor.getCategoryId(),vendor.getId(),vendor.getParentId(),"android",vendor.getId());
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();

        String nophone = "";

        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }

        final DocumentReference docRef = db.collection("statistic").document("favouriteVendors");

        docRef.collection(vendor.getId()).document(nophone)

                .set(ff, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                        Toast.makeText(ctx,"Add Favourite Success",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }});



    }
    String nophone = "";
    public void addFav(final Vendor vendor){
      final  FirebaseFav ff = new FirebaseFav(vendor.getCategoryId(),vendor.getId(),vendor.getParentId(),"android",vendor.getId());
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();



        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }



        final DocumentReference docRef = db.collection("statistic").document("favouriteVendors");

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(final Transaction transaction) throws FirebaseFirestoreException {



                docRef.collection(vendor.getId()).document(nophone)

                        .set(ff, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {



                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            if(documentSnapshot.getData().containsKey(vendor.getId())){
                                                double increment = documentSnapshot.getDouble(vendor.getId()) + 1;
                                                Map<String,Double> datax = new HashMap<String,Double>();
                                                datax.put(vendor.getId(),increment);
                                                docRef.set(datax,SetOptions.merge());
                                            }else{

                                                Map<String,Double> datax = new HashMap<String,Double>();
                                                datax.put(vendor.getId(),Double.parseDouble("1"));
                                                docRef.set(datax,SetOptions.merge());
                                            }
                                           // Map<String, Object> data = documentSnapshot.getData();
                                           // data.isEmpty();
                                        }
                                    });




                                }



                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }});




                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    public void deleteFav(final Vendor vendor ){
        final  FirebaseFav ff = new FirebaseFav(vendor.getCategoryId(),vendor.getId(),vendor.getParentId(),"android",vendor.getId());
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        List<UserMobile> lstUser =  (ArrayList) SQLite.select().from(UserMobile.class)
                .queryList();



        if (lstUser.size()>0){
            nophone = lstUser.get(0).getId();
        }



        final DocumentReference docRef = db.collection("statistic").document("favouriteVendors");

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(final Transaction transaction) throws FirebaseFirestoreException {



                docRef.collection(vendor.getId()).document(nophone)

                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {



                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        if(documentSnapshot.getData().containsKey(vendor.getId())){
                                            double increment = documentSnapshot.getDouble(vendor.getId()) - 1;
                                            Map<String,Double> datax = new HashMap<String,Double>();
                                            datax.put(vendor.getId(),increment);
                                            docRef.set(datax,SetOptions.merge());
                                        }else{

                                            Map<String,Double> datax = new HashMap<String,Double>();
                                            datax.put(vendor.getId(),Double.parseDouble("0"));
                                            docRef.set(datax,SetOptions.merge());
                                        }
                                        // Map<String, Object> data = documentSnapshot.getData();
                                        // data.isEmpty();
                                    }
                                });




                            }



                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }});




                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


}