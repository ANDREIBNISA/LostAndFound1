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

Recompile with `javac` after any code change. Keep `lf-out/` in `.gitignore` (it is not listed there yet, so check). Run the integration test by right-clicking `LostAndFoundFlowTest` in Android Studio, or `gradlew :app:testDebugUnitTest`.

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

## Progress so far

Done:

- Three XML layouts added and committed.
- `AndroidManifest.xml` repaired. It was cut off mid-tag (`<application` never closed, no theme, no activity), so the app could not build. It now has the theme and `MainActivity` as the launcher.
- `MainActivity.java` added.
- Two typos fixed ("TestRunInstruction" had replaced the word "a" in `ClaimItemFeature.java` and a comment in `LostAndFoundFlowTest.java`).
- `./gradlew :app:assembleDebug` and `:app:testDebugUnitTest` both pass.

Not done yet:

- Run the app on an emulator or phone and inspect each screen (the home PC ran out of RAM, which is why work moves to the school lab).
- Consider an ImageView (for example a logo on the login screen), since the PDF lists it as an example component.
- Consider IDs on the Claim buttons in the list.
- Write the detailed XML explanation the student asked for.
- Add `lf-out/` to `.gitignore`.

## XML explanation the student still needs

The student needs a detailed explanation of how XML works and what it did for the project. Cover: tags and nesting, ViewGroups (like `LinearLayout`) versus Views (like `Button`), `layout_width`/`layout_height`, `android:id` and `findViewById`, how the manifest registers `MainActivity`, and how XML describes what is on screen while Java decides what happens on a tap. Use simple analogies.

## Git notes

- Remote: `https://github.com/ANDREIBNISA/LostAndFound1.git`.
- Work is on the branch `sample-screens`. At the school lab: clone the repo, then `git fetch` and `git switch sample-screens`.
- `config.properties` and `local.properties` are git-ignored and will not come along; Android Studio creates `local.properties` on each machine.

## Emulator notes

- The home PC has about 7 GB of RAM, and the Pixel 6 emulator ran out of memory and crashed Android Studio. The AVD's `hw.ramSize` was lowered from 2048 to 1536 (backup: `config.ini.bak`). A real phone with USB debugging is a good fallback.
- If the emulator says "already running", a leftover `qemu-system-x86_64` / `emulator` process is still alive. End it in Task Manager.
