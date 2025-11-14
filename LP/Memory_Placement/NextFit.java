import java.util.Scanner;

public class NextFit {
    static void nextFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        int lastAllocated = 0; 
        int totalUsed = 0;     

        for (int i = 0; i < n; i++) {
            allocation[i] = -1; 
            int j = lastAllocated;
            boolean allocated = false;

            do {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j + 1;
                    blockSize[j] -= processSize[i];
                    lastAllocated = j; 
                    allocated = true;
                    totalUsed += processSize[i];
                    break;
                }
                j = (j + 1) % m; 
            } while (j != lastAllocated);
        }

        System.out.println("\nProcess No.\tProcess Size\tBlock No.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1) {
                System.out.println(allocation[i]);
            } else {
                System.out.println("Not Allocated");
            }
        }

        int totalMemory = 0;
        for (int size : blockSize) {
            totalMemory += size;
        }

        int originalTotal = totalMemory + totalUsed;

        double utilization = (double) totalUsed / originalTotal * 100;

        System.out.println("\nTotal Memory Available: " + originalTotal);
        System.out.println("Total Memory Used: " + totalUsed);
        System.out.printf("Memory Utilization: %.2f%%\n", utilization);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int m = in.nextInt();
        int blockSize[] = new int[m];
        System.out.println("Enter the sizes of the blocks:");
        for (int i = 0; i < m; i++) {
            blockSize[i] = in.nextInt();
        }

        System.out.print("Enter the number of processes: ");
        int n = in.nextInt();
        int processSize[] = new int[n];
        System.out.println("Enter the sizes of the processes:");
        for (int i = 0; i < n; i++) {
            processSize[i] = in.nextInt();
        }

        nextFit(blockSize, m, processSize, n);

        in.close();
    }
}
