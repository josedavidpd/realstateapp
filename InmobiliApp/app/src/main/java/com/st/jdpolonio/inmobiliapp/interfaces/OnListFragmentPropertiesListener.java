package com.st.jdpolonio.inmobiliapp.interfaces;

import android.widget.ImageView;


import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;

public interface OnListFragmentPropertiesListener {

    void OnClickProperty(PropertyResponse property);
    void onClickFav(ImageView ic_fav, String id_property);
    void onClickDeletFav(ImageView ic_fav, String id_property);
}
