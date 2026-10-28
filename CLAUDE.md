# Lost and Found App (class prototype)

Android Studio project, Java. A lost and found prototype that is console-first and uses simulated data (no database for now). The student is new to Java, so explain with simple analogies, give complete files rather than snippets, and write explanations in English.

## Working rules

- Do not add Claude as a co-author, contributor, or "generated with" line to commits or pull requests.
- The student does their own commits and pushes. Only commit if they ask.
- Do not run `git init` in a folder that already has a `.git`. A fresh `git init` earlier caused "unrelated histories" errors.
- Never put real credentials in the repo. A `config.properties` (git-ignored) with a committed `config.properties.example` is the plan for when a database is added later.
- Look before deleting anything. Earlier, a text file was wrongly called a folder.

## Where things live

- Console Java classes and the JUnit test: `app/src/test/java/com/example/quarter2/minipeta3/` (package `com.example.quarter2.minipeta3`). Do not put these in `src/main`.
- XML screen layouts: `app/src/main/res/layout/` (Android only reads layouts from here).
- The launcher Activity (the only Android class): `app/src/main/java/com/example/lostandfound/MainActivity.java`. Android only runs Activities from `src/main`, so this is the one exception to the rule above.
- `app/src/test/java/com/example/quarter2/TestRunInstruction` is a plain text file with the terminal steps for running the console version. Keep it.

Note: the assignment slide says `com/example/systemname/quarter2/minipeta3`. The student chose `com.example.quarter2.minipeta3` for now. Do not rename the package unless asked.

## Assignment technical requirements for the console code (must keep)

1. **Test directory only.** All console class files and test scripts go in the minipeta3 test folder above.
2. **Scanner Golden Rule.** Feature classes must never write `new Scanner(System.in)`. Every feature method takes a `Scanner` parameter (for example `runFeature(Scanner scanner, SimulatedData data)`). Only `ConsoleLauncher.main` creates a Scanner on `System.in`.
3. **Echo all inputs.** Print each value right after reading it (`int choice = ...; System.out.println(choice);`).
4. **Automated integration test.** One central JUnit `@Test` file (`LostAndFoundFlowTest`) runs the whole program flow start to finish.
5. **Dynamic test generation.** The test builds the keystroke sequence with a `StringBuilder`, `while` loops and `if / else` statements.
6. **Stream conversion.** The generated string goes into a `ByteArrayInputStream`, which feeds the `Scanner` that launches the main menu class (`MainMenu`).

## Current console code

- `Item` : one report (postID, itemName, itemStatus lost/found/claim, itemHolder, itemOwner, itemCategory, itemDescription, itemLocation, reportDate, contactInfo, matchScore).
- `SimulatedData` : in-memory list of items with 3 sample posts, `add`, `getAll`, `findById`. This is the stand-in for a database.
- `MainMenu` : main loop (1 Reported items, 2 Search items, 3 Exit).
- `ViewItemsFeature` : prints the list, then a sub-menu to add or claim an item.
- `ReportItemFeature`, `ClaimItemFeature`, `SearchItemFeature` : the individual features.
- `ConsoleLauncher` : `main` that starts `MainMenu` on the real keyboard.
- `LostAndFoundFlowTest` : the integration test.

Data is in memory only and resets every run. The Android screens do not use any of this code yet.

## Running the console version

Android Studio's run arrow cannot run `main` from the test folder (error: `SourceSet with name 'unitTest' not found`). Run from a terminal in `app/src/test/java` instead. On Windows PowerShell, borrow Android Studio's JDK first:

```
$env:Path += ";C:\Program Files\Android\Android Studio\jbr\bin"
javac -d ..\..\..\..\lf-out com\example\quarter2\minipeta3\ConsoleLauncher.java
java -cp ..\..\..\..\lf-out com.example.quarter2.minipeta3.ConsoleLauncher
```

Recompile with `javac` after any code change. `lf-out/` is build output and is no longer tracked by git. The root `.gitignore` does not list it yet (only `app/.gitignore` has `/lf-out`, which covers the wrong folder), so it shows as untracked until `lf-out/` is added to the root `.gitignore`. Run the integration test by right-clicking `LostAndFoundFlowTest` in Android Studio, or `gradlew :app:testDebugUnitTest`.

## Mini PETA: Mobile App UI Prototype (current task)

Source: the class PDF "Q3 Mini PETA 1". The goal is the front-end UI built with XML in Android Studio. Backend, database and arrays are not needed yet.

What the PDF asks for:

- At least 3 screens: 01 Welcome/Login, 02 Home/Dashboard, 03 Main Feature. More screens are allowed.
- XML layouts using suitable components: TextView, EditText, Button, ImageView and layout containers.
- Meaningful IDs on important buttons, inputs and views so Java can use them later.
- Consistent spacing, text hierarchy and placement, readable on a phone-sized screen.
- The project must open and run. The student should inspect each screen on an emulator or device.
- Allowed: placeholder data, buttons that do nothing, simple icons, extra screens.
- Not required: database/Firebase, array processing, complex backend logic, advanced authentication, full CRUD.
- Graded out of 20: UI completeness, XML and technical implementation, design and usability, project compliance (5 points each). Going beyond the scope can cost points in "project compliance".

The three layouts, all in `app/src/main/res/layout/`:

- `activity_login.xml` : login screen (IDs: `usernameInput`, `passwordInput`, `loginButton`, `createAccountText`).
- `activity_home.xml` : home menu (IDs: `welcomeText`, `reportedItemsButton`, `reportLostButton`, `reportFoundButton`, `searchButton`, `logoutButton`).
- `activity_lost_found.xml` : main feature screen with a search box, All/Lost/Found tabs, three sample posts typed into the XML, and Add New Item (IDs: `searchInput`, `allTab`, `lostTab`, `foundTab`, `addItemButton`).

`MainActivity` only swaps between these screens so they can be seen on a device: Login button goes to Home, "Browse Lost and Found Items" goes to the list, Log Out goes back to Login, and the phone's Back button goes one screen back. Everything else is intentionally non-functional. Do not add data wiring unless the student asks. If asked, keep the console code and the test working.

## Worksheet 5 redesign (asked for, then paused)

On 2026-10-28 (on the SHS PC) the student asked to replace everything in `app/src/main/res/layout/` with the UI from their PDF "uhh yeah worksheet number 5" (in their Downloads folder, not in the repo). They cancelled before any project file was changed, so the three old layouts and `MainActivity` are still as described above. If they ask again, this is the design:

- Six screens, all drawn landscape (page size 1123 x 794).
- 01 Login: a school building photo with a white haze as the background, and a centered dark-blue rounded card. It has the title "FoundIt: An app built by students", "Username/Email/Mobile number" and "Password" boxes, a "Login to Found it" button, a "Forgot password or username" link, "Or", "Login with google" (with the Google G), "Login with MCS Email account", and "Dont have an account? Click here to Sign up!".
- Screens 02 to 06 share a top bar (app name on the left, a search box "Search for items. search by description" with a magnifier) and a left sidebar (gray avatar with "User12345", then buttons Home, Lost items, Found items, Make a report, Report status, Messages, Settings; the button for the current screen is a darker blue). The content area is dark navy with a light-blue rounded panel.
- 02 Home: "Suggested items" with rows "High priority searches" and "Recent report" (shown twice). Each row has 5 item cards (picture placeholder, "Item:", "Lost by: User67" or "Found by: User67") and a round "More items" arrow. The page scrolls.
- 03 Make a report: "Making a report / Step 1/3: Item details". Inputs with sample hints: Item name (Black leather wallet), Item Owner or Item Finder (Juan Dela Cruz), Lost/Found at (Canteen table), Contact details (Email, Mobile number, Alternate contact (Ex. Social Media)). Checkboxes "Lost item" and "Found item". Buttons "Import details from settings" and "Next Step" with a big arrow.
- 04 Report status: a table with columns Report ID, Item name, Status, Report date and a checkbox. Nine sample rows (L000001 Black leather wallet, F000002 iPair 67 pro max 64tb, L000067 rtx4090, L067676 ryzen 9 9980x3d, L694201 128gb ddr5 ram, F123456 1,000$ worth of cash, F193847, L080910 and L723650 Black leather wallet). "Found" is green, "Not Found" is white, every date is September 11, 2026.
- 05 Messages: a list of 4 chats (User67890, User54321, User38576, User42198) with an avatar and a one-line preview; the first is highlighted. The right side is empty.
- 06 Settings: tabs on the left (Account Information, Security, Notifications, App preferences, Other settings). On the right: Account Information (Username, Full name, Email, Number), Contact information (Phone number, Email, Social medias, Alternative contacts), Login Methods (Passkeys: 0 Added, Backup codes: 10 left, two step verification, Alternitive Contacts).

The plan that was being followed: layouts `activity_login`, `activity_home`, `activity_make_report`, `activity_report_status`, `activity_messages`, `activity_settings`, with the top bar and sidebar in their own layout files pulled in with `<include>`; `MainActivity` changed so the sidebar buttons switch screens; and the activity locked to landscape in the manifest, since the whole design is landscape. Points to remember:

- With the MaterialComponents theme, a `<Button>` with its own `android:background` also needs `app:backgroundTint="@null"`, or the theme color paints over it.
- `targetSdk` is 36, so the app draws behind the status bar. Give the root view `android:fitsSystemWindows="true"`.
- The building photo for the login background is not in the project.
- The PDF has typos ("Dont", "Alternitive"). Ask before fixing them.

### Student's uncommitted changes on the SHS PC (2026-10-28)

These were in the working tree, made by the student, and were left uncommitted:

- Five PNGs staged in `app/src/main/res/drawable/`: `manila cathedral school logo.png`, `menu (bars).png`, `more (three dots).png`, `search icon.png`, `settings.png` (each 1048 x 1048). Android resource file names may only use lowercase letters, digits and `_`, so the four names with spaces or brackets will stop the build. Rename them (for example `manila_cathedral_school_logo.png`, `menu_bars.png`, `more_three_dots.png`, `search_icon.png`). Big pictures in plain `drawable/` are scaled up on high-density phones, so `drawable-nodpi/` is a better home for them.
- `cardview` 1.0.0 added to `gradle/libs.versions.toml` and `app/build.gradle.kts`.
- `activity_login.xml` was emptied down to a bare `ConstraintLayout`. The old login content is gone, but `MainActivity` still looks up `loginButton`, so the app will crash on start until the login screen is rebuilt. ConstraintLayout is also not a declared dependency.

### Tools on the SHS PC

- Python is not installed (only the Microsoft Store stub), and the Read tool cannot open PDFs because `pdftoppm` is missing. Windows' built-in `Windows.Data.Pdf` API, called from PowerShell, can render PDF pages to PNG instead.
- Global git identity is `Andrei <andreisalew2010@gmail.com>`, with no repo-local identity set.

## Picking this up on the main (home) PC

The latest session was on the second PC. Work moves back to the main PC like this:

1. In the project folder run `git fetch` then `git switch sample-screens` then `git pull`. If the pull complains about untracked `lf-out/` files, move the `lf-out/` folder aside and pull again (see Git notes).
2. Check `git config user.name` and `git config user.email`. Commits must be authored as `Andrei <peejaysale001@gmail.com>`. Set them for the repo if the main PC shows something else.
3. In Android Studio let Gradle sync, then run `LostAndFoundFlowTest` (or `gradlew :app:testDebugUnitTest`). Gradle worked on the main PC before. Confirm both tests pass: `fullProgramFlow` and `badMenuInputDoesNotCrash`.
4. Add `lf-out/` to the root `.gitignore` and save it.
5. Run the app and check the three screens (login, home, lost/found list). Remember the main PC has about 7 GB of RAM: see the emulator notes, or use a real phone with USB debugging.
6. Fix any spacing or layout problems in the XML. Keep the IDs that `MainActivity` uses: `loginButton`, `reportedItemsButton`, `logoutButton`.

State of the branch when work left the second PC: `sample-screens` on GitHub was up to date with the second PC, with nothing uncommitted. The emulator and `.android/avd` folder on the second PC do not travel with the repo.

## Progress so far

Done:

- Three XML layouts added and committed.
- `AndroidManifest.xml` repaired. It was cut off mid-tag (`<application` never closed, no theme, no activity), so the app could not build. It now has the theme and `MainActivity` as the launcher.
- `MainActivity.java` added.
- Two typos fixed ("TestRunInstruction" had replaced the word "a" in `ClaimItemFeature.java` and a comment in `LostAndFoundFlowTest.java`).
- `./gradlew :app:assembleDebug` and `:app:testDebugUnitTest` both passed on the home PC.
- `MainMenu` and `ViewItemsFeature` now catch `NumberFormatException` for non-numeric menu input, print "Please type a number." and show the menu again. The input is echoed before parsing. A second test, `badMenuInputDoesNotCrash`, covers this. Checked by hand with `javac`/`java`; the Gradle run of the new test is not confirmed yet.
- The purple app-name banner was removed by switching the theme parent to `Theme.MaterialComponents.DayNight.NoActionBar` in both `values/themes.xml` and `values-night/themes.xml`.
- Home screen outer padding changed from 24dp to 32dp (student's edit).
- Stale `androidTest/.../myapplication/ExampleInstrumentedTest.java` deleted (it asserted the wrong package). The `lostandfound` one stays.
- `lf-out/` class files removed from git tracking.
- A detailed XML walkthrough was given in chat (tags, nesting, View vs ViewGroup, attributes, IDs and `findViewById`, the manifest, how to edit and move things in a `LinearLayout`). It was not saved to a file.
- A new emulator was created on the second PC (the old `Medium_Phone` was deleted).

Not done yet:

- Confirm all three screens look right on the emulator (spacing, nothing cut off) and fix any layout issues in the XML.
- Run `gradlew :app:testDebugUnitTest` or the test in Android Studio to confirm both tests pass after the bad-input change. Claude's shell cannot run Gradle on this PC ("Unable to establish loopback connection"), so run it from Android Studio.
- Add `lf-out/` to the root `.gitignore` and save it.
- `config.properties.example` still has what looks like a real Supabase host and username. Replace them with placeholders.
- Optional, only if the student asks: ImageView logo, IDs on the Claim buttons, moving text to `strings.xml`. The student said the project only needs the sample screens, so these were added and then reverted. Do not add them unprompted.

## XML explanation (given in chat, not saved to a file)

The student asked for a detailed explanation of how XML works and what it did for the project, and it was given in chat. If they want it again or saved as a file (for example `XML_EXPLANATION.md`), cover: tags and nesting, ViewGroups (like `LinearLayout`) versus Views (like `Button`), `layout_width`/`layout_height`, `android:id` and `findViewById`, how the manifest registers `MainActivity`, and how XML describes what is on screen while Java decides what happens on a tap. Use simple analogies.

## Git notes

- Remote: `https://github.com/ANDREIBNISA/LostAndFound1.git`.
- Work is on the branch `sample-screens`. On another PC: clone the repo (if it is not there yet), then `git fetch` and `git switch sample-screens`.
- `config.properties` and `local.properties` are git-ignored and will not come along; Android Studio creates `local.properties` on each machine.
- Identity: commits should be authored as `Andrei`. The repo's local git config (`.git/config`, which is not pushed) is set to `Andrei <peejaysale001@gmail.com>`. The global `~/.gitconfig` on the second PC says `clyde` with a school email, so check `git config user.name` and `git config user.email` before committing on a new machine. Older commits use `andreisalew2010@gmail.com`. GitHub only shows the student's profile on a commit if the commit email is added and verified under GitHub Settings, Emails.
- Never force-push without the student's yes. `git push --force-with-lease` was used once, with their approval, to fix the author of the latest commit.
- If `git rebase` or `git pull` stops with "untracked working tree files would be overwritten" for `lf-out/`, move the `lf-out/` folder aside (it is regenerated by `javac`) and retry.

## Emulator notes

- The home PC has about 7 GB of RAM, and the Pixel 6 emulator ran out of memory and crashed Android Studio. The AVD's `hw.ramSize` was lowered from 2048 to 1536 (backup: `config.ini.bak`). A real phone with USB debugging is a good fallback.
- If the emulator says "already running", a leftover `qemu-system-x86_64` / `emulator` process is still alive. End it in Task Manager.
- The second PC has about 15 GB of RAM. Its first emulator ran as `qemu-system-i386` (a 32-bit image) and sat at `offline` in `adb devices` for about 30 minutes. It was stopped and replaced. Use an x86_64 system image from the Recommended tab (API 34 or 35).
- Emulators (AVDs) live in `C:\Users\<user>\.android\avd\`, outside the repo, so each PC needs its own.
- To see the screens: pick the emulator in Android Studio's device dropdown with `app` selected, then press the green Run arrow. Login, then Home, then "Browse Lost and Found Items".
