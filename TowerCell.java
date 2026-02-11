
// Represents a vertex in the interference graph
// Each TowerCell models a node whose edges represent adjacency (signal overlap)
// constraints in the graph coloring problem.
import java.util.ArrayList;
import java.util.Objects;

public class TowerCell{
    // Unique logical identifier for the tower
    private String towerCellID;
    
    // Geographical metadata(notused directly in coloring but useful for spatial graph construction)
    private double longitude;
    private double latitude;
    private int easting;
    private int northing;

    // Adjacency list representation of the graph(space-efficient for sparse graphs)
    private ArrayList<TowerCell> neighbours;

    // Assigned color. -1 indicates uncolored.
    private int colour = -1;

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

    // Returns assigned tower cells color
    public int getColouring(){
        return colour;
    }

    // Assigns a color to the vertex
    public void setColouring(int kValue){
        this.colour = kValue;
    }

    public String getTowerID(){
        return towerCellID;
    }

    public void setTowerID(String twID){
        this.towerCellID = twID;
    }

    // Returns adjacency list
    public ArrayList<TowerCell> getNeighbours(){
        return neighbours;
    }


    // ----------- Graph Operations ------------------//

    // Adds an undirected edge(must be reciprocated externally if graph is undirected)
    public void addNeighbours(TowerCell twCell){
        neighbours.add(twCell);  //all the neighbouring tower should have a different graph colouring
    }


    // Checks adjacency in O(degree) time
    public boolean inNeighbours(TowerCell towerQuery){
        return neighbours.contains(towerQuery);
    }

    // Equality is based on spatial coordinates.
    // This ensures structural graph uniqueness independent of object reference.
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

    // Hash consistency with equals - required for correct behaviour in hash-based collections.
    @Override
    public int hashCode(){
        return Objects.hash(easting, northing);
    }
}