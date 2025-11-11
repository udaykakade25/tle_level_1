import java.io.*;
import java.util.*;

class Student {
    int rollNumber;
    String name;
    String division;
    String address;

    // Constructor
    public Student(int rollNumber, String name, String division, String address) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.division = division;
        this.address = address;
    }

    // To display student details
    public void displayStudent() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Name: " + name);
        System.out.println("Division: " + division);
        System.out.println("Address: " + address);
    }

    // Convert student details to a single string to save in file
    public String toString() {
        return rollNumber + "," + name + "," + division + "," + address;
    }

    // Static method to read from a string (for file reading)
    public static Student fromString(String data) {
        String[] parts = data.split(",");
        int rollNumber = Integer.parseInt(parts[0]);
        String name = parts[1];
        String division = parts[2];
        String address = parts[3];
        return new Student(rollNumber, name, division, address);
    }
}

public class Main {
    static String fileName = "students.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Display Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    deleteStudent(scanner);
                    break;
                case 3:
                    displayStudent(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to add a student to the file
    static void addStudent(Scanner scanner) throws IOException {
        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter division: ");
        String division = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Student student = new Student(rollNumber, name, division, address);

        // Open the file in append mode
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(student.toString() + "\n");
        writer.close();

        System.out.println("Student added successfully.");
    }

    // Method to delete a student based on roll number
    static void deleteStudent(Scanner scanner) throws IOException {
        System.out.print("Enter roll number to delete: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        File tempFile = new File("temp_students.txt");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            Student student = Student.fromString(line);
            if (student.rollNumber != rollNumber) {
                writer.write(line + "\n");
            } else {
                found = true;
            }
        }
        reader.close();
        writer.close();

        // Delete the original file and rename temp file to original
        if (found) {
            File originalFile = new File(fileName);
            originalFile.delete();
            tempFile.renameTo(originalFile);
            System.out.println("Student record deleted successfully.");
        } else {
            System.out.println("Student record not found.");
            tempFile.delete();
        }
    }

    // Method to display student details based on roll number
    static void displayStudent(Scanner scanner) throws IOException {
        System.out.print("Enter roll number to display: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            Student student = Student.fromString(line);
            if (student.rollNumber == rollNumber) {
                student.displayStudent();
                found = true;
                break;
            }
        }
        reader.close();

        if (!found) {
            System.out.println("Student record not found.");
        }
    }
}
