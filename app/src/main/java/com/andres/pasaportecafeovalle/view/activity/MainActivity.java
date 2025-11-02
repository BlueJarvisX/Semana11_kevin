package com.andres.pasaportecafeovalle.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.view.fragment.ScanerQrFragment;
import com.andres.pasaportecafeovalle.view.fragment.admin.beneficios.BeneficiosGestionFragment;
import com.andres.pasaportecafeovalle.view.fragment.admin.clientes.FragmentAdminClientes;
import com.andres.pasaportecafeovalle.view.fragment.admin.sucursales.FragmentAdminSucursales;
import com.andres.pasaportecafeovalle.view.fragment.cliente.productos.FragmentProductos;
import com.andres.pasaportecafeovalle.view.fragment.cliente.beneficios.BeneficiosFragment;
import com.andres.pasaportecafeovalle.view.fragment.cliente.home.HomeFragment;
import com.andres.pasaportecafeovalle.view.fragment.cliente.perfil.PerfilFragment;
import com.andres.pasaportecafeovalle.view.fragment.admin.productos.FragmentAdminProductos;
import com.andres.pasaportecafeovalle.view.fragment.cliente.sucursales.SucursalesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        String rol = getIntent().getStringExtra("rol");
        String correo = getIntent().getStringExtra("correo");
        int id_usuario = getIntent().getIntExtra("id_usuario", -1);
        int id_sucursal = getIntent().getIntExtra("id_sucursal", -1);

        if (rol != null) {
            switch (rol) {
                case "admin":
                    bottomNavigationView.inflateMenu(R.menu.menu_nav_admin);
                    break;
                case "cliente":
                    bottomNavigationView.inflateMenu(R.menu.menu_nav_icon);
                    break;
                case "cajero":
                    bottomNavigationView.inflateMenu(R.menu.menu_nav_cajero);
                    break;
                case "super admin":
                    bottomNavigationView.inflateMenu(R.menu.menu_nav_superadmin);
                    break;
            }

        }

        //Los fragment por defecto xd
        cargarFragmentCliente();

    }

    private void cargarFragmentCliente() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            }
            else if (itemId == R.id.nav_beneficios) {
                selectedFragment = new BeneficiosFragment();
            }
            else if (itemId == R.id.nav_perfil) {
                selectedFragment = new PerfilFragment();
            }
            else if (itemId == R.id.nav_productos) {
                selectedFragment = new FragmentProductos();
            }
            else if (itemId == R.id.nav_sucursales){
                selectedFragment = new SucursalesFragment();
            }
            else if (itemId == R.id.nav_gestion_clientes) {
                selectedFragment = new FragmentAdminClientes();
            }
            else if (itemId == R.id.nav_gestion_sucursales) {
                selectedFragment = new FragmentAdminSucursales();
            }
            else if (itemId == R.id.nav_gestion_productos) {
                selectedFragment = new FragmentAdminProductos();
            }
            else if (itemId == R.id.nav_gestion_beneficios) {
                selectedFragment = new BeneficiosGestionFragment();
            }
            else if (itemId == R.id.nav_registrar_qr) {
                selectedFragment = new ScanerQrFragment();
            }
            else if (itemId == R.id.nav_crear_sucursal){
                selectedFragment = new FragmentAdminSucursales();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
