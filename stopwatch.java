package collegeassignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class stopwatch extends JFrame {

    private JLabel timeLabel;
    private JButton startBtn, stopBtn, resetBtn, lapBtn;
    private JTextArea lapArea;

    private Timer timer;
    private int elapsedTime = 0; // milliseconds
    private boolean running = false;

    private ArrayList<String> laps = new ArrayList<>();

    public stopwatch() {
        setTitle("Stopwatch");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Time Label
        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        timeLabel.setForeground(Color.GREEN);
        panel.add(timeLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.DARK_GRAY);

        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");
        resetBtn = new JButton("Reset");
        lapBtn = new JButton("Lap");

        btnPanel.add(startBtn);
        btnPanel.add(stopBtn);
        btnPanel.add(resetBtn);
        btnPanel.add(lapBtn);

        panel.add(btnPanel, BorderLayout.CENTER);

        // Lap Area
        lapArea = new JTextArea();
        lapArea.setEditable(false);
        lapArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(lapArea);

        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel);

        // Timer (updates every 10 ms)
        timer = new Timer(10, e -> {
            elapsedTime += 10;
            updateTimeLabel();
        });

        // Button Actions
        startBtn.addActionListener(e -> start());
        stopBtn.addActionListener(e -> stop());
        resetBtn.addActionListener(e -> reset());
        lapBtn.addActionListener(e -> lap());
    }

    private void start() {
        if (!running) {
            timer.start();
            running = true;
        }
    }

    private void stop() {
        timer.stop();
        running = false;
    }

    private void reset() {
        timer.stop();
        running = false;
        elapsedTime = 0;
        laps.clear();
        lapArea.setText("");
        updateTimeLabel();
    }

    private void lap() {
        if (running) {
            String lapTime = formatTime(elapsedTime);
            laps.add(lapTime);
            lapArea.append("Lap " + laps.size() + ": " + lapTime + "\n");
        }
    }

    private void updateTimeLabel() {
        timeLabel.setText(formatTime(elapsedTime));
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        int ms = (millis % 1000) / 10;

        return String.format("%02d:%02d:%02d", minutes, seconds, ms);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new stopwatch().setVisible(true);
        });
    }
}