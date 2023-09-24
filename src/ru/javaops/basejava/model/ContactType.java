package ru.javaops.basejava.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }
}
