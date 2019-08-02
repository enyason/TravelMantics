package com.enyason.travelmantics.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.enyason.travelmantics.Model.TravelDeal;
import com.enyason.travelmantics.R;
import com.enyason.travelmantics.utils.FirebaseUtil;
import com.enyason.travelmantics.view.InsertActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {

    ArrayList<TravelDeal> deals;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageDeal;

    public DealAdapter() {
//
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        deals = FirebaseUtil.mDeals;
//
        mChildListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TravelDeal td = dataSnapshot.getValue(TravelDeal.class);
                td.setId(dataSnapshot.getKey());
                Log.e("Deal: ", td.getTitle());
                deals.add(td);
                notifyItemInserted(deals.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TravelDeal td = dataSnapshot.getValue(TravelDeal.class);
                td.setId(dataSnapshot.getKey());
                Log.e("Deal: ", td.getTitle());
                Log.e("Deal: ", td.getImageUrl());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);


    }

    @Override
    public DealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_item, parent, false);
        return new DealViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(DealAdapter.DealViewHolder holder, int position) {
        TravelDeal deal = deals.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        if (deals == null) {
            return 0;
        }
        return deals.size();
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvPrice;

        public DealViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imageDeal = itemView.findViewById(R.id.image_view_deal);
            itemView.setOnClickListener(this);
        }

        public void bind(TravelDeal deal) {
            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            tvPrice.setText("$"+deal.getPrice());
            showImage(deal.getImageUrl());

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            TravelDeal selectedDeal = deals.get(position);
            Intent intent = new Intent(view.getContext(), InsertActivity.class);
            intent.putExtra("Deal", selectedDeal);
            view.getContext().startActivity(intent);
        }

//        private void showImage(String url) {
//            if (url != null && url.isEmpty() == false) {
//                Picasso.get().load(url)
//                        .resize(160, 160)
//                        .centerCrop()
//                        .into(imageDeal);
//            }
//        }

        private void showImage(String url) {
            if (url != null && url.isEmpty() == false) {
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                Picasso.get()
                        .load(url)
                        .resize(width, width * 2 / 3)
                        .centerCrop()
                        .into(imageDeal);
            }
        }
    }


}
