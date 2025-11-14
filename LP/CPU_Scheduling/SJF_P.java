import java.util.*;

public class SJF_P {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        String task_id[] = new String[n];
        int arrival_time[] = new int[n];
        int burst_time[] = new int[n];
        int remaining_time[] = new int[n];
        int completion_time[] = new int[n];
        int waiting_time[] = new int[n];
        int turnaround_time[] = new int[n];

        
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Task ID: ");
            task_id[i] = sc.next();

            System.out.print("Enter Arrival Time: ");
            arrival_time[i] = sc.nextInt();

            System.out.print("Enter Burst Time: ");
            burst_time[i] = sc.nextInt();

            remaining_time[i] = burst_time[i];
        }

        int complete = 0, currentTime = 0, minIndex = -1;
        boolean check = false;

        
        while (complete != n) {
            int minRemaining = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (arrival_time[i] <= currentTime && remaining_time[i] > 0 && remaining_time[i] < minRemaining) {
                    minRemaining = remaining_time[i];
                    minIndex = i;
                    check = true;
                }
            }

            if (!check) {
                currentTime++;
                continue;
            }

            remaining_time[minIndex]--;
            currentTime++;

            
            if (remaining_time[minIndex] == 0) {
                complete++;
                check = false;
                completion_time[minIndex] = currentTime;
                turnaround_time[minIndex] = completion_time[minIndex] - arrival_time[minIndex];
                waiting_time[minIndex] = turnaround_time[minIndex] - burst_time[minIndex];
                if (waiting_time[minIndex] < 0) waiting_time[minIndex] = 0;
            }
        }

        
        System.out.println("\n--- SJF (Preemptive) - Shortest Remaining Time First (SRTF) ---");
        System.out.println("Task\tAT\tBT\tCT\tTAT\tWT");

        double avgWT = 0, avgTAT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println(task_id[i] + "\t" + arrival_time[i] + "\t" + burst_time[i] + "\t" +
                    completion_time[i] + "\t" + turnaround_time[i] + "\t" + waiting_time[i]);
            avgWT += waiting_time[i];
            avgTAT += turnaround_time[i];
        }

        System.out.println("\nAverage Waiting Time: " + (avgWT / n));
        System.out.println("Average Turnaround Time: " + (avgTAT / n));

        sc.close();
    }
}
