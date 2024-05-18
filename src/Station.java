import java.util.ArrayList;

public class Station {

    private int stationSpeed;
    private String stationID; //must start with a letter
    ArrayList<Task> taskQueue = new ArrayList<>();

    //singleTask, multipleTaskSameType, multipleTaskDifType-> bazılarının hızları değişken bazılarınınki sabit

    ArrayList<Station> singleTask= new ArrayList<>();
    ArrayList<Station> multipleTaskSameType= new ArrayList<>();
    ArrayList<Station> multipleTaskDifType= new ArrayList<>();



    public Station(int stationSpeed, String stationID) {
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
