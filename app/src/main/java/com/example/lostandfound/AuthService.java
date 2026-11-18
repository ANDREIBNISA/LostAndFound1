package com.example.lostandfound;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * All the account work (log in, sign up, password reset, log out) goes through this one class,
 * so the screens never talk to Firebase directly.
 *
 * Firebase only works once app/google-services.json is in the project (see FIREBASE_SETUP.md).
 * Until then isReal() is false and every method does the old prototype thing: it says "yes" and
 * the screens carry on, so the app can still be shown without a Firebase project.
 */
final class AuthService {

    /** What a screen wants to hear back: it worked, or here is a short message to show the person. */
    interface Result {
        void onSuccess();

        void onError(String message);
    }

    private AuthService() {
    }

    /** True when Firebase is set up (google-services.json was added), false in prototype mode. */
    static boolean isReal(Context context) {
        try {
            return !FirebaseApp.getApps(context).isEmpty();
        } catch (RuntimeException e) {
            return false;
        }
    }

    /** True when a real account is already logged in on this phone. */
    static boolean isLoggedIn(Context context) {
        return isReal(context) && FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    /** The email of the account that is logged in, or "" in prototype mode. */
    static String currentEmail(Context context) {
        if (!isReal(context)) {
            return "";
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user == null || user.getEmail() == null ? "" : user.getEmail();
    }

    static void logIn(Context context, String email, String password, Result result) {
        if (!isReal(context)) {
            result.onSuccess();
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(done -> result.onSuccess())
                .addOnFailureListener(error -> result.onError(friendly(error)));
    }

    /** Make a new account. The username is kept as the account's display name. */
    static void signUp(Context context, String email, String password, String username, Result result) {
        if (!isReal(context)) {
            result.onSuccess();
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(done -> {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        user.updateProfile(new UserProfileChangeRequest.Builder()
                                .setDisplayName(username).build());
                    }
                    // Firebase logs a new account in by itself. The Sign up screen goes back to
                    // Login, so log out here and let the person log in on purpose.
                    auth.signOut();
                    result.onSuccess();
                })
                .addOnFailureListener(error -> result.onError(friendly(error)));
    }

    static void sendPasswordReset(Context context, String email, Result result) {
        if (!isReal(context)) {
            result.onSuccess();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(done -> result.onSuccess())
                .addOnFailureListener(error -> result.onError(friendly(error)));
    }

    static void logOut(Context context) {
        if (isReal(context)) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    /** Turn Firebase's technical errors into a short sentence a person can act on. */
    private static String friendly(Exception error) {
        if (error instanceof FirebaseAuthInvalidUserException) {
            return "No account was found with that email";
        }
        if (error instanceof FirebaseAuthInvalidCredentialsException) {
            return "The email or password is not right";
        }
        if (error instanceof FirebaseAuthUserCollisionException) {
            return "That email already has an account";
        }
        if (error instanceof FirebaseAuthWeakPasswordException) {
            return "Please use a stronger password (at least 6 characters)";
        }
        if (error instanceof FirebaseNetworkException) {
            return "No internet connection. Please try again";
        }
        return "Something went wrong. Please try again";
    }
}
