
import java.lang.Math;
import java.util.ArrayList;

public class CalculateDistances{

   /**
    * Computes the nearest neighbouring tower for each TowerCell in the collection
    * using pairwise Euclidean distance comparison.
    * 
    * Time Complexity: O(n^2), where n is the number of towers.
    * Assumes latitude and longitude are represented in a consistent coordinate system.
    */
   public void computeMinDistances(ArrayList<TowerCell> towersArr){
      double changeXs = 0;       //Delta in latitude between two towers
      double changeYs = 0;       //Delta in longitude between two towers
      double distance = 0;       //Euclidean distance between two towers

      //Iterates over each tower as the reference point
      for(TowerCell towerA: towersArr){
         double minDist = Double.MAX_VALUE;  //positive infinity
         String closest = "";                //ID of the closest neighbouring tower

         //Compare towerA against all other tower
         for(TowerCell towerB: towersArr){
            if(towerA.equals(towerB)) continue;

            changeXs = towerA.getLatitude() - towerB.getLatitude();
            changeYs = towerA.getLongitude() - towerB.getLongitude();
            distance = Math.sqrt((changeXs * changeXs) + (changeYs * changeYs));

            assert(distance >= 0);  //distance must be a positive value

            if(distance < minDist){
               minDist = distance;
               closest = towerB.getTowerID();
            }

            
         }
         towerA.addNeighbours(closest);   //each tower has an arrayList containing 
      }
    
   }

   public void fillNeighArr(ArrayList<TowerCell> towersArr){
      for(TowerCell tower: towersArr){
         for(TowerCell pointer: towersArr){
            if(pointer == tower) continue;

            if(pointer.inNeighbours(tower) && !tower.inNeighbours(pointer)){
               tower.addNeighbours(pointer.getTowerID());
            }
         }
      }
   }
}
    /* for(int i = 0; i < n; i++){
         for(int j = 0; j < n; j++){
            if(i == j) continue; //since the distance is obviously 0
            
            double changeXs = arr[i][0] - arr[j][0];
            double changeYs = arr[i][1] - arr[j][1];
            double distance = Math.sqrt((changeXs * changeXs) + (changeYs * changeYs));
            
            if(distance < minDist){
               minDist = distance;
               closest = j;
            }
         }
         track[i] = closest;
      }
   
   }*/