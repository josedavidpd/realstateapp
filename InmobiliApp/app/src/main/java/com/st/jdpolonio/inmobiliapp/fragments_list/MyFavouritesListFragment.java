package com.st.jdpolonio.inmobiliapp.fragments_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.adapters.MyFavouritesRecyclerViewAdapter;
import com.st.jdpolonio.inmobiliapp.adapters.MyPropertiesRecyclerViewAdapter;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListMyFavouritesListener;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.responses.PropertyFavResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.retrofit.TipoAutenticacion;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFavouritesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListMyFavouritesListener mListener;
    private MyFavouritesRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Context ctx;


    public MyFavouritesListFragment() {
    }

    public static MyFavouritesListFragment newInstance(int columnCount) {
        MyFavouritesListFragment fragment = new MyFavouritesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfavourites_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyFavouritesRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            setData();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListMyFavouritesListener) {
            mListener = (OnListMyFavouritesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListMyFavouritesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setData() {
        PropertyService service = ServiceGenerator.createService(PropertyService.class, Util.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<PropertyFavResponse>> call = service.getPropsFavs();
        call.enqueue(new Callback<ResponseContainer<PropertyFavResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PropertyFavResponse>> call, Response<ResponseContainer<PropertyFavResponse>> response) {
                if(response.isSuccessful()) {
                    adapter = new MyFavouritesRecyclerViewAdapter(ctx, R.layout.fragment_myfavourites,response.body().getRows(),mListener);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ctx, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PropertyFavResponse>> call, Throwable t) {

                Log.i("AAAAAAAA",t.getMessage());
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
