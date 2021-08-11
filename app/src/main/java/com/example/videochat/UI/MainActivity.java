package com.example.videochat.UI;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videochat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText editTextPass, editTextEmail;
    TextView txtRegister;
    Button btnLogin;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass =findViewById(R.id.editTextPass);
        txtRegister = findViewById(R.id.txtRegister);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "The button is not ready yet!", Toast.LENGTH_SHORT).show();
        });

        userLogin();

        clickText();
    }

    private void userLogin() {
        btnLogin.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPass.getText().toString().trim();

            if (email.isEmpty()){
                editTextEmail.setError("Email is required!");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please enter a valid email!");
                editTextPass.requestFocus();
                return;
            }

            if (password.isEmpty()){
                editTextPass.setError("Password is required!");
                editTextPass.requestFocus();
                return;
            }

            if (password.length() < 6){
                editTextPass.setError("Please min 6 symbol");
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                      startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        });
    }

    private void clickText() {
        txtRegister.setOnClickListener(view -> {
            progressBar.setVisibility(VISIBLE);
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}