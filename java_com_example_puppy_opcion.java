package com.example.puppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class opcion extends AppCompatActivity  {

    Button  orga;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion);
        auth = FirebaseAuth.getInstance();

        orga = findViewById(R.id.bt_organiza);

        ///boton de inicio de sesion de dueño
        Button due;
        due = findViewById(R.id.bt_dueno);
        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Dingreso.class);
                startActivity(intent);
            }
        });

    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(opcion.this, MainActivity.class));
            finish();
        }

    }*/

}