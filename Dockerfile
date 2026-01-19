# 1. IMAGEN BASE
# Usamos OpenJDK 17 en su versión "slim" (ligera) para reducir el tamaño final de la imagen.
# Esto asegura que tengamos el entorno de ejecución de Java necesario para Spring Boot 3.
FROM openjdk:21-jdk-slim

# 2. VARIABLE DEL JAR
# Definimos una variable que apunta a donde Maven genera el .jar compilado.
# Hay que asegurarse de que "supermercado-1.0.0.jar" coincida con la versión en el pom.xml.
ARG JAR_FILE=target/supermercado-1.0.0.jar

# 3. COPIAR AL CONTENEDOR
# Copiamos el .jar desde tu PC hacia la imagen y lo renombramos a "api_supermercado.jar"
# para que sea más fácil de manejar internamente.
COPY ${JAR_FILE} api_supermercado.jar

# 4. PUERTO
# Indicamos que el contenedor escuchará peticiones en el puerto 8080.
EXPOSE 8080

# 5. PUNTO DE ENTRADA
# El comando que se ejecutará al iniciar el contenedor.
# Levanta la aplicación Java usando el archivo que acabamos de copiar.
ENTRYPOINT ["java","-jar","/api_supermercado.jar"]
