package ru.javaops.basejava.sql;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.util.SqlExceptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T getPreparedStatement(String query, Executer<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw SqlExceptionUtil.exception(e);
        }
    }

    public <T> void transactionalExecute(SqlTransaction<T> executor) {
        try(Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw SqlExceptionUtil.exception(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
