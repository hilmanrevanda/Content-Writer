package com.example.teukuatjeh.finalprojectmp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    String TAG = "RegisterActivity";
//    Firebase
    private FirebaseAuth mAuth;

//    Widget Init
    TextView tvLogin;

    Button btnRegister;

    EditText etName,etEmail,etPass1,etPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Firebase Login Action
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //Check if user is signed in (non-null) and update UI accordingly.

//        Widget
        tvLogin = (TextView)findViewById(R.id.Login);

        btnRegister = (Button)findViewById(R.id.RegisterButton);

        etEmail = (EditText)findViewById(R.id.userEmail);
        etName = (EditText)findViewById(R.id.userName);
        etPass1 = (EditText)findViewById(R.id.userPassword1);
        etPass2 = (EditText)findViewById(R.id.userPassword2);

//        Register Action
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass1.getText().toString();
                String password2 = etPass2.getText().toString();

                if(password.equals(password2)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, "Authentication Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(getBaseContext(), LoginActivity.class);
                                        startActivity(in);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this, "Password tidak sama.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Login Action
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }
}
