package com.st.jdpolonio.inmobiliapp.interfaces;

import android.widget.ImageView;

import com.st.jdpolonio.inmobiliapp.models.User;

public interface OnListFragmentPropertiesListener {

    void OnClickProperty();
    void onClickFav(ImageView ic_fav, String id_property, User user);
    void onClickDeletFav(ImageView ic_fav, String id_property, User user);
}
