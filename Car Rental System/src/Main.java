import java.util.*;

/*
Farhadul Islam
IIUC CSE-56
 */
class Car {
    private String id;
    private String model;
    private String licenseNumber;
    private double mileage;
    private double dailyRate;
    private String manufacturingCountry;
    private String manufacturingYear;
    private String seatNumber;
    private boolean isAvailable;

    public Car(String id, String model, String licenseNumber, double mileage, double dailyRate, String manufacturingCountry, String manufacturingYear, String seatNumber) {
        this.id = id;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.mileage = mileage;
        this.dailyRate = dailyRate;
        this.manufacturingCountry = manufacturingCountry;
        this.manufacturingYear = manufacturingYear;
        this.seatNumber = seatNumber;
        this.isAvailable = true;
    }

    //getters
    public String getCarId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    //setters
    public void setAvailability(boolean status) {
        isAvailable = status;
    }
}

abstract class User {
    protected String name;
    protected String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public boolean check(String pass) {
        return this.pass.equals(pass);
    }

    public abstract void show();
}

class Customer extends User {
    public Customer(String name, String pass) {
        super(name, pass);
    }

    @Override
    public void show() {
        System.out.println("Customer Name: " + name);
    }
}

class Admin extends User {
    public Admin(String name, String pass) {
        super(name, pass);
    }

    @Override
    public void show() {
        System.out.println("Admin Name: " + name);
    }
}

class RentalSystem {
    private HashMap<String, User> users;
    private HashMap<String, Car> cars;

    public RentalSystem() {
        users = new HashMap<>();
        cars = new HashMap<>();
        users.put("admin", new Admin("frad", "frad123"));// what a security technique 😆

    }

    public void register(String name, String pass) {
        if (users.containsKey(name)) {
            System.out.println("User already exists.");
        } else {
            users.put(name, new Customer(name, pass));
            System.out.println("User registered successfully.");
        }
    }

    public User login(String name, String pass) {
        if (users.containsKey(name) && users.get(name).check(pass)) {
            return users.get(name);
        }
        return null;
    }

    public void addCar(String id, String model, String licenseNumber, double mileage, double dailyRate, String country, String year, String seatNumber) {
        if (cars.containsKey(id)) {
            System.out.println("Car already exists.");
        } else {
            cars.put(id, new Car(id, model, licenseNumber, mileage, dailyRate, country, year, seatNumber));
            System.out.println("Car added successfully.");
        }
    }

    public void listAvailableCars() {

        if (cars.isEmpty()) {
            System.out.println("No car available");
        }
        for (Car car : cars.values()) {
            if (car.isAvailable()) {
                System.out.println("Car ID: " + car.getCarId() + ", Model: " + car.getModel() + ", Daily Rate: $" + car.getDailyRate());
            }
        }
    }

    public void rentCar(String carId) {


        if (cars.containsKey(carId) && cars.get(carId).isAvailable()) {
            cars.get(carId).setAvailability(false);
            System.out.println("Car " + carId + " has been rented successfully.");
        } else {
            System.out.println("Car is not available.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RentalSystem rentalSystem = new RentalSystem();
        User cruser = null;

        while (true) {
            System.out.println("\nWelcome to Car Rental System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String regName = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPass = scanner.nextLine();
                    rentalSystem.register(regName, regPass);
                    break;
                case 2:
                    System.out.print("Enter name: ");
                    String lgname = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String lgpass = scanner.nextLine();
                    cruser = rentalSystem.login(lgname, lgpass);
                    if ( cruser!= null) {
                        System.out.println("Login successful. Welcome, " +  cruser.getName() + "!");
                        manage(scanner, rentalSystem,  cruser);
                    } else {
                        System.out.println("Login failed. Incorrect credentials.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void manage(Scanner scanner, RentalSystem rentalSystem, User loggedInUser) {
        while (true) {
            System.out.println("\nCar Rental System");
            System.out.println("1. Add Car (Admin Only)");
            System.out.println("2. List Available Cars");
            System.out.println("3. Rent a Car (Customers Only)");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (loggedInUser instanceof Admin) {
                        System.out.print("Enter Car ID: ");
                        String carId = scanner.nextLine();
                        System.out.print("Enter Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter License Number: ");
                        String license = scanner.nextLine();
                        System.out.print("Enter Mileage: ");
                        double mileage = scanner.nextDouble();
                        System.out.print("Enter Daily Rate: ");
                        double dailyRate = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter Manufacturing Country: ");
                        String country = scanner.nextLine();
                        System.out.print("Enter Manufacturing Year: ");
                        String year = scanner.nextLine();
                        System.out.print("Enter Seat Number: ");
                        String seatNumber = scanner.nextLine();
                        rentalSystem.addCar(carId, model, license, mileage, dailyRate, country, year, seatNumber);
                    } else {
                        System.out.println("Only admins can add cars.");
                    }
                    break;
                case 2:
                    rentalSystem.listAvailableCars();
                    break;
                case 3:
                    System.out.print("Enter Car ID to rent: ");
                    String carId = scanner.nextLine();
                    rentalSystem.rentCar(carId);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
