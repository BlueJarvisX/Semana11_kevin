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
import com.andres.pasaportecafeovalle.view.adapter.cliente.productos.ProductoAdapter;
import com.andres.pasaportecafeovalle.view.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {

    private List<Producto> listaFavoritos = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clientes_favoritos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favoritos);
        

        cargarFavoritos();

        // Configurar adapter


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void cargarFavoritos() {
        // Agregar productos de ejemplo

    }

}