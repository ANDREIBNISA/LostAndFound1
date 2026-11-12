package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Screen 01: Login. There is no real account check yet, so every login button opens Home. */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginButton).setOnClickListener(v -> openHome());
        findViewById(R.id.googleLoginButton).setOnClickListener(v -> openHome());
        findViewById(R.id.mcsLoginButton).setOnClickListener(v -> openHome());

        findViewById(R.id.forgotPasswordText).setOnClickListener(v ->
                Toast.makeText(this, "Password recovery is not available in this prototype",
                        Toast.LENGTH_SHORT).show());
        // Open Sign up on top of Login. Login stays open underneath, so Back returns here.
        findViewById(R.id.signUpText).setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void openHome() {
        startActivity(new Intent(this, HomeActivity.class));
        // Close Login, so the Back button on Home leaves the app instead of coming back here.
        finish();
    }
}
