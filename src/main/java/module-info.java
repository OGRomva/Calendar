module com.example.calendar {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.testng;


    opens com.example.calendar to javafx.fxml;
    exports com.example.calendar;
}