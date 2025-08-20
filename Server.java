package multi;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.Scanner;
public class Server {
    public static void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        Server serverInstance = new Server();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> serverInstance.getConsumer().accept(clientSocket)).start();
        }
    }
    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String clientAddress = clientSocket.getInetAddress().toString();
                System.out.println("Connected to client: " + clientAddress);
                toSocket.println("Hello from server " + clientAddress);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    // main method should be inside the class
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter port number:");
        int port = sc.nextInt();
        sc.close(); // Close scanner after use

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(70000); // Optional: timeout for accept()
            System.out.println("Server is listening on port " + port);
            Server server = new Server();
            // Infinite loop to accept and handle client connections
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Blocking call
                    final Socket socketForThread = clientSocket;
                    // Handle each client in a new thread
                    Thread thread = new Thread(() -> server.getConsumer().accept(socketForThread));
                    thread.start();
                } catch (IOException acceptEx) {
                    System.err.println("Accept failed: " + acceptEx.getMessage());
                    acceptEx.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
