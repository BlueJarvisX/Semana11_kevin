package com.andres.pasaportecafeovalle.view.fragment.admin.clientes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.utils.Utils;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentAdminCrearCliente extends Fragment {

    private ClientesViewModel clientesViewModel;
    private ClientesModel clientes;
    EditText etNombre, etEmail, etTelefono, etClave;

    DatePicker dpFechaNac;
    Spinner spEstado;

    private FloatingActionButton fabVolver;
    Button btnCrear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_crear_cliente, container, false);


        clientesViewModel = new ViewModelProvider(this).get(ClientesViewModel.class);
        
        etNombre = view.findViewById(R.id.etNombre);
        etEmail = view.findViewById(R.id.etEmail);
        etTelefono = view.findViewById(R.id.etTelefono);
        dpFechaNac = view.findViewById(R.id.dpFechaNac);
        spEstado = view.findViewById(R.id.spEstado);
        btnCrear = view.findViewById(R.id.btnCrear);
        fabVolver = view.findViewById(R.id.fabVolver);
        etClave = view.findViewById(R.id.etClave);


        configurarSpinnerEstado();

        fabVolver.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().
                    beginTransaction();
            transaction.replace(R.id.fragment_container, new FragmentAdminClientes());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnCrear.setOnClickListener(v -> {
            if (!validarCampos()) return;

            int dia = dpFechaNac.getDayOfMonth();
            int mes = dpFechaNac.getMonth() + 1;
            int anio = dpFechaNac.getYear();
            String fechaNac = String.format("%04d-%02d-%02d", anio, mes, dia);


            String creado_en = Utils.obtenerFechaActual();
            ClientesModel clientes = new ClientesModel(
                    etNombre.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etClave.getText().toString().trim(),
                    etTelefono.getText().toString().trim(),
                    fechaNac,
                    spEstado.getSelectedItem().toString().trim(),
                    creado_en
            );
            insertarCliente(clientes);
        });

        return view;
    }

    private void configurarSpinnerEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.estado_cliente,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);

        // Establecer "Activo" como selección por defecto
        spEstado.setSelection(0);
    }


    private void insertarCliente(ClientesModel clientes){
        try{
            clientesViewModel.insertarClientes(clientes);
            Toast.makeText(getContext(), "Cliente creado", Toast.LENGTH_SHORT).show();

            clientesViewModel.getClientesCountLive().observe(getViewLifecycleOwner(), count -> {
                if (count != null) {
                    Log.d("FragmentClientes", "Cantidad de clientes: " + count);
                    Toast.makeText(getContext(), "Clietnes en BD: " + count, Toast.LENGTH_SHORT).show();

                }
            });
            limpiarCampos();
        }catch(Exception e){
            Toast.makeText(getContext(), "Error al crear el usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("FragmentClientes", "Error al insertar cliente", e);
        }
    }

    private boolean validarCampos(){
        if (etNombre.getText().toString().isEmpty()){
            etNombre.setError("El nombre es obligatorio");
            return false;
        }
        if (etEmail.getText().toString().isEmpty()){
            etEmail.setError("El email es obligatorio");
            return false;
        }
        if(etTelefono.getText().toString().isEmpty()){
            etTelefono.setError("El telefono es obligatorio");
            return false;
        }

        return true;
    }

    private void limpiarCampos(){
        etNombre.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }
}
