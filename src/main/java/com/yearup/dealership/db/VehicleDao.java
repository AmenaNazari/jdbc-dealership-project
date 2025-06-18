package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (vin, make, model, year, sold, color, vehicleType, odometer, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vehicle.getVin());
            statement.setString(2, vehicle.getMake());
            statement.setString(3, vehicle.getModel());
            statement.setInt(4, vehicle.getYear());
            statement.setBoolean(5, vehicle.isSold());
            statement.setString(6, vehicle.getColor());
            statement.setString(7, vehicle.getVehicleType());
            statement.setInt(8, vehicle.getOdometer());
            statement.setDouble(9, vehicle.getPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO: Implement the logic to add a vehicle


    public void removeVehicle(String VIN) {
        String sql = "delete from vehicle where vin =?";


        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,VIN);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     // TODO: Implement the logic to remove a vehicle


    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        return executeVehicleQuery(sql, minPrice, maxPrice);
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        String sql = "SELECT * FROM vehicles WHERE make = ? AND model = ?";
        return executeVehicleQuery(sql, make, model);
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        String sql = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";
        return executeVehicleQuery(sql, minYear, maxYear);
    }

    public List<Vehicle> searchByColor(String color) {
        String sql = "SELECT * FROM vehicles WHERE color = ?";
        return executeVehicleQuery(sql, color);
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        String sql = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        return executeVehicleQuery(sql, minMileage, maxMileage);
    }

    public List<Vehicle> searchByType(String type) {
        String sql = "SELECT * FROM vehicles WHERE vehicleType = ?";
        return executeVehicleQuery(sql, type);
    }
    private List<Vehicle> executeVehicleQuery(String sql, Object... params) {
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vehicles.add(createVehicleFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
