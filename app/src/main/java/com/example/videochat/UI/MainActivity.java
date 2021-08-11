package com.example.videochat.UI;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videochat.R;

public class MainActivity extends AppCompatActivity {

    EditText editTextPass, editTextEmail;
    TextView txtRegister;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass =findViewById(R.id.editTextPass);
        txtRegister = findViewById(R.id.txtRegister);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "The button is not ready yet!", Toast.LENGTH_SHORT).show();
        });


        clickText();
    }


    private void clickText() {
        txtRegister.setOnClickListener(view -> {
            progressBar.setVisibility(VISIBLE);
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}