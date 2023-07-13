package ru.javaops.basejava.model;

import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private Map<ContactType, String> contacts;
    private Map<SectionType, Section> sections;

    public Resume(String uuid, String fullName, Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.sections = sections;
        this.contacts = contacts;
    }

    public Resume(String fullName, Map<ContactType, String> contacts, Map<SectionType, Section> sections) {
        this(UUID.randomUUID().toString(), fullName, contacts, sections);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid + ": " + fullName;
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
