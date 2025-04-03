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

import cash.flow.backend.models.Category;
import cash.flow.backend.utils.Helper;

@Repository
public class CategoryRepository {
    @Autowired
    private DataSource dataSource;

    public List<Category> getCategoriesByNote(UUID nId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT c_id, c_name, c_color, note_fk FROM category WHERE note_fk = ?");

            statement.setString(1, Helper.getStringUUID(nId));

            ResultSet resultSet = statement.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCId(Helper.convertUUID(resultSet.getString("c_id")));
                category.setCName(resultSet.getString("c_name"));
                category.setColor(resultSet.getString("c_color"));
                category.setNoteFk(Helper.convertUUID(resultSet.getString("note_fk")));
                categories.add(category);
            }

            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean createCategory(Category category) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO category (c_id, c_name, c_color, note_fk) VALUES (?, ?, ?, ?);");

            statement.setString(1, Helper.getStringUUID());
            statement.setString(2, category.getCName());
            statement.setString(3, category.getColor());
            statement.setString(4, Helper.getStringUUID(category.getNoteFk()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean updateCategory(Category category) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE category SET c_name = ?, c_color = ? WHERE c_id = ?;");

            statement.setString(1, category.getCName());
            statement.setString(2, category.getColor());
            statement.setString(3, Helper.getStringUUID(category.getCId()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public boolean deleteCategory(UUID cId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM category WHERE c_id = ?");

            statement.setString(1, Helper.getStringUUID(cId));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }
}
