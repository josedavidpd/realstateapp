package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.models.Rows;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.SingleResponseContainer;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends AppCompatActivity {

    private String property_id, title, address, rooms, price, size, description, createdAt;
    private SliderLayout sliderLayout;
    private TextView title_prop, price_prop, address_prop, rooms_prop, size_prop, createdAt_prop, description_prop;
    private ImageView iv_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            property_id = extras.getString("PROPERTY_ID");
            title = extras.getString("PROPERTY_NAME");
            address = extras.getString("PROPERTY_ADDRESS");
            rooms = extras.getString("PROPERTY_ROOMS");
            price = extras.getString("PROPERTY_PRICE");
            size = extras.getString("PROPERTY_SIZE");
            description = extras.getString("PROPERTY_DESCRIPTION");
            createdAt = extras.getString("PROPERTY_CREATEDAT");
        }
        setTitle(title);
        findViewsById();

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5);
        setSliderViews();
        setTexts();
      //  findOneProperty();
        onClickMapBtn();

    }

    public void setTexts() {
        title_prop.setText(title);
        price_prop.setText(price);
        address_prop.setText(address);
        rooms_prop.setText(rooms);
        size_prop.setText(size);
        createdAt_prop.setText(createdAt);
        description_prop.setText(description);

    }

    public void findOneProperty() {

        PropertyService service = ServiceGenerator.createService(PropertyService.class);
        Call<SingleResponseContainer> call = service.findOne(property_id);
        call.enqueue(new Callback<SingleResponseContainer>() {
            @Override
            public void onResponse(Call<SingleResponseContainer> call, Response<SingleResponseContainer> response) {
                if (response.isSuccessful()) {
                    title_prop.setText(response.body().getRows().getTitle());
                    price_prop.setText(String.valueOf(response.body().getRows().getPrice()));
                    address_prop.setText(response.body().getRows().getAddress());
                    rooms_prop.setText(String.valueOf(response.body().getRows().getRooms()));
                    size_prop.setText(String.valueOf(response.body().getRows().getSize()));
                    createdAt_prop.setText(response.body().getRows().getCreatedAt());
                    description_prop.setText(response.body().getRows().getDescription());

                } else {
                    Toast.makeText(PropertyDetailActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleResponseContainer> call, Throwable t) {
                Toast.makeText(PropertyDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://www.abc.es/Media/201304/22/vallecas-solvia--644x362.JPG");
                    break;
                case 1:
                    sliderView.setImageUrl("https://estudibasic.es/wp-content/uploads/2016/09/estudibasic-renders-de-interiores-3d-para-venta-inmobiliaria-1.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://weblego.blob.core.windows.net/weblegourl/www.maspiso.com/fotos/131214201260018530fotolia_50826585_s.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("https://www.abc.es/Media/201304/22/vallecas-solvia--644x362.JPG");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }


    }

    public void findViewsById() {

        sliderLayout = findViewById(R.id.imageSlider);
        title_prop = findViewById(R.id.prop_detail_title);
        price_prop = findViewById(R.id.prop_detail_price);
        address_prop = findViewById(R.id.prop_detail_address);
        rooms_prop = findViewById(R.id.prop_details_rooms);
        size_prop = findViewById(R.id.prop_details_size);
        createdAt_prop = findViewById(R.id.prop_detail_createdAt);
        description_prop = findViewById(R.id.prop_details_description);
        iv_map = findViewById(R.id.iv_map);

    }

    public void onClickMapBtn() {
        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0,0?q=" + address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        });
    }
}
