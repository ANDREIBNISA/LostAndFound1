# FoundIt UI: open questions before Firebase

Written on 2026-11-12 on the school PC. The student sent new sample screens and wants the interface prepared (buttons working) before a Firebase database is added. Claude started building, then the student asked to be questioned first so both sides agree on the UI. The student had to leave before answering, so the questions are saved here to continue on the personal PC.

**How to use this file:** the student answers the ten questions below (writing "ok" accepts the recommendation). Build nothing more until they are answered. After that, ask the "Next round" questions, then finish the work listed under "Where the work stopped". Delete this file once everything in it is settled and CLAUDE.md is updated.

## The new samples

Seven landscape pictures (2000 x 1414) in the student's zip "uhh yeah worksheet number 5.zip". The pictures are not in the repo, so ask the student for the zip if the personal PC does not have it.

- **Login screen:** same layout as before, new colors. White input boxes with a black outline, a green "Login to Found it" button, white "Login with google" (with the Google G) and "Login with MCS Email account" buttons with black text. "Forgot password or username" is underlined, and so is "Click here" in "Dont have an account? Click here to Sign up!".
- **Sign up screen (new):** same building photo and dark-blue card. From top to bottom:
  - the title "FoundIt: An app built by students";
  - "Date of Birth": one white box split as MM | DD | YYYY;
  - "Username" (hint: Ex. “JohnDoe26”);
  - "Email/Mobile number" (hint: Ex. “johndoe@email.com”);
  - "Password" (hint: Password);
  - the heading "Contact details (Optional)";
  - "Contact number" (hint: Ex. +63 273523510);
  - "Alternate contact" (hint: Eg. Social Media);
  - the note "Contact details will never be used outside of this platform";
  - a green "Create Account" button;
  - a terms paragraph with "Terms of Use" and "Privacy Policy" underlined;
  - "Already have an account? Click here to login!".
- **Main menu, Reporting screen, Report status:** the same as the screens already built.
- **Messaging (revamp):** the chat list on the left is unchanged (User67890, User54321, User38576, User42198). The right side now has a header strip with an avatar and "User67890", then a conversation: four gray bubbles on the left with small avatars, then four on the right with avatars. The bubbles are blank placeholders. There is no box for typing a message.
- **Setting page (revamp):** the five tabs are unchanged. The right side now shows a label above a white box for each value.
  - "Account Information": Username (User12345), Full name (John doe), Email (Johndoe26@email.com), Phone number (+67 235234124), Password (********).
  - "Contact information": Phone number (User12345), Email (John doe), Alternative contact (Johndoe26@email.com), Alternative email (+67 235234124). These four values look shifted by one box.
  - The old "Login Methods" section is gone.

## Round 1 questions

**Q1. What "prep the interface" covers.** Before Firebase, how far should this round go?

- A. Only make the screens look like the new samples.
- B. A, plus every button visibly does something (opens a screen, checks a box is filled, shows a message), still with placeholder text.
- C. B, plus Java classes that hold data in memory, ready to swap for Firebase later.

Recommended: B. It makes the app feel complete without writing data code that Firebase would replace anyway.

Answer:

**Q2. The XML rule once the database arrives.** Right now every card, table row and chat row is typed into XML because the teacher requires XML. With a database, the number of items is unknown, so Java has to repeat one XML row design for each item (a `RecyclerView`). Is that allowed for the Firebase task?

Recommended: ask the teacher, and assume yes. To prepare, move each repeated thing (table row, chat row, bubble) into its own small XML file now, as the item cards already are.

Answer:

**Q3. Portrait and landscape.** The samples are all landscape. The student said the other agent made the vertical layouts and to "base from that". Which is meant?

- A. Keep both. Landscape copies the samples exactly; portrait is Claude's adaptation in the existing portrait style.
- B. Portrait is the main one now, and landscape matters less.
- C. Something else.

Recommended: A. It is how Sign up and Messages were built so far.

Answer:

**Q4. Screens that have no sample.** These have no drawing: Lost items, Found items, steps 2 and 3 of "Making a report", an item details page (when a card is tapped), Forgot password, and four Settings tabs (Security, Notifications, App preferences, Other settings). For each: is the student drawing it, should Claude design it, or does it stay unbuilt?

Recommended:

- Lost items, Found items: keep them as they are.
- Report steps 2 and 3, item details: Claude designs them in the same style. Step 2 would be description, category, date and photo; step 3 would be review and submit. Those fields match the console `Item` class.
- Forgot password: stays a message.
- The four Settings tabs: stay simple placeholders.

Answer:

**Q5. Sign up rules.** Which boxes must be filled, and where does "Create Account" go? The sample marks only "Contact details" as optional.

Recommended: Date of Birth, Username, Email/Mobile number and Password are required; the two contact boxes are optional. After success, go back to Login with a message. The code currently treats Date of Birth as optional, so that would change.

Answer:

**Q6. Login before Firebase exists.** What should the three login buttons and "Forgot password" do for now?

Recommended: "Login to Found it" needs both boxes filled, then opens Home. "Login with google" and "Login with MCS Email account" open Home directly. "Forgot password" shows a message.

Answer:

**Q7. Messages.** Two things the sample leaves open.

- a. The bubbles are blank placeholders. Claude wrote sample sentences ("Is it a black leather wallet?"). Keep them, change them, or leave the bubbles empty?
- b. There is no box to type a message and no Send button. Add them?

Recommended: a. Keep the sample sentences. b. Add a "Type a message" box and a Send button at the bottom. For now Send would show the typed text in one ready-made bubble.

Answer:

**Q8. Settings.** Three things in the revamp.

- a. The four "Contact information" values look shifted (see the sample description above). Copy as drawn, or fix?
- b. The old "Login Methods" section (passkeys, backup codes) is gone from the new sample. Delete it?
- c. Should the white boxes be editable, and should there be a Save button? The sample has none.

Recommended: a. Fix them so each box shows the right kind of value. b. Delete it. c. Editable, with a "Save changes" button at the bottom that only shows a message for now.

Answer:

**Q9. Report status tick boxes.** Every row has a tick box, but no button uses the ticked rows. What are they for?

Recommended: they select reports for a future "Delete selected" button. For now they only tick, and the header box ticks all rows, as it does today.

Answer:

**Q10. Spelling as drawn.** The samples say "Dont have an account?", "Login to Found it", "Login with google", and use both "Ex." and "Eg." for examples. Keep them exactly, or fix?

Recommended: keep them exactly as drawn, as with the earlier typos, unless the student says which to fix.

Answer:

## Next round

These depend on the answers above, so ask them afterwards:

- If Q1 is B or C: after login or sign up, should the sidebar, header and Settings show the username that was typed, or stay "User12345"?
- What should the search box do before there is a database? Today it shows a message and opens Lost items.
- If Q3 is A: in portrait, should a tapped chat open under the list (as now) or fill the screen with a Back arrow?
- If Q4 has Claude designing steps 2 and 3: confirm the fields of each step, and whether "Next Step" on step 1 must check that Lost or Found is ticked.
- The app's name under its icon is still "LostAndFound". Change it to "FoundIt"?
- For Firebase later: the Login box says "Username/Email/Mobile number". Firebase logs in with email and password most easily. Is logging in by email only acceptable at first?

## Where the work stopped

Built on the school PC on 2026-11-12, before the questions. None of it has been compiled or run yet, so build first (`./gradlew :app:assembleDebug`, or the Run arrow in Android Studio) and fix anything that fails.

Done:

- **Sign up screen:** `res/layout/activity_signup.xml` (one layout for both orientations, like Login) and `SignUpActivity.java`, registered in `AndroidManifest.xml`. "Create Account" checks that username, email and password are filled, shows a message and closes back to Login. "Click here to login!" closes back to Login. New ids: `birthMonthInput`, `birthDayInput`, `birthYearInput`, `signUpUsernameInput`, `signUpEmailInput`, `signUpPasswordInput`, `contactNumberInput`, `signUpAlternateContactInput`, `createAccountButton`, `termsText`, `loginLinkText`.
- **Login:** the new colors, and `signUpText` now opens `SignUpActivity`. The underlined texts are in `values/strings.xml` because `<u>` only works there.
- **Messages:** the conversation is in `res/layout/include_conversation.xml`, included by both `content_messages.xml` files. Tapping a chat sets `chatTitle` and the first bubble (`chatBubble`); the first chat starts open.
- **Shared pieces:** drawables `bg_input_white`, `bg_green_button`, `bg_white_button`, `bg_bubble`; colors `fi_green`, `fi_green_pressed`, `fi_bubble`, `fi_hint_dark`; styles `FoundIt.InputWhite`, `FoundIt.DatePart`, `FoundIt.SettingsField`, `FoundIt.Bubble`.

Not done:

- **Settings revamp:** `res/layout/settings_pages.xml` is still the old version. The style `FoundIt.SettingsField` is ready for the white boxes. After the revamp, the styles `FoundIt.SettingsLine` and `FoundIt.Blank` are unused and can be removed.
- **Build and run check** of everything above, in portrait and landscape.
- **CLAUDE.md:** its "How the app is built" and "What the buttons do" sections still describe the app before these changes. Update them once the answers are in and the work is finished.
