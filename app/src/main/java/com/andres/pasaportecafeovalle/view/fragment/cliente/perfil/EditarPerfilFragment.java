package com.andres.pasaportecafeovalle.view.fragment.cliente.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
// Removed NavHostFragment import as we're using FragmentManager

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.fragment.admin.clientes.FragmentAdminClientes;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarPerfilFragment extends Fragment {
    private ClientesViewModel clientesViewModel;
    private EditText etNombre, etEmail, etTelefono, etClave;
    private int clienteId;
    DatePicker dpFechaNac;
    Spinner spEstado;
    private String creadoEn;
    private Button btnGuardar;
    private FloatingActionButton fabVolver;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin_crear_cliente, container, false);

        clientesViewModel = new ViewModelProvider(this).get(ClientesViewModel.class);

        // Inicializar vistas
        etNombre = view.findViewById(R.id.etNombre);
        etEmail = view.findViewById(R.id.etEmail);
        etClave = view.findViewById(R.id.etClave);
        etTelefono = view.findViewById(R.id.etTelefono);
        dpFechaNac = view.findViewById(R.id.dpFechaNac);
        spEstado = view.findViewById(R.id.spEstado);
        btnGuardar = view.findViewById(R.id.btnCrear);
        fabVolver = view.findViewById(R.id.fabVolver);

        configurarSpinnerEstado();

        btnGuardar.setText("Guardar Cambios");

        cargarDatosCliente();

        // Configurar listeners
        fabVolver.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new FragmentAdminClientes());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        btnGuardar.setOnClickListener(v -> {
            if (!validarCampos()) return;

            String fechaNac = getFechaEnDatePicker();

            ClientesModel clientes = new ClientesModel(
                    clienteId,
                    etNombre.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etClave.getText().toString().trim(),
                    etTelefono.getText().toString().trim(),
                    fechaNac,
                    spEstado.getSelectedItem().toString().trim(),
                    creadoEn
            );

            editarCliente(clientes);
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

    private String getFechaEnDatePicker(){
        int dia = dpFechaNac.getDayOfMonth();
        int mes = dpFechaNac.getMonth() + 1;
        int anio = dpFechaNac.getYear();
        String fechaNac = String.format("%04d-%02d-%02d", anio, mes, dia);
        return fechaNac;
    }

    private void cargarDatosCliente() {
        Bundle args = getArguments();
        if (args != null) {
            clienteId = args.getInt("cliente_id", 0);
            etNombre.setText(args.getString("cliente_nombre", ""));
            etEmail.setText(args.getString("cliente_email", ""));
            etClave.setText(args.getString("cliente_clave", ""));
            etTelefono.setText(args.getString("cliente_telefono", ""));

            String fechaNac = args.getString("cliente_fecha_nac", "");
            if (!fechaNac.isEmpty()){
                establecerFechaEnDatePicker(fechaNac);
            }

            String estado = args.getString("cliente_estado", "");
            establecerEstadoEnSpinner(estado);

            creadoEn = args.getString("cliente_creado_en", "");
        }
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError("El nombre es requerido");
            return false;
        }
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("El email es requerido");
            return false;
        }
        if (etTelefono.getText().toString().trim().isEmpty()) {
            etTelefono.setError("El teléfono es requerido");
            return false;
        }

        return true;
    }

    private void editarCliente(ClientesModel clientes) {
        try{
            clientesViewModel.editarCliente(clientes);
            Toast.makeText(getContext(), "Cliente editado", Toast.LENGTH_SHORT).show();

            limpiarCampos();
        }catch(Exception e){
            Toast.makeText(getContext(), "Error al editar el usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("FragmentClientes", "Error al editar cliente", e);
        }

        // Volver a la lista de clientes
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new FragmentAdminClientes());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void limpiarCampos(){
        etNombre.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }

    private void establecerFechaEnDatePicker(String fecha) {
        try {
            String[] partes = fecha.split("-");
            if (partes.length == 3) {
                int year = Integer.parseInt(partes[0]);
                int month = Integer.parseInt(partes[1]) - 1; // Los meses van de 0-11
                int day = Integer.parseInt(partes[2]);
                dpFechaNac.updateDate(year, month, day);
            }
        } catch (Exception e) {
            Log.e("FragmentEditarCliente", "Error al establecer fecha: " + e.getMessage());
        }
    }

    private void establecerEstadoEnSpinner(String estado) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spEstado.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(estado);
            if (position >= 0) {
                spEstado.setSelection(position);
            }
        }
    }

}