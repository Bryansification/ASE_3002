package com.example.jj.bryancare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private AccountManagerDao accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button createAccount = (Button) findViewById(R.id.buttonCreateAccount);
        createAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent createAccountIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createAccountIntent);
            }
        });

        Button signIn = (Button) findViewById(R.id.buttonSignIn);
        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        accountManager = new AccountManager();

        EditText nricField = (EditText) findViewById(R.id.nricField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);

        String nric = nricField.getText().toString().trim().toUpperCase();
        String password = passwordField.getText().toString().trim();

        if(verifyFieldInputs(nric, password)) {
            accountManager.login(getApplicationContext(), nric, password);
        }
    }

    private boolean verifyFieldInputs(String nric, String password) {
        boolean fieldsNotEmpty = !(nric.isEmpty() || password.isEmpty());
        if(!fieldsNotEmpty) {
            Toast.makeText(getApplicationContext(), "Please ensure that the required fields are filled", Toast.LENGTH_SHORT).show();
        }

        return fieldsNotEmpty;
    }

}
