import java.util.ArrayList;

public class Job {

    private String jobType; //must start with a letter
    private String jobID; //must start with a letter
    private int jobDuration;
    private ArrayList<Task> taskArrayList = new ArrayList<>();

    public void setJobType(String jobType){this.jobType=jobType;}
    public void setJobID(String jobID){this.jobID=jobID;}
    public void setJobDuration(int jobDuration){this.jobDuration=jobDuration;}

    public String getJobType(){return jobType;}
    public String getJobID(){return jobID;}
    public int getJobDuration(){return jobDuration;}
}
