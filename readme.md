# 🎓 Conference Attendee Management App
**CHRIST University — AI Conference**

A native Android application built with Kotlin and Jetpack Compose that allows
an admin to register, view, edit, and delete conference attendees, with automatic
SMS confirmation sent upon successful registration.

---

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Architecture](#architecture)
- [Screens](#screens)
- [Permissions](#permissions)

---

## ✨ Features
- ✅ Register new attendees (Name, Age, Phone Number)
- ✅ Automatically sends SMS confirmation on registration
- ✅ View full list of all registered attendees
- ✅ Edit any attendee's details
- ✅ Delete an attendee with confirmation dialog
- ✅ Input validation with inline error messages
- ✅ Persistent local storage using Room (SQLite)

---

## 🛠 Tech Stack

| Layer        | Technology                          |
|--------------|--------------------------------------|
| Language     | Kotlin 2.0                          |
| UI           | Jetpack Compose + Material 3        |
| Architecture | MVVM (Model-View-ViewModel)         |
| Database     | Room (SQLite)                        |
| Async        | Kotlin Coroutines + Flow            |
| Navigation   | Navigation Compose                  |
| SMS          | Android SmsManager API              |
| Min SDK      | API 26 (Android 8.0)                |
| Target SDK   | API 34 (Android 14)                 |

---

## 📁 Project Structure

```
com.example.conferenceapp/
│
├── data/
│   ├── Attendee.kt              ← Room Entity (@Entity)
│   ├── AttendeeDao.kt           ← DAO interface (CRUD queries)
│   └── AttendeeDatabase.kt     ← Singleton RoomDatabase
│
├── repository/
│   └── AttendeeRepository.kt   ← Abstracts DAO from ViewModel
│
├── viewmodel/
│   └── AttendeeViewModel.kt    ← Business logic + SMS trigger
│
├── ui/
│   ├── AddAttendeeScreen.kt    ← Registration form screen
│   ├── AttendeeListScreen.kt   ← List view with Edit/Delete
│   └── EditAttendeeScreen.kt   ← Pre-filled edit form
│
└── MainActivity.kt              ← NavHost + SMS permission
```

---

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- Kotlin 2.0+
- Android device or emulator (API 26+)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/ConferenceApp.git
   cd ConferenceApp
   ```

2. **Open in Android Studio**
   - File → Open → select the project folder

3. **Sync Gradle**
   - Click **Sync Now** when prompted, or go to
     File → Sync Project with Gradle Files

4. **Run the app**
   - Select a device/emulator
   - Click ▶ Run or press `Shift + F10`

5. **Grant SMS Permission**
   - On first launch, allow the **Send SMS** permission
     when the system dialog appears

---

## 🏗 Architecture

The app follows **MVVM Clean Architecture**:

```
UI (Compose Screens)
        ↓ observes StateFlow
ViewModel (AttendeeViewModel)
        ↓ calls
Repository (AttendeeRepository)
        ↓ calls
DAO (AttendeeDao)
        ↓ queries
Room Database (SQLite)
```

- **UI** never talks to the database directly
- **ViewModel** survives configuration changes (screen rotation)
- **Flow** ensures the list screen auto-updates on any DB change
- **Repository** acts as the single source of truth

---

## 📱 Screens

| Screen               | Description                                      |
|----------------------|--------------------------------------------------|
| Add Attendee         | Admin form to register a new attendee + SMS send |
| Attendee List        | Scrollable list with Edit and Delete per card    |
| Edit Attendee        | Pre-filled form to update existing details       |

---

## 🔐 Permissions

| Permission      | Purpose                                  |
|-----------------|------------------------------------------|
| `SEND_SMS`      | Send confirmation SMS after registration |

> ⚠️ SMS sending requires a **real physical device** with an active SIM.
> It will not work on an emulator without additional configuration.

---

## 📥 Download APK

You can download the compiled APK for testing using the link below:

[![Download APK](https://img.shields.io/badge/View_&_Download-APK-blue.svg?logo=android&style=for-the-badge)](https://github.com/krypton-arch/ConferenceAI/blob/main/apk/app-release-signed.apk)

### Installation Steps:
1. Click the badge above to open the APK file repository page.
2. On the GitHub page, click the **Download raw file** button (usually a download icon ⬇️ on the right side).
3. Transfer the downloaded `.apk` to your Android device (if downloaded on PC) and install it.
