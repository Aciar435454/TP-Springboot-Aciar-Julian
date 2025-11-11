# TP - APIs REST con Spring Boot

## Descripción

Trabajo práctico desarrollado para la materia **Desarrollo de Software**, cuyo objetivo es construir una **API RESTful** en **Spring Boot** para la gestión de productos.  
La aplicación permite realizar operaciones CRUD (crear, leer, actualizar y eliminar), filtrar por categoría y modificar parcialmente el stock.

---

## Tecnologías utilizadas

- **Java 17**  
- **Spring Boot 3.x**  
- **Spring Web**  
- **Spring Data JPA**  
- **Spring Validation (Jakarta)**  
- **Lombok**  
- **DevTools**  
- **H2 Database (en memoria)**  
- **Springdoc OpenAPI / Swagger UI**

---

## Configuración del proyecto

Archivo: `src/main/resources/application.properties`

```properties
# Base de datos H2 (en memoria)
spring.datasource.url=jdbc:h2:mem:productosdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=create-drop

# Consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Puerto del servidor
server.port=8080
```properties

---

## Estructura del proyecto
com.utn.productos
 ├── controller        # Controladores REST
 ├── dto               # Clases DTO (entrada/salida)
 ├── exception         # Manejo global de errores
 ├── model             # Entidades JPA
 ├── repository        # Interfaces JpaRepository
 ├── service           # Lógica de negocio
 └── ProductosApiApplication.java
