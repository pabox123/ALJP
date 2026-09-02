# Examen Spring Intro - Gestión de dispositivos IoT

Este proyecto implementa un sistema de gestión de dispositivos IoT y mediciones usando **Spring Context** + **Servlets** con arquitectura por capas:

- **Modelo**: `Device`, `Measurement`
- **Repositorio**: `DeviceRepository`, `MeasurementRepository` (en memoria)
- **Servicio**: `DeviceService` (reglas de negocio)
- **Presentación**: 3 servlets

---

## 1) Requisitos previos

1. Java 17+
2. Maven 3.9+
3. Apache Tomcat 10+

---

## 2) Estructura de carpetas

```text
src/main/java/com/aljp/
  config/AppConfig.java
  model/Device.java
  model/Measurement.java
  repository/DeviceRepository.java
  repository/MeasurementRepository.java
  service/DeviceService.java
  servlet/BaseSpringServlet.java
  servlet/AddDeviceServlet.java
  servlet/UpdateDeviceEstateServlet.java
  servlet/ListDevicesServlet.java
src/main/webapp/WEB-INF/web.xml
```

---

## 3) Paso a paso para desarrollarlo

1. Crear proyecto web Maven (empaquetado WAR).
2. Agregar dependencias de Spring Context, Spring Web y Servlet API.
3. Crear modelos con los atributos solicitados:
   - `Device`: `id`, `name`, `serialNumber`, `Ubicación`, `type`, `Estate`
   - `Measurement`: `id`, `timestamp`, `value`, `unit`, `assetId`
4. Crear un repositorio concreto por entidad con `Collection` en memoria.
5. Inicializar repositorios con al menos 2 registros asociados (mediciones ligadas a dispositivos).
6. Crear `DeviceService` con inyección de dependencias y reglas:
   - `serialNumber` único
   - `name` no vacío ni nulo
   - `serialNumber` mínimo 5 caracteres
   - `serialNumber` máximo 20 caracteres
   - no eliminar dispositivo con mediciones asociadas
7. Configurar Spring con `@Configuration` + `@ComponentScan`.
8. Configurar `ContextLoaderListener` y servlets en `web.xml` para compartir contexto entre requests.
9. Implementar 3 servlets:
   - agregar dispositivo
   - actualizar estado (`Estate`) del dispositivo
   - listar dispositivos en HTML
10. Compilar y empaquetar con Maven.
11. Desplegar el WAR en Tomcat.

---

## 4) Endpoints

- **Listar dispositivos (HTML)**: `GET /aljp-iot/devices`
- **Agregar dispositivo**:
  - `GET /aljp-iot/devices/add` (formulario)
  - `POST /aljp-iot/devices/add`
- **Actualizar Estate**:
  - `GET /aljp-iot/devices/state` (formulario)
  - `POST /aljp-iot/devices/state`

> `aljp-iot` es el context path por defecto del WAR generado.

---

## 5) Build y ejecución

### Compilar y ejecutar pruebas

```bash
mvn clean test
```

### Generar WAR

```bash
mvn clean package
```

El archivo generado queda en:

```text
target/aljp-iot.war
```

### Desplegar en Tomcat

1. Copiar `target/aljp-iot.war` a `TOMCAT_HOME/webapps/`
2. Iniciar Tomcat
3. Abrir:
   - `http://localhost:8080/aljp-iot/`
   - `http://localhost:8080/aljp-iot/devices`

---

## 6) Notas de negocio implementadas

- Persistencia en memoria durante la ejecución (sin base de datos).
- Beans singleton de Spring compartidos entre solicitudes HTTP.
- Relación 1:N entre `Device` y `Measurement` usando `assetId`.
