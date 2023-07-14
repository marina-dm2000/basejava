package ru.javaops.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String item : list) {
            s.append('•').append(item).append('\n');
        }
        return s.toString();
    }
}
