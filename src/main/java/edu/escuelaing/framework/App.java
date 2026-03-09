package edu.escuelaing.framework;

/**
 * Entry point. Starts the IoC scanner and the HTTP server.
 *
 * Run with:
 *   java -cp target/classes edu.escuelaing.framework.App
 */
public class App {

    public static void main(String[] args) throws Exception {

        // Serve static files from the "webroot" folder in the classpath
        WebFramework.staticfiles("webroot");

        // Scan classpath for @RestController classes and register their routes
        MicroSpringBoot.run();

        // Start listening
        HttpServer.main(args);
    }
}
