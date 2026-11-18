# Firebase setup for FoundIt

The app code is ready for Firebase sign-in (email and password). One part only you can do: make a
Firebase project with your Google account and put one file into the project. It takes about 10 minutes.

**Until you do, the app still runs in prototype mode.** Any filled-in login opens Home and no account
is saved, exactly like before. Nothing breaks.

## What you do

1. **Make the project.** Go to <https://console.firebase.google.com>, sign in with your Google account,
   and press **Create a project**. Name it `FoundIt`. Google Analytics is not needed, so you can turn it off.

2. **Add the Android app to it.** On the project page press the Android icon (the green robot).
   - **Android package name:** `com.example.lostandfound` (type it exactly)
   - **App nickname:** `FoundIt` (optional)
   - **SHA-1:** leave empty for now. It is only needed later for "Log in with Google".

3. **Download the file.** Press **Download google-services.json**.
   Put it in the `app` folder of the project, so the path is
   `LostAndFound1\app\google-services.json`, next to `build.gradle.kts`.
   You can skip the other steps Firebase shows you (the Gradle ones are already done).

4. **Turn on email sign-in.** In the Firebase console open **Build, then Authentication**, press
   **Get started**, choose **Email/Password**, switch on the first switch (Email/Password), and press **Save**.

5. **Run the app** (Android Studio, then Sync Project with Gradle Files, then Run).
   - Press **Click here to Sign up!**, fill every required box with a real-looking email and a password of at
     least 6 characters, and press **Create Account**.
   - Log in with that email and password. You should land on Home.
   - In the Firebase console, **Authentication, then Users** should list your new account.

## What works with Firebase on

| Screen | What it does |
| --- | --- |
| Log in | Checks the email and password. Wrong ones show a message. |
| Sign up | Makes a real account (email and password). The username is kept as the account's display name. |
| Forgot password | Type your email in the first box, then tap it: Firebase emails a reset link. |
| Log out | Ends the real login. Opening the app again while logged in goes straight to Home. |

## What does not work yet

- **Log in with Google and Log in with MCS Email account** still just open Home. Real Google sign-in needs
  your app's SHA-1 added in Firebase and a few more steps. Say when you want it.
- **Only emails can sign in.** A mobile number or a username in the login box shows "Please log in with your
  email address".
- Date of birth, contact details, reports, items and messages are still placeholders. They need a database
  (Firestore). That is the next step after sign-in works.

## Good to know

- `google-services.json` is **not** committed to git (it is in `.gitignore`), because the repo rule is no real
  settings in it. A fake example is in `app/google-services.json.example`. On another PC, copy your real
  file into `app` by hand (USB stick or your own Drive).
- Never paste the contents of that file, or any key, into a chat. If one is shared by mistake, make a new
  one in the Firebase console (Project settings, then Your apps).
- If sign-in says "Something went wrong", check the phone has internet and that step 4 was saved.
- The code is in `AuthService.java`. The screens only call that one class, so changes stay in one place.
