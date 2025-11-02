package com.andres.pasaportecafeovalle.view.adapter.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;

import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;


import java.util.ArrayList;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {
    private List<ClientesModel> listaClientes;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ClientesModel cliente);
        void onEditClick(ClientesModel cliente);
        void onToggleStatusClick(ClientesModel cliente);
    }

    public ClientesAdapter(List<ClientesModel> listaClientes, OnItemClickListener listener) {
        this.listaClientes = listaClientes != null ? listaClientes : new ArrayList<>();
        this.listener = listener;
    }

    // Permite actualizar lista desde el fragment
    public void setClientes(List<ClientesModel> clientes) {
        this.listaClientes.clear();
        if (clientes != null) {
            this.listaClientes.addAll(clientes);
        }
        notifyDataSetChanged();
    }

    public static class ClientesViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, email, telefono, fecha_nac, estado, creado_en;
        ImageButton btnEditar, btnHabilitar;

        public ClientesViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreCliente);
            email = itemView.findViewById(R.id.tvEmailCliente);
            telefono = itemView.findViewById(R.id.tvTelefonoCliente);
            fecha_nac = itemView.findViewById(R.id.tvFechaNacCliente);
            estado = itemView.findViewById(R.id.tvEstadoCliente);
            creado_en = itemView.findViewById(R.id.tvCreadoEnCliente);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnHabilitar = itemView.findViewById(R.id.btnHabilitar);
        }

        public void bind(ClientesModel cliente, OnItemClickListener listener) {
            if (cliente == null) return;

            nombre.setText("Nombre: " + cliente.getNombre());
            email.setText("Email: " + cliente.getEmail());
            telefono.setText("Teléfono: " + cliente.getTelefono());
            fecha_nac.setText("Fecha nacimiento: " + cliente.getFecha_nac());
            estado.setText("Estado: " + cliente.getEstado());
            creado_en.setText("Creado en: " + cliente.getCreado_en());

            // Estado visual
            if ("Activo".equalsIgnoreCase(cliente.getEstado())) {
                estado.setTextColor(0xFF4CAF50); // Verde

            } else {
                estado.setTextColor(0xFFF44336); // Rojo

            }

            // Eventos
            itemView.setOnClickListener(v -> listener.onItemClick(cliente));
            btnEditar.setOnClickListener(v -> listener.onEditClick(cliente));
            btnHabilitar.setOnClickListener(v -> listener.onToggleStatusClick(cliente));
        }
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesViewHolder holder, int position) {
        holder.bind(listaClientes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }
}


