package com.example.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
@Service
public class ReadFile {
    public List<String> read() {
        ClassPathResource resource = new ClassPathResource("application.properties");
        //List<String> content = new ArrayList<>();
        List<String> content = null;
        try {
            Path path = Paths.get(resource.getURI());
            content = Files.readAllLines(path);
            //content.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return content;
    }

    public String readLine() throws IOException {
        ClassPathResource resource = new ClassPathResource("application.properties");
        File file = resource.getFile();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            br.close();
            fr.close();
        }
        return line;
    }
}
