import java.util.ArrayList;
import java.util.HashSet;

public class GraphColoring{


    public GraphColoring(ArrayList<TowerCell> towersArr){
         colorGraph(towersArr);
    }

    public void colorGraph(ArrayList<TowerCell> towersArr){

        for(TowerCell current: towersArr){

            //Track colors used by neighbors
            HashSet<Integer> usedColors = new HashSet<>();

            for(TowerCell neighbor: current.getNeighbours()){
                if(neighbor.getColouring() != -1){
                    usedColors.add(neighbor.getColouring());
                }    
            }
            
            //assign the smallest available color
            int color = 0;
            while(usedColors.contains(color)){
                color++;
            }

            current.setColouring(color);
            
        }

        //Print results
        for(TowerCell cell : towersArr){
            System.out.println(cell.getTowerID() + "--> Color: " + cell.getColouring());
        }
    }
}
