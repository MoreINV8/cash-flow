package cash.flow.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cash.flow.backend.models.Month;
import cash.flow.backend.utils.Helper;

@Repository
public class MonthRepository {
    @Autowired
    private DataSource dataSource;

    public List<Month> getMonthByNId(UUID nId, int limit) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "SELECT m_id, years, months, note_fk FROM months WHERE note_fk = ? ORDER BY years DESC, months DESC LIMIT ?;");

            statement.setString(1, Helper.getStringUUID(nId));
            statement.setInt(2, limit);

            System.out.println("Excecute Statement: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            List<Month> months = new ArrayList<>();

            while (resultSet.next()) {
                Month monthObj = new Month();
                monthObj.setMId(Helper.convertUUID(resultSet.getString("m_id")));
                monthObj.setYear(resultSet.getInt("years"));
                monthObj.setMonth(resultSet.getInt("months"));
                monthObj.setNoteFk(Helper.convertUUID(resultSet.getString("note_fk")));
                months.add(monthObj);
            }

            return months;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public Month getMonthByNIdYearMonth(UUID nId, int year, int month) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "SELECT m_id, years, months, note_fk FROM months WHERE note_fk = ? AND years = ? AND months = ?;");

            statement.setString(1, Helper.getStringUUID(nId));
            statement.setInt(2, year);
            statement.setInt(3, month);

            System.out.println("Excecute Statement: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Month monthObj = new Month();
                monthObj.setMId(Helper.convertUUID(resultSet.getString("m_id")));
                monthObj.setYear(resultSet.getInt("years"));
                monthObj.setMonth(resultSet.getInt("months"));
                monthObj.setNoteFk(Helper.convertUUID(resultSet.getString("note_fk")));
                return monthObj;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }

        return null;
    }

    public boolean createMonth(Month month) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO months (m_id, years, months, note_fk) VALUES (?, ?, ?, ?);");

            statement.setString(1, Helper.getStringUUID());
            statement.setInt(2, month.getYear());
            statement.setInt(3, month.getMonth());
            statement.setString(4, Helper.getStringUUID(month.getNoteFk()));

            System.out.println("Excecute Statement: " + statement.toString());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }
    
}
