package port;
import java.util.ArrayDeque;
import java.util.Scanner;
public class sorting {

    private static class Container {
        private String id;
        private String description;
        private double weight;
        public Container(String id, String description, double weight) {
            this.id = id;
            this.description = description;
            this.weight = weight;
        }
        @Override
        public String toString() {
            return String.format("%-7s | %-12s | %.0fkg", id, description, weight);
        }
    }
    private static class Ship {
        private String name;
        private String captain;
        public Ship(String name, String captain) {
            this.name = name;
            this.captain = captain;
        }
        @Override
        public String toString() {
            return String.format("%s | %s", name, captain);
        }
    }
    private static ArrayDeque<Container> containerStack = new ArrayDeque<>();
    private static ArrayDeque<Ship> shipQueue = new ArrayDeque<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    storeContainer();
                    break;
                case 2:
                    viewPortContainers();
                    break;
                case 3:
                    registerArrivingShip();
                    break;
                case 4:
                    viewWaitingShips();
                    break;
                case 5:
                    loadNextShip();
                    break;
                case 0:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
        scanner.close();
    }
    private static void displayMenu() {
        System.out.println("=== Port Container Management System ===");
        System.out.println("[1] Store container (push)");
        System.out.println("[2] View port containers");
        System.out.println("[3] Register arriving ship (enqueue)");
        System.out.println("[4] View waiting ships");
        System.out.println("[5] Load next ship (pop container + poll ship)");
        System.out.println("[0] Exit");
    }
    private static void storeContainer() {
        System.out.print("Enter container ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter container description: ");
        String description = scanner.nextLine();
        System.out.print("Enter container weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        Container container = new Container(id, description, weight);
        containerStack.push(container);
        System.out.println("Stored: " + container);
    }
    private static void viewPortContainers() {
        if (containerStack.isEmpty()) {
            System.out.println("No containers in the port.");
            return;
        }
        System.out.println("TOP → " + containerStack.peek().toString().replace(" |", "     |"));
        containerStack.stream().skip(1).forEach(c -> System.out.println("        " + c));
        System.out.println("← BOTTOM");
    }
    private static void registerArrivingShip() {
        System.out.print("Enter ship name: ");
        String name = scanner.nextLine();
        System.out.print("Enter captain name: ");
        String captain = scanner.nextLine();

        Ship ship = new Ship(name, captain);
        shipQueue.add(ship);
        System.out.println("Registered: " + ship);
    }

    private static void viewWaitingShips() {
        if (shipQueue.isEmpty()) {
            System.out.println("No ships waiting at the dock.");
            return;
        }
        System.out.println("FRONT →");
        shipQueue.forEach(s -> System.out.println(s));
        System.out.println("← REAR");
    }

    private static void loadNextShip() {
        if (containerStack.isEmpty()) {
            System.out.println("Error: No containers available to load.");
            return;
        }
        if (shipQueue.isEmpty()) {
            System.out.println("Error: No ships waiting to be loaded.");
            return;
        }

        Container loadedContainer = containerStack.pop();
        Ship loadedShip = shipQueue.poll();
        System.out.println("Loaded: " + loadedContainer + " → " + loadedShip);
        System.out.println("Remaining containers: " + containerStack.size());
        System.out.println("Remaining ships waiting: " + shipQueue.size());
    }
}