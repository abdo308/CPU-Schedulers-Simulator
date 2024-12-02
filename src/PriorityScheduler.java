import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityScheduler implements SchedulerAlgorithm {

    @Override
    public void schdule(Process[] processes) {
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        for(int i=0 ; i < processes.length ; ++i){
            queue.add(processes[i]);
        }
        int sz=queue.size();
        double sumTurnAround=0,sumWaitingTime=0,completion=0;
        boolean first=true;
        while(!queue.isEmpty()){
            Process p=queue.poll();
            System.out.println("Name of the process: "+p.getName());//print the execution
            if(first){
                completion=p.getArrivalTime() +  p.getBurstTime();
                first= false;
            }
            else
                completion+=p.getBurstTime();
            double turnAround = completion - p.getArrivalTime();
            double waitingTime = turnAround - p.getBurstTime();
            sumTurnAround+=turnAround;
            sumWaitingTime+=waitingTime;
            System.out.println("Waiting Time: "+waitingTime);
            System.out.println("Turnaround Time: "+turnAround);
            System.out.println("===============================");
        }
        if(sz >0){
            System.out.println("Average Waiting Time: "+sumWaitingTime/sz);
            System.out.println("Average Waiting Time: "+sumTurnAround/sz);
        }
        else{
            System.out.println("No process to schedule");
        }

    }
}
