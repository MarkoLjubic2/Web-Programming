package repositories.mysql;

import model.User;
import repositories.abstraction.UserRepository;

public class MySqlUserRepository implements UserRepository {
    private final Executor executor = new Executor();

    @Override
    public void addUser(User user) {
        String query = "INSERT INTO users (username, password) VALUES(?, ?)";
        executor.execute(query, statement -> {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
        }, resultSet -> {
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            return null;
        }, true);
    }

    @Override
    public User findUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        return executor.execute(query, statement -> statement.setString(1, username), resultSet -> {
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
            return null;
        }, false);
    }
}