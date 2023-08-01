package ru.javaops.basejava.storage.serialization;

import ru.javaops.basejava.model.Link;
import ru.javaops.basejava.model.ListSection;
import ru.javaops.basejava.model.Organization;
import ru.javaops.basejava.model.OrganizationSection;
import ru.javaops.basejava.model.Period;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.TextSection;
import ru.javaops.basejava.util.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializationStrategy {
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class, Period.class,
                OrganizationSection.class, TextSection.class, ListSection.class);
    }


    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
