public class Process implements Comparable<Process>{
    private int priority;
    private double arrivalTime;
    private double burstTime;
    private String Name;
    private double timeQuantum;
    private double contextSwitching;
    // Constructor
    public Process(String Name,int priority, double arrivalTime, double burstTime, double timeQuantum, double contextSwitching) {
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.Name=Name;
        this.timeQuantum = timeQuantum;
        this.contextSwitching = contextSwitching;
    }

    // Getter and Setter methods for fields
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }

    public double getContextSwitching() {
        return contextSwitching;
    }
    public void setContextSwitching(double contextSwitching) {
        this.contextSwitching = contextSwitching;
    }

    public double getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(double timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public int compareTo(Process o) {
        return Integer.compare(this.priority, o.priority); // Ascending order
    }
}
