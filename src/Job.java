import java.util.ArrayList;

public class Job {

    private JobType jobType; //must start with a letter
    private String jobID; //must start with a letter
    private int jobDuration;
    private ArrayList<Task> taskArrayList = new ArrayList<>();

    public Job(JobType jobType, String jobID, int jobDuration){
        this.jobType=jobType;
        this.jobID=jobID;
        this.jobDuration=jobDuration;
    }

    public void setJobType(JobType jobType){this.jobType=jobType;}
    public void setJobID(String jobID){this.jobID=jobID;}
    public void setJobDuration(int jobDuration){this.jobDuration=jobDuration;}


    public JobType getJobType(){return jobType;}
    public String getJobID(){return jobID;}
    public int getJobDuration(){return jobDuration;}
}
