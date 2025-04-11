package com.carrental.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carrental.entity.Customer;
import com.carrental.entity.Lease;
import com.carrental.entity.Payment;
import com.carrental.entity.Vehicle;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CustomerNotFoundException;
import com.carrental.exception.LeaseNotFoundException;
import com.carrental.util.DBPropertyUtil;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {

    private Connection connection;

    public ICarLeaseRepositoryImpl() {
        try {
            this.connection = DBPropertyUtil.getConnection();
            System.out.println("Connected? " + (this.connection != null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- Vehicle Management ----------------

    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, vehicle.getMake());
            pstmt.setString(2, vehicle.getModel());
            pstmt.setInt(3, vehicle.getYear());
            pstmt.setDouble(4, vehicle.getDailyRate());
            pstmt.setString(5, vehicle.getStatus());
            pstmt.setInt(6, vehicle.getPassengerCapacity());
            pstmt.setDouble(7, vehicle.getEngineCapacity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(int carID) {
        String sql = "DELETE FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, carID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle WHERE status = 'available'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vehicles.add(buildVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle WHERE status = 'notAvailable'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(buildVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Vehicle findCarById(int carID) throws CarNotFoundException {
        String sql = "SELECT * FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, carID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildVehicleFromResultSet(rs);
                } else {
                    throw new CarNotFoundException("Car ID " + carID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new CarNotFoundException("Error finding car: " + e.getMessage());
        }
    }

    @Override
    public void updateCarStatus(int vehicleID, String status) {
        String sql = "UPDATE Vehicle SET status = ? WHERE vehicleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, vehicleID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle buildVehicleFromResultSet(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("vehicleID"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("dailyRate"),
                rs.getString("status"),
                rs.getInt("passengerCapacity"),
                rs.getDouble("engineCapacity")
        );
    }

    // ---------------- Customer Management ----------------

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCustomer(int customerID) {
        String sql = "DELETE FROM Customer WHERE customerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> listCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("customerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customerID"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                } else {
                    throw new CustomerNotFoundException("Customer ID " + customerID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new CustomerNotFoundException("Error: " + e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        String sql = "UPDATE Customer SET firstName = ?, lastName = ?, email = ?, phoneNumber = ? WHERE customerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setInt(5, customer.getCustomerID());

            int updated = pstmt.executeUpdate();
            if (updated == 0) {
                throw new CustomerNotFoundException("Customer ID " + customer.getCustomerID() + " not found for update.");
            }
        } catch (SQLException e) {
            throw new CustomerNotFoundException("Error updating customer: " + e.getMessage());
        }
    }

    // ---------------- Lease Management ----------------

    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate, String type) {
        String sql = "INSERT INTO Lease (vehicleID, customerID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, carID);
            pstmt.setInt(2, customerID);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.setString(5, type);
            pstmt.executeUpdate();
            updateCarStatus(carID, "notAvailable");

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Lease(keys.getInt(1), carID, customerID, startDate, endDate, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Lease returnCar(int leaseID) {
        try {
            Lease lease = findLeaseById(leaseID);
            String query = "DELETE FROM Lease WHERE leaseID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, leaseID);
                pstmt.executeUpdate();
                updateCarStatus(lease.getVehicleID(), "available");
            }
            return lease;
        } catch (LeaseNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Lease findLeaseById(int leaseID) throws LeaseNotFoundException {
        String sql = "SELECT * FROM Lease WHERE leaseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, leaseID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Lease(
                            rs.getInt("leaseID"),
                            rs.getInt("vehicleID"),
                            rs.getInt("customerID"),
                            rs.getDate("startDate"),
                            rs.getDate("endDate"),
                            rs.getString("type")
                    );
                } else {
                    throw new LeaseNotFoundException("Lease ID " + leaseID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new LeaseNotFoundException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> leases = new ArrayList<>();
        String sql = "SELECT * FROM Lease";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                leases.add(new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("vehicleID"),
                        rs.getInt("customerID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leases;
    }

    @Override
    public List<Lease> listLeaseHistory() {
        return listActiveLeases();
    }

    // ---------------- Payment Handling ----------------

    @Override
    public void processPayment(int leaseID, double amount) throws LeaseNotFoundException {
        String checkSql = "SELECT * FROM Lease WHERE leaseID = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, leaseID);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO Payment (leaseID, paymentDate, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, leaseID);
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> listPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Payment(
                        rs.getInt("paymentID"),
                        rs.getInt("leaseID"),
                        rs.getDate("paymentDate"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
