import java.util.Scanner;

public class main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Choose the Scheduling Algorithm");
        System.out.println("1-Priority Scheduling" +
                "\n2-Shortest- Job First (SJF)" +
                "\n3-Shortest- Remaining Time First (SRTF)" +
                "\n4-FCAI Scheduling");
        int number = scanner.nextInt();
        SchedulerAlgorithm ans;
        switch (number){
            // Do the same with different Algorithms
            case 1:
                ans=new PriorityScheduler();
        }
        System.out.println("Enter the number of the processes:");
        int numberOfProcesses= scanner.nextInt();
        Process[]processes=new Process[numberOfProcesses];
        for(int i=0; i < numberOfProcesses; ++i){
            scanner.nextLine();  // This line is necessary to clear the newline character
            System.out.println("Enter Process Name:");
            String name=scanner.nextLine();
            System.out.println("Enter Process Arrival Time:");
            Double ArrivalTime=scanner.nextDouble();
            System.out.println("Enter Process Burst Time:");
            Double BurstTime=scanner.nextDouble();
            System.out.println("Enter Process Priority:");
            int Priority=scanner.nextInt();
            Process p=new Process(name,Priority,ArrivalTime,BurstTime);
            processes[i]=p;
        }
    }
}
