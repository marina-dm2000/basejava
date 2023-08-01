package ru.javaops.basejava.model;

import com.google.gson.annotations.JsonAdapter;
import ru.javaops.basejava.util.JsonLocalDateAdapter;
import ru.javaops.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonLocalDateAdapter.class)
    private LocalDate startPeriod;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonLocalDateAdapter.class)
    private LocalDate endPeriod;
    private String title;
    private String description;

    public Period() {
    }

    public Period(LocalDate startPeriod, LocalDate endPeriod, String title, String description) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!Objects.equals(startPeriod, period.startPeriod)) return false;
        if (!Objects.equals(endPeriod, period.endPeriod)) return false;
        if (!Objects.equals(title, period.title)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = startPeriod != null ? startPeriod.hashCode() : 0;
        result = 31 * result + (endPeriod != null ? endPeriod.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startPeriod + " - " + endPeriod +
                "\n" + title +
                '\n' + (description != null ? description + '\n' : "");
    }
}
