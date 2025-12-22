//represent each node of the graph, ie the TowerCell are the nodes of the graph
import java.util.ArrayList;
import java.util.Objects;

public class TowerCell{

    private String towerCellID;
    private double longitude;
    private double latitude;
    private int easting;
    private int northing;

    private ArrayList<String> neighbours;
    private int colour = 0;

    public TowerCell(String towerID, int east,int north, double longitude, double latitude){
        this.towerCellID = towerID;
        this.easting = east;
        this.northing = north;
        this.longitude = longitude;
        this.latitude = latitude;
        neighbours = new ArrayList<>();
    }

    //-------       Getters and Setters     -----------///
    public void setLongitude(double longiT){
        this.longitude = longiT;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLatitude(double latt){
        this.latitude = latt;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setEasting(int east){
        this.easting = east;
    }

    public void setNorthing(int north){
        this.northing = north;
    }

    public int getColouring(){
        return colour;
    }

    public void setColouring(int kValue){
        this.colour = kValue;
    }

    public String getTowerID(){
        return towerCellID;
    }

    public void setTowerID(String twID){
        this.towerCellID = twID;
    }

    public ArrayList<String> getNeighbours(){
        return neighbours;
    }
    // ----------- Methods ------------------//
    public void addNeighbours(String twID){
        neighbours.add(twID);  //all the neighbouring tower should have a different graph colouring
    }

    public boolean inNeighbours(TowerCell towerQuery){
        return neighbours.contains(towerQuery.getTowerID());
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true; //same reference
        }

        if(obj == null || getClass() != obj.getClass()){    //also handle bulls and different classes
            return false;
        }

        TowerCell other = (TowerCell) obj;
        return (this.easting == other.easting) && (this.northing == other.northing);
    }

    @Override
    public int hashCode(){
        return Objects.hash(easting, northing);
    }
}