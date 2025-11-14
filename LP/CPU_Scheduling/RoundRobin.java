import java.util.*;

class Process {
    int pid;       
    int at;       
    int bt;         
    int rbt;       
    int ct;         
    int tat;      
    int wt;         
    boolean completed;
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter Time Quantum: ");
        int tq = sc.nextInt();

        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].pid = i + 1;
            System.out.print("Enter Arrival Time for P" + (i + 1) + ": ");
            p[i].at = sc.nextInt();
            System.out.print("Enter Burst Time for P" + (i + 1) + ": ");
            p[i].bt = sc.nextInt();
            p[i].rbt = p[i].bt;
        }

        Arrays.sort(p, Comparator.comparingInt(a -> a.at));

        Queue<Process> q = new LinkedList<>();
        boolean[] inQueue = new boolean[n];
        int time = 0, completed = 0;
        int totalWT = 0, totalTAT = 0;

        q.add(p[0]);
        inQueue[0] = true;
        time = p[0].at;

        while (completed < n) {
            if (q.isEmpty()) {
                for (int i = 0; i < n; i++) {
                    if (!p[i].completed) {
                        time = Math.max(time, p[i].at);
                        q.add(p[i]);
                        inQueue[i] = true;
                        break;
                    }
                }
            }

            Process curr = q.poll();

            if (curr.rbt > tq) {
                time += tq;
                curr.rbt -= tq;
            } else {
                time += curr.rbt;
                curr.rbt = 0;
                curr.ct = time;
                curr.tat = curr.ct - curr.at;
                curr.wt = curr.tat - curr.bt;
                curr.completed = true;
                completed++;

                totalWT += curr.wt;
                totalTAT += curr.tat;
            }

            for (int i = 0; i < n; i++) {
                if (!inQueue[i] && !p[i].completed && p[i].at <= time) {
                    q.add(p[i]);
                    inQueue[i] = true;
                }
            }

            if (!curr.completed) {
                q.add(curr);
            }
        }

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i].pid + "\t" + p[i].at + "\t" + p[i].bt + "\t" +
                               p[i].ct + "\t" + p[i].tat + "\t" + p[i].wt);
        }

        System.out.println("\nAverage Waiting Time = " + (double) totalWT / n);
        System.out.println("Average Turnaround Time = " + (double) totalTAT / n);

        sc.close();
    }
}
