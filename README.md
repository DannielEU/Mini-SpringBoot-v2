# MiniSpringBoot - Custom Java Web Framework

<div align="center">

**Un framework web ligero y educativo construido desde cero en Java**

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white)
![Version](https://img.shields.io/badge/version-1.0-blue?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)

</div>

---

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Guía de Uso](#guía-de-uso)
- [Componentes del Framework](#componentes-del-framework)
- [Arquitectura](#arquitectura)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Limitaciones y Mejoras](#limitaciones-y-mejoras)
- [Objetivo Educativo](#objetivo-educativo)

---

## Descripción General

**MiniSpringBoot** es un framework web minimalista escrito en Java desde cero, diseñado para comprender cómo funcionan los servidores web y frameworks modernos como Spring Boot por debajo.

El proyecto implementa un **servidor HTTP personalizado** que permite:
- Definir servicios REST usando expresiones lambda
- Manejo automático de parámetros de consulta (query parameters)
- Servir archivos estáticos (HTML, CSS, JavaScript, imágenes)
- Manejo básico de respuestas HTTP (200 OK, 404 Not Found)
- Routing dinámico de requests

### ¿Por qué este proyecto?

Entender cómo funcionan frameworks como Spring Boot es crucial para cualquier desarrollador Java. Este proyecto desglosa los conceptos fundamentales de manera simple y educativa.

---

## Características

### ✅ Principales

| Característica | Descripción |
|---|---|
| **Servidor HTTP** | Servidor TCP personalizado en el puerto 8080 |
| **Routing** | Sistema de rutas basado en HashMap para mapear URLs a funciones |
| **Lambda Expressions** | Uso de expresiones lambda para definir manejadores de rutas |
| **Query Parameters** | Parseo automático de parámetros GET desde la URL |
| **Static Files** | Servicio de archivos estáticos con detección automática de MIME types |
| **HTTP Responses** | Respuestas HTTP correctamente formateadas con headers |
| **Content-Type Detection** | Detección automática de tipos de contenido (HTML, CSS, JS, imágenes, etc.) |

### 🎯 Funcionalidades Específicas

#### 1. Servicios REST con Expresiones Lambda

Define rutas dinámicamente usando lambdas:

```java
WebFramework.get("/App/hello", (req, res) -> "Hello World");
WebFramework.get("/App/pi", (req, res) -> String.valueOf(Math.PI));
```

Las rutas se registran internamente en un `Map<String, Route>`.

#### 2. Extracción de Parámetros de Consulta

Los parámetros GET se parsean automáticamente y son accesibles:

```java
req.getValues("name");  // Obtiene el valor del parámetro "name"
```

**Ejemplo de solicitud:**
```
http://localhost:8080/App/hello?name=Pedro
```

**Respuesta esperada:**
```
Hello Pedro
```

#### 3. Gestión de Archivos Estáticos

Sirve archivos desde un directorio configurado:

```java
WebFramework.staticfiles("/webroot");
```

El servidor automáticamente:
- Serve `index.html` cuando se accede a `/`
- Detecta el tipo de contenido correcto
- Devuelve errores 404 para archivos no encontrados
- Soporta múltiples tipos MIME

---

## Requisitos Previos

### Software Requerido

- **Java Development Kit (JDK)**: Versión 8 o superior
  - Verificar instalación: `java -version`
- **Apache Maven**: Para compilación y gestión de dependencias
  - Verificar instalación: `mvn -version`
- **IDE** (recomendado):
  - IntelliJ IDEA
  - Eclipse
  - Visual Studio Code con extensiones Java
- **Terminal/Consola** para ejecución

### Dependencias

Este proyecto es minimalista y no requiere dependencias externas en tiempo de ejecución. Utiliza solo la librería estándar de Java:
- `java.io.*` - Manejo de streams
- `java.net.*` - Operaciones de red (ServerSocket, Socket)
- `java.nio.file.*` - Lectura de archivos
- `java.util.*` - Colecciones (HashMap, Map)

---

## Instalación y Configuración

### Paso 1: Clonar o Descargar el Proyecto

```bash
# Si está en un repositorio Git
git clone <repository-url>
cd Mini-Spring/v1

# O descargar el archivo ZIP y extraer
```

### Paso 2: Verificar la Estructura

```
c:\Users\Danie\Documents\TDSE\Mini-Spring\v1/
├── MiniSpringBoot.iml
├── README.md
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── edu/escuelaing/framework/
        │       ├── App.java
        │       ├── HttpServer.java
        │       ├── WebFramework.java
        │       ├── Request.java
        │       ├── Response.java
        │       └── Route.java
        └── resources/
            └── webroot/
                ├── index.html
                └── public/
```

### Paso 3: Compilar el Proyecto

```bash
# Usando Maven
mvn clean compile

# O compilar manualmente desde el IDE
```

### Paso 4: Ejecutar la Aplicación

```bash
# Opción 1: Ejecutar desde Maven
mvn exec:java -Dexec.mainClass="main.java.edu.escuelaing.framework.App"

# Opción 2: Compilar y ejecutar manualmente
mvn package
java -cp target/classes main.java.edu.escuelaing.framework.App

# Opción 3: Desde el IDE
# Click derecho en App.java → Run
```

### Paso 5: Verificar que el Servidor Está Funcionando

```bash
# Deberías ver en la consola:
# "Servidor iniciado en puerto 8080..."
```

Ahora accede a:
- **Navegador**: http://localhost:8080/
- **cURL**: `curl http://localhost:8080/App/hello?name=Diego`

---

## Estructura del Proyecto

### Directorios Principales

```
Mini-Spring/v1/
│
├── 📄 MiniSpringBoot.iml           # Configuración de IntelliJ IDEA
├── 📄 README.md                     # Este archivo
├── 📄 pom.xml                       # Configuración de Maven
│
└── 📁 src/
    └── main/
        ├── 📁 java/                 # Código fuente Java
        │   └── edu/escuelaing/framework/
        │       ├── 📄 App.java                # Punto de entrada de la aplicación
        │       ├── 📄 HttpServer.java        # Servidor HTTP personalizado
        │       ├── 📄 WebFramework.java      # API del framework
        │       ├── 📄 Request.java           # Clase para manejo de solicitudes
        │       ├── 📄 Response.java          # Clase para manejo de respuestas
        │       └── 📄 Route.java             # Interfaz funcional para rutas
        │
        └── 📁 resources/            # Recursos estáticos
            └── webroot/
                ├── 📄 index.html             # Página principal
                └── 📁 public/               # Archivos públicos adicionales
```

### Tamaño del Proyecto

- **Líneas de código Java**: ~200 líneas
- **Complejidad**: Baja (ideal para aprendizaje)
- **Tiempo de compilación**: < 1 segundo
- **Tiempo de inicio**: < 100ms

---

## Guía de Uso

### Uso Básico

El archivo principal es [App.java](src/main/java/edu/escuelaing/framework/App.java):

```java
package main.java.edu.escuelaing.framework;

public class App {
    public static void main(String[] args) throws Exception {
        // Configurar directorio de archivos estáticos
        WebFramework.staticfiles("/webroot");

        // Definir rutas GET
        WebFramework.get("/App/hello", (req, resp) ->
                "Hello " + req.getValues("name"));

        WebFramework.get("/App/pi", (req, resp) ->
                String.valueOf(Math.PI));

        // Iniciar servidor HTTP
        HttpServer.main(args);
    }
}
```

### Flujo de Ejecución

1. **Inicio**: Se registran las rutas y se configura el directorio estático
2. **Escucha**: El servidor se queda escuchando en el puerto 8080
3. **Recepción**: Se acepta una conexión del cliente
4. **Parseo**: Se analiza la solicitud HTTP (método, ruta, parámetros)
5. **Enrutamiento**: Se busca la ruta en el registro
6. **Procesamiento**: Se ejecuta el manejador (lambda) o se serve un archivo estático
7. **Respuesta**: Se envía la respuesta HTTP formateada
8. **Limpieza**: Se cierran streams y sockets

---

## Componentes del Framework

### 1. **App.java** - Punto de Entrada

**Propósito**: Configurar y arrancar la aplicación.

**Responsabilidades**:
- Registrar rutas (endpoints)
- Configurar directorio de archivos estáticos
- Iniciar el servidor HTTP

**Métodos principales**:
```java
WebFramework.staticfiles(String folder)  // Configurar carpeta estática
WebFramework.get(String path, Route route)  // Registrar ruta GET
HttpServer.main(String[] args)  // Arrancar servidor
```

---

### 2. **HttpServer.java** - Servidor HTTP

**Propósito**: Implementar el servidor TCP que maneja solicitudes HTTP.

**Características**:
- Escucha en el puerto 8080
- Acepta múltiples conexiones en un loop
- Parsea solicitudes HTTP (método, ruta, parámetros)
- Enruta solicitudes a handlers o archivo estático
- Construye y envía respuestas HTTP formateadas

**Métodos principales**:
```java
public static void main(String[] args)  // Punto de entrada del servidor

private static void sendResponse(Socket clientSocket, String body)
// Envía respuesta HTTP con headers y body

private static void serveStaticFile(Socket clientSocket, String path)
// Serve archivos estáticos con tipo MIME correcto

private static String getContentType(String path)
// Detecta el MIME type según la extensión del archivo
```

**Flujo de Procesamiento**:
1. `ServerSocket` escucha en puerto 8080
2. Acepta conexión: `Socket clientSocket`
3. Lee línea de solicitud: GET /path HTTP/1.1
4. Parsea la ruta y parámetros
5. Busca ruta en `WebFramework`
6. Si existe → ejecuta handler
7. Si no existe → intenta servir archivo estático
8. Construye respuesta HTTP y envía

---

### 3. **WebFramework.java** - API del Framework

**Propósito**: Proporcionar la API para registrar rutas.

**Componentes Principales**:
```java
private static Map<String, Route> routes        // Registro de rutas
private static String staticFolder              // Carpeta de archivos estáticos
```

**Métodos Públicos**:
```java
public static void staticfiles(String folder)   // Configurar carpeta estática
public static void get(String path, Route route)  // Registrar ruta GET
public static Route getRoute(String path)       // Obtener handler de una ruta
public static String getStaticFolder()          // Obtener carpeta configurada
```

**Ejemplo de Uso**:
```java
WebFramework.staticfiles("/webroot");
WebFramework.get("/api/user", (req, res) -> "User: " + req.getValues("name"));
Route handler = WebFramework.getRoute("/api/user");
```

---

### 4. **Request.java** - Manejo de Solicitudes

**Propósito**: Encapsular los datos de la solicitud HTTP.

**Características**:
- Parsea query parameters automáticamente
- Almacena parámetros en un HashMap

**Métodos**:
```java
public Request(String queryString)  // Constructor que parsea parámetros
public String getValues(String key)  // Obtener valor de un parámetro
```

**Ejemplo de Parseo**:
```
URL: /App/hello?name=Pedro&age=25
Query String: name=Pedro&age=25

Resultado:
{
  "name": "Pedro",
  "age": "25"  // Nota: Como String
}
```

**Uso**:
```java
Request req = new Request("name=Pedro&age=25");
String name = req.getValues("name");  // "Pedro"
String age = req.getValues("age");    // "25"
```

---

### 5. **Response.java** - Manejo de Respuestas

**Propósito**: Encapsular datos de la respuesta HTTP (actualmente minimal).

**Estado Actual**: Clase vacía (placeholder para futuras extensiones)

```java
public class Response {
    // Próximas mejoras podrían incluir:
    // - setStatus(int status)
    // - setHeader(String key, String value)
    // - setBody(String body)
    // - getHeaders(), getStatus(), getBody()
}
```

---

### 6. **Route.java** - Interfaz de Rutas

**Propósito**: Definir el contrato para un manejador de ruta.

**Definición**:
```java
@FunctionalInterface
public interface Route {
    String handle(Request req, Response res);
}
```

**Características**:
- Interfaz funcional (puede usarse con lambdas)
- Recibe `Request` y `Response`
- Retorna `String` (el cuerpo de la respuesta)

**Uso**:
```java
// Implementación con lambda
Route handler = (req, res) -> "Hello " + req.getValues("name");

// Ejecución
String response = handler.handle(request, response);
```

---

## Arquitectura

### Diagrama de Flujo

```
┌─────────────────────────────────────────────────────────────┐
│                    Cliente (Browser/cURL)                   │
└────────────────────────────┬────────────────────────────────┘
                             │
                    HTTP Request (GET)
                             │
                             ▼
┌─────────────────────────────────────────────────────────────┐
│                      HttpServer                             │
│  - Escucha en puerto 8080                                  │
│  - Acepta conexiones Socket                                │
│  - Lee y parsea solicitudes HTTP                           │
└────────────────────────────┬────────────────────────────────┘
                             │
                    Parsea ruta y parámetros
                             │
                             ▼
                    ┌────────────────────┐
                    │ ¿Ruta registrada?  │
                    └────┬─────────────┬──┘
                         │ YES        │ NO
           ┌─────────────▼──┐    ┌────▼──────────────┐
           │  WebFramework  │    │  Archivo estático │
           │  Route Registry│    │  (webroot)        │
           │ (Map<K,V>)     │    │                   │
           └────────┬───────┘    └────┬──────────────┘
                    │                  │
        Ejecuta Lambda Handler    Lee archivo del disco
                    │                  │
                    └────┬──────────┬──┘
                         │          │
                         ▼          ▼
                 ┌──────────────────────┐
                 │  Construye Response  │
                 │  HTTP (status, body) │
                 └──────────┬───────────┘
                            │
                            ▼
                  Envía respuesta al cliente
                            │
                            ▼
                ┌─────────────────────────┐
                │ Cliente recibe respuesta │
                └─────────────────────────┘
```

### Modelo de Requests/Responses

#### Request HTTP → Objeto Request

```
HTTP Request:
GET /App/hello?name=Pedro&age=30 HTTP/1.1
Host: localhost:8080

                    ↓ (Parsed by HttpServer)

Request Object:
{
  queryString: "name=Pedro&age=30",
  queryParams: {
    "name": "Pedro",
    "age": "30"
  }
}
```

#### Objeto Response → HTTP Response

```
Handler Execution:
String result = handler.handle(req, res);
// result = "Hello Pedro"

                    ↓ (Formatted by HttpServer)

HTTP Response:
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 11

Hello Pedro
```

### Tipos MIME Soportados

El servidor detecta automáticamente:

| Extensión | MIME Type |
|-----------|-----------|
| `.html` | `text/html` |
| `.css` | `text/css` |
| `.js` | `text/javascript` |
| `.jpg`, `.jpeg` | `image/jpeg` |
| `.png` | `image/png` |
| `.gif` | `image/gif` |
| `.ico` | `image/x-icon` |
| Otros | `application/octet-stream` |

---

## Ejemplos de Uso

### Ejemplo 1: Servicio Simple

**App.java**:
```java
WebFramework.get("/greet", (req, res) -> 
    "Hello " + req.getValues("name"));
```

**Solicitud**:
```bash
curl "http://localhost:8080/greet?name=Alice"
```

**Respuesta**:
```
Hello Alice
```

---

### Ejemplo 2: Múltiples Parámetros

**App.java**:
```java
WebFramework.get("/calc", (req, res) -> {
    int a = Integer.parseInt(req.getValues("a"));
    int b = Integer.parseInt(req.getValues("b"));
    return "Suma: " + (a + b);
});
```

**Solicitud**:
```bash
curl "http://localhost:8080/calc?a=10&b=20"
```

**Respuesta**:
```
Suma: 30
```

---

### Ejemplo 3: Servicios Matemáticos

**App.java**:
```java
WebFramework.get("/App/pi", (req, res) -> 
    String.valueOf(Math.PI));

WebFramework.get("/square", (req, res) -> {
    int num = Integer.parseInt(req.getValues("n"));
    return "Cuadrado: " + (num * num);
});
```

**Solicitudes**:
```bash
# Pi
curl "http://localhost:8080/App/pi"

# Cuadrado
curl "http://localhost:8080/square?n=5"
```

---

### Ejemplo 4: Archivos Estáticos

**Estructura**:
```
src/main/resources/webroot/
├── index.html
├── style.css
└── script.js
```

**Acceso**:
```bash
# Página principal
curl http://localhost:8080/

# CSS
curl http://localhost:8080/style.css

# JavaScript
curl http://localhost:8080/script.js
```

---

### Ejemplo 5: Combinación Dinámica y Estática

**App.java**:
```java
WebFramework.staticfiles("/webroot");

WebFramework.get("/api/data", (req, res) -> {
    String format = req.getValues("format");
    if ("json".equals(format)) {
        return "{\"message\": \"Hello JSON\"}";
    }
    return "Message: Hello Plain Text";
});
```

**Solicitudes**:
```bash
# JSON
curl "http://localhost:8080/api/data?format=json"

# Plain Text
curl "http://localhost:8080/api/data?format=text"

# Archivo estático
curl http://localhost:8080/index.html
```

---

## Limitaciones y Mejoras

### Limitaciones Actuales

| Limitación | Impacto | Solución Futura |
|-----------|--------|-----------------|
| Solo GET requests | No puede enviar datos POST | Implementar POST/PUT/DELETE |
| Sin HTTP Headers | No se pueden leer cookies/auth | Parsear headers completos |
| Response vacía | No hay control sobre respuesta | Completar clase Response |
| Sin logging | Difícil debugging | Agregar System.out logs o logger |
| Servidor síncrono | Una conexión a la vez | Implementar threading o NIO |
| Sin control de status codes | Siempre 200 o 404 | Permitir establecer status codes |
| Sin manejo de excepciones | Crashes no contemplados | Try-catch y error pages |
| Sin CORS | No funciona con frontend externo | Agregar headers CORS |

### Mejoras Recomendadas (Por Prioridad)

1. **Soporte para POST/PUT/DELETE**
   ```java
   WebFramework.post("/api/users", handler);
   WebFramework.put("/api/users/:id", handler);
   WebFramework.delete("/api/users/:id", handler);
   ```

2. **Parseo de Headers HTTP**
   ```java
   // En clase Request
   public String getHeader(String name);
   public Map<String, String> getHeaders();
   ```

3. **Control de Response**
   ```java
   // En clase Response
   public void setStatus(int status);
   public void setHeader(String key, String value);
   public void setBody(String body);
   ```

4. **Threading/Pool de Threads**
   ```java
   ExecutorService executor = Executors.newFixedThreadPool(10);
   executor.execute(() -> handleClient(socket));
   ```

5. **Logging Estructurado**
   ```java
   logger.info("Request: {} {}", method, path);
   logger.debug("Parameters: {}", params);
   ```

6. **Manejo de Excepciones**
   ```java
   try {
       // ... handling
   } catch (Exception e) {
       sendErrorResponse(socket, 500, "Internal Server Error");
   }
   ```

7. **Parámetros en Ruta**
   ```java
   WebFramework.get("/users/:id", handler);
   // String id = req.getParam("id");
   ```

8. **JSON Response Helper**
   ```java
   public static String toJson(Object obj);
   public static Object fromJson(String json, Class<?> type);
   ```

---

## Objetivo Educativo

### ¿Qué Aprendes con Este Proyecto?

Este framework enseña conceptos fundamentales:

1. **Programación de Red**
   - Sockets TCP/IP
   - Servidor multi-cliente (básico)
   - Protocolo HTTP

2. **Servidores Web**
   - Ciclo de solicitud/respuesta HTTP
   - Parseo de URLs y query strings
   - Content-Type y headers HTTP
   - Códigos de estado (200, 404)

3. **Patrones de Diseño**
   - Registry pattern (rutas)
   - Handler/Callback pattern
   - Strategy pattern (lambdas)

4. **Java Avanzado**
   - Expresiones lambda
   - Interfaces funcionales
   - HashMap y colecciones
   - I/O y NIO
   - Excepciones checked vs unchecked

5. **Comparación con Frameworks Reales**
   - Spring Boot, Quarkus, etc.
   - Entender qué hace cada librería
   - Apreciar la complejidad detrás de abstracciones

### Cómo Extender Este Proyecto

```java
// Agrega métricas
public class Metrics {
    public static void recordRequest(String path, long duration) { }
}

// Agrega middleware
public interface Middleware {
    void process(Request req, Response res, Handler next);
}

// Agrega validación
public class Validator {
    public static void requireParam(Request req, String... params) { }
}

// Agrega templating
public class Templates {
    public static String render(String template, Map<String, Object> data) { }
}
```

---

## Referencia Rápida

### Compilar y Ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="main.java.edu.escuelaing.framework.App"

# O desde IDE: Click derecho en App.java → Run
```

### URLs Comunes

```
Página principal:      http://localhost:8080/
Hello handler:         http://localhost:8080/App/hello?name=YOUR_NAME
Pi constant:           http://localhost:8080/App/pi
Archivo no existe:     http://localhost:8080/notfound.html (404)
```

### Agregar Nueva Ruta

```java
// En App.java, antes de HttpServer.main()
WebFramework.get("/your/path", (req, res) -> {
    String param = req.getValues("paramName");
    return "Your response with: " + param;
});
```

### Agregar Archivo Estático

```
1. Coloca el archivo en: src/main/resources/webroot/
2. Accede en: http://localhost:8080/filename
```

---

## Preguntas Frecuentes

### ¿Por qué puerto 8080?

Es un puerto estándar para desarrollo web local. No requiere privilegios de administrador (los puertos < 1024 los requieren).

### ¿How puedo cambiar el puerto?

En [HttpServer.java](src/main/java/edu/escuelaing/framework/HttpServer.java), línea con `new ServerSocket(8080)`:
```java
ServerSocket serverSocket = new ServerSocket(3000);  // Tu puerto aquí
```

### ¿Soporta HTTPS?

No. Este es un servidor HTTP simple. HTTPS requeriría SSLServerSocket y certificados.

### ¿Cuántos clientes simultáneos soporta?

Uno a la vez (servidor sincrónico). Para múltiples: implementar `ExecutorService` o NIO.

### ¿Puedo servir contenido dinámico (JSON, XML)?

Sí, desde el handler:
```java
WebFramework.get("/api/user", (req, res) -> 
    "{\"name\": \"" + req.getValues("name") + "\"}");
```

### ¿Por qué no usa Spring Boot o similar?

Este proyecto es educativo. Spring Boot abstraería todos estos detalles. Entender el "cómo funciona" es valioso.

---

## Contribuciones y Mejoras

Si deseas mejorar este proyecto:

1. Agrega nuevas características en ramas separadas
2. Mantén el código simple y legible
3. Documenta cambios importantes
4. Considera agregar comentarios para nuevas secciones

---

## Licencia

Este proyecto es de código abierto bajo licencia MIT. Siéntete libre de usarlo, modificarlo y distribuirlo.

```
MIT License

Copyright (c) 2024 Escuela de Ingeniería

Permission is hereby granted, free of charge...
```

---

## Recursos Adicionales

### Para Aprender Más

- **HTTP Protocol**: [RFC 7230](https://tools.ietf.org/html/rfc7230)
- **Java Sockets**: [Oracle Docs: Java Networking](https://docs.oracle.com/javase/tutorial/networking/)
- **Lambda Expressions**: [Oracle Docs: Lambda](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
- **Spring Framework**: [spring.io](https://spring.io)

### Proyectos Relacionados

- **Jetty**: Un servidor Java ligero
- **Undertow**: Motor de servidor de JBoss
- **Vert.x**: Framework reactivo de JVM
- **Express.js**: Framework Node.js (similar en concepto)

### Herramientas Útiles

```bash
# Prueba solicitudes GET
curl "http://localhost:8080/path"

# Con parámetros
curl "http://localhost:8080/path?param=value"

# Ver headers
curl -i "http://localhost:8080/path"

# Usuario/contraseña
curl -u user:pass "http://localhost:8080/path"

# Postman: IDE gráfica para APIs (recomendado)
```

---

<div align="center">

**Hecho con ❤️ para aprender cómo funcionan los frameworks web**

¿Preguntas? Revisa el código fuente - ¡es educativo!

</div>

Supported file types:

- .html
- .css
- .js
- .png
- .jpg
- .gif

Binary files (such as images) are handled correctly using OutputStream.

---

### 4. HTTP Handling

The server correctly implements:

- HTTP/1.1 200 OK
- 404 Not Found
- Content-Type
- Content-Length
- Proper header-body separation using \r\n\r\n

---

## Core Classes

HttpServer  
Responsible for:
- Handling socket connections
- Parsing HTTP requests
- Matching routes
- Serving static files
- Writing HTTP responses

WebFramework  
Responsible for:
- Registering routes
- Managing the static folder location

Route (Functional Interface)  
Allows lambda-based route definitions.

Request  
Parses and stores query parameters.

Response  
Reserved for future response enhancements.

---

## How to Run

1. Clone the repository

git clone https://github.com/your-username/your-repo.git  
cd your-repo  

2. Build with Maven

mvn clean package  

3. Run the application

mvn exec:java -Dexec.mainClass="edu.escuelaing.framework.App"

Or run App.java directly from your IDE.

---

## Test Cases

### REST Endpoints

Test 1:

http://localhost:8080/App/hello?name=Pedro

Expected output:

Hello Pedro

Test 2:

http://localhost:8080/App/pi

Expected output:

3.141592653589793

---

### Static Files

Test 3:

http://localhost:8080/index.html

Test 4:

http://localhost:8080/style.css

Test 5 (Image test):

http://localhost:8080/images/logo.png

---

### Test with curl

curl "http://localhost:8080/App/hello?name=Daniel"

---

## Technical Concepts Applied

- HTTP protocol structure
- TCP socket programming
- Client-server architecture
- Functional interfaces in Java
- Lambda expressions
- RESTful service design
- Static resource handling
- Content-Type management
- Binary file streaming
- Routing tables

---

## Possible Improvements

- Multi-threaded request handling
- POST method support
- JSON response handling
- Middleware support
- Automatic MIME type detection
- Logging system
- Exception handling layer

---

## Learning Outcomes

After completing this project, the developer gains practical understanding of:

- How web frameworks work internally
- How HTTP communication is structured
- How routing systems operate
- How distributed systems communicate over sockets
- How static content is delivered in web architectures

---

## Author

Daniel  
TDSE Student  

---

## License

Academic project developed for educational purposes.