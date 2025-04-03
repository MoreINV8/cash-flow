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

import cash.flow.backend.models.Day;
import cash.flow.backend.utils.Helper;

@Repository
public class DayRepository {
    @Autowired
    private DataSource dataSource;

    public List<Day> getDaysByMonth(UUID monthId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "SELECT d.d_id, d.detail, d.transaction_value, d.note, d.month_fk, c.c_id, c.c_name, c.c_color FROM days AS d INNER JOIN category AS c ON d.category_fk=c.c_id WHERE month_fk = ?;");

            statement.setString(1, Helper.getStringUUID(monthId));

            ResultSet resultSet = statement.executeQuery();

            List<Day> days = new ArrayList<>();
            while (resultSet.next()) {
                Day day = new Day();
                day.setDId(Helper.convertUUID(resultSet.getString("d_id")));
                day.setDetail(resultSet.getString("detail"));
                day.setTransactionValue(resultSet.getInt("transaction_value"));
                day.setNote(resultSet.getString("note"));
                day.setMonthFk(Helper.convertUUID(resultSet.getString("month_fk")));
                day.setCategoryFk(Helper.convertUUID(resultSet.getString("c_id")));

                day.setCategoryColor(resultSet.getString("c_color"));
                day.setCategoryName(resultSet.getString("c_name"));

                days.add(day);
            }

            return days;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean createDay(Day day) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "INSERT INTO days (d_id, detail, transaction_value, note, month_fk, category_fk) VALUES (?, ?, ?, ?, ?, ?);");

            statement.setString(1, Helper.getStringUUID());
            statement.setString(2, day.getDetail());
            statement.setInt(3, day.getTransactionValue());
            statement.setString(4, day.getNote());
            statement.setString(5, Helper.getStringUUID(day.getMonthFk()));
            statement.setString(6, Helper.getStringUUID(day.getCategoryFk()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean updateDay(Day day) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(
                            "UPDATE days SET detail = ?, transaction_value = ?, note = ?, category_fk = ? WHERE d_id = ?;");

            statement.setString(1, day.getDetail());
            statement.setInt(2, day.getTransactionValue());
            statement.setString(3, day.getNote());
            statement.setString(4, Helper.getStringUUID(day.getCategoryFk()));
            statement.setString(5, Helper.getStringUUID(day.getDId()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean deleteDay(UUID dayId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM days WHERE d_id = ?;");

            statement.setString(1, Helper.getStringUUID(dayId));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }
}
