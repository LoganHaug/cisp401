import java.util.Scanner;

abstract class Employee {
    String empName;
    double hrlyPay;
    static int empCount;
    {
        // increase employee count
    }

    // getter and setter methods for instance variables
    // getter method for static variable
    abstract double computePay(double hoursWorked); // computes weekly pay
}

class HourlyEmployee extends Employee {
    HourlyEmployee(String empName, double hrlyPay) {
    }

    double computePay(double hoursWorked) {
        // compute both regular time as well as overtime if needed
        return -1.0;
    }
}

class SalariedEmployee extends Employee {
    SalariedEmployee(String empName, double annualSalary) {
        // the annualSalary will be used to assign a value to the hrlyPay
        // annualSalary/52/40 would give the empwage
        // 52 weeks in a year/ 40 hours worked in a week.
    }

    double computePay(double hoursWorked) {
        // multiply hrlypay x hoursWorked. hoursWorked is 40 for salaried
        // employees. It is only being passed in so that we can get experience with the
        // overriding
        // feature in Java. For salaried employees it is already assumed that it is 40
        // hours
        // a week so what is being passed in is essentially being ignored
        return -1.0;
    }
}

class EmployeeDriver {
    // declare array
    public static void employeeMenu() {
        // Displays menu options
        System.out.println("N: New Employee");
        System.out.println("C: Compute paychecks");
        System.out.println("R: Raise wages");
        System.out.println("L: List employees");
        System.out.println("Q: Quit");
        System.out.println("");
        System.out.print("Enter a command: ");
    }

    public static int select() {
        Scanner scnr = new Scanner(System.in);
        String user;
        while (true) {
            employeeMenu();
            user = scnr.nextLine();
            switch (user) {
                case "N": {
                    newEmployee();
                    break;
                }
                case "C": {
                    computePaycheck();
                    break;
                }
                case "R": {
                    raiseWages();
                    break;
                }
                case "L": {
                    listEmployees();
                    break;
                }
                case "Q": {
                    System.out.println("Goodbye.");
                    scnr.close();
                    return 0;
                }
                default: {
                    System.out.println("\nInvalid selection, please try again\n");
                }
            }
        }
    }

    public static void newEmployee() {
        // grab input from user
        // create employee object
        // expand array as needed
        System.out.println("");
        System.out.println("New employee: not implemented");
        System.out.println("");
    }

    public static void computePaycheck() {
        // display weekly pay for all employees.
        // For hourly employees first grab hours from users
        // for salaried employees assume weekly hours is 40.
        // call computePay method
        System.out.println("");
        System.out.println("Compute paycheck: not implemented");
        System.out.println("");
    }

    public static void raiseWages() {
        // grab percentage and raise wages for all employees. This means that the hourly
        // pay for
        // each employee will be raised by the inputted percentage
        System.out.println("");
        System.out.println("Raise wages: not implemented");
        System.out.println("");
    }

    public static void listEmployees() {
        // display information for all employees
        System.out.println("");
        System.out.println("List employees: not implemented");
        System.out.println("");
    }

    public static void main(String args[]) {
        select();
    }
}