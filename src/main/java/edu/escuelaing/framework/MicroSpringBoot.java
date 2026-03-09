package edu.escuelaing.framework;

import edu.escuelaing.framework.annotations.GetMapping;
import edu.escuelaing.framework.annotations.RequestParam;
import edu.escuelaing.framework.annotations.RestController;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * IoC framework that scans the classpath for @RestController classes
 * and registers their @GetMapping methods as HTTP routes.
 */
public class MicroSpringBoot {

    /**
     * Scans all classpath directories, finds classes annotated with
     * @RestController, and registers their routes in WebFramework.
     */
    public static void run() throws Exception {
        String classpath = System.getProperty("java.class.path");
        String[] entries = classpath.split(File.pathSeparator);

        for (String entry : entries) {
            File file = new File(entry);
            if (file.isDirectory()) {
                scanDirectory(file, file);
            }
        }
    }

    private static void scanDirectory(File root, File dir) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(root, file);
            } else if (file.getName().endsWith(".class")) {
                String relativePath = root.toURI().relativize(file.toURI()).getPath();
                String className = relativePath
                        .replace("/", ".")
                        .replace("\\", ".")
                        .replace(".class", "");
                processClass(className);
            }
        }
    }

    private static void processClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);

            if (!clazz.isAnnotationPresent(RestController.class)) return;

            System.out.println("[MicroSpringBoot] Loading controller: " + className);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(GetMapping.class)) continue;

                String path = method.getAnnotation(GetMapping.class).value();

                WebFramework.get(path, (req, res) -> {
                    try {
                        Parameter[] parameters = method.getParameters();
                        Object[] args = new Object[parameters.length];

                        for (int i = 0; i < parameters.length; i++) {
                            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                                RequestParam rp = parameters[i].getAnnotation(RequestParam.class);
                                String value = req.getValues(rp.value());
                                args[i] = (value != null) ? value : rp.defaultValue();
                            } else {
                                args[i] = null;
                            }
                        }

                        return (String) method.invoke(instance, args);
                    } catch (Exception e) {
                        return "Internal Server Error: " + e.getMessage();
                    }
                });

                System.out.println("[MicroSpringBoot] Registered GET " + path
                        + " -> " + clazz.getSimpleName() + "." + method.getName() + "()");
            }

        } catch (ClassNotFoundException | NoClassDefFoundError ignored) {
            // Skip non-user classes that can't be loaded
        } catch (Exception e) {
            System.err.println("[MicroSpringBoot] Failed to load: " + className + " - " + e.getMessage());
        }
    }
}
