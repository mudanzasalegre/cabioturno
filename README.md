# CabioTurno

CabioTurno es una aplicación para la gestión de cambios de turno en una empresa. Los usuarios pueden solicitar cambios de turno, y los administradores pueden gestionar y aprobar estas solicitudes.

## Características

- Solicitud de cambio de turno por parte de los usuarios.
- Aprobación de cambios de turno por parte de los administradores.
- Notificaciones en tiempo real para los usuarios y administradores sobre el estado de las solicitudes.
- Sistema de autenticación y autorización basado en roles (Operador y Administrador).

## Requisitos

- Java 17 o superior
- Spring Boot 3.3 o superior
- Base de datos MySQL

## Instalación

1. Clona el repositorio:

    ```sh
    git clone https://github.com/tu-usuario/cabioturno.git
    cd cabioturno
    ```

2. Configura la base de datos en el archivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/cabioturno
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Construye y ejecuta la aplicación:

    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

4. Accede a la aplicación en tu navegador web en `http://localhost:8080`.

## Uso

### Operadores

- Los operadores pueden iniciar sesión y solicitar cambios de turno.
- Pueden ver el estado de sus solicitudes y recibir notificaciones sobre los cambios.

### Administradores

- Los administradores pueden iniciar sesión y gestionar las solicitudes de cambio de turno.
- Pueden aprobar o rechazar solicitudes y recibir notificaciones sobre nuevas solicitudes.

## Dependencias

El proyecto utiliza las siguientes dependencias:

- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Spring Boot Starter WebSocket
- MySQL Connector Java
- Spring Security Web
- Spring Security Config
- Spring Boot DevTools
- Thymeleaf Extras Spring Security5

Asegúrate de que estas dependencias estén especificadas en tu archivo `pom.xml`.

## Contribuciones

1. Haz un fork del proyecto
2. Crea una rama para tu nueva característica (`git checkout -b feature/nueva-caracteristica`)
3. Realiza los commits necesarios (`git commit -am 'Añadir nueva característica'`)
4. Sube los cambios a tu rama (`git push origin feature/nueva-caracteristica`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
