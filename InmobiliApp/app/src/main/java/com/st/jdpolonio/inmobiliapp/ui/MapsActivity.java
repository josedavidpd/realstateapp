package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        PropertyService service = ServiceGenerator.createService(PropertyService.class);
        Call<ResponseContainer<PropertyResponse>> call = service.getNearProps("-5.9731700,37.3828300", 100);

        call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PropertyResponse>> call, final Response<ResponseContainer<PropertyResponse>> response) {
                if (response.isSuccessful()) {


                    for (int i = 0; i < response.body().getRows().size(); i++) {

                        if (response.body().getRows().get(i).getLoc() == null) {
                            LatLng seville = new LatLng(37.3828300, -5.9731700);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seville, 30));
                        } else {

                            String[] latlong = response.body().getRows().get(i).getLoc().split(",");

                            double lat = Double.parseDouble(latlong[0]);
                            double lon = Double.parseDouble(latlong[1]);

                            LatLng loc_marker = new LatLng(lat, lon);
                            LatLng seville = new LatLng(37.3828300, -5.9731700);
                            if(response.body().getRows().get(i).getCategoryId().getName().equals("Alquiler")) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(loc_marker)
                                        .title(response.body().getRows().get(i).getTitle())
                                        .snippet(response.body().getRows().get(i).getPrice() + " €/mes")
                                        .draggable(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_house))
                                );
                            }else {
                                mMap.addMarker(new MarkerOptions()
                                        .position(loc_marker)
                                        .title(response.body().getRows().get(i).getTitle())
                                        .snippet(response.body().getRows().get(i).getPrice() + " €")
                                        .draggable(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_house))
                                );
                            }



                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seville, 10));
                        }
                    }

                } else {
                    Toast.makeText(MapsActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
