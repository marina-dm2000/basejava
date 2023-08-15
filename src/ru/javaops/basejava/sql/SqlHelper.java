package ru.javaops.basejava.sql;

import ru.javaops.basejava.util.SqlExceptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T getPreparedStatement(String query, Executer<T> executer) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executer.execute(ps);
        } catch (SQLException e) {
            throw SqlExceptionUtil.exception(e);
        }
    }
}
