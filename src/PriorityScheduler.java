import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityScheduler implements SchedulerAlgorithm {

    @Override
    public void schdule(Process[] processes) {
        // Priority queue sorted by priority and arrival time
        PriorityQueue<Process> queue = new PriorityQueue<>((p1, p2) -> {
            if (p1.getPriority() == p2.getPriority()) {
                return Double.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
            return Integer.compare(p1.getPriority(), p2.getPriority());
        });

        // Add all processes to a list (for dynamic checking of arrival times)
        List<Process> processList = new ArrayList<>();
        for (Process process : processes) {
            processList.add(process);
        }

        double sumTurnAround = 0, sumWaitingTime = 0;
        double completion = 0;
        int sz = processes.length;

        // Start scheduling
        while (!processList.isEmpty() || !queue.isEmpty()) {
            // Check for processes that have arrived and add them to the queue
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getArrivalTime() <= completion) {
                    queue.add(processList.remove(i));
                    i--; // Adjust index after removal
                }
            }

            // If the queue is empty, jump to the next arrival time
            if (queue.isEmpty() && !processList.isEmpty()) {
                completion = processList.get(0).getArrivalTime();
                continue;
            }

            // Poll the highest priority process
            Process p = queue.poll();
            System.out.println("Name of the process: " + p.getName());

            // Calculate completion time, turnaround time, and waiting time
            if (completion < p.getArrivalTime()) {
                completion = p.getArrivalTime(); // Handle idle time
            }
            completion += p.getBurstTime();
            double turnAround = completion - p.getArrivalTime();
            double waitingTime = turnAround - p.getBurstTime();
            sumTurnAround += turnAround;
            sumWaitingTime += waitingTime;

            // Print process details
            System.out.println("Waiting Time: " + waitingTime);
            System.out.println("Turnaround Time: " + turnAround);
            System.out.println("===============================");
        }

        // Print average times
        if (sz > 0) {
            System.out.println("Average Waiting Time: " + sumWaitingTime / sz);
            System.out.println("Average TurnAround Time: " + sumTurnAround / sz);
        } else {
            System.out.println("No process to schedule");
        }
    }
}
