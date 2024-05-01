public class Task {

    String taskType; //must start with a letter
    double taskSize;

    public void setTaskType(String taskType){this.taskType= taskType;}
    public void setTaskSize(double taskSize){this.taskSize=taskSize;}

    public String getTaskType(){return taskType;}
    public double getTaskSize(){return taskSize;}

    public Task(String taskType, double taskSize){
        this.taskType= taskType;
        this.taskSize=taskSize;
    }

    public Task(){}
}
