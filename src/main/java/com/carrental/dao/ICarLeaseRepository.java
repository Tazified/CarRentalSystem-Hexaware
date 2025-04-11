package com.carrental.dao;

import java.sql.Date;
import java.util.List;

import com.carrental.entity.Customer;
import com.carrental.entity.Lease;
import com.carrental.entity.Payment;
import com.carrental.entity.Vehicle;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CustomerNotFoundException;
import com.carrental.exception.LeaseNotFoundException;

public interface ICarLeaseRepository {

    // Vehicle Management
    void addVehicle(Vehicle vehicle);
    void removeCar(int carID);
    List<Vehicle> listAvailableCars();
    List<Vehicle> listRentedCars();
    Vehicle findCarById(int carID) throws CarNotFoundException;
    void updateCarStatus(int vehicleID, String status);

    // Customer Management
    void addCustomer(Customer customer);
    void removeCustomer(int customerID);
    List<Customer> listCustomers();
    Customer findCustomerById(int customerID) throws CustomerNotFoundException;
    void updateCustomer(Customer customer) throws CustomerNotFoundException;

    // Lease Management
    Lease createLease(int customerID, int carID, Date startDate, Date endDate, String type);
    Lease returnCar(int leaseID);
    List<Lease> listActiveLeases();
    List<Lease> listLeaseHistory();

    // Payment Handling
    void processPayment(int leaseID, double amount) throws LeaseNotFoundException;
    List<Payment> listPayments();
}
