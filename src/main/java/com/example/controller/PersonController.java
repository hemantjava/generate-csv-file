package com.example.controller;

import com.example.dto.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class PersonController {




    @GetMapping("/csv")
    public ResponseEntity<Byte[]> getCSV() throws IOException {

        ClassLoader classLoader = PersonController.class.getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(classLoader.getResource("people.json").getFile());
        List<Person> personList = objectMapper.readValue(file, new TypeReference<>() {
        });
        byte[] csv = convertToCSV(personList).getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.add("Content-Disposition", "attachment; filename=users.csv");
        return new ResponseEntity(csv, headers, HttpStatus.OK);
    }

    public String convertToCSV(List<Person> data) {
        StringBuilder csvBuilder = new StringBuilder();
        // Write the headers
        csvBuilder.append("Id,firstName,LastName,Email,Gender,age\n");
        // Write the data
        for (Person entity : data) {
            csvBuilder.append(entity.getId()).append(",")
                    .append(entity.getFirstName()).append(",")
                    .append(entity.getLastName()).append(",")
                    .append(entity.getEmail()).append(",")
                    .append(entity.getGender()).append(",")
                    .append(entity.getAge()).append("\n");
        }
        return csvBuilder.toString();
    }


}


