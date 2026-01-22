# 🛒 API SuperMarket

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen?style=flat-square&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=flat-square&logo=docker)
![License](https://img.shields.io/badge/License-Educational-yellow?style=flat-square)

## 📋 Descripción

API RESTful para la gestión de un sistema de supermercado que permite administrar productos, sucursales y ventas. Este proyecto implementa un CRUD completo con arquitectura en capas y está completamente dockerizado para facilitar su despliegue.

**⚠️ Nota importante:** Este proyecto es exclusivamente para fines educativos y de aprendizaje.

---

## 👤 Autor

**Diego Arias**

---

## 🏗️ Arquitectura y Patrones de Diseño

El proyecto sigue una **arquitectura en capas (Layered Architecture)** implementando los siguientes patrones:

### Estructura de Capas

```
📦 com.ventas.supermercado
 ┣ 📂 controllers/       → Capa de Presentación (REST Controllers)
 ┣ 📂 services/          → Capa de Lógica de Negocio
 ┣ 📂 repositories/      → Capa de Acceso a Datos (Spring Data JPA)
 ┣ 📂 models/            → Entidades JPA (Modelos de dominio)
 ┣ 📂 dtos/              → Data Transfer Objects
 ┣ 📂 mapper/            → Conversión entre Entidades y DTOs
 ┣ 📂 exceptions/        → Manejo centralizado de excepciones
 ┗ 📂 config/            → Configuraciones (Swagger/OpenAPI)
```

### Patrones Implementados

- **Repository Pattern**: Abstracción del acceso a datos mediante Spring Data JPA
- **DTO Pattern**: Transferencia de datos entre capas sin exponer entidades
- **Builder Pattern**: Construcción de objetos mediante Lombok (@Builder)
- **Dependency Injection**: Inyección de dependencias con Spring Framework
- **RESTful API Pattern**: Arquitectura basada en recursos HTTP

---

## 🛠️ Tecnologías y Versiones

### Stack Principal

| Tecnología | Versión |
|------------|---------|
| **Java** | 21 |
| **Spring Boot** | 3.5.9 |
| **PostgreSQL** | 16 (Alpine) |
| **Maven** | 3.x |
| **Docker** | Compose V3.8 |

### Dependencias Principales

```xml
<!-- Spring Framework -->
- spring-boot-starter-web          (REST API)
- spring-boot-starter-data-jpa     (Persistencia)

<!-- Base de Datos -->
- postgresql                        (Driver JDBC)

<!-- Documentación -->
- springdoc-openapi-starter-webmvc-ui  (Swagger UI 2.8.5)

<!-- Utilidades -->
- lombok                            (Reducción de boilerplate)
```

---

## 📊 Modelo de Datos

El sistema gestiona las siguientes entidades:

- **Producto**: Catálogo de productos del supermercado
- **Sucursal**: Información de las sucursales
- **Venta**: Registro de transacciones de venta
- **DetalleVenta**: Detalle de productos vendidos en cada venta

---

## 🐳 Docker

El proyecto está completamente **dockerizado** con:

- **Dockerfile**: Construcción de la imagen de la API Spring Boot
- **docker-compose.yaml**: Orquestación de servicios (API + PostgreSQL)

### Características Docker

✅ Base de datos PostgreSQL incluida en el mismo `docker-compose`  
✅ **Sin contraseña de usuario** (credenciales por defecto)  
✅ Persistencia de datos mediante volúmenes  
✅ Healthcheck automático de la base de datos  
✅ Límite de memoria configurado (768MB para la API)  
✅ Reinicio automático de contenedores  

### Credenciales de Base de Datos

```properties
DB_NAME: market_db
DB_USER: dev
DB_PASSWORD: 123
```

---

## 🚀 Cómo Usar Este Proyecto

### Prerrequisitos

- Docker y Docker Compose instalados
- Git (para clonar el repositorio)

### Opción 1: Ejecución con Docker (Recomendado) 🐳

1. **Clonar el repositorio**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd supermercado
   ```

2. **Levantar los servicios**
   ```bash
   docker-compose up --build
   ```

   Esto creará y ejecutará:
   - Contenedor de PostgreSQL en `localhost:5432`
   - API Spring Boot en `localhost:8080`

3. **Acceder a la API**
   - API REST: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI Docs: `http://localhost:8080/v3/api-docs`

4. **Detener los servicios**
   ```bash
   docker-compose down
   ```

   Para eliminar también los volúmenes (borrar datos):
   ```bash
   docker-compose down -v
   ```

---

### Opción 2: Ejecución Local (Sin Docker)

#### Prerrequisitos adicionales
- JDK 21 instalado
- Maven 3.x instalado
- PostgreSQL 16 instalado y en ejecución

#### Pasos

1. **Configurar PostgreSQL local**
   
   Crear la base de datos:
   ```sql
   CREATE DATABASE market_db;
   CREATE USER dev WITH PASSWORD '123';
   GRANT ALL PRIVILEGES ON DATABASE market_db TO dev;
   ```

2. **Configurar variables de entorno** (Opcional)
   
   Si usas IntelliJ IDEA:
   - Run → Edit Configurations
   - Environment variables → Añadir:
     ```
     DB_URL=jdbc:postgresql://localhost:5432/market_db
     DB_USER_NAME=dev
     DB_PASSWORD=123
     ```

3. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```
   
   O directamente con Java:
   ```bash
   java -jar target/supermercado-1.0.0.jar
   ```

5. **Acceder a la API**
   - API REST: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📚 Documentación de la API

La API está documentada con **Swagger/OpenAPI 3.0**.

### Acceder a Swagger UI

Una vez levantada la aplicación, visita:

```
http://localhost:8080/swagger-ui.html
```

Desde allí podrás:
- ✅ Ver todos los endpoints disponibles
- ✅ Probar las peticiones directamente desde el navegador
- ✅ Ver los esquemas de request/response
- ✅ Consultar códigos de respuesta HTTP

### Endpoints Principales

- **Productos**: `/api/productos`
- **Sucursales**: `/api/sucursales`
- **Ventas**: `/api/ventas`

Cada endpoint soporta operaciones CRUD completas (GET, POST, PUT, DELETE).

---

## 🔧 Configuración Avanzada

### Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `DB_URL` | URL de conexión JDBC | `jdbc:postgresql://localhost:5432/market_db` |
| `DB_USER_NAME` | Usuario de la base de datos | `dev` |
| `DB_PASSWORD` | Contraseña de la base de datos | `123` |
| `DB_PLATFORM` | Dialecto de Hibernate | `org.hibernate.dialect.PostgreSQLDialect` |

### Modificar el `application.properties`

El archivo usa **valores por defecto con fallback**:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/market_db}
spring.datasource.username=${DB_USER_NAME:dev}
spring.datasource.password=${DB_PASSWORD:123}
```

Si no existen las variables de entorno, usa los valores después de `:`.

---

## 📝 Notas Adicionales

### Generación del JAR

Para generar el archivo ejecutable:

```bash
mvn clean package
```

El JAR se generará en: `target/supermercado-1.0.0.jar`

### Reconstruir Docker después de cambios

Si modificas el código:

1. Regenera el JAR:
   ```bash
   mvn clean install
   ```

2. Reconstruye la imagen Docker:
   ```bash
   docker-compose up --build
   ```

### Codificación UTF-8

El proyecto está configurado para usar UTF-8 en todos los archivos (`pom.xml`):

```xml
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

---

## 🎓 Proyecto para repaso y aprendizaje

Este proyecto ha sido desarrollado como material de **aprendizaje y repaso** de las siguientes tecnologías:

- Spring Boot 3.x y Spring Framework 6
- JPA/Hibernate para persistencia
- Arquitectura REST
- Dockerización de aplicaciones Java
- Documentación de APIs con OpenAPI/Swagger
- Patrones de diseño en aplicaciones empresariales

---

## 📄 Licencia

Este proyecto es de uso exclusivamente educativo. No tiene garantía ni soporte.

---

## 🤝 Contribuciones

Al ser un proyecto educativo, las contribuciones no están abiertas. Sin embargo, puedes hacer fork del repositorio para tus propios experimentos.

---

**¡Gracias por revisar este proyecto! 🚀**
