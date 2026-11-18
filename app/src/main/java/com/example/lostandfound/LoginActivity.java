package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Screen 01: Login.
 *
 * With Firebase set up (see FIREBASE_SETUP.md), "Log in to FoundIt" checks the email and password
 * for real, and "Forgot password" sends a reset email. Without it (prototype mode) the buttons only
 * change screens: "Log in to FoundIt" opens Home once both boxes are filled in.
 * The Google and MCS buttons open Home straight away for now; real Google sign-in comes later.
 */
public class LoginActivity extends NoSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Someone who is already logged in goes straight to Home.
        if (AuthService.isLoggedIn(this)) {
            openHome();
            return;
        }

        setContentView(R.layout.activity_login);

        View loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> logIn(loginButton));
        findViewById(R.id.googleLoginButton).setOnClickListener(v -> openHome());
        findViewById(R.id.mcsLoginButton).setOnClickListener(v -> openHome());

        findViewById(R.id.forgotPasswordText).setOnClickListener(v -> forgotPassword());
        // Open Sign up on top of Login. Login stays open underneath, so Back returns here.
        findViewById(R.id.signUpText).setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void logIn(View loginButton) {
        String email = textOf(R.id.usernameInput);
        String password = textOf(R.id.passwordInput);
        if (email.isEmpty() || password.isEmpty()) {
            toast("Please fill in your username or email, and your password");
            return;
        }
        // Real accounts are made with an email address.
        if (AuthService.isReal(this) && !email.contains("@")) {
            toast("Please log in with your email address");
            return;
        }

        loginButton.setEnabled(false);   // so a second tap does not send it twice
        AuthService.logIn(this, email, password, new AuthService.Result() {
            @Override
            public void onSuccess() {
                openHome();
            }

            @Override
            public void onError(String message) {
                loginButton.setEnabled(true);
                toast(message);
            }
        });
    }

    private void forgotPassword() {
        if (!AuthService.isReal(this)) {
            toast("Password recovery is not available in this prototype");
            return;
        }
        String email = textOf(R.id.usernameInput);
        if (!email.contains("@")) {
            toast("Type your email in the first box, then tap Forgot password");
            return;
        }
        AuthService.sendPasswordReset(this, email, new AuthService.Result() {
            @Override
            public void onSuccess() {
                toast("If that email has an account, a reset link is on its way");
            }

            @Override
            public void onError(String message) {
                toast(message);
            }
        });
    }

    /** The words typed into a text box, without spaces at the start or end. */
    private String textOf(int inputId) {
        EditText input = findViewById(inputId);
        return input.getText().toString().trim();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openHome() {
        startActivity(new Intent(this, HomeActivity.class));
        // Close Login, so the Back button on Home leaves the app instead of coming back here.
        finish();
    }
}
