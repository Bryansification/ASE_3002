package com.example.jj.bryancare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    private AccountManagerDao accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button createAccount = (Button) findViewById(R.id.buttonCreateAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {

        accountManager = new AccountManager();
        EditText nricField = (EditText) findViewById(R.id.nricField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);
        EditText password2Field = (EditText) findViewById(R.id.passwordField2);
        EditText emailField = (EditText) findViewById(R.id.emailField);

        String nric = nricField.getText().toString().trim().toUpperCase();
        String password = passwordField.getText().toString().trim();
        String password2 = password2Field.getText().toString().trim();
        String email = emailField.getText().toString().toLowerCase();

        if(verifyFieldInputs(nric, password, password2, email)) {
            accountManager.createAccount(getApplicationContext(), nric, password, email);
        }
    }

    private boolean verifyFieldInputs(String nric, String password, String password2, String email) {
        boolean fieldsNotEmpty = !(nric.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty());
        boolean passwordsMatch = password.matches(password2);
        if(!fieldsNotEmpty) {
            Toast.makeText(getApplicationContext(), "Please ensure that the required fields are filled", Toast.LENGTH_SHORT).show();
        }
        if(!passwordsMatch) {
            Toast.makeText(getApplicationContext(), "The passwords do not match", Toast.LENGTH_SHORT).show();
        }
        return fieldsNotEmpty && passwordsMatch;
    }
}