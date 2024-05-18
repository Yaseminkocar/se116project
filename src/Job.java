import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job {

    private String jobTypeID; //must start with a letter
    private String jobID;
    private int startTime;
    private int duration;
    private String state;

    private ArrayList<Task> taskArrayList = new ArrayList<>();

    public Job(String jobTypeID, String jobID,int starTime, int duration, String state){
        this.jobTypeID=jobTypeID;
        this.jobID=jobID;
       this.startTime=starTime;
       this.duration=duration;
       this.state="waiting";
    }


    public String getJobTypeID() {
        return jobTypeID;
    }

    public void setJobTypeID(String jobTypeID) {
        this.jobTypeID = jobTypeID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public void setJobType(JobType jobType){this.jobTypeID=jobTypeID;}
    public void setJobID(String jobID){this.jobID=jobID;}

    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }

    public String  getJobType(){return jobTypeID;}
    public String getJobID(){return jobID;}

    public int deadline() {
        return startTime + duration;
    }



    @Override
    public String toString() {
        return "JobID: " + jobID + ", JobType: " + jobTypeID + ", StartTime: " + startTime + ", Duration: " + duration + ", Deadline: " + deadline();
    }


}



