
import java.lang.Math;
import java.util.ArrayList;

public class CalculateDistances{
   /**
    * Constructs adjacency relationships between TowerCell nodes by connecting
    * each tower to its nearest spatial neighbor using Euclidean distance.
    *
    * This effectively builds a proximity graph (1-nearest neighbor graph).
    *
    * Algorithmic Strategy:
    * - Perform pairwise distance comparisons.
    * - For each vertex, select the minimum-distance distinct vertex.
    *
    * Time Complexity: O(n^2)
    * Space Complexity: O(1) auxiliary (excluding adjacency storage)
    * 
    * Assumes latitude and longitude are represented in a consistent coordinate system.
    */
   public void computeMinDistances(ArrayList<TowerCell> towersArr){
      double changeXs = 0;       //Delta in latitude between two towers
      double changeYs = 0;       //Delta in longitude between two towers

      double distance = 0;       //Euclidean distance between two towers

      // Outer loop treats each tower as a reference vertex
     for(int i = 0; i < towersArr.size(); i++){
         double minDist = Double.MAX_VALUE;     // Initialize to positive infinity to guarantee first comparison succeeds

         TowerCell towerA = towersArr.get(i);
         TowerCell towerClosest = null;         //Closest neighbouring tower

         // Inner loop performs exhaustive comparison with all other vertices
         for(int j = 0; j < towersArr.size(); j++){
            TowerCell towerB = towersArr.get(j);

            // Skip self-comparison (distance to itself = 0)
            if(towerA.equals(towerB)) continue;
            
            // Compute Euclidean distance in 2D space
            changeXs = towerA.getEasting() - towerB.getEasting();
            changeYs = towerA.getNorthing() - towerB.getNorthing();

            distance = Math.sqrt((changeXs * changeXs) + (changeYs * changeYs));

            // Sanity assertion: Euclidean distance must be non-negative
            assert(distance >= 0); 

            // Greedy selection of minimum distance neigbor
            if(distance < minDist){
               //towerClosest = null;   //make sure that a new closest tower is placed in
               minDist = distance;
               towerClosest = towerB;
            }
         }

         // If a closest neighbor was found, add undirected edge
         if(towerClosest != null){
            // Adds mutual adjacency to maintain undirected graph structure
            towerA.addNeighbours(towerClosest);
            towerClosest.addNeighbours(towerA);
         }
      }

      for(TowerCell cell: towersArr){
         removeDuplicates(cell);
      }
      
   }

   // Removes duplicates in the neighbours
   private void removeDuplicates(TowerCell cell){
      ArrayList<TowerCell> neighbours = cell.getNeighbours();

      for(int i = 0; i < neighbours.size() - 1; i++){
         for(int j = i + 1; j < neighbours.size(); j++){
            // linearly traverse the neighbour array list and remove duplicates
            if(neighbours.get(i) == neighbours.get(j)){
               neighbours.remove(i);
            }
         }
      }
   }
}