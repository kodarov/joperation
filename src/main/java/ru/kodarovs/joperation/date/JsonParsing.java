package ru.kodarovs.joperation.date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;

public class JsonParsing {
    public static void main(String[] args) {
        String jsonString = "{\"date\": \"2022-01-27\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            DateEntity dateEntity = objectMapper.readValue(jsonString, DateEntity.class);
            LocalDate parsedDate = dateEntity.getDate();
            System.out.println("Date: " + parsedDate);
        } catch (IOException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }
}
