package com.example.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;



public class CalendarCore {

    private LocalDate selectedDate;
    private YearMonth selectedMonth;

    private DBLoader dbLoader = new DBLoader();

    public DBLoader getDbLoader() {
        return dbLoader;
    }

    public void setSelectedMonth(ComboBox<Year> yearComboBox, ComboBox<Month> monthComboBox) {
        selectedMonth = YearMonth.of(yearComboBox.getValue().getValue(), monthComboBox.getValue());
    }

    public void addNoteToMap(String note) {
        dbLoader.addNoteToData(selectedDate, note);
    }

    public void deleteNoteFromMap() throws IOException {
        dbLoader.deleteNoteFromData(selectedDate);
    }

    public void setYearsComboBox(ComboBox<Year> yearsComboBox) {
        ObservableList<Year> yearsList = FXCollections.observableArrayList();

        for (int year = 2003; year <= 2025; ++year) {
            yearsList.add(Year.of(year));
        }

        yearsComboBox.setItems(yearsList);
        yearsComboBox.setValue(Year.now());
    }

    public void setMonthComboBox(ComboBox<Month> monthsComboBox) {
        ObservableList<Month> monthList = FXCollections.observableArrayList();

        for (int month = 1; month <= 12; ++month) {
            monthList.add(Month.of(month));
        }

        monthsComboBox.setItems(monthList);
        monthsComboBox.setValue(Month.of(LocalDate.now().getMonthValue()));
    }

    public void initCalenderGridPane(GridPane calendarGrid) {

        calendarGrid.setPadding(new Insets(5));
        calendarGrid.setHgap(15);
        calendarGrid.setVgap(5);

        String[] weekDays = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};

        for (int i = 0; i < weekDays.length; ++i) {
            Label weekDayLabel = new Label(weekDays[i]);
            weekDayLabel.getStyleClass().add("weekDayLabel");
            calendarGrid.add(weekDayLabel, i, 0);
        }

    }

    public void updateGridCalendar(GridPane calendarGrid, Label newsLabel, Label textLabel, Label noteDate, TextArea noteArea) {
        calendarGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 0);
        LocalDate firstDayOfMonth = selectedMonth.atDay(1);
        int firstWeekDay = firstDayOfMonth.getDayOfWeek().getValue() - 1;
        int daysInMonth = selectedMonth.lengthOfMonth();
        int row = 1;
        int col = firstWeekDay;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = selectedMonth.atDay(day);
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.getStyleClass().add("dayLabel");

            VBox dayBox = new VBox(dayLabel);
            dayBox.getStyleClass().add("dayBox");

            if (date.equals(LocalDate.now())) {
                selectedDate = LocalDate.now();
                dayLabel.setId("todayLabel");
                dayLabel.setTextFill(Color.RED);
                dayLabel.setStyle("-fx-font-weight: bold");
                dayBox.setStyle("-fx-background-color: #d6a11a");
                showNoteAndNews(newsLabel, textLabel, noteDate, noteArea);
            }

            dayBox.setOnMouseClicked(e -> {
                selectedDate = date;
                viewSelectedCell(calendarGrid, dayBox);
                showNoteAndNews(newsLabel, textLabel, noteDate, noteArea);
            });

            calendarGrid.add(dayBox, col, row);
            GridPane.setHgrow(dayBox, Priority.ALWAYS);
            GridPane.setVgrow(dayBox, Priority.ALWAYS);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        System.out.println("161561651");
    }

    private void viewSelectedCell(GridPane calendarGrid, VBox dayBox) {
        ObservableList<Node> vBoxList= calendarGrid.getChildren();

        for (Node item: vBoxList) {
            item.setStyle("-fx-background-color: none");
        }
        dayBox.setStyle("-fx-background-color: #d6a11a");
    }

   public void showNoteAndNews(Label newsLabel, Label textLabel, Label noteDate, TextArea noteArea) {
        if (selectedDate.equals(LocalDate.now())) {
            newsLabel.setText("Праздник на сегодня: " + dbLoader.getNewsFromData(selectedDate).getNameOfHoliday());
            noteDate.setText("Заметка на сегодня");
        } else {
            newsLabel.setText("Праздник на " + selectedDate.toString() + " число: " + dbLoader.getNewsFromData(selectedDate).getNameOfHoliday());
            noteDate.setText("Заметка на " + selectedDate.toString()  + " число");
        }

        noteArea.setText(dbLoader.getNoteFromData(selectedDate));
        textLabel.setText(dbLoader.getNewsFromData(selectedDate).getDescriptionOfHoliday());
    }

}
