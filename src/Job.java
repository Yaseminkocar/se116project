import java.util.ArrayList;

public class Job {

    private JobType jobType; //must start with a letter
    private String jobID;
    private int startTime;
    private int duration;//must start with a letter

    private int jobDuration;
    private ArrayList<Task> taskArrayList = new ArrayList<>();

    public Job(JobType jobType, String jobID, int jobDuration,int starTime, int duration){
        this.jobType=jobType;
        this.jobID=jobID;
        this.jobDuration=jobDuration;
       this.startTime=starTime;
       this.duration=duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setJobType(JobType jobType){this.jobType=jobType;}
    public void setJobID(String jobID){this.jobID=jobID;}
    public void setJobDuration(int jobDuration){this.jobDuration=jobDuration;}

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }

    public JobType getJobType(){return jobType;}
    public String getJobID(){return jobID;}
    public int getJobDuration(){return jobDuration;}

        @Override
        public String toString() {
            return "JobID: " + jobID + ", JobType: " + jobType + ", StartTime: " + startTime + ", Duration: " + duration + ", Deadline: " + getDeadline();
        }
    }



