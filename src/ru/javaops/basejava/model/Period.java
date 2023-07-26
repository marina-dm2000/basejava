package ru.javaops.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Period implements Serializable {
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private String title;
    private String description;

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
