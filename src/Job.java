import java.util.ArrayList;

public class Job {

    String jobType; //must start with a letter
    String jobID; //must start with a letter
    int jobDuration;
    ArrayList<Task> taskArrayList = new ArrayList<>();

    public void setJobType(String jobType){this.jobType=jobType;}
    public void setJobID(String jobID){this.jobID=jobID;}
    public void setJobDuration(int jobDuration){this.jobDuration=jobDuration;}

    public String getJobType(){return jobType;}
    public String getJobID(){return jobID;}
    public int getJobDuration(){return jobDuration;}
}
