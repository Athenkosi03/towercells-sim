
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class MainClass{
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
    static ArrayList<TowerCell> towersArr = new ArrayList<>();

    static String nameOfFolder;         // User-provided dataset folder
    static String folderDirectory;      // Absolute path to dataset directory

    static CellTowerLoader loader;      // Responsible for CSV parsing and object instantiation

    public static void main(String[] args){

        Scanner scan =  new Scanner(System.in);

        // Acquire data source
        getUserInput(scan);
        // Validate directory and load tower data
        validateDirectory();

        // Construct spatial adjacency relationship
        CalculateDistances calcDist = new CalculateDistances();
        calcDist.computeMinDistances(towersArr);

        // Verification of adjacency list
        for(TowerCell tw: towersArr){
            System.out.println(tw.getTowerID() + "--> " + tw.displayNeighbours());
        }
        
        // Apply greedy graph coloring
        GraphColoring coloring = new GraphColoring(towersArr);
    }


    /**
     * Captures folder name from user input and constructs
     * absolute directory path.
     *
     * Defensive Consideration:
     * Currently assumes a Windows-based file system.
     * For production-level portability, Paths/Files API should be used.
     */
    private static void getUserInput(Scanner scan){
        try{
            System.out.println("Enter the name of the folder to be used for this simulation:");
            nameOfFolder = scan.nextLine().trim();

            if(nameOfFolder.isEmpty()){
                System.out.println("No folder name entered....");
            }

            folderDirectory = "C:/Users/athen/OneDrive - University of Cape Town/Athenkosi's Personals/Side Projects/CellTowers/" + nameOfFolder;
        }

        catch(IllegalArgumentException e){
            System.out.println(e.getMessage() + " Please try again");
        }
    }

    /**
     * Validates directory structure and loads the first detected CSV file.
     *
     * Operational Assumption:
     * - Folder contains at least one CSV file.
     * - CSV format matches CellTowerLoader specification.
     *
     * Failure cases are handled gracefully with diagnostic output.
     */
    private static void validateDirectory(){
        try{
            File folder = new File(folderDirectory);

            // Ensures the directory exists and is accessible
            if(folder.exists() && folder.isDirectory()){    
                
                //Filter for CSV files only
                File[] cellTowerFile = folder.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".csv"));

                if(cellTowerFile != null && cellTowerFile.length > 0){

                    for(File f: cellTowerFile){
                        String fileCompletePath = folderDirectory + "\\" + f.getName();
                        loader = new CellTowerLoader();

                        // Delegates parsing + object creation
                        loader.loadData(fileCompletePath, towersArr);

                        // Stops after first valid dataset
                        break;
                    }
                }

                else{
                    System.out.println("The folder does not exist or is not a directory....");
                }
            }
        }

        catch(Exception e){
            System.out.println("An error occured during directory validation.");
            e.printStackTrace();    // Detailed stack trace for debugging
        }
    }
}