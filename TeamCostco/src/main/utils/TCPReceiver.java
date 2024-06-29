package main.utils;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class TCPReceiver {
    private static final int PORT = 8080;
    private static TCPReceiver instance;
    private HttpServer server;
    private Consumer<String> messageHandler;

    private TCPReceiver() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/send", new MessageHandler());
            server.setExecutor(Executors.newFixedThreadPool(10));
        } catch (IOException e) {
            System.err.println("서버 생성 오류: " + e.getMessage());
        }
    }

    public static synchronized TCPReceiver getInstance() {
        if (instance == null) {
            instance = new TCPReceiver();
        }
        return instance;
    }

    public void start() {
        server.start();
        System.out.println("HTTP 서버 시작됨 (포트: " + PORT + ")");
    }

    public void stop() {
        server.stop(0);
        System.out.println("HTTP 서버 중지됨");
    }

    public void setMessageHandler(Consumer<String> handler) {
        this.messageHandler = handler;
    }

    class MessageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // CORS 헤더 설정
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                // 메시지 읽기
                String message = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("받은 메시지: " + message);

                // 외부 액션 실행
                if (messageHandler != null) {
                    messageHandler.accept(message);
                }

                // 응답 보내기
                String response = "메시지를 받았습니다: " + message;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
            exchange.close();
        }
    }
}