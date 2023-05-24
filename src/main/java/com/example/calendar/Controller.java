package com.example.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private CalendarCore core = new CalendarCore();
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label noteDate;
    @FXML
    private TextArea noteArea;
    @FXML
    private ComboBox<Year> yearsComboBox;
    @FXML
    private ComboBox<Month> monthsComboBox;
    @FXML
    private Label newsLabel;
    @FXML
    private Label textLabel;

    public void saveNoteOnClick() {
        core.addNoteToMap(noteArea.getText());
    }

    public void clearNoteOnClick() throws IOException {
        core.deleteNoteFromMap();
        noteArea.clear();
    }

    public void updateCalendarGrid() {
        core.setSelectedMonth(yearsComboBox, monthsComboBox);
        core.updateGridCalendar(calendarGrid, newsLabel, textLabel, noteDate, noteArea);
    }

    public void initialize(URL location, ResourceBundle resources) {
        core.initCalenderGridPane(calendarGrid);
        core.setYearsComboBox(yearsComboBox);
        core.setMonthComboBox(monthsComboBox);
        core.setSelectedMonth(yearsComboBox, monthsComboBox);
        core.updateGridCalendar(calendarGrid, newsLabel, textLabel, noteDate, noteArea);
        core.getDbLoader().initNoteDataFromJackson();
        core.getDbLoader().initNewsFromDataJackson();
        core.showNoteAndNews(newsLabel, textLabel, noteDate, noteArea);
    }
}