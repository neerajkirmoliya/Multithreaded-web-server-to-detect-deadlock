package multi;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Frontend extends JFrame {
    private final JTextField portField;
    private final JTextField requestCountField;
    private final JTextPane outputPane;
    private final SimpleAttributeSet centerStyle;

    public Frontend() {
        setTitle("Multithreaded Web Server - Frontend");
        setSize(750, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        centerStyle = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(centerStyle, 18);
        StyleConstants.setFontFamily(centerStyle, "Serif");
        StyleConstants.setBold(centerStyle, true);

        Font controlFont = new Font("Segoe UI", Font.BOLD, 18);

        // Labels and fields
        JLabel portLabel = new JLabel("Port:");
        portLabel.setFont(controlFont);
        portField = new JTextField("8080", 8);
        portField.setFont(controlFont);

        JLabel requestLabel = new JLabel("Requests:");
        requestLabel.setFont(controlFont);
        requestCountField = new JTextField("5", 8);
        requestCountField.setFont(controlFont);

        // Buttons
        JButton startServerButton = new JButton("Start Server");
        JButton sendRequestsButton = new JButton("Send Requests");

        startServerButton.setFont(controlFont);
        sendRequestsButton.setFont(controlFont);
        startServerButton.setBackground(new Color(144, 238, 144));
        sendRequestsButton.setBackground(new Color(173, 216, 230));
        startServerButton.setFocusPainted(false);
        sendRequestsButton.setFocusPainted(false);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Server Controls"));
        inputPanel.add(portLabel);
        inputPanel.add(portField);
        inputPanel.add(requestLabel);
        inputPanel.add(requestCountField);
        inputPanel.add(startServerButton);
        inputPanel.add(sendRequestsButton);

        // Output area
        outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setBackground(new Color(245, 245, 245));
        outputPane.setFont(new Font("Serif", Font.BOLD, 18));
        StyledDocument doc = outputPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), centerStyle, false);
        JScrollPane scrollPane = new JScrollPane(outputPane);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        // Navigation panel
        JButton prevButton = new JButton(" Previous");
        JButton nextButton = new JButton("Next ");
        prevButton.setFont(controlFont);
        nextButton.setFont(controlFont);
        prevButton.setBackground(new Color(224, 224, 224));
        nextButton.setBackground(new Color(255, 204, 102));
        prevButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        navPanel.add(prevButton);
        navPanel.add(nextButton);

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);
        prevButton.addActionListener(e -> {
            new Front().setVisible(true); // open next window
             this.dispose(); // close current window (optional)
        });
        // Actions
        nextButton.addActionListener(e -> {
            new Sharedresource().setVisible(true); // open next window
          //  this.dispose(); // close current window (optional)
        });
        startServerButton.addActionListener(this::startServer);
        sendRequestsButton.addActionListener(this::sendRequests);
    }

    private void startServer(ActionEvent e) {
        int port;
        try {
            port = Integer.parseInt(portField.getText().trim());
            if (port <= 0 || port > 65535) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid port number (1–65535).");
            return;
        }

        appendOutput("Starting server on port " + port + "...\n");

        new Thread(() -> {
            try {
                Server.start(port);
                appendOutput("Server started successfully on port " + port + ".\n");
            } catch (IOException ex) {
                appendOutput("Failed to start server: " + ex.getMessage() + "\n");
            }
        }).start();
    }

    private void sendRequests(ActionEvent e) {
        int port, count;
        try {
            port = Integer.parseInt(portField.getText().trim());
            count = Integer.parseInt(requestCountField.getText().trim());
            if (port <= 0 || port > 65535 || count <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric inputs.");
            return;
        }

        appendOutput("Sending " + count + " requests to port " + port + "...\n");

        new Thread(() -> {
            for (int i = 0; i < count; i++) {
                final int requestNum = i + 1;
                try (Socket socket = new Socket("localhost", port);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String response = in.readLine();
                    appendOutput("Request " + requestNum + ": Response = " + response + "\n");

                } catch (IOException ex) {
                    appendOutput("Request " + requestNum + ": Failed - " + ex.getMessage() + "\n");
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void appendOutput(String message) {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = outputPane.getStyledDocument();
            try {
                int offset = doc.getLength();
                doc.insertString(offset, message, null);
                doc.setParagraphAttributes(offset, message.length(), centerStyle, false);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frontend().setVisible(true));
    }
}
