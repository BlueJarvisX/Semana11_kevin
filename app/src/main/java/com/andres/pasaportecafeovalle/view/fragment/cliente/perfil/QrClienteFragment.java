package com.andres.pasaportecafeovalle.view.fragment.cliente.perfil;



import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.data.local.db.DataBase;
import com.andres.pasaportecafeovalle.data.local.entities.VisitaEntity;
import com.andres.pasaportecafeovalle.utils.Utils;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;
import com.andres.pasaportecafeovalle.viewmodel.VisitasViewModel;

public class QrClienteFragment extends Fragment {

    ImageView qrImageView;
    VisitasViewModel visitasViewModel;
    ClientesViewModel clientesViewModel;
    private static final int CAMERA_PERMISSION_CAMERA_PERMISSION = 100;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cliente_qr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrImageView = view.findViewById(R.id.qrImageView);

        // Inicializar ViewModels
        visitasViewModel = new ViewModelProvider(requireActivity()).get(VisitasViewModel.class);
        clientesViewModel = new ViewModelProvider(requireActivity()).get(ClientesViewModel.class);

        // Obtener email desde el intent
        String email = requireActivity().getIntent().getStringExtra("correo");
        if (email == null || email.trim().isEmpty()) {
            Toast.makeText(getContext(), "Error: No se pudo obtener el email del usuario", Toast.LENGTH_LONG).show();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return;
        }

        // Ejescutar en un hilo aparte
        DataBase.databaseWriteExecutor.execute(() -> {
            try {
                Integer idClienteInteger = clientesViewModel.obtenerIdClientePorEmail(email);

                if (idClienteInteger == null) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error: Usuario no encontrado", Toast.LENGTH_LONG).show();
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    });
                    return;
                }

                int idCliente = idClienteInteger;
                int idSucursal = 1;

                String hash = Utils.generarHashQR(idCliente, idSucursal);

                // Insertar la visita
                visitasViewModel.insertarVisita(
                        new VisitaEntity(idCliente, idSucursal, Utils.obtenerFechaActual(), "app", "pendiente", hash)
                );

                // Generar QR
                Bitmap qrBitmap = Utils.generarQRBitmap(hash, 600, 600);

                requireActivity().runOnUiThread(() -> {//volvemos al hilo principal para actualizar la UI
                    if (qrBitmap != null) {
                        qrImageView.setImageBitmap(qrBitmap);
                    } else {
                        Toast.makeText(getContext(), "Error al generar el código QR", Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                });
            }
        });
    }
}