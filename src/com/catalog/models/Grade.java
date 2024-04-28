package com.catalog.models;
import java.time.LocalDate;
public class Grade {
    private Integer value;
    private LocalDate date;

    public Grade(Integer value)
    {
        this.value = value;
        this.date = LocalDate.now();
    }

    public Grade(Integer value, LocalDate date)
    {
        this.value = value;
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setValue(Integer value) {
        this.value = value;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return "{"
                + value +
                ", " + date +
                '}';
    }
}
