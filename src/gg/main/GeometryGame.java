package gg.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gg.construction.Construction;
import gg.gui.ConstructionUI;

public class GeometryGame {
    private static final String TITLE = "Compass and Straightedge";
    public static final int MIN_WIDTH = 640;
    public static final int MIN_HEIGHT = 480;
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 768;

    public static void main(String[] args) {
        Construction construction = new Construction();
        GraphicsImage graphicsImage = new GraphicsImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        ConstructionUI constructionUI = new ConstructionUI(construction, graphicsImage);

        JPanel mainPanel = new GeometryPanel(graphicsImage, constructionUI);

        ConstructionMouseAdapter mouseAdapter = new ConstructionMouseAdapter(constructionUI, mainPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.addMouseListener(mouseAdapter);
        mainPanel.addMouseMotionListener(mouseAdapter);
        mainPanel.addMouseWheelListener(mouseAdapter);

        JFrame mainFrame = new JFrame(TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setFocusable(false);
        mainFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                constructionUI.setSize(mainPanel.getWidth(), mainPanel.getHeight());
            }
        });

        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    @SuppressWarnings("serial")
    private static class GeometryPanel extends JPanel {
        private final GraphicsImage graphicsImage;
        private final ConstructionUI constructionUI;

        private GeometryPanel(GraphicsImage graphicsImage, ConstructionUI constructionUI) {
            this.graphicsImage = graphicsImage;
            this.constructionUI = constructionUI;
        }

        @Override
        protected void paintComponent(Graphics g) {
            constructionUI.draw();
            g.drawImage(graphicsImage.getImage(), 0, 0, null);
        }
    }
}
