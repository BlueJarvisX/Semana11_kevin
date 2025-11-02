package com.andres.pasaportecafeovalle.view.adapter.cliente.beneficios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.modelo.Beneficio;

import java.util.List;

public class BeneficioAdapter extends RecyclerView.Adapter<BeneficioAdapter.BeneficioViewHolder>{
    private List<Beneficio> listaBeneficios;
    private BeneficioAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(Beneficio beneficio);
    }

    public BeneficioAdapter(List<Beneficio> listaBeneficios, BeneficioAdapter.OnItemClickListener listener) {
        this.listaBeneficios = listaBeneficios;
        this.listener = listener;
    }

    // ViewHolder
    public static class BeneficioViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, tipo, requisito_visitas, descuento_pct, estado;
        ProgressBar progressBar;

        public BeneficioViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreBeneficio);
            tipo = itemView.findViewById(R.id.tvTipoBeneficio);
            requisito_visitas = itemView.findViewById(R.id.tvRequisitoVisitas);
            descuento_pct = itemView.findViewById(R.id.tvDescuento);
            estado = itemView.findViewById(R.id.tvEstado);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

        public void bind(final Beneficio beneficio, final BeneficioAdapter.OnItemClickListener listener) {
            int visitasFaltantes = beneficio.getRequisito_visitas() - 2; // Suponiendo que el usuario ha realizado 2 visitas
            nombre.setText(beneficio.getNombre());
            tipo.setText("Tipo beneficio: " + beneficio.getTipo());
            requisito_visitas.setText("Visitas necesarias: " + String.valueOf(beneficio.getRequisito_visitas() + " | solo te faltan " + visitasFaltantes + " visitas!!!"));
            descuento_pct.setText(String.valueOf("Descuento: " + beneficio.getDescuento_pct() + "%"));
            estado.setText("Estado: " + beneficio.getEstado());

            itemView.setOnClickListener(v -> listener.onItemClick(beneficio));
        }
    }

    @NonNull
    @Override
    public BeneficioAdapter.BeneficioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beneficio, parent, false);
        return new BeneficioAdapter.BeneficioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficioAdapter.BeneficioViewHolder holder, int position) {
        holder.bind(listaBeneficios.get(position), listener);

        holder.progressBar.setMax(listaBeneficios.get(position).getRequisito_visitas());
        holder.progressBar.setProgress(2);

    }

    @Override
    public int getItemCount() {
        return listaBeneficios.size();
    }
}
