import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileListMaker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFilename = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu: ");
            System.out.println("A - Add");
            System.out.println("D - Delete");
            System.out.println("I - Insert");
            System.out.println("M - Move");
            System.out.println("V - View");
            System.out.println("O - Open");
            System.out.println("S - Save");
            System.out.println("C - Clear");
            System.out.println("Q - Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().toUpperCase();

            try {
                switch (choice) {
                    case "A": addItem(); break;
                    case "D": deleteItem(); break;
                    case "I": insertItem(); break;
                    case "M": moveItem(); break;
                    case "V": viewList(); break;
                    case "O": openFile(); break;
                    case "S": saveFile(); break;
                    case "C": clearList(); break;
                    case "Q":
                        if (needsToBeSaved) promptToSave();
                        System.out.println("Goodbye!");
                        return;
                    default: System.out.println("Invalid option.");
                }
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        }
    }

    private static void addItem() {
        System.out.print("Enter item to add: ");
        list.add(scanner.nextLine());
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        viewList();
        System.out.print("Enter index to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem() {
        System.out.print("Enter item to insert: ");
        String item = scanner.nextLine();
        System.out.print("Enter index: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void moveItem() {
        viewList();
        System.out.print("Enter index of item to move: ");
        int from = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new index: ");
        int to = Integer.parseInt(scanner.nextLine());
        if (from >= 0 && from < list.size() && to >= 0 && to <= list.size()) {
            String item = list.remove(from);
            list.add(to, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewList() {
        System.out.println("\nCurrent list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    private static void openFile() throws IOException {
        if (needsToBeSaved) promptToSave();
        System.out.print("Enter filename to open: ");
        String filename = scanner.nextLine();
        Path path = Paths.get(filename + ".txt");
        list.clear();
        list.addAll(Files.readAllLines(path));
        currentFilename = filename;
        needsToBeSaved = false;
        System.out.println("File loaded.");
    }

    private static void saveFile() throws IOException {
        if (currentFilename == null) {
            System.out.print("Enter filename to save as: ");
            currentFilename = scanner.nextLine();
        }
        Path path = Paths.get(currentFilename + ".txt");
        Files.write(path, list);
        needsToBeSaved = false;
        System.out.println("File saved.");
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static void promptToSave() throws IOException {
        System.out.print("You have unsaved changes. Save now? (Y/N): ");
        String response = scanner.nextLine().toUpperCase();
        if (response.equals("Y")) {
            saveFile();
        }
    }
}

