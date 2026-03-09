package edu.escuelaing.framework;

import java.io.*;
import java.net.*;

public class HttpServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor iniciado en puerto 8080...");

        while (true) {

            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String requestLine = in.readLine();
            if (requestLine == null) {
                clientSocket.close();
                continue;
            }

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 2) {
                clientSocket.close();
                continue;
            }

            String pathWithParams = requestParts[1];
            String path = pathWithParams;
            String query = null;

            if (pathWithParams.contains("?")) {
                String[] split = pathWithParams.split("\\?", 2);
                path = split[0];
                query = split[1];
            }

            Route route = WebFramework.getRoute(path);

            if (route != null) {
                Request req = new Request(query);
                Response res = new Response();
                String responseBody = route.handle(req, res);
                sendTextResponse(clientSocket, responseBody);
            } else {
                serveStaticFile(clientSocket, path);
            }

            in.close();
            clientSocket.close();
        }
    }

    private static void sendTextResponse(Socket clientSocket, String body) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        byte[] bodyBytes = body.getBytes("UTF-8");

        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type: text/plain; charset=UTF-8\r\n".getBytes());
        out.write(("Content-Length: " + bodyBytes.length + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(bodyBytes);
        out.flush();
    }

    private static void serveStaticFile(Socket clientSocket, String path) throws IOException {
        if (path.equals("/")) path = "/index.html";

        String resourcePath = WebFramework.getStaticFolder() + path;
        // Remove leading slash for ClassLoader lookup
        String classpathResource = resourcePath.startsWith("/")
                ? resourcePath.substring(1)
                : resourcePath;

        InputStream fileStream = HttpServer.class.getClassLoader().getResourceAsStream(classpathResource);
        OutputStream out = clientSocket.getOutputStream();

        if (fileStream != null) {
            byte[] fileBytes = fileStream.readAllBytes();
            fileStream.close();
            String contentType = getContentType(path);

            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(("Content-Type: " + contentType + "\r\n").getBytes());
            out.write(("Content-Length: " + fileBytes.length + "\r\n").getBytes());
            out.write("\r\n".getBytes());
            out.write(fileBytes);
        } else {
            String body = "404 Not Found: " + path;
            byte[] bodyBytes = body.getBytes();
            out.write("HTTP/1.1 404 Not Found\r\n".getBytes());
            out.write("Content-Type: text/plain\r\n".getBytes());
            out.write(("Content-Length: " + bodyBytes.length + "\r\n").getBytes());
            out.write("\r\n".getBytes());
            out.write(bodyBytes);
        }

        out.flush();
    }

    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css"))  return "text/css";
        if (path.endsWith(".js"))   return "application/javascript";
        if (path.endsWith(".png"))  return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif"))  return "image/gif";
        return "text/plain";
    }
}
