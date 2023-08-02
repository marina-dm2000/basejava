package ru.javaops.basejava.storage.serialization;

import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.Link;
import ru.javaops.basejava.model.ListSection;
import ru.javaops.basejava.model.Organization;
import ru.javaops.basejava.model.OrganizationSection;
import ru.javaops.basejava.model.Period;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.Section;
import ru.javaops.basejava.model.SectionType;
import ru.javaops.basejava.model.TextSection;
import ru.javaops.basejava.util.DateUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            Map<SectionType, Section> sections = resume.getSections();


            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();

                switch (section) {
                    case TextSection textSection -> dos.writeUTF(textSection.getText());
                    case ListSection listSection -> {
                        List<String> list = listSection.getList();
                        dos.writeInt(list.size());
                        for (String elem : list) {
                            dos.writeUTF(elem);
                        }
                    }
                    case OrganizationSection organizationSection -> {
                        List<Organization> organizations = organizationSection.getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            dos.writeUTF(organization.getWebsite().getTitle());
                            dos.writeUTF(organization.getWebsite().getUrl().toString());
                            List<Period> periods = organization.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                writeDate(dos, period.getStartPeriod());
                                writeDate(dos, period.getEndPeriod());
                                dos.writeUTF(period.getTitle());
                                if (period.getDescription() != null) {
                                    dos.writeInt(1);
                                    dos.writeUTF(period.getDescription());
                                } else {
                                    dos.writeInt(0);
                                }
                            }
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + section);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int sizeContact = dis.readInt();
            for (int i = 0; i < sizeContact; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = new ArrayList<>();
                        int sizeList = dis.readInt();
                        for (int j = 0; j < sizeList; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = new ArrayList<>();
                        int sizeOrganisation = dis.readInt();
                        for (int j = 0; j < sizeOrganisation; j++) {
                            Link link = new Link(dis.readUTF(), new URL(dis.readUTF()));
                            List<Period> periods = new ArrayList<>();
                            int sizePeriod = dis.readInt();
                            for (int k = 0; k < sizePeriod; k++) {
                                periods.add(new Period(readDate(dis), readDate(dis),
                                        dis.readUTF(), dis.readInt() == 1 ? dis.readUTF() : null));
                            }
                            organizations.add(new Organization(link, periods));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + sectionType);
                }
            }

            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeUTF(String.valueOf(date.getMonth()));
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.valueOf(dis.readUTF()));
    }
}
