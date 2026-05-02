import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CellTowerGUI extends JFrame {
    private ArrayList<TowerCell> towers;
    private GraphPanel graphPanel;
    private JPanel controlPanel;
    private JPanel statsPanel;
    private JComboBox<String> visualizationMode;
    private JButton colorButton;
    private JButton resetButton;
    private JLabel towerCountLabel;
    private JLabel colorCountLabel;
    private JLabel statusLabel;
    
    // Color palette for different frequencies (colors)
    private final Color[] COLOR_PALETTE = {
        new Color(255, 99, 132),   // Pink/Red
        new Color(54, 162, 235),    // Blue
        new Color(255, 206, 86),    // Yellow
        new Color(75, 192, 192),    // Teal
        new Color(153, 102, 255),   // Purple
        new Color(255, 159, 64),    // Orange
        new Color(46, 204, 113),    // Green
        new Color(231, 76, 60),     // Bright Red
        new Color(52, 152, 219),    // Light Blue
        new Color(155, 89, 182),    // Violet
        new Color(241, 196, 15),    // Gold
        new Color(230, 126, 34),    // Carrot Orange
        new Color(26, 188, 156),    // Turquoise
        new Color(192, 57, 43),     // Cinnabar
        new Color(41, 128, 185),    // Dark Blue
    };
    
    public CellTowerGUI(ArrayList<TowerCell> towers) {
        this.towers = towers;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Cell Tower Frequency Allocation System 🗼");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create main components
        graphPanel = new GraphPanel();
        createControlPanel();
        createStatsPanel();
        
        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);
        
        // Set frame properties
        setSize(1200, 800);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        // Apply modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setBackground(new Color(240, 240, 245));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title label
        JLabel titleLabel = new JLabel("Cell Tower Network");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(50, 50, 70));
        
        // Visualization mode selector
        visualizationMode = new JComboBox<>(new String[]{
            "Color by Frequency", 
            "Show Interference Graph",
            "Show Tower IDs",
            "Heat Map View"
        });
        visualizationMode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Buttons
        colorButton = createStyledButton("🎨 Assign Frequencies", new Color(46, 204, 113));
        resetButton = createStyledButton("🔄 Reset View", new Color(52, 152, 219));
        
        // Add action listeners
        colorButton.addActionListener(e -> {
            applyGraphColoring();
            graphPanel.repaint();
            updateStats();
            statusLabel.setText("Frequencies assigned successfully!");
        });
        
        resetButton.addActionListener(e -> {
            graphPanel.resetView();
            statusLabel.setText("View reset");
        });
        
        visualizationMode.addActionListener(e -> {
            graphPanel.repaint();
            statusLabel.setText("View mode changed to: " + visualizationMode.getSelectedItem());
        });
        
        // Add components
        controlPanel.add(titleLabel);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(new JLabel("View Mode:"));
        controlPanel.add(visualizationMode);
        controlPanel.add(colorButton);
        controlPanel.add(resetButton);
    }
    
    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 4, 20, 5));
        statsPanel.setBackground(new Color(50, 50, 70));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        towerCountLabel = createStatsLabel("Towers", String.valueOf(towers.size()));
        colorCountLabel = createStatsLabel("Frequencies Used", "0");
        
        JLabel edgesLabel = createStatsLabel("Connections", String.valueOf(countEdges()));
        
        statusLabel = new JLabel("Ready to assign frequencies");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setForeground(Color.WHITE);
        
        statsPanel.add(towerCountLabel);
        statsPanel.add(colorCountLabel);
        statsPanel.add(edgesLabel);
        statsPanel.add(statusLabel);
    }
    
    private JLabel createStatsLabel(String title, String value) {
        JLabel label = new JLabel("<html><b>" + title + ":</b> " + value + "</html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 35));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void applyGraphColoring() {
        // Reset colors
        for (TowerCell tower : towers) {
            tower.setColouring(-1);
        }
        
        // Apply greedy coloring
        GraphColoring coloring = new GraphColoring(towers);
        
        // Update stats
        updateStats();
    }
    
    private void updateStats() {
        int maxColor = -1;
        for (TowerCell tower : towers) {
            maxColor = Math.max(maxColor, tower.getColouring());
        }
        colorCountLabel.setText("<html><b>Frequencies Used:</b> " + (maxColor + 1) + "</html>");
    }
    
    private int countEdges() {
        int edges = 0;
        for (TowerCell tower : towers) {
            edges += tower.getNeighbours().size();
        }
        return edges / 2; // Undirected graph
    }
    
    // Inner class for the graph visualization panel
    private class GraphPanel extends JPanel {
        private double zoom = 1.0;
        private int panX = 0, panY = 0;
        private Point lastDragPoint;
        private Map<String, Point> towerPositions;
        private Random random = new Random(42); // Fixed seed for consistent layout
        
        public GraphPanel() {
            setBackground(new Color(250, 250, 255));
            setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 2));
            
            // Mouse listeners for pan and zoom
            MouseAdapter mouseAdapter = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    lastDragPoint = e.getPoint();
                }
                
                public void mouseDragged(MouseEvent e) {
                    if (lastDragPoint != null) {
                        panX += e.getX() - lastDragPoint.x;
                        panY += e.getY() - lastDragPoint.y;
                        lastDragPoint = e.getPoint();
                        repaint();
                    }
                }
                
                public void mouseWheelMoved(MouseWheelEvent e) {
                    double delta = 0.1;
                    if (e.getWheelRotation() < 0) {
                        zoom = Math.min(3.0, zoom + delta);
                    } else {
                        zoom = Math.max(0.5, zoom - delta);
                    }
                    repaint();
                }
                
                public void mouseReleased(MouseEvent e) {
                    lastDragPoint = null;
                }
            };
            
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
            addMouseWheelListener(mouseAdapter);
            
            // Initialize tower positions with force-directed layout simulation
            initializeTowerPositions();
        }
        
        private void initializeTowerPositions() {
            towerPositions = new HashMap<>();
            int width = 800;
            int height = 600;
            
            // Simple force-directed layout simulation
            for (int i = 0; i < towers.size(); i++) {
                TowerCell tower = towers.get(i);
                // Place towers in a circle initially
                double angle = 2 * Math.PI * i / towers.size();
                int x = width/2 + (int)(300 * Math.cos(angle));
                int y = height/2 + (int)(300 * Math.sin(angle));
                towerPositions.put(tower.getTowerID(), new Point(x, y));
            }
            
            // Run a few iterations of force-directed layout
            for (int iter = 0; iter < 50; iter++) {
                applyForceDirectedLayout();
            }
        }
        
        private void applyForceDirectedLayout() {
            double k = 100; // Spring constant
            double repulsion = 5000; // Repulsion force
            
            Map<String, Point> newPositions = new HashMap<>();
            
            for (TowerCell tower : towers) {
                Point pos = towerPositions.get(tower.getTowerID());
                double fx = 0, fy = 0;
                
                // Repulsion forces from all other nodes
                for (TowerCell other : towers) {
                    if (other == tower) continue;
                    Point otherPos = towerPositions.get(other.getTowerID());
                    
                    double dx = pos.x - otherPos.x;
                    double dy = pos.y - otherPos.y;
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    
                    if (dist > 0) {
                        // Repulsion force (inverse square)
                        double force = repulsion / (dist * dist);
                        fx += force * dx / dist;
                        fy += force * dy / dist;
                    }
                }
                
                // Attraction forces along edges
                for (TowerCell neighbor : tower.getNeighbours()) {
                    Point neighborPos = towerPositions.get(neighbor.getTowerID());
                    
                    double dx = neighborPos.x - pos.x;
                    double dy = neighborPos.y - pos.y;
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    
                    if (dist > 0) {
                        // Spring force (Hooke's law)
                        double force = (dist - k) * 0.05;
                        fx += force * dx / dist;
                        fy += force * dy / dist;
                    }
                }
                
                // Update position with damping
                int newX = (int)(pos.x - fx * 0.1);
                int newY = (int)(pos.y - fy * 0.1);
                
                // Keep within bounds
                newX = Math.max(50, Math.min(750, newX));
                newY = Math.max(50, Math.min(550, newY));
                
                newPositions.put(tower.getTowerID(), new Point(newX, newY));
            }
            
            towerPositions.putAll(newPositions);
        }
        
        public void resetView() {
            zoom = 1.0;
            panX = 0;
            panY = 0;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Enable anti-aliasing for smoother graphics
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Apply transformations for zoom and pan
            g2d.translate(panX, panY);
            g2d.scale(zoom, zoom);
            
            String mode = (String) visualizationMode.getSelectedItem();
            
            // Draw edges (interference connections)
            drawEdges(g2d);
            
            // Draw towers
            drawTowers(g2d, mode);
        }
        
        private void drawEdges(Graphics2D g2d) {
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            for (TowerCell tower : towers) {
                Point p1 = towerPositions.get(tower.getTowerID());
                if (p1 == null) continue;
                
                for (TowerCell neighbor : tower.getNeighbours()) {
                    Point p2 = towerPositions.get(neighbor.getTowerID());
                    if (p2 == null) continue;
                    
                    // Draw each edge only once
                    if (tower.getTowerID().compareTo(neighbor.getTowerID()) < 0) {
                        // Create gradient for edge
                        GradientPaint gradient = new GradientPaint(
                            p1.x, p1.y, new Color(200, 200, 220, 100),
                            p2.x, p2.y, new Color(150, 150, 200, 100)
                        );
                        g2d.setPaint(gradient);
                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        }
        
        private void drawTowers(Graphics2D g2d, String mode) {
            for (TowerCell tower : towers) {
                Point p = towerPositions.get(tower.getTowerID());
                if (p == null) continue;
                
                int colorIndex = tower.getColouring();
                Color towerColor;
                
                if (colorIndex >= 0 && mode.equals("Color by Frequency")) {
                    towerColor = COLOR_PALETTE[colorIndex % COLOR_PALETTE.length];
                } else {
                    towerColor = new Color(100, 100, 150); // Default gray
                }
                
                // Draw tower glow effect
                g2d.setColor(new Color(towerColor.getRed(), towerColor.getGreen(), towerColor.getBlue(), 50));
                g2d.fillOval(p.x - 18, p.y - 18, 36, 36);
                
                // Draw tower body
                g2d.setColor(towerColor);
                g2d.fillOval(p.x - 12, p.y - 12, 24, 24);
                
                // Draw border
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(p.x - 12, p.y - 12, 24, 24);
                
                // Draw tower ID if in appropriate mode
                if (mode.equals("Show Tower IDs")) {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2d.getFontMetrics();
                    String id = tower.getTowerID();
                    int textWidth = fm.stringWidth(id);
                    g2d.drawString(id, p.x - textWidth/2, p.y - 15);
                }
                
                // Draw color number if assigned
                if (colorIndex >= 0 && mode.equals("Color by Frequency")) {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    FontMetrics fm = g2d.getFontMetrics();
                    String colorNum = String.valueOf(colorIndex);
                    int textWidth = fm.stringWidth(colorNum);
                    g2d.drawString(colorNum, p.x - textWidth/2, p.y + 4);
                }
                
                // Heat map view
                if (mode.equals("Heat Map View") && colorIndex >= 0) {
                    float intensity = 0.3f + (colorIndex * 0.1f);
                    if (intensity > 1.0f) intensity = 1.0f;
                    g2d.setColor(new Color(1.0f, 0.3f, 0.3f, intensity));
                    g2d.fillOval(p.x - 20, p.y - 20, 40, 40);
                }
            }
        }
    }
    
    // Static method to launch the GUI
    public static void launchGUI(ArrayList<TowerCell> towers) {
        SwingUtilities.invokeLater(() -> {
            new CellTowerGUI(towers).setVisible(true);
        });
    }
}