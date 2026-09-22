# Memory Match Game for Android

An Android memory-card game built with Java and Android Views. Players choose a visual theme and difficulty, match card pairs, and track their time and moves. Scores are stored locally on the device and displayed in a leaderboard.

> **Kısa Türkçe özet:** Java ve Android Views kullanılarak geliştirilmiş hafıza eşleştirme oyunu. Tema ve zorluk seçimi, süre/hamle takibi, ses efektleri ve cihazda saklanan skor tablosu içerir.

## Features

- Four card themes: flags, animals, flowers, and fruits
- Difficulty and board-configuration selection
- Card-flip and pair-matching game logic
- Elapsed-time and move-count tracking
- Correct, incorrect, and win sound effects
- Result screen and locally persisted score history with `SharedPreferences`
- Leaderboard UI using RecyclerView adapters

## Tech Stack

- Java
- Android SDK 33 (min SDK 28)
- AndroidX AppCompat, ConstraintLayout, CardView, GridLayout, Material Components
- Gradle 8.13 and Android Gradle Plugin 8.13.2

## Run Locally

1. Install Android Studio and a working JDK 17 or newer.
2. Open this folder with Android Studio.
3. Let Gradle sync the dependencies.
4. Run the `app` configuration on an Android emulator or a device running Android 9 (API 28) or newer.

## Repository Notes

- Generated folders such as `build/`, `.gradle/`, and `.idea/` are excluded.
- A debug APK and a short demo video are kept outside the source repository; they can be attached to a GitHub Release after the source upload.
- The project includes a previously generated debug APK in its local source folder, but a fresh build still needs to be verified after the local JDK/Android Studio runtime is repaired.
