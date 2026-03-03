package edu.escuelaing.framework;

@FunctionalInterface
public interface Route {
    String handle(Request req, Response res);
}
