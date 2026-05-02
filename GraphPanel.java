import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;


import java.io.File;

public class GraphPanel{
    /**
     * Entry point for the Cell Tower Graph Simulation.
     *
     * Responsibilities:
     * 1. Acquire user input for dataset location.
     * 2. Validate and load tower data from CSV.
     * 3. Construct spatial proximity graph.
     * 4. Apply graph coloring to model frequency allocation constraints.
     *
     * This class acts as a high-level orchestration layer,
     * delegating computation and data parsing responsibilities
     * to specialized classes (separation of concerns).
     */

    // Global collection of graph vertices (adjacency handled internally)
    protected static ArrayList<TowerCell> towersArr = new ArrayList<>();

    public static void main(String[] args){
        CollectData dataCollector = new CollectData();
        String fileCompletePath = dataCollector.startCollection();
        
        CellTowerLoader loader  = new CellTowerLoader();      // Responsible for CSV parsing and object instantiation
        try{
             loader.loadData(fileCompletePath, towersArr);
        }

        catch(Exception e){
            System.out.println("An error occured during loading the file....");
            e.printStackTrace();    // Detailed stack trace for debugging
        }

        // Construct spatial adjacency relationship
        CalculateDistances calcDist = new CalculateDistances();
        calcDist.computeMinDistances();

        // Verification of adjacency list
        for(TowerCell tw: towersArr){
            System.out.println(tw.getTowerID() + "--> " + tw.displayNeighbours());
        }
        
        // Apply greedy graph coloring
        GraphColoring coloring = new GraphColoring(towersArr);

        /* 
        MyFrame frame = new MyFrame();
        
        //lauch the gui
        //CellTowerGUI.launchGUI(towersArr);*/
    }

   

   /*   private static void setUpGUI(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Cell Tower🗼");
       // frame.setTitle("Cell Tower Frequency Allocation System 🗼");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        legendPanel.setBackground(Color.MAGENTA);
        
    }*/
}