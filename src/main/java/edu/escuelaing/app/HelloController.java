package edu.escuelaing.app;

import edu.escuelaing.framework.annotations.GetMapping;
import edu.escuelaing.framework.annotations.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Mini-Spring!";
    }

    @GetMapping("/pi")
    public String pi() {
        return "Pi value is: " + Math.PI;
    }
}
