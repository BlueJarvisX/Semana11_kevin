package com.andres.pasaportecafeovalle.view.adapter.cliente.perfil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.Historial;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>{
    private List<Historial> listaHistorial;
    private HistorialAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(Historial historial);
    }

    public HistorialAdapter(List<Historial> listaHistorial, HistorialAdapter.OnItemClickListener listener) {
        this.listaHistorial = listaHistorial;
        this.listener = listener;
    }

    // ViewHolder
    public static class HistorialViewHolder extends RecyclerView.ViewHolder {

        TextView nombreBeneficio, nombreSucursal, fechaHora, productoRelacionado;

        public HistorialViewHolder(View itemView) {
            super(itemView);
            nombreBeneficio = itemView.findViewById(R.id.tvNombreBeneficio);
            nombreSucursal = itemView.findViewById(R.id.tvNombreSucursal);
            fechaHora = itemView.findViewById(R.id.tvFechaHora);
            productoRelacionado = itemView.findViewById(R.id.tvProductoRelacionado);


        }

        public void bind(final Historial historial, final HistorialAdapter.OnItemClickListener listener) {

            nombreBeneficio.setText(historial.getNombreBeneficio());
            nombreSucursal.setText("Sucursal: " + historial.getNombreSucursal());
            fechaHora.setText("Fecha y hora del canje: " + historial.getFechaHora());
            productoRelacionado.setText("Producto relacionado: " + historial.getProductoRelacionado());


            itemView.setOnClickListener(v -> listener.onItemClick(historial));
        }
    }

    @NonNull
    @Override
    public HistorialAdapter.HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new HistorialAdapter.HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.HistorialViewHolder holder, int position) {
        holder.bind(listaHistorial.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }
}
