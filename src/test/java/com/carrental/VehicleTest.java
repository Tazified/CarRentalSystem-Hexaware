package com.carrental;

import com.carrental.dao.ICarLeaseRepository;
import com.carrental.dao.ICarLeaseRepositoryImpl;
import com.carrental.entity.Vehicle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testAddVehicle() {
        ICarLeaseRepository repo = new ICarLeaseRepositoryImpl();

        Vehicle vehicle = new Vehicle(0, "Honda", "City", 2023, 950.0, "available", 5, 1.5);


        assertDoesNotThrow(() -> repo.addVehicle(vehicle));
    }
}
