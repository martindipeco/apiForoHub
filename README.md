# Foro - Hub

## Descripción

Foro Hub es una Java SpringBoot API REST que se centra en los tópicos (temas de consulta "posteos"), y permite a los usuarios:

Crear un nuevo tópico;
Mostrar todos los tópicos creados;
Mostrar un tópico específico;
Actualizar un tópico;
Eliminar un tópico.

[Link a video - demo parte 1 (5 min)](https://www.loom.com/share/eec277002ab24233876cefae0c5a0e64?sid=78514b57-42c2-4c90-a7fd-e69750382e58)
[Link a video - demo parte 2 (3 min)](https://www.loom.com/share/ecac073256644d36b4812d6268e58731?sid=594a1a70-8242-4f8a-8d43-6e34d6a5eefa)

## Funcionalidades

1. **API con rutas implementadas siguiendo las mejores prácticas del modelo REST**
2. **Validaciones realizadas según las reglas de negocio**
3. **Implementación de una base de datos relacional para la persistencia de la información**
4. **Servicio de autenticación/autorización para restringir el acceso a la información**


## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

- **Modelos**: Definición de las entidades `Usuario`, `Topico`, `Respuesta` .
- **Repositorios**: Interfaces que definen los métodos de acceso a la base de datos para las entidades `Usuario` y `Topico`.
- **Controladores**: Clases `Controllers` que proporciona métodos para recibir las peticiones del cliente y devolver los DTOs de acuerdo a como corresponda.
- **Capa de Seguridad**: Clase `FilterInternal` y `TokenService` para asegurar la aplicación.

## Requisitos

- Java 8 o superior
- Maven
- MySQL
- Dependencias de Maven para JPA, Hibernate y MySQL

## Configuración

1. **Clonar el repositorio:**

```bash
git clone <url_del_repositorio>
cd foro-hub
```

2. **Configurar la base de datos:**

Asegúrate de tener MySQL instalado y ejecutándose. Crea una base de datos para la aplicación.

```bash
CREATE DATABASE foro_hub;
```

3. **Configurar las propiedades de la aplicación:**

Edita el archivo src/main/resources/application.properties con los detalles de tu base de datos

```bash
spring.datasource.url=jdbc:mysql://localhost/foro_hub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

4. **Construir y ejecutar la aplicación:**

```bash
mvn clean install
mvn spring-boot:run
```

## Uso

Al ejecutar la aplicación, se podrán simular las peticiones del cliente. Se sugiere el uso de Insomnia o Postman


### Ejemplos de uso

- Autenticación de usuario:
Genera Token para validar posteriores pedidos.

- Obtener listado completo de topicos:
Devueltos en tandas de 5 resultados por página.

- Generar un tópico:
Ingresa un nuevo tópico.

- Generar una respuesta:
Agrega una respuesta a un tópico existente.

- Eliminar un tópico:
Usuarios sólo pueden acceder al borrado lógico, haciendo que un tópico pase a estar inactivo y por lo tanto no se muestre en las búsquedas.


## Contribuciones son bienvenidas!

Las contribuciones son bienvenidas. Por favor, sigue los siguientes pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama:
```bash
 git checkout -b feature/nueva-funcionalidad:
```
3. Realiza los cambios necesarios y realiza commits:
```bash
git commit -m 'Agregar nueva funcionalidad'
```
4. Empuja los cambios a tu fork:
```bash
git push origin feature/nueva-funcionalidad
```
5. Abre un Pull Request.
