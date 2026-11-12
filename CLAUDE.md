# Lost and Found App (class prototype)

Android Studio project, Java. A lost and found prototype. There are two parts: a console version that uses simulated data (no database for now), and the Android app called "FoundIt", whose screens are all designed in XML. The student is new to Java, so explain with simple analogies, give complete files rather than snippets, and write explanations in English.

## Working rules

- Do not add Claude as a co-author, contributor, or "generated with" line to commits or pull requests.
- The student does their own commits and pushes. Only commit or push if they ask.
- The teacher requires the UI to be built in XML. Put everything you can see on screen in XML layouts (cards, table rows, chat rows, settings pages). Java only handles taps and opening the next screen. Do not build views in Java loops.
- Do not run `git init` in a folder that already has a `.git`. A fresh `git init` earlier caused "unrelated histories" errors.
- Never put real credentials in the repo. A `config.properties` (git-ignored) with a committed `config.properties.example` is the plan for when a database is added later.
- Never paste or repeat tokens or passwords from the terminal in chat. Tell the student to revoke a token on GitHub if one was exposed.
- Look before deleting anything. Earlier, a text file was wrongly called a folder.
- Ask before fixing typos copied from the student's designs (see "Open decisions").

## Claude plugin: Matt Pocock's skills

Claude Code uses the plugin "Skills For Real Engineers" by Matt Pocock (source: `github.com/mattpocock/skills`, version 1.2.3). Its skills are named `mattpocock-skills:<skill>`. The ones that fit this project are `diagnosing-bugs`, `tdd`, `code-review` and `resolving-merge-conflicts`. Load `mattpocock-skills:writing-for-agents` before editing this file.

- The working rules above win over any skill. The skills are written for professional teams, so where a skill and a rule disagree, follow the rule: the student commits and pushes, explanations stay simple, and the UI stays in XML.
- The plugin is installed per PC for the Windows user, outside the repo, so each PC needs its own install. It was installed on the school PC (Windows user `SHS PC`) on 2026-11-12.
- Install it with `claude plugin install skills-for-real-engineers@anthropic-plugin-directory`. The Anthropic Directory lists it under that name, and `mattpocock-skills` is not found as an install name. The desktop app has no `claude` terminal command, so run the app's own `claude.exe`: in PowerShell, `Get-Process claude | Select-Object Path` shows it under `AppData\Local\Claude-Data`.

## Where things live

- Console Java classes and the JUnit test: `app/src/test/java/com/example/quarter2/minipeta3/` (package `com.example.quarter2.minipeta3`). Do not put these in `src/main`.
- Android screens: Java in `app/src/main/java/com/example/lostandfound/`, layouts in `app/src/main/res/layout/` (portrait) and `app/src/main/res/layout-land/` (landscape). Android only runs Activities from `src/main`, so these are the exception to the rule above.
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

Data is in memory only and resets every run. The Android screens do not use any of this code. Keep the console code and the test working.

## Running the console version

Android Studio's run arrow cannot run `main` from the test folder (error: `SourceSet with name 'unitTest' not found`). Run from a terminal in `app/src/test/java` instead. On Windows PowerShell, borrow Android Studio's JDK first:

```
$env:Path += ";C:\Program Files\Android\Android Studio\jbr\bin"
javac -d ..\..\..\..\lf-out com\example\quarter2\minipeta3\ConsoleLauncher.java
java -cp ..\..\..\..\lf-out com.example.quarter2.minipeta3.ConsoleLauncher
```

Recompile with `javac` after any code change. `lf-out/` is build output and is no longer tracked by git. The root `.gitignore` does not list it yet (only `app/.gitignore` has `/lf-out`, which covers the wrong folder), so it shows as untracked until `lf-out/` is added to the root `.gitignore`. Run the integration test by right-clicking `LostAndFoundFlowTest` in Android Studio, or `gradlew :app:testDebugUnitTest`.

## FoundIt app: Worksheet 5 UI prototype (current task)

Source: the student's PDFs "uhh yeah worksheet number 5.pdf" (7 pages) and "app icons.pdf", both in their Downloads folder (not in the repo). The goal is a complete UI in XML where every button works and changes screens. There is no backend, database or real login: all names, items and reports are placeholder text. This replaced the earlier Mini PETA 1 sample screens (login, home, lost/found list).

Design pages: 1 Login (landscape), 2 Home (landscape), 3 Home as a portrait phone mock (header with avatar and search, bottom bar), 4 Make a report, 5 Report status, 6 Messages, 7 Settings (all landscape).

### Unfinished: new samples before Firebase (started 2026-11-12)

Read `UI_QUESTIONS.md` in the repo root before touching the UI. It holds the student's new samples (Sign up, Messages revamp, Settings revamp, Login colors), ten questions the student has not answered yet, and the half-finished work. Get the answers first, then build. The sections below describe the app before that work.

### How the app is built

- One Activity per screen. `LoginActivity` is the launcher. The others extend `BaseActivity`: `HomeActivity`, `LostItemsActivity`, `FoundItemsActivity`, `MakeReportActivity`, `ReportStatusActivity`, `MessagesActivity`, `SettingsActivity`. All are registered in `AndroidManifest.xml`. There is no `MainActivity` any more.
- `BaseActivity` holds what every screen shares: the sidebar (landscape) or bottom bar (portrait), the three-dots menu, the search box, `openScreen(int navId)` (uses `CLEAR_TOP | SINGLE_TOP | NO_ANIMATION` so screens are not stacked twice), `logout()`, and `onItemCardClick` (called from XML with `android:onClick`).
- Every screen has two layouts with the same file name: `res/layout/` is portrait, `res/layout-land/` is landscape. Android picks the right one when the phone turns, so the app is not locked to one orientation. `activity_login.xml` exists only in `layout/` and works for both.
- Naming: `activity_X.xml` is the screen frame (header or top bar and sidebar, plus an `<include>` of the content), `content_X.xml` is the screen body, `card_lost.xml` / `card_found.xml` are item cards (wide in portrait, small squares in landscape), `tile_lost.xml` is the portrait "Recent report" tile, `include_header.xml` is the portrait header, `include_top_bar.xml` and `include_sidebar.xml` are landscape only, and `settings_pages.xml` holds the five Settings pages for both orientations.
- Buttons that open screens use the same ids in both orientations: `navHome`, `navLost`, `navFound`, `navReport`, `navStatus`, `navMessages`, `navSettings` (sidebar buttons in landscape, bottom bar items in portrait via `menu/bottom_nav_menu.xml`). `navLogout` exists only in `menu/more_menu.xml`. Other important ids: `bottomNav`, `moreButton`, `profileRow`, `searchInput`, `searchIcon`, `loginButton`, `googleLoginButton`, `mcsLoginButton`, `forgotPasswordText`, `signUpText`, `moreItems1` to `moreItems4`, `itemNameInput`, `lostItemCheck`, `foundItemCheck`, `importDetailsButton`, `nextStepButton`, `reportTable`, `selectAllCheck`, `chatRow1` to `chatRow4`, `chatTitle`, `chatBubble`, `tabAccount` to `tabOther`, `pageAccount` to `pageOther`, `logoutButton`. Java does null checks because some ids exist in only one orientation.
- Colors are in `values/colors.xml` (`fi_*` names, sampled from the design). Shared styles are `FoundIt.*` in `values/themes.xml`. Portrait uses the same palette as landscape, not the brighter blue of the page-3 mock.
- Buttons are `androidx.appcompat.widget.AppCompatButton` with the `FoundIt.Button` style (it sets `backgroundTint` to `@null`). With the MaterialComponents theme a plain `<Button>` with its own `android:background` gets painted over by the theme color.
- Icons: vector drawables for the magnifier, arrows, avatar, bottom bar icons, Google G, three dots and checkbox. `drawable-nodpi/` holds `login_building.jpg` (extracted from the worksheet PDF), `school_logo.png` and the four icon PNGs from the student (`icon_menu_bars.png`, `icon_more_three_dots.png`, `icon_search_png.png`, `icon_settings_gear.png`). The PNGs have white backgrounds and are not used yet.
- `targetSdk` is 36, so the app draws behind the status bar. The screen roots use `android:fitsSystemWindows="true"`.

### What the buttons do

- Login: every login button opens Home (no account check). "Forgot password" and "Sign up" only show a message.
- Sidebar, bottom bar and the avatar open the matching screens. The current screen is highlighted. In portrait, Report status, Messages and Log out are in the three-dots menu.
- Search (Enter or the magnifier) shows a message and opens Lost items.
- Home: "More items" opens Lost items (High priority searches) or Report status (Recent report). Item cards show "Item details are not built yet".
- Make a report: ticking Lost or Found un-ticks the other. "Import details from settings" fills sample contact details. "Next Step" shows a message and opens Report status, because steps 2 and 3 are not designed.
- Report status: the header checkbox ticks every row.
- Messages: tapping a chat highlights it and shows its message. In portrait the conversation appears under the list.
- Settings: the five tabs switch pages. "Log out" is under "Other settings" and returns to Login.

### Open decisions

- The worksheet has typos ("Dont", "Alternitive"). They are kept as in the design. Ask before fixing.
- Lost items, Found items and all portrait screens except Home were designed by Claude in the style of the samples, because the PDF does not include them.
- Next Step skips steps 2 and 3 (see above).

### Tools

- Read cannot open PDFs on these PCs (no `pdftoppm`) and Python is only the Microsoft Store stub. Render PDF pages to PNG with Windows' built-in `Windows.Data.Pdf` API from PowerShell. Pictures embedded in a PDF can be pulled out by decoding their image streams with .NET.
- Gradle works from Claude's shell on the home PC, but only with a smaller heap because of low RAM: `./gradlew :app:assembleDebug --no-daemon "-Dorg.gradle.jvmargs=-Xmx1024m -XX:MaxMetaspaceSize=384m"` with `JAVA_HOME` set to `C:\Program Files\Android\Android Studio\jbr`. A git worktree needs its own git-ignored `local.properties` (copy it from the main folder).

## Picking this up on another PC

1. In the project folder run `git fetch`, `git switch sample-screens`, `git pull`. If the pull complains about untracked `lf-out/` files, move the `lf-out/` folder aside and pull again (see Git notes).
2. Check `git config user.name` and `git config user.email` before committing (see Git notes).
3. In Android Studio let Gradle sync, then run `LostAndFoundFlowTest` (or `gradlew :app:testDebugUnitTest`) and confirm both tests pass: `fullProgramFlow` and `badMenuInputDoesNotCrash`.
4. Run the app (a real phone is lighter than the emulator, see the emulator notes) and check the screens in portrait and landscape.
5. Install the Claude plugin if this PC does not have it yet (see "Claude plugin: Matt Pocock's skills").

The emulator and `.android/avd` folder do not travel with the repo.

## Progress so far

Done:

- All eight FoundIt screens built in XML for portrait and landscape, with working navigation (commit `19fdad0` on `sample-screens`). The student ran it and said it works.
- Icons renamed to valid names and moved to `drawable-nodpi/`. The login building photo was extracted from the worksheet PDF.
- Earlier work: `AndroidManifest.xml` repaired, the purple app-name banner removed (theme parent `Theme.MaterialComponents.DayNight.NoActionBar` in both `values/themes.xml` and `values-night/themes.xml`), stale `androidTest/.../myapplication/ExampleInstrumentedTest.java` deleted (the `lostandfound` one stays), `lf-out/` class files removed from git tracking.
- Console version: `MainMenu` and `ViewItemsFeature` catch `NumberFormatException` for non-numeric menu input, print "Please type a number." and show the menu again, with a second test `badMenuInputDoesNotCrash`. Checked by hand with `javac`/`java`; the Gradle run of the new test is not confirmed yet.
- `./gradlew :app:assembleDebug` passes on the home PC.

Not done yet:

- Run `gradlew :app:testDebugUnitTest` (or the test in Android Studio) to confirm both console tests pass. This was not re-run after the FoundIt rebuild.
- Add `lf-out/` to the root `.gitignore` and save it.
- `config.properties.example` still has what looks like a real Supabase host and username. Replace them with placeholders.
- Steps 2 and 3 of "Making a report", item detail pages and a real conversation screen are not built. Do not add them or any data wiring unless the student asks.
- Optional, only if asked: using the school logo, moving text to `strings.xml`.

## XML explanation (given in chat, not saved to a file)

The student asked for a detailed explanation of how XML works and what it did for the project, and it was given in chat. If they want it again or saved as a file (for example `XML_EXPLANATION.md`), cover: tags and nesting, ViewGroups (like `LinearLayout`) versus Views (like `Button`), `layout_width`/`layout_height`, `android:id` and `findViewById`, how the manifest registers the Activities, how `layout` and `layout-land` give two versions of one screen, and how XML describes what is on screen while Java decides what happens on a tap. Use simple analogies.

## Git notes

- Remote: `https://github.com/ANDREIBNISA/LostAndFound1.git`.
- Work is on the branch `sample-screens`. On another PC: clone the repo (if it is not there yet), then `git fetch` and `git switch sample-screens`.
- `config.properties` and `local.properties` are git-ignored and will not come along; Android Studio creates `local.properties` on each machine.
- Pushing over HTTPS needs a valid GitHub login. If git says "Invalid username or token", run `gh auth setup-git` once so git uses the GitHub CLI login.
- Identity: commits are authored as `Andrei`. On the home PC the repo config says `Andrei <andreisalew2010@gmail.com>`. Older notes said `peejaysale001@gmail.com`; confirm which the student wants. The global `~/.gitconfig` on the second PC says `clyde` with a school email, so check `git config user.name` and `git config user.email` before committing on a new machine. GitHub only shows the student's profile on a commit if the commit email is added and verified under GitHub Settings, Emails.
- Never force-push without the student's yes. `git push --force-with-lease` was used once, with their approval, to fix the author of a commit.
- If `git rebase` or `git pull` stops with "untracked working tree files would be overwritten" for `lf-out/`, move the `lf-out/` folder aside (it is regenerated by `javac`) and retry.
- A leftover branch `sample-screens-work` may exist in a Claude worktree under `.claude/worktrees/`. It can be deleted.

## Emulator notes

- The home PC has about 7 GB of RAM. The Pixel 6 emulator has crashed Android Studio on it, and games or Discord running at the same time make it worse. The AVD's `hw.ramSize` was lowered from 2048 to 1536 (backup: `config.ini.bak`). A real phone with USB debugging is the lighter option. Close other heavy programs before running the emulator.
- If the emulator says "already running" or sits at `offline` in `adb devices`, a leftover `qemu-system-x86_64` / `emulator` process is still alive. End it in Task Manager, run `adb kill-server`, or use Device Manager, then Cold Boot Now.
- The second PC has about 15 GB of RAM. Its first emulator ran as `qemu-system-i386` (a 32-bit image) and sat at `offline` for about 30 minutes. It was replaced. Use an x86_64 system image from the Recommended tab (API 34 or 35).
- Emulators (AVDs) live in `C:\Users\<user>\.android\avd\`, outside the repo, so each PC needs its own.
- To see the screens: pick the emulator in Android Studio's device dropdown with `app` selected, then press the green Run arrow. The app opens on Login. Rotate the emulator to see the other layout.
