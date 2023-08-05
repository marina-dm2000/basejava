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
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeCollection(resume.getContacts().entrySet(), dos, (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(resume.getSections().entrySet(), dos, (entry) -> {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();

                switch (section) {
                    case TextSection textSection -> dos.writeUTF(textSection.getText());
                    case ListSection listSection -> writeCollection(listSection.getList(), dos, dos::writeUTF);
                    case OrganizationSection organizationSection ->
                            writeCollection(organizationSection.getOrganizations(), dos, organization -> {
                                dos.writeUTF(organization.getWebsite().getTitle());
                                dos.writeUTF(organization.getWebsite().getUrl().toString());

                                writeCollection(organization.getPeriods(), dos, period -> {
                                    writeDate(dos, period.getStartPeriod());
                                    writeDate(dos, period.getEndPeriod());
                                    dos.writeUTF(period.getTitle());
                                    if (period.getDescription() != null) {
                                        dos.writeInt(1);
                                        dos.writeUTF(period.getDescription());
                                    } else {
                                        dos.writeInt(0);
                                    }
                                });
                            });
                    default -> throw new IllegalStateException("Unexpected value: " + section);
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readMap(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readMap(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, getSection(dis, sectionType));
            });

            return resume;
        }
    }

    private Section getSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE, PERSONAL -> {
                return new TextSection(dis.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(dis, dis::readUTF));
            }
            case EXPERIENCE, EDUCATION -> {
                return new OrganizationSection(readList(dis, () -> {
                    Link link = new Link(dis.readUTF(), new URL(dis.readUTF()));
                    return new Organization(link, readList(dis,
                            () -> new Period(readDate(dis), readDate(dis),
                                    dis.readUTF(), dis.readInt() == 1 ? dis.readUTF() : null)));
                }));
            }
            default -> throw new IllegalStateException("Unexpected value: " + sectionType);
        }
    }

    @FunctionalInterface
    public interface CollectionFiller<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    public interface ReadList<T> {
        T reading() throws IOException;
    }

    @FunctionalInterface
    public interface ReadMap {
        void reading() throws IOException;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos,
                                     CollectionFiller<T> collectionFiller) throws IOException {
        dos.writeInt(collection.size());
        for (T collect : collection) {
            collectionFiller.accept(collect);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ReadList<T> readList) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(readList.reading());
        }

        return list;
    }

    private void readMap(DataInputStream dis, ReadMap readMap) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readMap.reading();
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
