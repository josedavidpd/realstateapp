package com.st.jdpolonio.inmobiliapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListMyFavouritesListener;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;

import java.util.List;


public class MyFavouritesRecyclerViewAdapter extends RecyclerView.Adapter<MyFavouritesRecyclerViewAdapter.ViewHolder> {

    private final List<PropertyResponse> mValues;
    private final OnListMyFavouritesListener mListener;
    private Context ctx;

    public MyFavouritesRecyclerViewAdapter(Context ctx, int layout, List<PropertyResponse> items, OnListMyFavouritesListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myfavourites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.address_fav.setText(holder.mItem.getAddress());
        holder.price_fav.setText(String.valueOf(holder.mItem.getPrice()));
        holder.rooms_fav.setText(String.valueOf(holder.mItem.getRooms()));
        holder.size_fav.setText(String.valueOf(holder.mItem.getSize()));

        holder.photo_fav.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (holder.mItem.getPhotos() == null) {


            Glide.with(ctx).load("http://www.cafonline.com/Portals/0/Images/no-available-image.png").into(holder.photo_fav);
        } else {

            Glide.with(ctx).load(holder.mItem.getPhotos().get(0)).into(holder.photo_fav);
        }

        holder.cardViewFavProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickPropfav(holder.mItem);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView address_fav, price_fav, rooms_fav, size_fav;
        public final ImageView photo_fav;
        public final CardView cardViewFavProperties;
        public PropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            address_fav = view.findViewById(R.id.my_property_address);
            price_fav = view.findViewById(R.id.my_property_price);
            rooms_fav = view.findViewById(R.id.my_property_rooms);
            size_fav = view.findViewById(R.id.my_property_size);
            photo_fav = view.findViewById(R.id.my_property_photo);
            cardViewFavProperties = view.findViewById(R.id.cardViewFavProperties);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
