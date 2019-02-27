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
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentPropertiesListener;
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
        holder.title.setText(holder.mItem.getTitle());
        holder.price.setText(String.valueOf(price));
        holder.size.setText(String.valueOf(size));
        holder.rooms.setText(String.valueOf(rooms));
        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (holder.mItem.getPhotos()==null) {


            Glide.with(ctx).load("https://www.abc.es/Media/201304/22/vallecas-solvia--644x362.JPG").into(holder.photo);
        } else {

            Glide.with(ctx).load(holder.mItem.getPhotos().get(0)).into(holder.photo);
        }

        /**Para ocultar el icono de favoritos si no estás logueado**/
        if (Util.getToken(ctx) == null) {
            holder.favourite.setVisibility(View.INVISIBLE);
        }

//        if (mValues.get(position).getIsFav()) {
//            holder.favourite.setImageResource(R.drawable.ic_like);
//        } else {
//            holder.favourite.setImageResource(R.drawable.ic_corazon);
//        }


        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favourite.getDrawable().getConstantState().equals(holder.favourite.getResources().getDrawable(R.drawable.ic_corazon).getConstantState())) {
                    mListener.onClickFav(holder.favourite, holder.mItem.getId());
                } else {
                    mListener.onClickDeletFav(holder.favourite, holder.mItem.getId());
                }

            }
        });


        holder.cardViewProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClickProperty(holder.mItem);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView address, price, rooms, size,title;
        public final ImageView photo, favourite;
        public final CardView cardViewProperties;
        public PropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            address = view.findViewById(R.id.my_property_address);
            price = view.findViewById(R.id.my_property_price);
            rooms = view.findViewById(R.id.my_property_rooms);
            size = view.findViewById(R.id.my_property_size);
            photo = view.findViewById(R.id.my_property_photo);
            favourite = view.findViewById(R.id.property_fav);
            title = view.findViewById(R.id.my_property_titlee);
            cardViewProperties = view.findViewById(R.id.cardViewProperties);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + address.getText() + "'";
        }
    }
}
