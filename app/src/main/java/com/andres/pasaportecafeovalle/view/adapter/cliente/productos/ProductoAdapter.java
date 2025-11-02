package com.andres.pasaportecafeovalle.view.adapter.cliente.productos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>{
    private List<Producto> listaProductos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Producto producto); // Editar/Ver producto
        void onItemLongClick(Producto producto); // Eliminar producto
    }

    public ProductoAdapter(List<Producto> listaProductos, OnItemClickListener listener) {
        this.listaProductos = listaProductos;
        this.listener = listener;
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombre, precio;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imgProducto);
            nombre = itemView.findViewById(R.id.tvNombreProducto);
            precio = itemView.findViewById(R.id.tvPrecioProducto);
        }

        public void bind(final Producto producto, final OnItemClickListener listener) {
            nombre.setText(producto.getNombre());
            precio.setText("Precio: $" + producto.getPrecio());
            imagen.setImageResource(producto.getImage());

            itemView.setOnClickListener(v -> listener.onItemClick(producto));
            itemView.setOnLongClickListener(v -> {
                listener.onItemLongClick(producto);
                return true;
            });
        }
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.bind(listaProductos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void updateProductos(List<Producto> nuevosProductos) {
        this.listaProductos = nuevosProductos;
        notifyDataSetChanged();
    }

}
