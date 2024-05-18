public class Task {

    private String taskType; //must start with a letter
    private double taskSize;


    public void setTaskType(String taskType){this.taskType= taskType;}
    public void setTaskSize(double taskSize){
        if(taskSize<0) {
            this.taskSize= -1*taskSize;
        }
        else {
            this.taskSize=taskSize;
        }

    }

    public String getTaskType(){return taskType;}
    public double getTaskSize(){return taskSize;}

    public Task(String taskType, double taskSize){
        this.taskType= taskType;
        //this.taskSize=taskSize;

        if(taskSize<0) {
            this.taskSize= -1*taskSize;
        }
        else {
            this.taskSize=taskSize;
        }
    }




    public Task(){}
}
