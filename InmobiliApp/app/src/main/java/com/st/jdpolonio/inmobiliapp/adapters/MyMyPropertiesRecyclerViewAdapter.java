package com.st.jdpolonio.inmobiliapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.st.jdpolonio.inmobiliapp.R;

import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentMyPropertiesListener;
import com.st.jdpolonio.inmobiliapp.responses.MinePropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;

import java.util.List;


public class MyMyPropertiesRecyclerViewAdapter extends RecyclerView.Adapter<MyMyPropertiesRecyclerViewAdapter.ViewHolder> {

    private final List<MinePropertyResponse> mValues;
    private final OnListFragmentMyPropertiesListener mListener;
    private Context ctx;

    public MyMyPropertiesRecyclerViewAdapter(Context context, int layout, List<MinePropertyResponse> items, OnListFragmentMyPropertiesListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myproperties, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.address.setText(holder.mItem.getAddress());
        holder.price.setText(String.valueOf(holder.mItem.getPrice()));
        holder.rooms.setText(String.valueOf(holder.mItem.getRooms()));
        holder.size.setText(String.valueOf(holder.mItem.getSize()));

        holder.cardViewMyProps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickMyProp(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView address, price, rooms, size;
        public final ImageView photo;
        public final CardView cardViewMyProps;
        public MinePropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            address = view.findViewById(R.id.my_property_address);
            price = view.findViewById(R.id.my_property_price);
            rooms = view.findViewById(R.id.my_property_rooms);
            size = view.findViewById(R.id.my_property_size);
            photo = view.findViewById(R.id.my_property_photo);
            cardViewMyProps = view.findViewById(R.id.cardViewMineProperties);

        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
