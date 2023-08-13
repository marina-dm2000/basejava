package ru.javaops.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Executer<T> {
    T execute(PreparedStatement t) throws SQLException;
}
