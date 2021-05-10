import java.util.Scanner;

abstract class Employee {
    String empName;
    double hrlyPay;
    static public int empCount;
    {
        empCount++;
    }

    // getter and setter methods for instance variables
    abstract String getEmpName();

    abstract double getHrlyPay();

    abstract void setEmpName(String empName);

    abstract void setHrlyPay(double hrlyPay);

    abstract void displayInfo();

    // getter method for static variable
    abstract void displayPay(double hoursWorked); // computes weekly pay
}

class HourlyEmployee extends Employee {

    HourlyEmployee(String empName, double hrlyPay) {
        this.empName = empName;
        this.hrlyPay = hrlyPay;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getHrlyPay() {
        return this.hrlyPay;
    }

    public void setHrlyPay(double hrlyPay) {
        this.hrlyPay = hrlyPay;
    }

    @Override
    public void displayInfo() {
        System.out.printf("%-15s $%.2f\n", this.getEmpName(), this.getHrlyPay());
    }

    @Override
    public void displayPay(double hoursWorked) {
        System.out.printf("Pay for %s: $%.2f\n", this.getEmpName(), this.hrlyPay * hoursWorked);
    }
}

class SalariedEmployee extends Employee {
    SalariedEmployee(String empName, double annualSalary) {
        // the annualSalary will be used to assign a value to the hrlyPay
        // annualSalary/52/40 would give the empwage
        // 52 weeks in a year/ 40 hours worked in a week.
        this.empName = empName;
        this.hrlyPay = annualSalary / (52 * 40);
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getHrlyPay() {
        return this.hrlyPay;
    }

    public void setHrlyPay(double hrlyPay) {
        this.hrlyPay = hrlyPay;
    }

    @Override
    public void displayInfo() {
        System.out.printf("%-15s $%.2f\n", this.getEmpName(), this.getHrlyPay());
    }

    @Override
    public void displayPay(double hoursWorked) {
        System.out.printf("Pay for %s: $%.2f\n", this.getEmpName(), hrlyPay * hoursWorked);
    }
}

class EmployeeDriver {
    // Global so that all methods can access
    static Scanner scnr = new Scanner(System.in);
    static Employee[] employees = new Employee[1];
    static int curPos = 0;

    static void resize() {
        Employee[] temp = employees.clone();
        employees = new Employee[Employee.empCount * 2];
        for (int i = 0; i < Employee.empCount; i++) {
            employees[i] = temp[i];
        }
    }

    // declare array
    public static void employeeMenu() {
        // Displays menu options
        System.out.println("N: New Employee");
        System.out.println("C: Compute paychecks");
        System.out.println("R: Raise wages");
        System.out.println("L: List employees");
        System.out.println("Q: Quit");
        System.out.println();
        System.out.print("Enter a command: ");
    }

    public static int select() {

        String user;
        while (true) {
            employeeMenu();
            user = scnr.nextLine().toLowerCase();
            switch (user) {
                case "n": {
                    newEmployee();
                    break;
                }
                case "c": {
                    computePaycheck();
                    break;
                }
                case "r": {
                    raiseWages();
                    break;
                }
                case "l": {
                    listEmployees();
                    break;
                }
                case "q": {
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

    public static String getUserString(String inputMessage) {
        String userString = "";
        while (true) {
            System.out.print(inputMessage);
            if (scnr.hasNextLine()) {
                userString = scnr.nextLine();
                break;
            }
            System.out.println();
        }
        return userString;
    }

    public static Double getUserDouble(String inputMessage) {
        Double userDouble = 0.0;
        while (true) {
            System.out.print(inputMessage);
            if (scnr.hasNextDouble()) {
                userDouble = scnr.nextDouble();
                scnr.nextLine();
                break;
            }
            scnr.nextLine();
            System.out.println();
        }
        return userDouble;
    }

    public static void newEmployee() {
        // grab input from user
        // create employee object
        // expand array as needed
        System.out.println();
        String employeeName = getUserString("Enter name of new employee: ");
        String employeeType;
        while (true) {
            employeeType = getUserString("Hourly (h) or salaried (s): ");
            if (employeeType.equals("s") || employeeType.equals("h"))
                break;
        }
        String inputStr = "";
        if (employeeType.equals("s")) {
            inputStr = "Enter annual salary: ";
        } else if (employeeType.equals("h")) {
            inputStr = "Enter hourly wage: ";
        }
        Double pay = getUserDouble(inputStr);
        if (Employee.empCount + 1 > employees.length) {
            resize();
        }
        if (employeeType.equals("s"))
            employees[curPos] = new SalariedEmployee(employeeName, pay);

        else if (employeeType.equals("h"))
            employees[curPos] = new HourlyEmployee(employeeName, pay);
        curPos++;
        System.out.println();
    }

    public static void computePaycheck() {
        // display weekly pay for all employees.
        // For hourly employees first grab hours from users
        // for salaried employees assume weekly hours is 40.
        // call computePay method
        System.out.println();
        if (Employee.empCount < 1) {
            System.out.println("There are no employees stored");
        } else {
            for (int i = 0; i < Employee.empCount; i++) {
                if (employees[i] instanceof HourlyEmployee) {
                    double employeeHours = getUserDouble("Enter hours worked for " + employees[i].getEmpName() + ": ");
                    employees[i].displayPay(Math.round(employeeHours));
                } else if (employees[i] instanceof SalariedEmployee) {
                    employees[i].displayPay(40);
                }
            }
        }
        System.out.println();
    }

    public static void raiseWages() {
        // grab percentage and raise wages for all employees. This means that the hourly
        // pay for
        // each employee will be raised by the inputted percentage
        System.out.println();
        System.out.println("Raise wages: not implemented");
        System.out.println();
    }

    public static void listEmployees() {
        // display information for all employees
        System.out.println("");
        if (Employee.empCount < 1) {
            System.out.println("There are no employees stored");
        } else {
            System.out.printf("%-15s %s\n", "Name", "Hourly Wages");
            System.out.println("----------------------------");
            for (int i = 0; i < Employee.empCount; i++) {
                employees[i].displayInfo();
            }
        }
        System.out.println();
    }

    public static void main(String args[]) {
        select();
    }
}