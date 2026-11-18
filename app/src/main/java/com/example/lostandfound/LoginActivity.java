package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Screen 01: Login.
 *
 * There is no real account check yet (Firebase comes later), so the buttons only change screens:
 * "Log in to FoundIt" opens Home once both boxes are filled in, and the Google and MCS buttons
 * open Home straight away. When Firebase is added, the check goes in logIn().
 */
public class LoginActivity extends NoSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(v -> logIn());
        findViewById(R.id.googleLoginButton).setOnClickListener(v -> openHome());
        findViewById(R.id.mcsLoginButton).setOnClickListener(v -> openHome());

        findViewById(R.id.forgotPasswordText).setOnClickListener(v ->
                Toast.makeText(this, "Password recovery is not available in this prototype",
                        Toast.LENGTH_SHORT).show());
        // Open Sign up on top of Login. Login stays open underneath, so Back returns here.
        findViewById(R.id.signUpText).setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void logIn() {
        String username = textOf(R.id.usernameInput);
        String password = textOf(R.id.passwordInput);
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in your username or email, and your password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        openHome();
    }

    /** The words typed into a text box, without spaces at the start or end. */
    private String textOf(int inputId) {
        EditText input = findViewById(inputId);
        return input.getText().toString().trim();
    }

    private void openHome() {
        startActivity(new Intent(this, HomeActivity.class));
        // Close Login, so the Back button on Home leaves the app instead of coming back here.
        finish();
    }
}
