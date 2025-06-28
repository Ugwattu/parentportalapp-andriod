# 📱 Parent Portal App Android

A comprehensive Android application developed as a **Final year university project** to empower parents with real-time access to their child's academic life, school activities, and device usage. The app features secure login, local database handling, and a modern Material Design UI.

---

## 🎓 Project Overview

* 🏫 **Final Year BSIT Project**
* 🧑‍💻 Developed by: \[Umair Ghafoor]
* 📅 Year: 2025

---

## ✨ Key Features

1. ✅ **Attendance Monitoring**
   View detailed attendance including total days **present**, **absent**, on **leave**, and **holidays**.

2. 📝 **Report Cards**
   Access term-wise academic performance reports to track your child's academic progress.

3. 💳 **Fee Details**
   Check **fee vouchers** and **payment history** directly in the app.

4. 📤 **Leave Application Submission**
   Submit student leave requests digitally through the app.

5. 📘 **Daily Diary Access**
   Stay updated with daily entries for **classwork**, **homework**, **assignments**, and more.

6. 🕘 **Timetable & Syllabus**
   View the full class timetable and syllabus for each subject.

7. 🚌 **Transport Facility**
   Access transport details, including **bus driver information** and route tracking (if implemented).

8. 🧑‍🎓 **Student Profile**
   View student profile information such as class, section, and personal details.

9. 💬 **Submit Feedback**
   Provide feedback and reviews to teachers or the school administration.

10. 📥 **Receive Feedback**
    Receive feedback or remarks from the school directly in the app.

11. 📢 **Announcements & Events**
    Get real-time notifications for **school news**, **events**, **holidays**, and **emergency notices**.

---

## 🧰 Tech Stack

| Component         | Technology                                               |
| ----------------- | -------------------------------------------------------- |
| **Frontend**      | [Material Design 3](https://m3.material.io/) (by Google) |
| **Language**      | Java (Android)                                           |
| **Backend Logic** | Java (on-device services)                                |
| **Database**      | SQLite (local persistent storage)                        |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Ugwattu/parentportalapp-andriod.git
cd parentportalapp-andriod
```

### 2. Open in Android Studio

* Sync Gradle
* Connect an emulator or Android device (API 21+)
* Run the app

### 3. Login Using These Sample Credentials

| Role   | Username | Password     |
| ------ | -------- | ------------ |
| Admin  | `admin`  | `admin` |
| Parent | `ali`    | `test` |

---

## 📂 Project Structure

```
parentportalapp-andriod/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/ugwattu/parentportal/
│           └── res/
├── AndroidManifest.xml
└── build.gradle
```

---

## 🔐 Authentication Notes

* Default Password for parent: `default123`
* Password change functionality can be added as a future improvement

---

## 📜 License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)**
See [`LICENSE`](./LICENSE) for full terms.
