package ru.javaops.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private Link website;
    private List<Period> periods;

    public Organization(Link website, List<Period> periods) {
        this.website = website;
        this.periods = periods;
    }

    public Link getWebsite() {
        return website;
    }

    public void setWebsite(Link website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        return Objects.equals(website, that.website) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        int result = website != null ? website.hashCode() : 0;
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(website.toString() + "\n");
        for (Period period : periods) {
            s.append(period.toString()).append('\n');
        }

        return s.toString();
    }
}
