import java.util.ArrayList;
public class JobTypeId {
    private int jobTypeId;
    private String type;
    private ArrayList<Task> taskArrayList= new ArrayList<>();

    public JobTypeId(int jobTypeId, String type, ArrayList<Task> taskArrayList) {
        this.jobTypeId = jobTypeId;
        this.type = type;
        this.taskArrayList = taskArrayList;
    }
}
