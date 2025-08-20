package multi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    // Modified to accept port number as a parameter
    public Runnable getRunnable(int port) {
        return () -> {
            try {
                InetAddress address = InetAddress.getByName("localhost");

                // Establish connection to the server
                try (
                    Socket socket = new Socket(address, port);
                    PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    // Send message to server
                    toSocket.println("Hello from Client " + socket.getLocalSocketAddress());

                    // Receive response from server
                    String response = fromSocket.readLine();
                    if (response != null) {
                        System.out.println("Response from Server: " + response);
                    } else {
                        System.err.println("Server closed connection unexpectedly.");
                    }

                } catch (IOException e) {
                    System.err.println("Error during communication with server: " + e.getMessage());
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.err.println("Failed to connect to server: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    // Main method to launch multiple client threads
    public static void main(String[] args) {
        Client client = new Client();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter port number:");
        int port = sc.nextInt();

        System.out.println("Enter number of requests:");
        int n = sc.nextInt();
        sc.close();

        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(client.getRunnable(port));
            thread.start();

            // Optional: small delay to avoid overwhelming the server
            try {
                Thread.sleep(10); // 10 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
