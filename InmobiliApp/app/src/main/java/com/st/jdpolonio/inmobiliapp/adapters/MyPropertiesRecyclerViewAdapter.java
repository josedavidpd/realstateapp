package com.st.jdpolonio.inmobiliapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentPropertiesListener;
import com.st.jdpolonio.inmobiliapp.models.Property;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.util.Util;

import java.util.List;


public class MyPropertiesRecyclerViewAdapter extends RecyclerView.Adapter<MyPropertiesRecyclerViewAdapter.ViewHolder> {

    private final List<PropertyResponse> mValues;
    private final OnListFragmentPropertiesListener mListener;
    private Context ctx;

    public MyPropertiesRecyclerViewAdapter(Context context, int layout, List<PropertyResponse> items, OnListFragmentPropertiesListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_properties, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        double price = holder.mItem.getPrice();
        float size = holder.mItem.getSize();
        int rooms = holder.mItem.getRooms();
        holder.address.setText(holder.mItem.getAddress());
        holder.price.setText(String.valueOf(price));
        holder.size.setText(String.valueOf(size));
        holder.rooms.setText(String.valueOf(rooms));

        /**Para ocultar el icono de favoritos si no estás logueado**/
        if (Util.getToken(ctx) == null) {
            holder.favourite.setVisibility(View.INVISIBLE);
        }


        if (holder.mItem.getOwnerId().getFavs() == null) {
            holder.favourite.setImageResource(R.drawable.ic_corazon);
        } else {
            holder.favourite.setImageResource(R.drawable.ic_like);
        }


        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favourite.getDrawable().getConstantState().equals(holder.favourite.getResources().getDrawable(R.drawable.ic_corazon).getConstantState())) {
                    mListener.onClickFav(holder.favourite, holder.mItem.getId(), holder.mItem.getOwnerId());
                } else {
                    mListener.onClickDeletFav(holder.favourite, holder.mItem.getId(), holder.mItem.getOwnerId());
                }

            }
        });



        /*if (holder.favourite.getDrawable().getConstantState().equals(holder.favourite.getResources().getDrawable(R.drawable.ic_corazon).getConstantState())) {

            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickFav(holder.favourite, holder.mItem.getId(), holder.mItem.getOwnerId());
                }
            });
        }*/
        /*else if((holder.favourite.getDrawable().getConstantState().equals(holder.favourite.getResources().getDrawable(R.drawable.ic_like).getConstantState()))){
            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickDeletFav(holder.favourite, holder.mItem.getId(), holder.mItem.getOwnerId());
                }
            });
        }*/


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView address, price, rooms, size;
        public final ImageView photo, favourite;
        public PropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            address = view.findViewById(R.id.property_address);
            price = view.findViewById(R.id.property_price);
            rooms = view.findViewById(R.id.property_rooms);
            size = view.findViewById(R.id.property_size);
            photo = view.findViewById(R.id.property_photo);
            favourite = view.findViewById(R.id.property_fav);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + address.getText() + "'";
        }
    }
}
