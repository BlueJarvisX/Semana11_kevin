package com.andres.pasaportecafeovalle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.andres.pasaportecafeovalle.R;
import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;
import com.andres.pasaportecafeovalle.data.repository.ClientesRepositoty;
import com.andres.pasaportecafeovalle.view.modelo.Usuario;
import com.andres.pasaportecafeovalle.viewmodel.ClientesViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    private ClientesViewModel clientesViewModel;
    List<Usuario> usuario = new ArrayList<>();
    EditText txtCorreo, txtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clientesViewModel = new ViewModelProvider(this).get(ClientesViewModel.class);

        for (int i = 1; i <= 4; i++){
            usuario.add(new Usuario(i, i, "admin" + i + "@gmail.com", "1234", "admin"));
        }

        for (int i = 1; i <= 4; i++){
            usuario.add(new Usuario(i, i, "cajero" + i + "@gmail.com", "1234", "cajero"));
        }

        usuario.add(new Usuario(10, 0, "superAdmin@gmail.com", "1234", "super admin"));


        btnLogin = findViewById(R.id.btnLogin);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtContraseña);

        btnLogin.setOnClickListener(v -> {
            String correo = txtCorreo.getText().toString().trim();
            String clave = txtPassword.getText().toString().trim();

            Usuario user = buscarUsuario(correo, clave);
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id_usuario", user.getId_usuario());
                intent.putExtra("id_sucursal", user.getId_sucursal());
                intent.putExtra("rol", user.getRol());
                intent.putExtra("correo", user.getEmail());
                startActivity(intent);
                finish();
                return;
            }

            if (correo.isEmpty()) {
                txtCorreo.setError("Ingrese su correo");
                return;
            }
            if (clave.isEmpty()) {
                txtPassword.setError("Ingrese su contraseña");
                return;
            }

            clientesViewModel.login(correo, clave, new ClientesRepositoty.LoginCallback() {
                @Override
                public void onSuccess(ClientesEntity cliente) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Bienvenido " + cliente.getNombre(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("correo", cliente.getEmail());
                        intent.putExtra("rol", "cliente");
                        startActivity(intent);
                        finish();
                    });
                }

                @Override
                public void onFailure(String mensajeError) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, mensajeError, Toast.LENGTH_SHORT).show();
                    });
                }
            });

        });
    }

    public Usuario buscarUsuario(String correo, String clave) {
        for (Usuario u : usuario) {
            if (u.getEmail().equals(correo) && u.getClave().equals(clave)) {
                return u;
            }
        }
        return null;
    }
}
