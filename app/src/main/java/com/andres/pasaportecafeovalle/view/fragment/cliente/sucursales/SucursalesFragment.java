package com.andres.pasaportecafeovalle.view.fragment.cliente.sucursales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.adapter.sucursales.SucursalAdapter;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;

import java.util.ArrayList;
import java.util.List;

public class SucursalesFragment extends Fragment {

    List<SucursalModel> lista = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cliente_sucursales, container, false);



        RecyclerView recyclerView = view.findViewById(R.id.rvSucursales);


        return view;
    }


}
