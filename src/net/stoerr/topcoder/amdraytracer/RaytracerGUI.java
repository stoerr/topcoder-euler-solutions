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

    private JLabel statusLabel = null;

    private JLabel imageLabel = null;

    private Image image = null;

    private ArrayImageDisplayer im = null; // @jve:decl-index=0:

    private RayTracer rayTracer;

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
            jFrame.setSize(1100, 1100);
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
            imageLabel = new JLabel();
            imageLabel.setText(""); // Generated
            statusLabel = new JLabel();
            statusLabel.setText("JLabel"); // Generated
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(statusLabel, BorderLayout.SOUTH); // Generated
            jContentPane.add(imageLabel, BorderLayout.CENTER); // Generated
            ArrayImageDisplayer arr = getIm();
            image = jContentPane.createImage(arr.source);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setPreferredSize(new Dimension(arr.xs, arr.ys));
        }
        return jContentPane;
    }

    private ArrayImageDisplayer getIm() {
        if (im == null) {
            im = new ArrayImageDisplayer(RayTracer.XS, RayTracer.YS);
        }
        return im;
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
        rayTracer = new RayTracer(im.a);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                return null;
            }

        };
        worker.execute();

        SwingWorker<Void, Void> displayer = new SwingWorker<Void, Void>() {
            int i;

            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    // rayTracer.lightScreen();
                    Thread.sleep(5000);
                    publish();
                }
            }

            @Override
            protected void process(List<Void> arg0) {
                ++i;
                statusLabel.setText(String.valueOf(i));
                im.refresh();
            }

        };
        displayer.execute();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    rayTracer.lightScreen();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    rayTracer.lightScreen();
                }
            }
        }.start();

    }

}
