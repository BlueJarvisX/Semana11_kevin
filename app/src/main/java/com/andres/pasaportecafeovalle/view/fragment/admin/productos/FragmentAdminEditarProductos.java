package com.andres.pasaportecafeovalle.view.fragment.admin.productos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;
import com.andres.pasaportecafeovalle.view.modelo.Producto;
import com.andres.pasaportecafeovalle.viewmodel.ProductosViewModel;

import java.io.Serializable;

public class FragmentAdminEditarProductos  extends Fragment {

    private EditText etNombre, etCategoria, etPrecio, etEstado;
    private Button btnEditar;
    private ProductosViewModel productoViewModel;
    private Producto producto;

    public static FragmentAdminEditarProductos newInstance(Producto producto) {
        FragmentAdminEditarProductos fragment = new FragmentAdminEditarProductos();
        Bundle args = new Bundle();
        args.putSerializable("producto", (Serializable) producto);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_editar_producto, container, false);

        etNombre = v.findViewById(R.id.etNombreProducto);
        etCategoria = v.findViewById(R.id.etCategoriaProducto);
        etPrecio = v.findViewById(R.id.etPrecioProducto);
        etEstado = v.findViewById(R.id.etEstadoProducto);
        btnEditar = v.findViewById(R.id.btnEditarProducto);

        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);

        if (getArguments() != null) {
            producto = (Producto) getArguments().getSerializable("producto");
            etNombre.setText(producto.getNombre());
            etCategoria.setText(producto.getCategoria());
            etPrecio.setText(String.valueOf(producto.getPrecio()));
            etEstado.setText(producto.getEstado());
        }

        btnEditar.setOnClickListener(view -> {
            String nombre = etNombre.getText().toString();
            String categoria = etCategoria.getText().toString();
            String precioTxt = etPrecio.getText().toString();
            String estado = etEstado.getText().toString();

            if(nombre.isEmpty() || categoria.isEmpty() || precioTxt.isEmpty() || estado.isEmpty()){
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            float precio;
            try {
                precio = Float.parseFloat(precioTxt);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Precio inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            ProductoEntity productoEntity = new ProductoEntity(
                    producto.getId_producto(), // Cambiado aquí
                    nombre,
                    categoria,
                    precio,
                    estado
            );
            productoViewModel.actualizarProducto(productoEntity);

            Toast.makeText(getContext(), "Producto actualizado", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return v;
    }
}
