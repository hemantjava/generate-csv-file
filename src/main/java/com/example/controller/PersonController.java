package com.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {
    private static final String CSV_FILE_PATH = "./file.csv";

    @GetMapping("/generate-csv-from-json")
    public void generateCSVFromJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = PersonController.class.getClassLoader();
        List<Map<String, Object>> jsonData = mapper.readValue(new File(classLoader.getResource("people.json").getFile()), new TypeReference<List<Map<String, Object>>>() {});

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord(jsonData.get(0).keySet());
            for (Map<String, Object> data : jsonData) {
                csvPrinter.printRecord(data.values());
            }
            csvPrinter.flush();
        }
    }
}

