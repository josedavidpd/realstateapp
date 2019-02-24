package com.st.jdpolonio.inmobiliapp.adapters;

import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView address,price,rooms,size;
        public final ImageView photo;
        public PropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            address = view.findViewById(R.id.property_address);
            price = view.findViewById(R.id.property_price);
            rooms = view.findViewById(R.id.property_rooms);
            size = view.findViewById(R.id.property_size);
            photo = view.findViewById(R.id.property_photo);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + address.getText() + "'";
        }
    }
}
