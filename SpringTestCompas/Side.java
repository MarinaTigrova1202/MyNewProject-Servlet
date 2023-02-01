package ru.appline;

public enum Side {
    South("South"),
    Southwest("Southwest"),
    Southeast("Southeast"),
    North("North"),
    Northwest("Northwest"),
    Northeast("Northeast"),
    West("West"),
    East("East");

    private String name;

    Side(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
