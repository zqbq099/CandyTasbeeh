name: Build Android APK

on:
  workflow_dispatch:

jobs:
  build:
    name: Build APK
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3

      - name: Make gradlew executable
        run: chmod +x gradlew

      - name: Build Debug APK
        run: ./gradlew assembleDebug

      - name: Upload APK to Artifacts
        uses: actions/upload-artifact@v4
        with:
          name: CandyTasbeeh-Debug-APK
          path: app/build/outputs/apk/debug/app-debug.apk
          retention-days: 7
