import java.util.ArrayList;

public class JobType {
    private int jobTypeId;
    private String type;
    private ArrayList<Task> taskArrayList;

    public JobType(int jobTypeId, String type, ArrayList<Task> taskArrayList) {
        this.jobTypeId = jobTypeId;
        this.type = type;
        this.taskArrayList = taskArrayList;
    }

}
