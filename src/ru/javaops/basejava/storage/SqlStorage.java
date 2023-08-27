package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.ListSection;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.Section;
import ru.javaops.basejava.model.SectionType;
import ru.javaops.basejava.model.TextSection;
import ru.javaops.basejava.sql.ConnectionFactory;
import ru.javaops.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.getPreparedStatement("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.getPreparedStatement("SELECT * FROM resume r " +
                        "  LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "  LEFT JOIN section s " +
                        "    ON r.uuid = s.resume_uuid " +
                        "WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        saveContact(r, rs);
                        saveSection(r, rs);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.getPreparedStatement("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.getPreparedStatement("SELECT * FROM resume r ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                getAllContacts(resume);
                getAllSections(resume);
                resumes.add(resume);
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.getPreparedStatement("SELECT COUNT(*) AS count FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("count") : 0;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(conn, resume);
            insertContact(conn, resume);

            deleteSection(conn, resume);
            insertSection(conn, resume);
            return null;
        });
    }

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private void saveContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(
                    ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void getAllContacts(Resume resume) {
        sqlHelper.getPreparedStatement("SELECT * FROM contact c WHERE c.resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                saveContact(resume, rs);
            }
            return null;
        });
    }

    private void insertSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type_section, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void saveSection(Resume resume, ResultSet rs) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type_section"));
            Section section;
            switch (type) {
                case OBJECTIVE, PERSONAL -> {
                    section = new TextSection(content.trim());
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    section = new ListSection(List.of(content.split("\n")));
                }
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
            resume.addSection(type, section);
        }
    }

    private void deleteSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private void getAllSections(Resume resume) {
        sqlHelper.getPreparedStatement("SELECT * FROM section s WHERE s.resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                saveSection(resume, rs);
            }
            return null;
        });
    }
}
