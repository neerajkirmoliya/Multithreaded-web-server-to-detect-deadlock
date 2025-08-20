package multi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Front {

    public static void main(String[] args) {
        // Optional: Use modern UI look
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("Welcome");
        frame.setSize(550, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Team info text (on top)
        JTextArea teamArea = new JTextArea(
            "Team Members:\n" +
            "1. Akshat Pant (AKS) - TEAM LEADER\n" +
            "2. Neeraj Singh\n" +
            "3. Isha Bharti\n" +
            "4. Vinay Singh Rawat\n\n" +
            "\"IT IS WHAT IT IS!\""
        );
        teamArea.setFont(new Font("Serif", Font.BOLD, 17));
        teamArea.setEditable(false);
        teamArea.setBackground(new Color(240, 240, 240));
        teamArea.setMargin(new Insets(10, 10, 10, 10));
        teamArea.setLineWrap(true);
        teamArea.setWrapStyleWord(true);
        teamArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(teamArea);
        scrollPane.setPreferredSize(new Dimension(480, 160));
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Welcome title
        JLabel titleLabel = new JLabel("Welcome to the Project!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(60, 60, 60));
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));

        // NEXT button
        JButton nextButton = new JButton("Move to Next Page");
        nextButton.setPreferredSize(new Dimension(170, 40));
        nextButton.setBackground(new Color(255, 153, 51));
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "MultithreadedSystem");
                pb.directory(new java.io.File("C:\\Users\\ISHA BHARTI\\Documents\\ac++\\Java\\OS"));
                pb.inheritIO();
                pb.start();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to launch the program.");
            }
        });
        JButton aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(100, 40));
        aboutButton.setBackground(new Color(100, 200, 255));
        aboutButton.setFocusPainted(false);
        aboutButton.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "This is a Multithreaded System project built using Java.\n"
                + "This project can also detect deadlock and take measures to prevent it"));


        JButton backButton = new JButton("Back to Previous Page");
        backButton.setPreferredSize(new Dimension(190, 40));
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "There is no previous page");

        });
        nextButton.addActionListener(e -> {
            new Frontend().setVisible(true); 
           // this.dispose(); 
        });
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(aboutButton);

        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

	public void setVisible(boolean b) {
	      
		
	}
}
