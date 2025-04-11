package com.carrental;

import com.carrental.dao.ICarLeaseRepository;
import com.carrental.dao.ICarLeaseRepositoryImpl;
import com.carrental.entity.*;
import com.carrental.exception.*;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ICarLeaseRepository repo = new ICarLeaseRepositoryImpl();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Car Rental System ---");
            System.out.println("1. Customer Management");
            System.out.println("2. Car Management");
            System.out.println("3. Lease Management");
            System.out.println("4. Payment Handling");
            System.out.println("5. Exit");
            System.out.print("Choose a category: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("--- Customer Management ---");
                    System.out.println("1. Add Customer");
                    System.out.println("2. Update Customer");
                    System.out.println("3. Find Customer");
                    System.out.println("4. List Customers");
                    System.out.println("5. Remove Customer");
                    int cChoice = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        switch (cChoice) {
                            case 1:
                                System.out.print("First Name: ");
                                String first = scanner.nextLine();
                                System.out.print("Last Name: ");
                                String last = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();
                                System.out.print("Phone: ");
                                String phone = scanner.nextLine();
                                repo.addCustomer(new Customer(first, last, email, phone));
                                System.out.println("Customer added.");
                                break;
                            case 2:
                                System.out.print("Customer ID to update: ");
                                int upId = scanner.nextInt(); scanner.nextLine();
                                System.out.print("First Name: ");
                                first = scanner.nextLine();
                                System.out.print("Last Name: ");
                                last = scanner.nextLine();
                                System.out.print("Email: ");
                                email = scanner.nextLine();
                                System.out.print("Phone: ");
                                phone = scanner.nextLine();
                                repo.updateCustomer(new Customer(upId, first, last, email, phone));
                                System.out.println("Customer updated.");
                                break;
                            case 3:
                                System.out.print("Customer ID: ");
                                int id = scanner.nextInt();
                                Customer found = repo.findCustomerById(id);
                                System.out.println(found);
                                break;
                            case 4:
                                List<Customer> customers = repo.listCustomers();
                                customers.forEach(System.out::println);
                                break;
                            case 5:
                                System.out.print("Customer ID: ");
                                int delId = scanner.nextInt();
                                repo.removeCustomer(delId);
                                System.out.println("Customer removed.");
                                break;
                        }
                    } catch (CustomerNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("--- Car Management ---");
                    System.out.println("1. Add Car");
                    System.out.println("2. Remove Car");
                    System.out.println("3. List Available Cars");
                    System.out.println("4. List Rented Cars");
                    System.out.println("5. Update Car Status");
                    int carChoice = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        switch (carChoice) {
                            case 1:
                                System.out.print("Make: ");
                                String make = scanner.nextLine();
                                System.out.print("Model: ");
                                String model = scanner.nextLine();
                                System.out.print("Year: ");
                                int year = scanner.nextInt();
                                System.out.print("Rate: ");
                                double rate = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.print("Status: ");
                                String status = scanner.nextLine();
                                System.out.print("Passenger Capacity: ");
                                int pc = scanner.nextInt();
                                System.out.print("Engine Capacity: ");
                                double ec = scanner.nextDouble();
                                repo.addVehicle(new Vehicle(0, make, model, year, rate, status, pc, ec));
                                System.out.println("Car added.");
                                break;
                            case 2:
                                System.out.print("Car ID: ");
                                int cid = scanner.nextInt();
                                repo.removeCar(cid);
                                System.out.println("Car removed.");
                                break;
                            case 3:
                                List<Vehicle> available = repo.listAvailableCars();
                                available.forEach(System.out::println);
                                break;
                            case 4:
                                List<Vehicle> rented = repo.listRentedCars();
                                rented.forEach(System.out::println);
                                break;
                            case 5:
                                System.out.print("Car ID: ");
                                int vid = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("New Status: ");
                                String newStatus = scanner.nextLine();
                                repo.updateCarStatus(vid, newStatus);
                                System.out.println("Status updated.");
                                break;
                        }
                    } catch (CarNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("--- Lease Management ---");
                    System.out.println("1. Create Lease");
                    System.out.println("2. Return Car");
                    System.out.println("3. List Active Leases");
                    System.out.println("4. List Lease History");
                    int leaseChoice = scanner.nextInt();

                    switch (leaseChoice) {
                        case 1:
                            System.out.print("Customer ID: ");
                            int custId = scanner.nextInt();
                            System.out.print("Car ID: ");
                            int carId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Start Date (yyyy-mm-dd): ");
                            Date start = Date.valueOf(scanner.nextLine());
                            System.out.print("End Date (yyyy-mm-dd): ");
                            Date end = Date.valueOf(scanner.nextLine());
                            System.out.print("Lease Type (daily/monthly): ");
                            String type = scanner.nextLine();
                            Lease lease = repo.createLease(custId, carId, start, end, type);
                            System.out.println("Lease Created: " + lease);
                            break;
                        case 2:
                            System.out.print("Lease ID: ");
                            int lid = scanner.nextInt();
                            Lease returned = repo.returnCar(lid);
                            System.out.println("Car returned: " + returned);
                            break;
                        case 3:
                            List<Lease> active = repo.listActiveLeases();
                            active.forEach(System.out::println);
                            break;
                        case 4:
                            List<Lease> history = repo.listLeaseHistory();
                            history.forEach(System.out::println);
                            break;
                    }
                    break;

                case 4:
                    System.out.println("--- Payment Handling ---");
                    System.out.println("1. Process Payment");
                    System.out.println("2. List All Payments");
                    int payChoice = scanner.nextInt();

                    switch (payChoice) {
                    case 1:
                        System.out.print("Lease ID: ");
                        int leaseID = scanner.nextInt(); 
                        System.out.print("Amount: ");
                        double amount = scanner.nextDouble(); 
                        scanner.nextLine();
                        try {
                            repo.processPayment(leaseID, amount); 
                            System.out.println("Payment recorded.");
                        } catch (LeaseNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        List<Payment> payments = repo.listPayments();
                        payments.forEach(System.out::println);
                        break;
                }
                    break;

                case 5:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }
        scanner.close();
        System.out.println("Exiting Car Rental System.");
    }
}
