# JetpackComposeProjects

## 🔧 Project Overview
This MonoRepo contains various Android projects developed with Jetpack Compose. Currently, it includes the **ToDo Jetpack Compose** project, with planned extensions for **NewsReader Jetpack Compose** and **Weather Jetpack Compose**.

---
## 📚 Included Projects
### 1. ToDo Jetpack Compose 📅
A minimalist to-do list management tool with a terminal-like user interface.
#### **Main Features:**
- ✅ Create, edit, delete tasks
- ✔️ Mark tasks as completed
- 🔒 Persistent storage with **Room Database** (SQLCipher encrypted)
- 🔑 Secure access using **EncryptedSharedPreferences**
- ⚙️ Modular repository approach
- 🛠️ Extendable algorithm for database completion
- 📴 **Fully offline, no internet dependencies**

### 2. NewsReader Jetpack Compose 📰 *(not started yet)*
A local news reader that will provide stored news articles without requiring an internet connection.

### 3. Weather Jetpack Compose ☁️ *(not started yet)*
A weather app that will use locally stored data for forecasts without relying on external APIs.



---
## ⚙️ Setup & Installation
### **1. Requirements**
- Android Studio **Giraffe or later**
- Java **17 or later**
- Kotlin **1.8 or later**
- Gradle **8.0 or later**

### **2. Clone and Run the Project**
```bash
# Clone the repository
git clone https://github.com/AlGSyntax/JetpackComposeProjects.git
cd jetpackcomposeprojects

# Open in Android Studio
```
### **3. Build & Run**
1. **In Android Studio**: Select a connected device or emulator.
2. **Build the project:** Click *Run* (▶).
3. **Test and use the app!** 

---
## 📊 Technology Stack
- **UI:** Jetpack Compose, Material3
- **Database:** Room (SQLCipher encryption)
- **State Management:** ViewModel, Flow
- **Storage:** EncryptedSharedPreferences
- **Dependency Injection:** Hilt (planned)
- **No networking or cloud dependencies** 🚫🌐

---
## 🏆 Future Enhancements
- **NewsReader Jetpack Compose** with **local news storage**
- **Weather Jetpack Compose** with **local weather data**
- **Extendable algorithm for database completion**

---
## 👨‍💻 Contributors

If you want to contribute, feel free to create an issue or pull request! 🚀

---
## 📜 License
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

