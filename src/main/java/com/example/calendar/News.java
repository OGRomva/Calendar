package com.example.calendar;

public class News {
    private String nameOfHoliday;
    private String descriptionOfHoliday;

    public News(String nameOfHoliday, String descriptionOfHoliday) {
        this.nameOfHoliday = nameOfHoliday;
        this.descriptionOfHoliday = descriptionOfHoliday;
    }

    public String getNameOfHoliday() {
        System.out.println("name of holiday " + nameOfHoliday);
        return nameOfHoliday;
    }

    public String getDescriptionOfHoliday() {
        return descriptionOfHoliday;
    }
}
