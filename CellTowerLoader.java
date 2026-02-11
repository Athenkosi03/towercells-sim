
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.*;

class CellTowerLoader{

    public void loadData(String filePath, ArrayList<TowerCell> towersArr) throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath), 64* 1024)){
            //--- Read header line ----
            String headerLine = reader.readLine().trim();
            if(headerLine == null) throw new IOException("Cell Tower file is empty");
            int numOfCellTowers = Integer.parseInt(headerLine);

            String line = "";
            int c = 1;
            while((line = reader.readLine()) != null){
                String[] lineData = line.trim().split(",");
                String towerID = lineData[0];
                int  east = Integer.parseInt(lineData[1]);
                int north = Integer.parseInt(lineData[2]);
                double longitude = Double.parseDouble(lineData[3]);
                double latitude =  Double.parseDouble(lineData[4]);

                //--- Debugging the loading.... ----//
                //System.out.println(towerID + " " + " " + east + " " + north + " " + longitude + " "+ latitude);
                TowerCell towerCell = new TowerCell(towerID, east, north, longitude, latitude);
                towersArr.add(towerCell);
                c++;
            }

            if(c != numOfCellTowers){
                throw new IOException("Incomplete Cell Tower data: read only " + c + " tower locations.");
            }
        }
    }
}