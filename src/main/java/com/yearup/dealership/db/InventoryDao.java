package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {String sql = "INSERT INTO inventory (vin, dealership_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vin);
            stmt.setInt(2, dealershipId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // TODO: Implement the logic to add a vehicle to the inventory


    public void removeVehicleFromInventory(String vin) {
        // TODO: Implement the logic to remove a vehicle from the inventory
    }
}
