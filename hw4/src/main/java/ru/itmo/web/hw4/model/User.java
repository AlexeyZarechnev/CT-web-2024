package ru.itmo.web.hw4.model;

public class User {

    public static enum Color {
        RED, GREEN, BLUE
    }

    private final long id;
    private final String handle;
    private final String name;
    private final long postsCount;
    private final Color color;

    public User(long id, String handle, String name, long postsCount, Color color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.postsCount = postsCount;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public long getPostsCount() {
        return postsCount;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }
}
