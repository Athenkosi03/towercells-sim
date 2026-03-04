import java.util.ArrayList;

class Bounds {
    private double maxEasting = 0;
    private double minEasting = Double.MAX_VALUE;

    private double maxNorthing = 0;
    private double minNorthing = Double.MAX_VALUE;

    public double[] getTowerRanges(){
        double[] rangeArr = new double[4];
        findingMaxEast();
        findingMinEast();
        findingMaxNorth();
        findingMinNorth();

        rangeArr[0] = maxEasting;
        rangeArr[1] = minEasting;
        rangeArr[2] = maxNorthing;
        rangeArr[3] = minNorthing;

        return rangeArr;

    }

    private void findingMaxEast(){
        for(TowerCell tower: MainClass.towersArr){  //there's only one copy of a static variable, shared by all potential instances
            if(tower.getEasting() > maxEasting){
                maxEasting = tower.getEasting();
            }
        }
    }

    private void findingMinEast(){
        for(TowerCell tower: MainClass.towersArr){
            if(tower.getEasting() < minEasting){
                minEasting = tower.getEasting();
            }
        }
    }

    private void findingMaxNorth(){
        for(TowerCell tower: MainClass.towersArr){
            if(tower.getNorthing() > maxNorthing){
                maxNorthing = tower.getNorthing();
            }
        }
    }

    private void findingMinNorth(){
        for(TowerCell tower: MainClass.towersArr){
            if(tower.getNorthing() < minNorthing){
                minNorthing = tower.getNorthing();
            }
        }
    }
}
