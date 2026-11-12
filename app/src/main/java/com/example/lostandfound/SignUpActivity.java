package com.example.lostandfound;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Sign up screen. It opens from the "Click here to Sign up!" line on Login.
 *
 * There is no database yet, so no account is really saved. "Create Account" only checks
 * that the three required boxes are filled in, then goes back to Login.
 * When Firebase is added, the saving goes in createAccount().
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.createAccountButton).setOnClickListener(v -> createAccount());

        // Login is still open underneath this screen, so closing this one shows it again.
        findViewById(R.id.loginLinkText).setOnClickListener(v -> finish());

        findViewById(R.id.termsText).setOnClickListener(v ->
                toast("Terms of Use and Privacy Policy are not written yet"));
    }

    private void createAccount() {
        String username = textOf(R.id.signUpUsernameInput);
        String email = textOf(R.id.signUpEmailInput);
        String password = textOf(R.id.signUpPasswordInput);

        // Date of Birth and the contact details are optional for now.
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            toast("Please fill in your username, email or mobile number, and password");
            return;
        }

        toast("Account created for " + username + " (prototype). You can log in now.");
        finish();
    }

    /** The words typed into a text box, without spaces at the start or end. */
    private String textOf(int inputId) {
        EditText input = findViewById(inputId);
        return input.getText().toString().trim();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
