package com.andres.pasaportecafeovalle.view.fragment.admin.productos;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;
import com.andres.pasaportecafeovalle.view.adapter.cliente.productos.ProductoAdapter;
import com.andres.pasaportecafeovalle.view.modelo.Producto;
import com.andres.pasaportecafeovalle.viewmodel.ProductosViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdminProductos extends Fragment {
    private ProductosViewModel productoViewModel;
    private ProductoAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnFiltro;
    private boolean mostrandoTodos = false; // Para alternar activos/todos

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_productos, container, false);

        recyclerView = v.findViewById(R.id.recyclerProductos);
        btnFiltro = v.findViewById(R.id.btnFiltroProductos);

        adapter = new ProductoAdapter(new ArrayList<>(), new ProductoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Producto producto) {
                // Editar producto
                FragmentAdminEditarProductos fragmentEditar = FragmentAdminEditarProductos.newInstance(producto);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragmentEditar)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onItemLongClick(Producto producto) {
                mostrarDialogoEliminar(producto);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);

        productoViewModel.getProductos().observe(getViewLifecycleOwner(), productoEntities -> {
            List<Producto> productos = new ArrayList<>();
            for (ProductoEntity entity : productoEntities) {
                productos.add(ProductoMapper.entityToModel(entity));
            }
            adapter.updateProductos(productos);
        });

        btnFiltro.setOnClickListener(view -> {
            if (mostrandoTodos) {
                productoViewModel.cargarProductos();
                btnFiltro.setText("Mostrar Todos");
                mostrandoTodos = false;
            } else {
                productoViewModel.cargarTodosLosProductos();
                btnFiltro.setText("Solo Activos");
                mostrandoTodos = true;
            }
        });

        FloatingActionButton fabCrear = v.findViewById(R.id.fabCrearProducto);
        fabCrear.setOnClickListener(view -> {
            FragmentAdminCrearProductos fragmentCrear = new FragmentAdminCrearProductos();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentCrear)
                    .addToBackStack(null)
                    .commit();
        });

        return v;
    }

    private void mostrarDialogoEliminar(Producto producto) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar producto")
                .setMessage("¿Estás seguro de eliminar el producto " + producto.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Eliminar usando ProductoEntity
                    ProductoEntity entity = new ProductoEntity(
                            producto.getId_producto(),
                            producto.getNombre(),
                            producto.getCategoria(),
                            producto.getPrecio(),
                            producto.getEstado());
                    productoViewModel.eliminarProducto(entity);
                    Toast.makeText(getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
