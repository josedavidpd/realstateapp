package com.st.jdpolonio.inmobiliapp.fragments_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.adapters.MyMyPropertiesRecyclerViewAdapter;
import com.st.jdpolonio.inmobiliapp.adapters.MyPropertiesRecyclerViewAdapter;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentMyPropertiesListener;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.responses.MinePropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.retrofit.TipoAutenticacion;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPropertiesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentMyPropertiesListener mListener;
    private MyMyPropertiesRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;


    public MyPropertiesListFragment() {
    }

    public static MyPropertiesListFragment newInstance(int columnCount) {
        MyPropertiesListFragment fragment = new MyPropertiesListFragment();
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
        View view = inflater.inflate(R.layout.fragment_myproperties_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyMyPropertiesRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            setData();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListFragmentMyPropertiesListener) {
            mListener = (OnListFragmentMyPropertiesListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setData() {
        PropertyService service = ServiceGenerator.createService(PropertyService.class, Util.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<MinePropertyResponse>> call = service.getMyProps();
        call.enqueue(new Callback<ResponseContainer<MinePropertyResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<MinePropertyResponse>> call, Response<ResponseContainer<MinePropertyResponse>> response) {
                if(response.isSuccessful()) {
                    adapter = new MyMyPropertiesRecyclerViewAdapter(ctx, R.layout.fragment_myproperties,response.body().getRows(),mListener);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ctx, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<MinePropertyResponse>> call, Throwable t) {

                Toast.makeText(ctx, "Error conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
