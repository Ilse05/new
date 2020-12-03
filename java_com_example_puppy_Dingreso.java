package com.example.puppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dingreso extends AppCompatActivity implements View.OnClickListener {

    EditText nomP, pswP;
    Button ingreso;
    TextView refe;

    private String email ="";
    private String passwprd="";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingreso);
        auth = FirebaseAuth.getInstance();

       Componentes();
    }

    private void Componentes() {
        botonesComponentes();
        Editcomponentes();
    }

    private void botonesComponentes() {
        ingreso = (Button) findViewById(R.id.bt_salir);
        refe =  (TextView) findViewById(R.id.txRefeRe);

        ingreso.setOnClickListener(this);
        refe.setOnClickListener(this);
    }

    private void Editcomponentes() {

        nomP = (TextInputEditText)findViewById(R.id.txuser);
        pswP = (TextInputEditText)findViewById(R.id.txpass);

        //referencia de los views
        nomP = (TextInputEditText)findViewById(R.id.txuser);
        pswP = (TextInputEditText)findViewById(R.id.txpass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_salir:

                email= nomP.getText().toString();
                passwprd = pswP.getText().toString();

                if (!email.isEmpty()  && !passwprd.isEmpty()){
                    loginUser();

                }else{
                    Toast.makeText(Dingreso.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.txRefeRe:
                startActivity(new Intent(Dingreso.this, registro.class));
                break;
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email, passwprd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //nos mete al nav_vanegation
                    startActivity(new Intent(Dingreso.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(Dingreso.this, "NO SE PUDO INICIAR SESIÓN... compruebe que los datos sean correctos", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}