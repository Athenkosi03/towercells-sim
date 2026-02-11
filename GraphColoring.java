
import java.util.ArrayList;
import java.util.HashSet;

// Implements the Greedy Graph Coloring algorithm.
// Time Complexity: O(V + E) assuming bounded color usage.
// The number of colors used depends on vertex ordering.

public class GraphColoring{


    public GraphColoring(ArrayList<TowerCell> towersArr){
         colorGraph(towersArr);
    }

    // Greedy coloring algorithm:
    // For each vertex:
    //      1. Collect colors used by adjacent vertices.
    //      2. Assign the smallest non-conflicting color.
    // This does not guarentee optimal coloring (graph coloring is NP-Hard);
    // but runs efficiently for large sparse graphs.
    public void colorGraph(ArrayList<TowerCell> towersArr){

        for(TowerCell current: towersArr){

            // Tracks colors currently used by neighbors
            // HashSet ensures O(1) average lookup
            HashSet<Integer> usedColors = new HashSet<>();

            // Build set of forbidden colors
            for(TowerCell neighbor: current.getNeighbours()){
                if(neighbor.getColouring() != -1){
                    usedColors.add(neighbor.getColouring());
                }    
            }
            
            // Assign the smallest available color
            int color = 0;
            while(usedColors.contains(color)){
                color++;
            }

            current.setColouring(color);
        }

        // Print results
        for(TowerCell cell : towersArr){
            System.out.println(cell.getTowerID() + "--> Color: " + cell.getColouring());
        }
    }
}
