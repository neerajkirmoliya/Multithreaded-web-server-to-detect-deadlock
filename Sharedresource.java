package multi;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class Sharedresource extends JFrame {
    private final JTextPane outputPane;
    private final SimpleAttributeSet centerStyle;
    private final Font font = new Font("Segoe UI", Font.PLAIN, 16);

    public Sharedresource() {
        setTitle("🔍 Deadlock Detector");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 250, 255));

        // Output pane setup
        outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setFont(font);
        outputPane.setBackground(new Color(250, 250, 250));
        centerStyle = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(centerStyle, 16);
        StyleConstants.setFontFamily(centerStyle, "Serif");
        StyledDocument doc = outputPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), centerStyle, false);
        JScrollPane scrollPane = new JScrollPane(outputPane);
        scrollPane.setBorder(new TitledBorder("Deadlock Status"));

        // Title
        JLabel title = new JLabel("🧵 Deadlock Simulator & Detector", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(10, 0, 0, 0));
        title.setForeground(new Color(0, 51, 102));

        // Buttons
        JButton simulateBtn = new JButton("Start Deadlock Simulation");
        JButton checkBtn = new JButton("Check Deadlock");
        simulateBtn.setFont(font);
        checkBtn.setFont(font);
        simulateBtn.setBackground(new Color(144, 238, 144));
        checkBtn.setBackground(new Color(255, 204, 102));
        simulateBtn.setFocusPainted(false);
        checkBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.add(simulateBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.setOpaque(false);

        // Add components
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button logic
        simulateBtn.addActionListener(this::startDeadlockSimulation);
        checkBtn.addActionListener(this::checkDeadlock);
    }

    private void startDeadlockSimulation(ActionEvent e) {
        appendOutput("⚙️ Starting deadlock simulation...\n");

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                sleep(100);
                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                sleep(100);
                synchronized (lock1) {
                    System.out.println("Thread 2 acquired lock1");
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        appendOutput("🧠 Two threads started and might be in deadlock...\n");
    }

    private void checkDeadlock(ActionEvent e) {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        long[] ids = mxBean.findDeadlockedThreads();

        if (ids != null && ids.length > 0) {
            ThreadInfo[] infos = mxBean.getThreadInfo(ids);
            appendOutput("❌ Deadlock detected!\n");
            for (ThreadInfo info : infos) {
                appendOutput("🔒 " + info.getThreadName() + " is blocked on " + info.getLockName() + "\n");
            }
        } else {
            appendOutput("✅ No deadlock detected.\n");
        }
    }

    private void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputPane.getStyledDocument();
                int offset = doc.getLength();
                doc.insertString(offset, text, null);
                doc.setParagraphAttributes(offset, text.length(), centerStyle, false);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sharedresource().setVisible(true));
    }
}
