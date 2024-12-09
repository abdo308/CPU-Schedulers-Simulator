import java.util.*;

public class FCAI implements SchedulerAlgorithm {

    @Override
    public void schdule(Process[] processes) {
        int n = processes.length;
        double[] remainingBurstTime = new double[n];
        double[] turnaroundTime = new double[n];
        double[] waitingTime = new double[n];
        boolean[] isAddedToQueue = new boolean[n];
        List<Integer> completionOrder = new ArrayList<>(); // Track the order of process completion

        for (int i = 0; i < n; i++) {
            remainingBurstTime[i] = processes[i].getBurstTime();
        }

        // Calculate V1 and V2
        double lastArrivalTime = Arrays.stream(processes).mapToDouble(Process::getArrivalTime).max().orElse(1);
        double maxBurstTime = Arrays.stream(processes).mapToDouble(Process::getBurstTime).max().orElse(1);
        double V1 = lastArrivalTime / 10.0;
        double V2 = maxBurstTime / 10.0;

        // Initialize quantum for each process
        Map<Process, Double> quantumMap = new HashMap<>();
        for (Process process : processes) {
            quantumMap.put(process, process.getTimeQuantum());
        }

        // Create a priority queue for scheduling
        PriorityQueue<ProcessData> readyQueue = new PriorityQueue<>(Comparator.comparingDouble(pd -> pd.fcaiFactor));
        List<String> executionTimeline = new ArrayList<>();

        int completed = 0;
        double currentTime = 0;

        // History for detailed execution
        List<ExecutionHistory> executionHistory = new ArrayList<>();

        while (completed < n) {
            // Add processes to the ready queue based on arrival time
            for (int i = 0; i < n; i++) {
                if (!isAddedToQueue[i] && processes[i].getArrivalTime() <= currentTime) {
                    int fcaiFactor = calculateFCAIFactor(processes[i], remainingBurstTime[i], V1, V2);
                    readyQueue.add(new ProcessData(i, processes[i], fcaiFactor));
                    isAddedToQueue[i] = true;
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            // Fetch the process with the smallest FCAI Factor
            ProcessData selectedProcessData = readyQueue.poll();
            Process selectedProcess = selectedProcessData.process;
            int processIndex = selectedProcessData.index;

            // Get the current quantum and calculate execution time
            double quantum = quantumMap.get(selectedProcess);
            double execTime = Math.min(quantum, remainingBurstTime[processIndex]);
            int preemptionThreshold = (int) Math.ceil(0.4 * quantum);

            if (execTime > preemptionThreshold) {
                execTime = preemptionThreshold;
            }

            // Execute the process
            double startTime = currentTime;
            currentTime += execTime;
            remainingBurstTime[processIndex] -= execTime;

            // Log the execution details
            int newFCAIFactor = calculateFCAIFactor(selectedProcess, remainingBurstTime[processIndex], V1, V2);
            executionHistory.add(new ExecutionHistory(
                    (int) startTime, (int) currentTime, selectedProcess, (int) remainingBurstTime[processIndex],
                    (int) quantum, selectedProcess.getPriority(), newFCAIFactor
            ));

            // Update quantum if preempted or process continues
            if (remainingBurstTime[processIndex] > 0) {
                double unusedQuantum = quantum - execTime;
                quantumMap.put(selectedProcess, quantum + unusedQuantum);
                readyQueue.add(new ProcessData(processIndex, selectedProcess, newFCAIFactor));
            } else {
                // Process completed
                completed++;
                double finishTime = currentTime;
                turnaroundTime[processIndex] = finishTime - selectedProcess.getArrivalTime();
                waitingTime[processIndex] = turnaroundTime[processIndex] - selectedProcess.getBurstTime();
            }
        }

        // Print the detailed execution history
        System.out.println("Detailed Execution Timeline:");
        for (ExecutionHistory history : executionHistory) {
            System.out.printf("%5d --> %-5d %-5s Exec: %-5d RBT: %-5d Q: %-5d P: %-5d FCAI: %d\n",
                    history.startTime, history.endTime, history.process.getName(),
                    history.endTime - history.startTime, history.remainingBurstTime,
                    history.updatedQuantum, history.priority, history.fcaiFactor);
        }

        // Calculate and print average times
        double avgWaitingTime = Arrays.stream(waitingTime).sum() / n;
        double avgTurnaroundTime = Arrays.stream(turnaroundTime).sum() / n;

        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
    }

    private int calculateFCAIFactor(Process process, double remainingBurstTime, double V1, double V2) {
        double res = (10 - process.getPriority()) + (process.getArrivalTime() / V1) + (remainingBurstTime / V2);
        double ceilRes = Math.ceil(res);
        return (int) ceilRes;
    }

    private static class ProcessData {
        int index;
        Process process;
        int fcaiFactor;

        ProcessData(int index, Process process, int fcaiFactor) {
            this.index = index;
            this.process = process;
            this.fcaiFactor = fcaiFactor;
        }
    }

    private static class ExecutionHistory {
        int startTime, endTime;
        Process process;
        int remainingBurstTime;
        int updatedQuantum;
        int priority;
        int fcaiFactor;

        ExecutionHistory(int startTime, int endTime, Process process, int remainingBurstTime,
                         int updatedQuantum, int priority, int fcaiFactor) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.process = process;
            this.remainingBurstTime = remainingBurstTime;
            this.updatedQuantum = updatedQuantum;
            this.priority = priority;
            this.fcaiFactor = fcaiFactor;
        }
    }
}
