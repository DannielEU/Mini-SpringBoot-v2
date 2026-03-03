package edu.escuelaing.framework;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor iniciado en puerto 8080...");

        while (true) {

            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            String requestLine = in.readLine();
            if (requestLine == null) continue;

            String[] requestParts = requestLine.split(" ");
            String pathWithParams = requestParts[1];

            String path = pathWithParams;
            String query = null;

            if (pathWithParams.contains("?")) {
                path = pathWithParams.split("\\?")[0];
                query = pathWithParams.split("\\?")[1];
            }

            Route route = WebFramework.getRoute(path);

            if (route != null) {

                Request req = new Request(query);
                Response res = new Response();

                String responseBody = route.handle(req, res);
                sendResponse(clientSocket, responseBody);

            } else {

                serveStaticFile(clientSocket, path);
            }

            out.close();
            in.close();
            clientSocket.close();
        }
    }

    private static void sendResponse(Socket clientSocket, String body) throws IOException {

        OutputStream out = clientSocket.getOutputStream();
        byte[] bodyBytes = body.getBytes();

        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type: text/plain\r\n".getBytes());
        out.write(("Content-Length: " + bodyBytes.length + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(bodyBytes);
        out.flush();
    }

    private static void serveStaticFile(Socket clientSocket, String path) throws IOException {

        if (path.equals("/")) path = "/index.html";

        String fullPath = "../main/resources" +
                WebFramework.getStaticFolder() + path;

        File file = new File(fullPath);

        OutputStream out = clientSocket.getOutputStream();

        if (file.exists()) {

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String contentType = getContentType(path);

            out.write(("HTTP/1.1 200 OK\r\n").getBytes());
            out.write(("Content-Type: " + contentType + "\r\n").getBytes());
            out.write(("Content-Length: " + fileBytes.length + "\r\n").getBytes());
            out.write(("\r\n").getBytes());
            out.write(fileBytes);
        } else {
            out.write(("HTTP/1.1 404 Not Found\r\n\r\n").getBytes());
        }

        out.flush();
    }
    private static String getContentType(String path) {

        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";

        return "text/plain";
    }
}