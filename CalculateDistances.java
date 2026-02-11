
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
     for(int i = 0; i < towersArr.size(); i++){
         double minDist = Double.MAX_VALUE;      //positive infinity

         TowerCell towerA = towersArr.get(i);
         TowerCell towerClosest = null;         //Closest neighbouring tower

          //Compare towerA against all other tower
         for(int j = 0; j < towersArr.size(); j++){
            TowerCell towerB = towersArr.get(j);
            if(towerA.equals(towerB)) continue;//since the distance is obviously 0
            
            changeXs = towerA.getLatitude() - towerB.getLatitude();
            changeYs = towerA.getLongitude() - towerB.getLongitude();
            distance = Math.sqrt((changeXs * changeXs) + (changeYs * changeYs));

            assert(distance >= 0);  //distance must be a positive value

            //System.out.println("Distance between " + towerA.getTowerID()  + " and "+ towerB.getTowerID() +  " = " + distance);
            if(distance < minDist){
               towerClosest = null;   //make sure that a new closest tower is placed in
               //System.out.println("Found a neighbour:)");
               //System.out.println("Distance between " + towerA.getTowerID()  + " and "+ towerB.getTowerID() +  " = " + distance);
               minDist = distance;
               //System.out.println("minDist now equals: " + minDist);
               towerClosest = towerB;
               //System.out.println("TowerCloset is: " + towerB.getTowerID());
            }
         }
         towerA.addNeighbours(towerClosest.getTowerID());   //each tower has an arrayList containing 
         towerClosest.addNeighbours(towerA.getTowerID());
         //TowerCell towerClose = towerA.getNeighbours().get(0);
      }
    
   }
}

     /*  for(int i = 0; i < towerArr.size(); i++){
         double minDist = Double.MAX_VALUE;      //positive infinity
         TowerCell towerA = towerArr.get(i);
         TowerCell towerClosest = null;         //Closest neighbouring tower

          //Compare towerA against all other tower
         for(int j = i; j < towerArr.size() - 1; j++){
            TowerCell towerB = towerArr.get(j);
           if(towerA.equals(towerB)) continue;//since the distance is obviously 0
            
            changeXs = towerA.getLatitude() - towerB.getLatitude();
            changeYs = towerA.getLongitude() - towerB.getLongitude();
            distance = Math.sqrt((changeXs * changeXs) + (changeYs * changeYs));

            assert(distance >= 0);  //distance must be a positive value

            if(distance < minDist){
               towerClosest = null;   //make sure that a new closest tower is placed in
               minDist = distance;
               towerClosest = towerB;
            }

         }
         track[i] = closest;
      }
   
   }*/