package ru.javaops.basejava.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(fullName + '\n');
        s.append('\n');
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            s.append(entry.getKey().getTitle())
                    .append(": ")
                    .append(entry.getValue())
                    .append('\n');
        }
        s.append('\n');
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            s.append(entry.getKey().getTitle())
                    .append('\n')
                    .append(entry.getValue().toString());
        }

        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return fullName.equals(resume.fullName) && uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        int result = fullName == null ? 0 : fullName.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
