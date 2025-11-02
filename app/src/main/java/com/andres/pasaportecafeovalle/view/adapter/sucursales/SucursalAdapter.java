package com.andres.pasaportecafeovalle.view.adapter.sucursales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;



public class SucursalAdapter extends RecyclerView.Adapter<SucursalAdapter.SucursalViewHolder> {
    private List<SucursalModel> listaSucursales;
    private OnItemClickListener listener;

    /**
     * Interfaz para manejar los eventos de clic en los elementos del RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(SucursalModel sucursal); // Para el clic en toda la vista del ítem
        void onEditClick(SucursalModel sucursal); // Para el clic en el botón de editar
    }

    public SucursalAdapter(List<SucursalModel> listaSucursales, OnItemClickListener listener) {
        this.listaSucursales = listaSucursales;
        this.listener = listener;
    }

    /**
     * ViewHolder que contiene las vistas para cada ítem de sucursal.
     * Se han eliminado las referencias a ImageView, despues vere como hacer eso, por el momento de deja asi.
     */
    public static class SucursalViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, direccion, horario;
        ImageButton btnEditar;


        public SucursalViewHolder(View itemView) {
            super(itemView);
            // Inicialización de las vistas de texto y el botón de editar
            nombre = itemView.findViewById(R.id.tvNombreSucursal);
            direccion = itemView.findViewById(R.id.tvDireccion);
            horario = itemView.findViewById(R.id.tvHorario);
            btnEditar = itemView.findViewById(R.id.btnEditar);

            // La referencia a la ImageView ha sido eliminada.
        }

        /**
         * Vincula los datos del modelo de sucursal a las vistas del ViewHolder.
         * Asigna los listeners para los eventos de clic.
         */
        public void bind(final SucursalModel sucursal, final OnItemClickListener listener) {
            // Asignar los datos a los TextViews
            nombre.setText(sucursal.getNombre());
            direccion.setText(sucursal.getDireccion());
            horario.setText(sucursal.getHorario());

            // Asignar los listeners si el listener no es nulo
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(sucursal));
                btnEditar.setOnClickListener(v -> listener.onEditClick(sucursal));
            }
        }
    }

    @NonNull
    @Override
    public SucursalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout 'item_sucursal.xml' para cada ítem
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sucursal, parent, false);
        return new SucursalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SucursalViewHolder holder, int position) {
        SucursalModel sucursalActual = listaSucursales.get(position);

        // Vincular los datos y los listeners al ViewHolder
        holder.bind(sucursalActual, listener);


        // Lógica de visibilidad para el botón de editar
        String rol = "admin"; // Esto debería obtenerse de una fuente real
        if (rol.equals("admin")) {
            holder.btnEditar.setVisibility(View.VISIBLE);

        } else {
            holder.btnEditar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista, o 0 si la lista es nula para evitar errores.
        return listaSucursales != null ? listaSucursales.size() : 0;
    }

    /**
     * Método para actualizar la lista de sucursales en el adaptador.
     */
    public void submitList(List<SucursalModel> nuevasSucursales) {
        this.listaSucursales = nuevasSucursales;
        notifyDataSetChanged();
    }
}