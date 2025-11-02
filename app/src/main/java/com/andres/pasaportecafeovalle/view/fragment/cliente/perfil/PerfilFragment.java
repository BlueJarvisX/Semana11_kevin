package com.andres.pasaportecafeovalle.view.fragment.cliente.perfil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;
import com.andres.pasaportecafeovalle.viewmodel.VisitasViewModel;

public class PerfilFragment extends Fragment {

    TextView etNombre, etEmail, etCanjes, etPuntos, etNivel;

    private VisitasViewModel visitasViewModel;
    private ClientesViewModel clientesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cliente_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        visitasViewModel = new ViewModelProvider(requireActivity()).get(VisitasViewModel.class);
        clientesViewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        etNombre = view.findViewById(R.id.etNombre);
        etEmail = view.findViewById(R.id.etEmail);
        etCanjes = view.findViewById(R.id.etCanjes);
        etPuntos = view.findViewById(R.id.etPuntos);
        etNivel = view.findViewById(R.id.etNivel);

        String email = requireActivity().getIntent().getStringExtra("correo");
        int clienteId = clientesViewModel.obtenerIdClientePorEmail(email);

        etNombre.setText("Nombre del Usuario");
        etEmail.setText("");
        etCanjes.setText(visitasViewModel.contarVisitasCliente(clienteId).toString());

        LinearLayout btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        LinearLayout btnFavorites = view.findViewById(R.id.btn_favorites);
        LinearLayout btnHistorial = view.findViewById(R.id.btn_order_history);
        LinearLayout btnScanner = view.findViewById(R.id.btn_scanner);


        btnEditProfile.setOnClickListener(v -> {
            // Navegar al fragment de editar perfil
            Fragment editarPerfilFragment = new EditarPerfilFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, editarPerfilFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnFavorites.setOnClickListener(v -> {
            // Navegar al fragment de favoritos
            Fragment favoritosFragment = new FavoritosFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, favoritosFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnHistorial.setOnClickListener(v -> {
            // Navegar al fragment de historial
            Fragment historialFragment = new HistorialFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, historialFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnScanner.setOnClickListener(v -> {
            // Navegar al fragment de scanner QR
            Fragment scannerFragment = new QrClienteFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, scannerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
}

