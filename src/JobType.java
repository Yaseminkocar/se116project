import java.util.ArrayList;

public class JobType {
    private int jobTypeId;
    private String type;

    public int getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }

    private ArrayList<Task> taskArrayList;

    public JobType(int jobTypeId, String type, ArrayList<Task> taskArrayList) {
        this.jobTypeId = jobTypeId;
        this.type = type;
        this.taskArrayList = taskArrayList;
    }
}
