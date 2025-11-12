import java.util.*;

public class FIFO {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();
        
        System.out.print("Enter reference string length: ");
        int refLen = sc.nextInt();
        
        System.out.print("Enter reference string (space-separated): ");
        int[] reference = new int[refLen];
        for(int i = 0; i < refLen; i++)
            reference[i] = sc.nextInt();
        
        int[] buffer = new int[frames];
        Arrays.fill(buffer, -1);  // Initialize with -1 (empty)
        
        int pointer = 0, hits = 0, faults = 0;
        
        System.out.println("\nPage Replacement Process:");
        for(int i = 0; i < refLen; i++) {
            boolean found = false;
            
            // Check if page is already in buffer (Hit)
            for(int j = 0; j < frames; j++) {
                if(buffer[j] == reference[i]) {
                    found = true;
                    hits++;
                    break;
                }
            }
            
            // Page fault - replace using FIFO
            if(!found) {
                buffer[pointer] = reference[i];
                pointer = (pointer + 1) % frames;  // Circular increment
                faults++;
            }
            
            // Print current frame status
            System.out.print(reference[i] + " -> ");
            for(int j = 0; j < frames; j++)
                System.out.print((buffer[j] == -1 ? "-" : buffer[j]) + " ");
            System.out.println(found ? "[Hit]" : "[Fault]");
        }
        
        System.out.println("\nHits: " + hits);
        System.out.println("Faults: " + faults);
        System.out.println("Hit Ratio: " + (hits * 100.0 / refLen) + "%");
    }
}