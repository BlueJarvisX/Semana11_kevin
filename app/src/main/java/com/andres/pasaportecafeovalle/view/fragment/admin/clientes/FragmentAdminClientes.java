package com.andres.pasaportecafeovalle.view.fragment.admin.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.adapter.admin.ClientesAdapter;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdminClientes extends Fragment {
    View view;
    Button btnListar, btnCrear, btnEditar;

    private ClientesViewModel clientesViewModel;
    List<ClientesModel> lista = new ArrayList<>();
    private ClientesAdapter adapter;
    private FloatingActionButton fabCrear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin_cliente, container, false);

        clientesViewModel = new ViewModelProvider(this).get(ClientesViewModel.class);

        fabCrear = view.findViewById(R.id.fabCrear);
        setUpRecyclerView();

        fabCrear.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new FragmentAdminCrearCliente());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        
        listarClientes();
        return view;
    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.rvClientes);
        
        // Implementar todos los métodos de la interfaz OnItemClickListener
        adapter = new ClientesAdapter(lista, new ClientesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ClientesModel clientesModel) {
                Toast.makeText(getContext(), "Cliente: " + clientesModel.getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEditClick(ClientesModel clientesModel) {
                // Navegar al fragment de edición
                FragmentAdminEditarCliente editFragment = new FragmentAdminEditarCliente();
                
                // Pasar datos del cliente al fragment de edición
                Bundle bundle = new Bundle();
                bundle.putInt("cliente_id", clientesModel.getId_cliente());
                bundle.putString("cliente_nombre", clientesModel.getNombre());
                bundle.putString("cliente_email", clientesModel.getEmail());
                bundle.putString("cliente_clave", clientesModel.getClave());
                bundle.putString("cliente_telefono", clientesModel.getTelefono());
                bundle.putString("cliente_fecha_nac", clientesModel.getFecha_nac());
                bundle.putString("cliente_estado", clientesModel.getEstado());
                bundle.putString("cliente_creado_en", clientesModel.getCreado_en());
                editFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, editFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            @Override
            public void onToggleStatusClick(ClientesModel clientesModel) {
                // Cambiar estado del cliente
                String nuevoEstado = clientesModel.getEstado().equals("activo") ? "inactivo" : "activo";

                Toast.makeText(getContext(),
                    "Cambiando estado de " + clientesModel.getNombre() + " a: " + nuevoEstado,
                    Toast.LENGTH_SHORT).show();

                // clientesViewModel.actualizarEstadoCliente(clientesModel, nuevoEstado);
            }
        });
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void listarClientes() {
        clientesViewModel.listarClientesLive().observe(getViewLifecycleOwner(), clientes -> {
            if (clientes != null && !clientes.isEmpty()) {
                adapter.setClientes(clientes);
            } else {
                adapter.setClientes(new ArrayList<>());
                Toast.makeText(getContext(), "No hay clientes para mostrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

}