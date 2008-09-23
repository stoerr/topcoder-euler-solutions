package net.stoerr.topcoder.amdraytracer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public final class RaytracerGUI {

    private JFrame jFrame = null;

    private JPanel jContentPane = null;

    JLabel statusLabel = null;

    private JLabel screenImageLabel = null;

    private Image screenImage = null;

    ArrayImageDisplayer camera = null; // @jve:decl-index=0:

    private JLabel cameraImageLabel = null;

    private Image cameraImage = null;

    ArrayImageDisplayer screen = null;

    RayTracer rayTracer;

    /**
     * This method initializes jFrame
     * 
     * @return javax.swing.JFrame
     */
    JFrame getJFrame() {
        if (jFrame == null) {
            jFrame = new JFrame();
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setContentPane(getJContentPane());
            jFrame.setTitle("RaytracerGUI");
            jFrame.setSize(2100, 1100);
        }
        return jFrame;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            screenImageLabel = new JLabel();
            screenImageLabel.setText(""); // Generated
            cameraImageLabel = new JLabel();
            cameraImageLabel.setText(""); // Generated
            statusLabel = new JLabel();
            statusLabel.setText("JLabel"); // Generated
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(statusLabel, BorderLayout.SOUTH); // Generated
            jContentPane.add(screenImageLabel, BorderLayout.CENTER); // Generated
            jContentPane.add(cameraImageLabel, BorderLayout.EAST); // Generated
            screenImage = jContentPane.createImage(getScreen().source);
            screenImageLabel.setIcon(new ImageIcon(screenImage));
            screenImageLabel.setPreferredSize(new Dimension(screen.xs, screen.ys));
            cameraImage = jContentPane.createImage(getCamera().source);
            cameraImageLabel.setIcon(new ImageIcon(cameraImage));
            cameraImageLabel.setPreferredSize(new Dimension(camera.xs, camera.ys));
        }
        return jContentPane;
    }

    private ArrayImageDisplayer getScreen() {
        if (screen == null) {
            screen = new ArrayImageDisplayer(RayTracer.XS, RayTracer.YS);
        }
        return screen;
    }

    private ArrayImageDisplayer getCamera() {
        if (camera == null) {
            camera = new ArrayImageDisplayer(RayTracer.XS, RayTracer.YS);
        }
        return camera;
    }

    /**
     * Launches this application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RaytracerGUI application = new RaytracerGUI();
                application.getJFrame().setVisible(true);
                application.startTracing();
            }
        });
    }

    void startTracing() {
        rayTracer = new RayTracer(screen.a, camera.a);
        SwingWorker<Void, Void> displayer = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    Thread.sleep(5000);
                    publish();
                }
            }

            @Override
            protected void process(List<Void> arg0) {
                statusLabel.setText(rayTracer.getStatusText());
                screen.refresh();
                camera.refresh();
            }

        };
        displayer.execute();

        rayTracer.doRendering();

    }

}
