package com.st.jdpolonio.inmobiliapp.dialog_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.viewmodel.AddPropertyViewModel;

public class AddPropertyFragment extends DialogFragment {

    private AddPropertyViewModel mViewModel;

    private DialogInterface.OnDismissListener onDismissListener;

    private View view;

    public static AddPropertyFragment newInstance() {
        return new AddPropertyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_property_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPropertyViewModel.class);
        // TODO: Use the ViewModel
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_property_fragment, null);

        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("")
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
