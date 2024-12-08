import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {


        System.out.println("Enter the number of the processes:");
        int numberOfProcesses= scanner.nextInt();
        Process[]processes=new Process[numberOfProcesses];
        for(int i=0; i < numberOfProcesses; ++i){
            scanner.nextLine();  // This line is necessary to clear the newline character

            System.out.println("Enter Process Name:");
            String name=scanner.nextLine();

            System.out.println("Enter Time Quantum:");
            int timeQuantum =scanner.nextInt();

            System.out.println("Enter Context Time:");
            int contextTime =scanner.nextInt();

            System.out.println("Enter Process Arrival Time:");
            Double ArrivalTime=scanner.nextDouble();

            System.out.println("Enter Process Burst Time:");
            Double BurstTime=scanner.nextDouble();

            System.out.println("Enter Process Priority:");
            int Priority=scanner.nextInt();
            System.out.println("Enter Time Quantum:");

            Process p=new Process(name,Priority,ArrivalTime,BurstTime, timeQuantum, contextTime);
            processes[i]=p;
        }

        for(int i = 0 ; i < 4; i++){
            SchedulerAlgorithm schedulerAlgorithm;
            if(i == 0)
                schedulerAlgorithm=new PriorityScheduler();
            else if(i == 1)
                schedulerAlgorithm = new SJF();
            else if(i == 2)
                schedulerAlgorithm = new SRTF();
            else
                schedulerAlgorithm = new FCAI();

            schedulerAlgorithm.schdule(processes);
        }
    }
}