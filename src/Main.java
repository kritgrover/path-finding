import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input = -1;
        Maze maze = null;
        int size;
        String filePath;

        while (input != 6) {
            if (maze != null) {
                System.out.println();
            }

            System.out.println("=== Menu ===");
            System.out.println("1. Generate new maze");
            System.out.println("2. Load existing maze");

            if (maze != null) {
                System.out.println("3. Save current maze");
                System.out.println("4. Display the maze");
                System.out.println("5. Find the escape");
                System.out.println("6. Exit");
            }

            input = s.nextInt();

            switch (input) {

                case 1:
                    System.out.println();
                    System.out.println("Enter dimensions of new maze:");
                    size = s.nextInt();

                    maze = new Maze(size, size);
                    System.out.println();
                    System.out.println("Generating new maze...");
                    maze.generate();
                    System.out.println();
                    System.out.println("Maze generated!");
                    break;

                case 2:
                    System.out.println("Enter File Name:");
                    filePath = s.next();
                    maze = Maze.load(filePath);
                    if (maze != null) {
                        System.out.println();
                        System.out.println("Maze loaded!");
                    } else {
                        System.out.println("Try again or generate one.");
                    }
                    break;

                case 3:
                    if (maze != null) {
                        System.out.println("Enter File Name:");
                        filePath = s.next();
                        maze.save(filePath);
                        System.out.println();
                        System.out.println("Maze saved in " + filePath + " in current directory.");
                    } else {
                        System.out.println("ERROR: No maze loaded to save.");
                    }
                    break;

                case 4:
                    if (maze != null) {
                        maze.print();
                    } else {
                        System.out.println("ERROR: No maze loaded to display.");
                    }
                    break;

                case 5:
                    if (maze != null) {
                        maze.escape();
                    } else {
                        System.out.println("ERROR: No maze loaded. Try generating one or loading from existing file.");
                    }
                    break;

                case 6:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid Option. Please try again.");
                    break;
            }
        }
    }
}
