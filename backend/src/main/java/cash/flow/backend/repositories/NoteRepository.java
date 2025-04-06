package cash.flow.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cash.flow.backend.models.Note;
import cash.flow.backend.utils.Helper;

@Repository
public class NoteRepository {
    @Autowired
    private DataSource dataSource;

    public boolean createNote(String username) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO note (n_id, user_fk) VALUES (?, ?);");

            statement.setString(1, Helper.getStringUUID());
            statement.setString(2, username);

            System.out.println("Excecute Statement: " + statement.toString());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public Note getNoteByUsername(String username) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT n_id, user_fk FROM note WHERE user_fk = ?;");

            statement.setString(1, username);

            System.out.println("Excecute Statement: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Note note = new Note();
                note.setNId(Helper.convertUUID(resultSet.getString("n_id")));
                note.setUserFk(resultSet.getString("user_fk"));
                return note;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
        return null;
    }

    public void deleteNote(UUID nId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM note WHERE n_id = ?;");
            statement.setString(1, nId.toString());

            System.out.println("Excecute Statement: " + statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }
}
