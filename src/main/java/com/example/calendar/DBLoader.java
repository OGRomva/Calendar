package com.example.calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.HashMap;
import java.util.Map;

public class DBLoader {
    private Map<String, News> newsData = new HashMap<>();

    private Map<String, String> noteData = new HashMap<>();

    private File noteDataDBFile = new File("noteData.json");

    public News getNewsFromData(LocalDate data) {
        MonthDay monthDay = MonthDay.of(data.getMonth(), data.getDayOfMonth());
        if (newsData.get(monthDay.toString()) != null) {
            System.out.println("getNewsFromData selected date: " + monthDay.toString());
            System.out.println("getNewsFromData newsData: " + newsData.get(monthDay.toString()).getNameOfHoliday());
            return newsData.get(monthDay.toString());
        }

        return new News("placeholder1", "placeholder2");
    }

    public String getNoteFromData(LocalDate data) {
        System.out.println(noteData);
        System.out.println(noteData.get(data.toString()));
        return noteData.get(data.toString());
    }

    public void addNoteToData(LocalDate data, String note) {
        noteData.put(data.toString(), note);
        updateNoteDate();
    }

    public void deleteNoteFromData(LocalDate data) {
        noteData.remove(data.toString());
        if (noteData.isEmpty()) {
            noteDataDBFile.delete();
            try {
                noteDataDBFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        updateNoteDate();
    }

    public void updateNoteDate() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(noteData);
            objectMapper.writeValue(noteDataDBFile, noteData);
            System.out.println("updateNoteData json: " + json);
            System.out.println("updateNoteData hashMap: " + noteData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initNoteDataFromJackson() {
        if (noteDataDBFile.length() != 0) {
            try {
                noteData = new ObjectMapper().readValue(noteDataDBFile, HashMap.class);
                System.out.println("initNoteData" + noteData);
                updateNoteDate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initNewsFromDataJackson() {
        newsData.put("--01-01", new News("Happy new year", "i don't think that it need's for description, but i must to fill a empty space on the label"));
        System.out.println("getNewsFromData newsData: " + newsData.get("--01-01").getNameOfHoliday() + " description + " + newsData.get("--01-01").getDescriptionOfHoliday() + " " + "--01-01");
    }


}
