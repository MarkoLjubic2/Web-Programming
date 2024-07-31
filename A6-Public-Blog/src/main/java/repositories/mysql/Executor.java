package repositories.mysql;

import java.sql.*;

public class Executor extends MySqlRepository {

    public <R> R execute(String query, SQLConsumer<PreparedStatement> parameterSetter, SQLFunction<ResultSet, R> resultSetProcessor, boolean isUpdate) {
        try (Connection connection = newConnection();
            PreparedStatement statement = isUpdate ? connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) : connection.prepareStatement(query)) {
            parameterSetter.accept(statement);
            try (ResultSet resultSet = isUpdate ? executeUpdate(statement) : executeQuery(statement)) {
                return resultSetProcessor.apply(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    private ResultSet executeUpdate(PreparedStatement statement) throws SQLException {
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }
}

@FunctionalInterface
interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}

@FunctionalInterface
interface SQLFunction<T, R> {
    R apply(T t) throws SQLException;
}