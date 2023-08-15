package ru.javaops.basejava.util;

import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.StorageException;

import java.sql.SQLException;

public class SqlExceptionUtil {
    public static StorageException exception(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);
    }
}
