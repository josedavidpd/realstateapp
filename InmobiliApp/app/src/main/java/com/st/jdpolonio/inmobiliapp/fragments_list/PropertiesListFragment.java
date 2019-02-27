package com.st.jdpolonio.inmobiliapp.fragments_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.adapters.MyPropertiesRecyclerViewAdapter;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentPropertiesListener;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertiesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentPropertiesListener mListener;
    private MyPropertiesRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;

    public PropertiesListFragment() {
    }

    public static PropertiesListFragment newInstance(int columnCount) {
        PropertiesListFragment fragment = new PropertiesListFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_properties_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listProperties);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
           // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            setData();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                PropertyService service = ServiceGenerator.createService(PropertyService.class);
                Call<ResponseContainer<PropertyResponse>> call = service.query(s);
                call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseContainer<PropertyResponse>> call, Response<ResponseContainer<PropertyResponse>> response) {
                        if(response.isSuccessful()) {
                            adapter = new MyPropertiesRecyclerViewAdapter(ctx, R.layout.fragment_properties,response.body().getRows(),mListener);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(ctx, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {

                        Toast.makeText(ctx, "Error conexión", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListFragmentPropertiesListener) {
            mListener = (OnListFragmentPropertiesListener) context;
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
        PropertyService service = ServiceGenerator.createService(PropertyService.class);
        Call<ResponseContainer<PropertyResponse>> call = service.getProperties();
        call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PropertyResponse>> call, Response<ResponseContainer<PropertyResponse>> response) {
                if(response.isSuccessful()) {
                    adapter = new MyPropertiesRecyclerViewAdapter(ctx, R.layout.fragment_properties,response.body().getRows(),mListener);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(ctx, response.body().getCount()+" resultados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {

                Toast.makeText(ctx, "Error conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
