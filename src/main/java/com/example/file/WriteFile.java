package com.example.file;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;

@Service
public class WriteFile {

    public void write() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("writeEx.txt", true))) {
            for (int i = 1; i < 10; i++) {
                bw.write(String.format("write : %d", i));
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
