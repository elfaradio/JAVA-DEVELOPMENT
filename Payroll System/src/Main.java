import java.util.Scanner;

abstract class Employee {
    private String name;
    private String id;

    Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract double calcSalary();

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class FullTime extends Employee {
    private double salary;

    FullTime(String name, String id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    @Override
    public double calcSalary() {
        return salary;
    }
}

class PartTime extends Employee {
    private double hr, rate;

    PartTime(String name, String id, double hr, double rate) {
        super(name, id);
        this.hr = hr;
        this.rate = rate;
    }

    @Override
    public double calcSalary() {
        return hr * rate;
    }
}

abstract class PayMethod {
    public abstract void processing(double total);
}

class Bank extends PayMethod {
    private String idd;

    public Bank(String idd) {
        this.idd = idd;
    }

    @Override
    public void processing(double total) {
        System.out.printf("Paid $%.2f via Bank Transfer to %s\n", total, idd);
    }
}

class Cash extends PayMethod {
    @Override
    public void processing(double total) {
        System.out.printf("Paid $%.2f in Cash\n", total);
    }
}

abstract class Katinewa {
    public abstract double calcKatinewa();
}

class Health extends Katinewa {
    private double tt;

    public Health() {
        this.tt = 1000;
    }

    @Override
    public double calcKatinewa() {
        return tt;
    }
}

class Retire extends Katinewa {
    private double tt;

    public Retire() {
        this.tt = 1000;
    }

    @Override
    public double calcKatinewa() {
        return tt;
    }
}

class Tax extends Katinewa {
    private double salary;

    public Tax(double salary) {
        this.salary = salary;
    }

    @Override
    public double calcKatinewa() {
        if (salary <= 100000) {
            return salary * 0.08;  // 8% tax
        } else if (salary <= 500000) {
            return salary * 0.10;  // 10% tax
        }
        return salary * 0.15; // 15% tax
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your type (FullTime / PartTime):");
        String type = sc.nextLine();
        System.out.println("Enter your name:");
        String name = sc.nextLine();
        System.out.println("Enter your ID:");
        String id = sc.nextLine();

        Employee user = null;

        if (type.equalsIgnoreCase("FullTime")) {
            System.out.println("Enter your monthly salary:");
            double salary = sc.nextDouble();
            user = new FullTime(name, id, salary);
        } else if (type.equalsIgnoreCase("PartTime")) {
            System.out.println("Enter hourly rate:");
            double rate = sc.nextDouble();
            System.out.println("Enter total hour worked:");
            double hr = sc.nextDouble();
            user = new PartTime(name, id, hr, rate);
        } else {
            System.out.println("Invalid user type!");
            sc.close();
            return;
        }

        sc.nextLine();
        System.out.println("Enter Payment Method (Bank / Cash):");
        String mtype = sc.nextLine();
        PayMethod pmethod = null;

        if (mtype.equalsIgnoreCase("Bank")) {
            System.out.println("Enter Bank Account Number:");
            String iddd = sc.nextLine();
            pmethod = new Bank(iddd);
        } else if (mtype.equalsIgnoreCase("Cash")) {
            pmethod = new Cash();
        } else {
            System.out.println("Invalid payment method!");
            sc.close();
            return;
        }

        double mainsalary = user.calcSalary();
        double net = mainsalary;

        Katinewa health = new Health();
        Katinewa retire = new Retire();
        Katinewa tax = new Tax(mainsalary);

        double totalkatinewa = 0;

        if (type.equalsIgnoreCase("FullTime")) {
            totalkatinewa = health.calcKatinewa() + retire.calcKatinewa() + tax.calcKatinewa();
            net = mainsalary - totalkatinewa;
        }

        pmethod.processing(net);

        System.out.println("\n=================================");
        System.out.println("           REPORT              ");
        System.out.println("=================================");
        System.out.printf("| %-15s : %s%n", "Name", user.getName());
        System.out.printf("| %-15s : %s%n", "ID", user.getId());
        System.out.println("---------------------------------");
        System.out.printf("| %-15s : $%.2f%n", "Gross Salary", mainsalary);
        System.out.println("---------------------------------");

        if (type.equalsIgnoreCase("FullTime")) {
            System.out.printf("| %-15s : $%.2f%n", "Health Deduction", health.calcKatinewa());
            System.out.printf("| %-15s : $%.2f%n", "Retirement Deduction", retire.calcKatinewa());
            System.out.printf("| %-15s : $%.2f%n", "Tax Deduction", tax.calcKatinewa());
            System.out.println("---------------------------------");
        } else {
            System.out.println("| No deductions for Part-Time Employees. |");
            System.out.println("---------------------------------");
        }

        System.out.printf("| %-15s : $%.2f%n", "Net Salary", net);
        System.out.println("=================================\n");

        sc.close();
    }
}
