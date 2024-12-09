import java.util.*;

public class SRTF implements SchedulerAlgorithm {

    @Override
    public void schdule(Process[] processes) {
        int n = processes.length;
        double[] remainingTime = new double[n];
        double[] waitingTime = new double[n];
        double[] turnaroundTime = new double[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            remainingTime[i] = processes[i].getBurstTime();
        }

        double currentTime = 0;
        int completed = 0;
        int prevProcess = -1; // Track the last executed process to handle context switching
        List<Integer> completionOrder = new ArrayList<>(); // Track the order of process completion

        while (completed < n) {
            // Find the process with the shortest remaining time that has arrived
            int minIndex = -1;
            double minBurstTime = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && processes[i].getArrivalTime() <= currentTime && remainingTime[i] < minBurstTime) {
                    minBurstTime = remainingTime[i];
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                // If no process is ready, increment the time
                currentTime++;
                continue;
            }

            // Add context switching time if switching to a different process
            if (prevProcess != -1 && prevProcess != minIndex) {
                currentTime += processes[minIndex].getContextSwitching();
            }

            // Process the selected process
            remainingTime[minIndex]--;
            currentTime++;
            prevProcess = minIndex;

            if (remainingTime[minIndex] == 0) {
                // Process has completed
                isCompleted[minIndex] = true;
                completed++;
                completionOrder.add(minIndex); // Record the process completion order

                double finishTime = currentTime;
                turnaroundTime[minIndex] = finishTime - processes[minIndex].getArrivalTime();
                waitingTime[minIndex] = turnaroundTime[minIndex] - processes[minIndex].getBurstTime();
            }
        }



        //        ------------------------------------------
        // Calculate averages
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (int i = 0; i < n; i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
        }

        double averageWaitingTime = totalWaitingTime / n;
        double averageTurnaroundTime = totalTurnaroundTime / n;

        // Print results based on completion order
        System.out.println("Process\tArrival\tBurst\tWaiting\tTurnaround");
        for (int index : completionOrder) {
            Process process = processes[index];
            System.out.printf("%s\t%.1f\t%.1f\n",
                    process.getName(),
                    turnaroundTime[index],
                    waitingTime[index]);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", averageTurnaroundTime);
    }
}
