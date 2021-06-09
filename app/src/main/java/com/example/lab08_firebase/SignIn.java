package com.example.lab08_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignIn extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_screen);
        connectView();


        // get firebase
        auth = FirebaseAuth.getInstance();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edtEmail.getText().toString();
                final String Password = edtPassword.getText().toString();

                //check empty
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(SignIn.this, "Enter Email address !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(SignIn.this, "Enter Password !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Authenticate user
                auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            if(Password.length()<6){
                                edtPassword.setError("Password too short, enter minimum 6 characters!");
                            } else {
                                Toast.makeText(SignIn.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Intent intent = new Intent(SignIn.this, Home.class);
                            startActivity(intent);

                        }

                    }
                });

            }
        });
    }
    void connectView(){
        edtEmail = (EditText) findViewById(R.id.edtEmail_SignIn);
        edtPassword = (EditText) findViewById(R.id.edtPassword_SignIn);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

    }

}
