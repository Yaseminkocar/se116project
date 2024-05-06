public class TaskType {
    private double size;
    private String type;
    public TaskType(double size,String type) {
        this.size = size;
        this.type=type;
    }
    public void check(){
        if(type.equals("t1")){

        }
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
