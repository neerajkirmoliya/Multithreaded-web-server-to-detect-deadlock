# Multithread Web Server with Deadlock Detection

## 📌 Project Description
This project implements a **Multithreaded Web Server in Java** that can handle multiple client requests simultaneously.  
Additionally, it integrates a **Deadlock Detection Mechanism** to monitor threads and identify potential deadlocks in the system.  

This project demonstrates **Operating Systems concepts** like multithreading, synchronization, resource sharing, and deadlock handling.

---

## 🚀 Features
- Multi-threaded request handling  
- Shared resources with synchronization  
- Deadlock simulation and detection  
- Logs and console outputs for debugging  
- Educational project to understand **Concurrency & Deadlock Detection**  

---

## 🛠️ Tech Stack
- **Language:** Java  
- **Concepts:** Threads, Synchronization, Deadlock Detection, Socket Programming (if used)  
- **Tools:** JDK, IDE (VS Code / IntelliJ / Eclipse)  

---

## 📂 Project Structure
The project contains the following Java files:

- `SharedResource.java` → Implements shared resources for threads  
- `Server.java` → Handles incoming client connections  
- `Client.java` → Simulates client requests to the server  
- `DeadlockDetector.java` → Monitors running threads and detects deadlocks  
- `Main.java` → Entry point to run the application  

---

## ⚙️ How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/multithread-web-server-to-detect-deadlock.git
   
2. Navigate to project folder:
cd multithread-web-server-to-detect-deadlock

3. Compile all Java files:
javac *.java

4. Run the server:
java Main
