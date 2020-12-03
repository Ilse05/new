package com.example.puppy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManagerNonConfig;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puppy.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity implements View.OnClickListener {

    private EditText nomP, corrP, pswP;
    TextView refe;
    Button regis;
    //VARIABLES DE LOS DATOS A UTILIZAR
    private String name = "";
    private String correo = "";
    private String contrase = "";

    private ProgressDialog progressDialog;


    ///paquete de autenticacion de firebase
    private FirebaseAuth auth;

    //metodo para utilizar la BD realtime de frirebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //instanciamos el metodo de autentificacion
        auth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference();

        Componentes();

        //iniciaFB();

    }

    private void Componentes() {
        botonesComponentes();
        Editcomponentes();

    }

    private void Editcomponentes() {
        //referencia de los views
        nomP = (TextInputEditText)findViewById(R.id.txRuser);
        corrP = (TextInputEditText) findViewById(R.id.txRemail);
        pswP = (TextInputEditText)findViewById(R.id.txRpass);

        //referencia de los views
        nomP = (TextInputEditText)findViewById(R.id.txRuser);
        corrP = (TextInputEditText) findViewById(R.id.txRemail);
        pswP = (TextInputEditText)findViewById(R.id.txRpass);

        //progressBar
        progressDialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
    }

    private void botonesComponentes() {
        regis = (Button) findViewById(R.id.bt_registro);
        refe =  (TextView) findViewById(R.id.txRefeIn);

        regis.setOnClickListener(this);
        refe.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_registro:
                name = nomP.getText().toString();
                correo = corrP.getText().toString();
                contrase = pswP.getText().toString();

                if (!name.isEmpty() && !correo.isEmpty() && !contrase.isEmpty()){

                    //validacion de 6 caracteres para la contraseña
                    if (contrase.length() >= 6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(registro.this, "La contraseña debe tener almenos 6 carácteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(registro.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.txRefeIn:
                startActivity(new Intent(registro.this, Dingreso.class));
                finish();
                break;
        }

    }

    private void registrarUsuario() {
        auth.createUserWithEmailAndPassword(correo,contrase).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //si el registro fue exitoso se almacenan los datos
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email", correo);
                    map.put("password",contrase);

                    //obtencion del ID del usuario dado por firebase
                    String id = auth.getCurrentUser().getUid();

                    databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            //si el crear datos fue correcto, se envia el usuario a la pantalla Dingreso de video
                            if (task2.isSuccessful()){
                                /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
                                mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home).setDrawerLayout(drawer).build();
                                 */
                               Toast.makeText(registro.this, "Datos guardatos", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registro.this, Dingreso.class));
                                finish();

                            }//por si los datos nos se crearon debidamente
                            else{
                                Toast.makeText(registro.this, "Los datos no fueron guardados", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }//sino, manda mensaje de que no se puso registar el usuario
                else{
                    Toast.makeText(registro.this, "Los datos no fueron creados correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}