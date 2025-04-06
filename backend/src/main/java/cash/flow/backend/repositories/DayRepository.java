package cash.flow.backend.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
                            "SELECT d.d_id, d.detail, d.transaction_value, d.note, d.noted_date, d.month_fk, c.c_id, c.c_name, c.c_color FROM days AS d LEFT JOIN category AS c ON d.category_fk=c.c_id WHERE month_fk = ?;");

            statement.setString(1, Helper.getStringUUID(monthId));

            System.out.println("Excecute Statement: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            
            List<Day> days = new ArrayList<>();
            while (resultSet.next()) {
                Day day = new Day();
                day.setDId(Helper.convertUUID(resultSet.getString("d_id")));
                day.setDetail(resultSet.getString("detail"));
                day.setTransactionValue(resultSet.getInt("transaction_value"));
                day.setNote(resultSet.getString("note"));
                day.setDate(resultSet.getDate("noted_date"));
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
                "INSERT INTO days (d_id, detail, transaction_value, note, month_fk, category_fk, noted_date) VALUES (?, ?, ?, ?, ?, ?, ?);");
                
                statement.setString(1, Helper.getStringUUID());
                statement.setString(2, day.getDetail());
                statement.setDouble(3, day.getTransactionValue());
                statement.setString(4, day.getNote());
                statement.setString(5, Helper.getStringUUID(day.getMonthFk()));
                statement.setString(6, Helper.getStringUUID(day.getCategoryFk()));
                statement.setDate(7, Date.valueOf(LocalDate.now()));
                
                System.out.println("Excecute Statement: " + statement.toString());
                return statement.executeUpdate() > 0;
                
            } catch (SQLException e) {
                throw new RuntimeException("Error while connecting to the database", e);
            }
        }
        
        public boolean updateDay(Day day) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection
                .prepareStatement(
                    "UPDATE days SET detail = ?, transaction_value = ?, note = ?, category_fk = ?, noted_date = ? WHERE d_id = ?;");
                    
                    statement.setString(1, day.getDetail());
                    statement.setDouble(2, day.getTransactionValue());
                    statement.setString(3, day.getNote());
                    statement.setString(4, Helper.getStringUUID(day.getCategoryFk()));
                    statement.setDate(5, day.getDate());
                    statement.setString(6, Helper.getStringUUID(day.getDId()));

                    System.out.println("Excecute Statement: " + statement.toString());
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
                    
                    System.out.println("Excecute Statement: " + statement.toString());
                    return statement.executeUpdate() > 0;
                    
                } catch (SQLException e) {
                    throw new RuntimeException("Error while connecting to the database", e);
                }
            }
}
