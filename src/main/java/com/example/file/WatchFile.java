package com.example.file;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@Service
public class WatchFile {
    private static final int DELAY_MILLIS = 1000;
    private boolean isRun;
    private File file;
    private FileReader fr;
    private BufferedReader br;
    private long fileSize;

    private final WriteFile writeFile;

    public WatchFile(WriteFile writeFile) {
        this.writeFile = writeFile;
    }

    @PostConstruct
    private void start() {
        isRun = true;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(() -> {
            if (initFiles() >= 0) {
                isRun = true;

                while (isRun) {
                    readFiles();
                }
            } else {
                isRun = false;
            }
        }, 0, DELAY_MILLIS, TimeUnit.MILLISECONDS);

        executor.scheduleAtFixedRate(() -> {
            if (isRun) {
                writeFile.write();
            }
        }, 0, DELAY_MILLIS * 10, TimeUnit.MILLISECONDS);
    }

    private int initFiles() {
        try {
            file = new File("writeEx.txt");
            if (!file.exists()) {
                return -1;
            }

            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return 0;
    }

    private synchronized void readFiles() {
        String line;
        checkFiles();

        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                fileSize += line.length();
                // sendKafka();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeFiles() {
        try {
            if (br != null) br.close();
            if (fr != null) fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkFiles() {
        if (file.length() < fileSize) {
            System.out.println("file is changed");
            closeFiles();
            initFiles();
            fileSize = 0;
        }
    }

    private void checkDirectory() {
        //String dir = "writeEx.txt";
        String dir = new File(".").getAbsolutePath();
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(dir);
            path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key = service.take();
                List<WatchEvent<?>> list = key.pollEvents(); // 이벤트를 받을 때 까지 대기
                for (WatchEvent<?> event : list) {
                    Kind<?> kind = event.kind();
                    Path pth = (Path) event.context();
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        // 파일이 생성되었을 때 실행되는 코드
                        System.out.println("생성 : " + pth.getFileName());
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                        // 파일이 삭제되었을 때 실행되는 코드
                        System.out.println("삭제 : " + pth.getFileName());
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                        // 파일이 수정되었을 때 실행되는 코드
                        System.out.println("수정 : " + pth.getFileName());
                    } else if (kind.equals(StandardWatchEventKinds.OVERFLOW)) {
                        // 운영체제에서 이벤트가 소실되었거나 버려질 경우 실행되는 코드
                        System.out.println("OVERFLOW");
                    }
                }
                if (!key.reset()) break; // 키 리셋
            }
            service.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkFile(File file) {
        isRun = true;

        if (!file.exists()) {
            System.out.println("Failed to find a file : " + file.getPath());
        }

        // try 문에서 Stream 을 열면 블럭이 끝났을 때 close 해줌
        try (BufferedReader br
                     = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), StandardCharsets.UTF_8))) {
            while (isRun) {
                // readLine() : 파일의 한 line 을 읽어오는 메서드
                final String line = br.readLine();
                if (line != null) {
                    System.out.println("added new line : " + line);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("stop to tail a file : " + file.getPath());
    }

    /*
    * 스레드 중지 기능
    */
    private void stop() {
        isRun = false;
    }

    @PreDestroy
    private void destroy() {
        stop();
        System.out.println("destroy watchFile");
    }
}
