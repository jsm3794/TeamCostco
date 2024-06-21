package main.java.com.teamcostco.model.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private static LogManager instance = new LogManager();

    // private 생성자: 외부에서 인스턴스를 생성하지 못하게 함
    private LogManager() {
    }

    // LogManager 인스턴스를 반환하는 메서드
    public static LogManager getInstance() {
        return instance;
    }

    // 로그를 콘솔에 출력하는 메서드
    public void log(Object message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }
}
