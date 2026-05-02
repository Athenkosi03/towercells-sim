import java.io.File;
import java.util.Scanner;

class CollectData{

    private String nameOfFolder;
    private String folderDirectory;     // Absolute path to dataset direcory
    private String fileCompletePath;

    public String startCollection(){
        Scanner scan = new Scanner(System.in);
        promptUser(scan);
        return(fileCompletePath);
    }

    /**
     * Captures folder name from user input and constructs absolute directory path
     * 
     * Currently assumes a Window-based file system
     * For production-level portability, Paths/Files API should be used
     */
    private void promptUser(Scanner scan){
        // Acquire data source
        try{
            System.out.println("Enter the name of the folder to be used for the Graph Simulation:");
            nameOfFolder = scan.nextLine().trim();
            
            while(nameOfFolder.isEmpty()){
                System.out.println("No folder name entered....");
                System.out.println("Enter the name of the folder to be used for the Graph Simulation:");
                nameOfFolder = scan.nextLine().trim();
            }

            folderDirectory = "C:/Users/athen/OneDrive - University of Cape Town/Athenkosi's Personals/Side Projects/CellTowers/" + nameOfFolder;

            System.out.println(folderDirectory);
            validateDirectory();
        }

        catch(IllegalArgumentException e){
            System.out.println(e.getMessage() + "Please try again....");
        }
    }

    /**
     * Validates directory structure and loads the CSV file name "tower_case"
     * 
     * Operational Assumption:
     * -Folder contains at least one CSV file and there is one name "tower_cell"
     * -CSV format matches CellTowerLoad specification
     * 
     * Failure cases are handled gracefully with diagnostic output.
     */
    private void validateDirectory(){
        try{
            File folder = new File(folderDirectory);

            // Ensure the directory exists and is accessible
            if(folder.exists() && folder.isDirectory()){
                //now I want to travers this direcotory and find a csv files and especially one name "TowerData"

                File[] csvFilesInDirectory = folder.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".csv"));

                int count = 0;
                for(File f: csvFilesInDirectory){
                    System.out.println(f.getName());
                    if (f.getName().equalsIgnoreCase("tower_data")){
                        fileCompletePath = folderDirectory + "\\" + f.getName();
                        count = 1;
                        break;
                    }
                }

                if(count < 1){
                    System.out.println("Tower data not found....");
                }
            }

            else{
                System.out.println("The folder does not exist or is not a directory....");
            }
        }

        catch(Exception e){
            System.out.println("An error occured during directory validation.");
            e.printStackTrace(); // Detailed stack trace for debugging
        }
    }

}