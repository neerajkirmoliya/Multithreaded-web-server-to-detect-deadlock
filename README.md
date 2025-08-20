# Multithread Web Server with Deadlock Detection
📌 Overview

This project is a multithreaded web server simulation that demonstrates:
Handling multiple client requests concurrently.
Managing shared resources with synchronization.
Simulating and detecting deadlocks in multithreaded environments.
Providing a simple frontend entry (Front & Frontend classes) to interact with the server.
It is designed as a third-year project / learning tool for understanding concurrency, client-server architecture, and deadlock management in Java.


⚙️ Features

✅ Multithreaded Server to handle multiple clients simultaneously.

✅ Client simulation to send requests.

✅ SharedResource class for synchronized resource access.

✅ Deadlock detection mechanism for safe execution.

✅ Front & Frontend classes to simulate request flow and provide an entry point.


📂 Project Structure

multithread-webserver-deadlock/
│── README.md              # Documentation  
│── Server.java            # Multithreaded server  
│── Client.java            # Client simulator  
│── SharedResource.java    # Shared resources & synchronization  
│── Front.java             # Entry / dispatcher class  
│── Frontend.java          # Frontend simulation / UI layer  

🚀 Getting Started
🔧 Prerequisites

Java JDK 8+

Any IDE (VS Code / IntelliJ / Eclipse) or terminal

📥 Installation & Run

1. Clone the repository:
git clone https://github.com/neerajkirmoliya/Multithreaded-web-server-to-detect-deadlock.git
cd Multithreaded-web-server-to-detect-deadlock


2. Compile the project:
javac *.java

3. Run the server:
java Server

4. Run one or more clients in separate terminals:
java Client


🛠️ How It Works

-> Server listens for incoming requests using multithreading.

-> Clients send simulated requests concurrently.

-> SharedResource manages resources with locking to show contention.

-> Deadlock situations can be simulated (when two or more threads hold resources in a circular wait).

-> A Deadlock detection mechanism monitors thread states and resolves issues.

-> Front.java acts as a request dispatcher, while Frontend.java simulates the user-side interaction.


📊 Example Flow

👉 Start Server.java.

👉Run multiple Client.java instances.

👉Clients request access to resources managed by SharedResource.java.

👉If circular wait occurs → server’s deadlock detector identifies it.

👉Front & Frontend manage how client requests are routed to the server.


📚 Learning Outcomes

📜Understand multithreading & concurrency in Java.

📜Learn about deadlocks and deadlock detection techniques.

📜Build a basic client-server architecture.

📜Gain insights into synchronization and shared resource management.

👨‍💻 Contributors

NEERAJ SINGH (Project Developer)
