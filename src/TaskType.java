public class TaskType {
    private double size;
    private String type;

    public String getType() {
        return type;
    }

    public TaskType(double size, String type) {
        this.size = size;
        this.type=type;
    }
    public void check(){
        if(type.equals("T1")){

        }
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
