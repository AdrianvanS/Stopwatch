import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Stopwatch extends JFrame implements ActionListener {

    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
    }

    private JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JLabel labelHNum;
    private JLabel labelMNum;
    private JLabel labelSNum;

    private int hours;
    private int minutes;
    private int seconds;
    private int secondsElapsed;
    private long nanoseconds;
    private long start;
    private long elapsed;

    private Timer timer;


    public Stopwatch(){
        //Frame
        frame = new JFrame("Stopwatch");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Path path = FileSystems.getDefault().getPath(".");
        String imagePath = path.normalize().toAbsolutePath() + "\\Stopwatch.png";
        ImageIcon icon = new ImageIcon(imagePath);
        frame.setIconImage(icon.getImage());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //Panels
        panel1 = new JPanel();
        panel1.setBackground(new Color(45, 22, 74));
        panel1.setPreferredSize(new Dimension(100, 30));
        panel3 = new JPanel();
        panel3.setBackground(new Color(45, 22, 74));
        panel3.setPreferredSize(new Dimension(100, 30));
        panel4 = new JPanel();
        panel4.setBackground(new Color(45, 22, 74));
        panel4.setPreferredSize(new Dimension(30, 100));
        panel5 = new JPanel();
        panel5.setBackground(new Color(45, 22, 74));
        panel5.setPreferredSize(new Dimension(30, 100));

        //Time panel
        panel6 = new JPanel();
        panel6.setBackground(new Color(45, 22, 74));
        panel6.setPreferredSize(new Dimension(50, 100));
        panel6.setLayout(new GridLayout(1, 6, 5, 0));

        //Time labels
        hours = 0;
        minutes = 0;
        seconds = 0;
        nanoseconds = 0;
        elapsed = 0;
        timer = new Timer(1000, e -> updateClock());

        labelHNum = new JLabel(hours + " h");
        labelHNum.setFont(new Font("Calibri", Font.PLAIN, 40));
        labelHNum.setPreferredSize(new Dimension(70, 50));
        labelHNum.setForeground(Color.WHITE);
        labelHNum.setBackground(new Color(45, 22, 74));
        labelHNum.setHorizontalAlignment(SwingConstants.CENTER);
        labelHNum.setVerticalAlignment(SwingConstants.TOP);
        labelHNum.setVisible(true);

        labelMNum = new JLabel(minutes + " m");
        labelMNum.setFont(new Font("Calibri", Font.PLAIN, 40));
        labelMNum.setPreferredSize(new Dimension(90, 50));
        labelMNum.setForeground(Color.WHITE);
        labelMNum.setBackground(new Color(45, 22, 74));
        labelMNum.setHorizontalAlignment(SwingConstants.CENTER);
        labelMNum.setVerticalAlignment(SwingConstants.TOP);
        labelMNum.setVisible(true);

        labelSNum = new JLabel(seconds + " s");
        labelSNum.setFont(new Font("Calibri", Font.PLAIN, 40));
        labelSNum.setPreferredSize(new Dimension(70, 50));
        labelSNum.setForeground(Color.WHITE);
        labelSNum.setBackground(new Color(45, 22, 74));
        labelSNum.setHorizontalAlignment(SwingConstants.CENTER);
        labelSNum.setVerticalAlignment(SwingConstants.TOP);
        labelSNum.setVisible(true);

        panel6.add(labelHNum);
        panel6.add(labelMNum);
        panel6.add(labelSNum);

        //Button panel
        panel7 = new JPanel();
        panel7.setBackground(new Color(45, 22, 74));
        panel7.setPreferredSize(new Dimension(50, 100));
        panel7.setLayout(new GridLayout(1, 3, 10, 0));

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 1, 10, 20));
        panel2.setBackground(new Color(45, 22, 74));

        //-------------------Buttons start-------------------

        startButton = new Gradient("Start", Color.white, new Color(204, 194, 255));
        startButton.setForeground(new Color(45, 22, 74));
        startButton.setFont(new Font("Calibri", Font.BOLD,17));
        startButton.setFocusable(false);
        startButton.addActionListener((e) -> {
            start = System.nanoTime();
            timer.start();

        });

        stopButton = new Gradient("Stop", Color.white, new Color(204, 194, 255));
        stopButton.setForeground(new Color(45, 22, 74));
        stopButton.setFont(new Font("Calibri", Font.BOLD,17));
        stopButton.setFocusable(false);
        stopButton.addActionListener((e) -> {
            timer.stop();
            seconds = Integer.parseInt(labelSNum.getText().replaceFirst(" s", ""));
            minutes = Integer.parseInt(labelMNum.getText().replaceFirst(" m", ""));
            hours = Integer.parseInt(labelHNum.getText().replaceFirst(" h", ""));
        });

        resetButton = new Gradient("Reset", Color.white, new Color(204, 194, 255));
        resetButton.setForeground(new Color(45, 22, 74));
        resetButton.setFont(new Font("Calibri", Font.BOLD,17));
        resetButton.setFocusable(false);
        resetButton.addActionListener((e) -> {
            hours = 0;
            minutes = 0;
            seconds = 0;
            secondsElapsed = 0;
            start = System.nanoTime();

            labelHNum.setText(hours + " h");
            labelMNum.setText(minutes + " m");
            labelSNum.setText(seconds + " s");
        });

        panel7.add(startButton);
        panel7.add(stopButton);
        panel7.add(resetButton);


        //--------------------Buttons end--------------------

        panel2.add(panel6);
        panel2.add(panel7);

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.SOUTH);
        frame.add(panel4, BorderLayout.WEST);
        frame.add(panel5, BorderLayout.EAST);
        frame.setVisible(true);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void updateClock(){
        // 1 h 1 m 1 s = 3661 s
        long current = System.nanoTime();
        elapsed = current - start;
        secondsElapsed = (int) (elapsed / 1000000000);

        int currentSeconds = (seconds + (minutes * 60) + (hours * 3600));

        int totalSeconds = (currentSeconds + secondsElapsed);

        int displayHours = totalSeconds/3600;
        int remainingMinutes = totalSeconds % 3600;
        int displayMinutes = remainingMinutes / 60;
        int remainingSeconds = remainingMinutes % 60;

        labelHNum.setText(displayHours + " h");
        labelMNum.setText(displayMinutes + " m");
        labelSNum.setText(remainingSeconds + " s");
    }
}
