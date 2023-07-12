package ru.javaops.basejava.model;

import java.net.URL;

public class Link {
    private String title;
    private URL url;

    public Link(String title, URL url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
