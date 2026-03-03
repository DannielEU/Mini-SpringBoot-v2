package edu.escuelaing.framework;

public class App {

    public static void main(String[] args) throws Exception {

        WebFramework.staticfiles("/webroot");

        WebFramework.get("/App/hello", (req, resp) ->
                "Hello " + req.getValues("name"));

        WebFramework.get("/App/pi", (req, resp) ->
                String.valueOf(Math.PI));

        HttpServer.main(args);
    }
}