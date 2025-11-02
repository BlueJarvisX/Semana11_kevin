package com.andres.pasaportecafeovalle.view.fragment.cliente.perfil;

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
import com.andres.pasaportecafeovalle.view.adapter.cliente.perfil.HistorialAdapter;
import com.andres.pasaportecafeovalle.view.modelo.Historial;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {

    List<Historial> lista = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cliente_historial, container, false);

        lista.add(new Historial("Super descuento en cafe", "Cafeteria la estrella", "12/12/2023 - 09:21:42 am", "Cafe negro 100ml"));
        lista.add(new Historial("Super descuento en galletas", "Cafeteria la estrella", "10/02/2023 - 10:21:42 am", "Galletas acosta 400 gramos"));
        lista.add(new Historial("Producto gratis", "Cafeteria cafe", "12/12/2023 - 09:21:42 am", "Rebanada de torta de chocolate gratis"));
        lista.add(new Historial("Super descuento en cafe", "Cafeteria la estrella", "12/12/2023 - 09:21:42 am", "Cafe negro 100ml"));



        RecyclerView recyclerView = view.findViewById(R.id.rvHistorial);
        HistorialAdapter adapter = new HistorialAdapter(lista, historial -> {

            Toast.makeText(getContext(), "beneficio: " + historial.getNombreBeneficio(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
