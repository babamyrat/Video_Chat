package com.example.videochat.UI;

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
import com.example.videochat.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextAge, editTextPass, editTextEmail;
    TextView txtLogin;
    Button btnRegister;
    ProgressBar progressBar2;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextEmail = findViewById(R.id.editTextEmail2);
        editTextPass = findViewById(R.id.editTextPass2);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        progressBar2 = findViewById(R.id.progressBar2);
        auth = FirebaseAuth.getInstance();

        clickLogin();

        registerUser();

    }

    private void registerUser() {
        //Button for register
        btnRegister.setOnClickListener(view -> {
            Toast.makeText(RegisterActivity.this, "The button is not ready yet", Toast.LENGTH_SHORT).show();
            String fullName = editTextName.getText().toString().trim();
            String age = editTextAge.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPass.getText().toString().trim();

            if (fullName.isEmpty()){
                editTextName.setError("Full name is required");
                editTextName.requestFocus();
                return;
            }

            if (age.isEmpty()){
                editTextAge.setError("Age is required");
                editTextAge.requestFocus();
                return;
            }

            if (email.isEmpty()){
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please provide valid email");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()){
                editTextPass.setError("Password is required");
                editTextPass.requestFocus();
                return;
            }
            if (password.length() < 6){
                editTextPass.setError("Please write min 6 symbol!");
                editTextPass.requestFocus();
                return;
            }

            progressBar2.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                UserModel user = new UserModel(fullName, age, email);

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Successful!!!", Toast.LENGTH_SHORT).show();
                                            progressBar2.setVisibility(View.GONE);

                                            //...
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Not successful!", Toast.LENGTH_SHORT).show();
                                            progressBar2.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                progressBar2.setVisibility(View.GONE);
                            }
                        }
                    });

        });
    }

    //Click text for new activity
    private void clickLogin() {
        txtLogin.setOnClickListener(view -> {
            progressBar2.setVisibility(View.VISIBLE);
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}