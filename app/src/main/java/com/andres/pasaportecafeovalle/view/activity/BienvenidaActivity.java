package com.andres.pasaportecafeovalle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.andres.pasaportecafeovalle.R;

public class BienvenidaActivity extends AppCompatActivity {

    Button btnLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);



        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(BienvenidaActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }


}
