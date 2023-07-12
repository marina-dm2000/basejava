package ru.javaops.basejava.model;

import java.util.List;

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
}
