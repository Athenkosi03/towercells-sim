
import java.util.ArrayList;

public class MainClass{
    static ArrayList<TowerCell> towersArr = new ArrayList<>();

    public static void main(String[] args){
        //selecting and opening the folder with the data
        //Scanner scanner = new Scanner(System.in);

        //selecting the csv file with the data
        //new MyFrame();

        //loading the data 

        //starting the program
        //for all the lines of the csv file turn it into a TowerCell objects
        /*while buffered readers -> new TowerCell
                towersArr.add(towerObj);
        */
       TowerCell towerA = new TowerCell("A", 536660, 183800, -0.03098, 51.53657);
       TowerCell towerB = new TowerCell("B", 537032, 184006, -0.02554, 51.53833);
       TowerCell towerC = new TowerCell("C", 537109, 183884, -0.02448, 51.53721);
       TowerCell towerD = new TowerCell("D", 537110, 184695, -0.02415, 51.5445);
       TowerCell towerE = new TowerCell("E", 537206, 184685, -0.02277, 51.54439);

       towersArr.add(towerA);
       towersArr.add(towerB);
       towersArr.add(towerC);
       towersArr.add(towerD);
       towersArr.add(towerE);

       CalculateDistances calcDist = new CalculateDistances();
       calcDist.computeMinDistances(towersArr);
       calcDist.fillNeighArr(towersArr);
       for(TowerCell tw: towersArr){
            System.out.println(tw.getNeighbours());
            //System.out.println("/////-------------*------------///////");
       }
       //System.out.println();

       
    }


}