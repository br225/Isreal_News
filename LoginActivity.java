package com.example.khalil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {
    //properties:
    private Button btnLogin;
    private Button btnRegister;
    private EditText etPassword;//TextInputEditText is An EditText (extends) (Polymorphism)
    private EditText etEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //find views:
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
//setOn***listener:
        //setOn***listener:
        btnLogin.setOnClickListener(v -> {
            if (isEmailValid() | isPasswordValid()) {
                return;
            }
            toggleProgressDialog(true);//show


            //ref to the Auth Object of firebase:
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            //use the singInWithEmailAndPassword(email ,password) method:
            mAuth.signInWithEmailAndPassword(getEmail(), getPassword()).
                    addOnSuccessListener(this, authResult -> {
                        gotoMain();
                    }).addOnFailureListener(this, this::showError);
        });

        btnRegister.setOnClickListener(v -> {

            if (isEmailValid() | isPasswordValid()) {
                return;
            }
            toggleProgressDialog(true);//show


            //ref to the Auth Object of firebase:
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            //use the createUserWithEmailAndPassword(email ,password) method:
            mAuth.createUserWithEmailAndPassword(getEmail(), getPassword()).
                    addOnSuccessListener(this, authResult -> {
                        gotoMain();
                    }).addOnFailureListener(this, this::showError);
        });
    }

    private void showError(Exception e) {
        toggleProgressDialog(false);
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void gotoMain() {
        toggleProgressDialog(false);//dismiss
        //success! Go to Main Activity using an intent:
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
        finish(); //make sure back button does not send us back to login
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etEmail.getText().toString();
    }

    private boolean isEmailValid() {
        //return getEmail().contains("@")&& getEmail().length()>5;
        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
        if (!isValid) {
            etEmail.setError("Email is not valid");
        }
        return !isValid;
    }

    private boolean isPasswordValid() {
        boolean isValid = getPassword().length() > 5;
        if (!isValid) {
            etPassword.setError("Password must contain at least 6 characters");
        }
        return !isValid;
    }

    public void toggleProgressDialog(boolean shouldShow) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Connecting to remote server");
        }
        if (shouldShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

}

