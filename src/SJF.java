import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF implements SchedulerAlgorithm{

    @Override
    public void schdule(Process[] processes) {
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingDouble(Process::getBurstTime));
        int idx = 0;

        Arrays.sort(processes, (p1, p2) -> Double.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        double firstProcessArriveTime = processes[0].getArrivalTime();
        double currentTime = firstProcessArriveTime;

        while(idx < processes.length && firstProcessArriveTime == processes[idx].getArrivalTime()){
            pq.add(processes[idx]);
            idx++;
        }

        int sz=processes.length;
        double sumTurnAround=0,sumWaitingTime=0;
        System.out.println("Name\t\tTurnAround\t\tWaitingTime\n");


        while(!pq.isEmpty()){
            Process p=pq.poll();

            double completionTime=currentTime+p.getBurstTime();
            currentTime = completionTime;

            double turnAround = completionTime-p.getArrivalTime();
            sumTurnAround+=turnAround;

            double waitingTime = turnAround-p.getBurstTime();
            sumWaitingTime+=waitingTime;

            System.out.println(p.getName()+"\t\t"+turnAround + "\t\t"+waitingTime + "\t\t");

            while(idx < processes.length && currentTime >= processes[idx].getArrivalTime()){
                pq.add(processes[idx]);
                idx++;
            }
        }

        System.out.println("\n");
        if(sz >0){
            System.out.println("Average Waiting Time: "+sumWaitingTime/sz);
            System.out.println("Average Waiting Time: "+sumTurnAround/sz);
        }
        else{
            System.out.println("No process to schedule");
        }
    }
}
