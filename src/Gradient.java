import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Gradient extends JButton {
    private static final long serialVersionUID = 1L;
    private Color stopTop;
    private Color stopBottom;
    private Paint colorGradient;
    private Point[] stopPoints = new Point[2];

    public Gradient(Color stopTop, Color stopBottom) {
        this("", stopTop, stopBottom);
    }

    public Gradient(String text, Color stopTop, Color stopBottom) {
        this(text, null, stopTop, stopBottom);
    }

    public Gradient(String text, Icon icon, Color stopTop, Color stopBottom) {
        super(text, icon);

        setContentAreaFilled(false);
        setFocusPainted(false);

        this.stopTop = stopTop;
        this.stopBottom = stopBottom;

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                invalidate();
            }
        });
    }

    public void invalidate() {
        super.invalidate();

        stopPoints[0] = new Point(0, 0);
        stopPoints[1] = new Point(0, getHeight());

        if (getModel().isPressed()) {
            colorGradient = new GradientPaint(stopPoints[0], stopBottom, stopPoints[1], stopTop);
        } else {
            colorGradient = new GradientPaint(stopPoints[0], stopTop, stopPoints[1], stopBottom);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setPaint(colorGradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

}
