
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



class MyFrame extends JFrame{
    //private GraphPanel graphPane;
    private JPanel controlPanel;
    private JComboBox<String> visualisationMode;
    private JButton colorButton;
    private JLabel towerCount;
    //private JLabel colorCountLabel;
    JButton button;


    public MyFrame(){
        System.out.println("The GUI is launching.....");
        initializeFrame();

    }

    private void initializeFrame(){
        setTitle("Cell Tower🗼");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //main components
        GraphPanel graph = new GraphPanel();
        createControlPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*private JPanel holderPanels(){
        JPanel placeholder = new JPanel();
        placeholder.setPreferredSize(new Dimension(600, 600));
        placeholder.setBackground(Color.lightGray);
        return(placeholder);
    }*/

    private void createControlPanel(){
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(new Color(230,230,250));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 ,10));

        //Creating the title
        JLabel title = new JLabel("Cell Tower Network Project");
        title.setForeground(new Color(50, 50, 70));
        title.setFont(new Font("Segoe UI", Font.BOLD, 18)); 

        //Visualisation mode selector
        visualisationMode = new JComboBox<>(new String[]{
            "Show Tower ID",
            "Show Interference Graph"
        });

        visualisationMode.setFont(new Font("Segoe UI", Font.PLAIN, 12));


        //useless button
        colorButton = createStyleButton("Assign Frequencies", new Color(46, 204, 113));
        //write code for the reset button
        controlPanel.add(title);
        controlPanel.add(visualisationMode);
        controlPanel.add(colorButton);


    }

    private JButton createStyleButton(String buttonName, Color buttnColor){
        JButton buttn = new JButton(buttonName);
        buttn.setBackground(buttnColor);
        buttn.setForeground(Color.WHITE);
        buttn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        buttn.setFocusPainted(false);
        /** setFocusPainted(boolean b);    
            * This method controls whether a "focus indicator" is drawn when the button has keyboard focus
            * Key Purpose: To toggle the rendering of a rectangle(usally dotted or colored) around the button text/icon when the user tabs onto
                        the button or clicks it
        
        */
        buttn.setBorderPainted(false);

        /**setBorderPainted(boolean b);
            * This method controls whether the button's border is rendered
            * Key Purpose: To toggle the drawing of the button's edge border
            * When you want a borderless button, such as using an image icon as a button, or when you are applying a custom Border and want to disable the default
                platform(e.g., in macOS, disabling it allows custom background colors to appear)
         */
        
        buttn.setOpaque(true);
        buttn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttn.setPreferredSize(new Dimension(150, 35));
        //Hover Effect
        buttn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){     //have to name the function "mouseEntered" otherwise this function won't work
                buttn.setBackground(buttnColor.darker());
            }

            public void mouseExited(MouseEvent e){      //similarly, you have to name this function "mouseExited"
                buttn.setBackground(buttnColor);
            }
        });

        return buttn;
    }
}
    /*     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        //need a button that says "Select File"
        button = new JButton("Select File");
        button.addActionListener(this);

        this.add(button);
        this.pack(); 
        this.setVisible(true);
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == button){
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
        }
    }*/