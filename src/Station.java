public class Station {

    private int stationSpeed;
    private String stationID; //must start with a letter

    Task handleTask= new Task();

    //singleTask, multipleTaskSameType, multipleTaskDifType-> bazılarının hızları değişken bazılarınınki sabit
    Station singleTask=new Station();
    Station multipleTaskSameType=new Station();
    Station multipleTaskDifType=new Station();

    public Station() {
        this.stationSpeed = stationSpeed;
        this.stationID = stationID;
    }


    public int getStationSpeed() {
        return stationSpeed;
    }

    public void setStationSpeed(int stationSpeed) {
        this.stationSpeed = stationSpeed;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
}
