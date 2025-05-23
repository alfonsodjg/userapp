# UsersApp

Aplicación Android para gestionar usuarios, con almacenamiento local y 	sincronización remota.

---

## Características

- Muestra una lista de usuarios desde caché y red.
- Funcion de actualizar usuarios al marcar checkbox, (aplica en caché).
- Funcion de eliminar usuarios deslizando cada item hacia la izquierda(aplica en caché).
- Implementación con MVVM, Retrofit/Ktor, Room, Hilt.
- Compatibilidad con Android 23+.

---

## Cómo ejecutar

**Recomendación:** Usar la versión mas reciente de Android Studio para evitar errores de compatibilidad con Gradle y Kotlin.

1. Clonar el repositorio "https://github.com/alfonsodjg/userapp.git"
2. Abrir en Android Studio
3. Conectar un dispositivo o emulador
4. Ejecutar la app

------------------------------------------------------------------------------------------------------------------

## Estructura
Este proyecto sigue una arquitectura MVVM + Clean Architecture. Las capas principales se organizan de la siguiente manera:

### data – Capa de datos
Gestiona el acceso, transformación y manejo de datos locales y remotos, además de la conectividad y errores.

- `core/handler/` – Manejo de errores genéricos (`ServiceDataHandler`)
- `local/`
    - `dao/` – Define la interfaz DAO de Room
    - `entity/` – Contiene la entidad (tabla) de la base de datos
    - `database/` – Configuración y acceso a Room Database
    - `mapper/` – Conversores entre entidades y modelos
- `modules/users/`
    - `mapper/` – Mapeadores que transforman modelos de red a modelos de dominio
    - `model/` – Contiene la representación del modelo con estructura del JSON original(Respuesta del backend)
    - `repository/` – Implementación del repositorio que conecta las fuentes de datos
- `network/`
    - `endpoint/` – Definición de endpoints mediante Retrofit
    - `NetworkConnectivity` – Verifica la conexión a Internet
- `utils/`
    - `InterceptorError` – Interceptor para manejar errores HTTP
    - `ServiceErrorDataToDomain` – Mapeo de errores hacia objetos de dominio

---

### domain – Capa de dominio
Incluye la lógica de negocio pura de la aplicación.

- `conecction/`
    - `ConnectivityObserver` – Interfaz que observa el estado de la conexión
    - `NetworkStatus` – Enum que representa los posibles estados de red
- `core/handler/`
    - `ServiceDomainHandler` – Manejador de estados de éxito o error en esta capa
- `modules/users/`
    - `model/` – Modelo de dominio para usuarios
    - `repository/` – Interfaz que define operaciones del repositorio para los usuarios
    - `usecase/` – Casos de uso con la lógica de negocio:
        - `GetUsersUseCase` – Obtiene usuarios desde la API
        - `GetLocalUsersUseCase` – Recupera usuarios locales de Room
        - `UpdateUserUseCase` – Actualiza información de un usuario local
        - `DeleteUserUseCase` – Elimina un usuario de la base local

---

### ui – Capa de interfaz de usuario
Gestiona la interacción con el usuario y conecta con la lógica del ViewModel.

- `modules/users/`
    - `adapter/` – Adaptador para el RecyclerView de la lista de usuarios
    - `mapper/` – Transforma datos de dominio a modelos de presentación o UI
    - `model/` – Modelo usado exclusivamente en la capa de UI
    - `swipe/` – Componente que permite deslizar ítems en la lista
    - `view/` – Activity principal y layout de la aplicación
    - `viewmodel/` – ViewModel que expone datos y estados a la vista
    - `viewstate/` – Define los distintos estados visuales de la pantalla

### di – Inyección de dependencias
Contiene `AppModule`, el objeto responsable de proveer Retrofit, Room, DAO y repositorios mediante Hilt para toda la aplicación.

--------------------------------------------------------------------------------------------------------

### test – Pruebas unitarias
Incluye tests unitarios para los casos de uso y lógica de dominio, asegurando el correcto funcionamiento de la lógica de negocio y validación de estados.
> Estas pruebas se encuentran en `app/kotlin+java/com.alfonso.usersapp(test)/domain.modules.users.usecase

----------------------------------------------------------------------------------------------------------

## Tecnologias y arquitecturas usadas
- Kotlin
- XML
- MVVM
- Clean Architecture
- Hilt para inyeccion de dependencias
- Retrofit para consumo de APIs
- Room para persistencia local

---

## Preguntas y reflexiones

### ¿Qué problemas enfrentaste y cómo los solucionaste?
    - **Inyección de dependencias con Hilt**: 
        Inicialmente tuve problemas de compatibilidad con kotlin 2.0.21, pero se solucionó consultando la documentación oficial y actualizando version de hilt.
    - **Conectividad de red**:
        Fue un reto porque no lo había implementado antes, pero lo resolví con la clase `NetworkConnectivity` usando la interfaz `ConnectivityObserver`.
    - **Compatibilidad de Room con KSP**:
       Tuve advertencias de Gradle al usar Room con `kapt`; consulté la documentación oficial y migré a `ksp`, actualizando la versión de Room.

### ¿Qué decisiones tomaste respecto a la organización del código y por qué?
    -**Decidí estructurar el proyecto por capas siguiendo Clean Architecture + MVVM para evitar acoplamientos innecesarios y mantener un código escalable. 
     Implementé mapeadores en cada capa para transformar los modelos y asegurar que cada capa trabaje solo con su propio tipo de datos,
     esto facilita los cambios sin afectar el resto de la app.

### ¿Qué harías diferente si tuvieras más tiempo o fuera un proyecto real?
    - Separaría la lógica de `MainActivity` moviéndola a un `Fragment` que contenga los componentes de UI.
    - Usaría tecnologías modernas como:
     **Jetpack Compose** para la UI, **Ktor**(cliente HTTP moderno) para red y **Koin**(inyección ligera de dependencias) para inyección de dependencias, por ser más flexibles y ligeras.
    - En proyectos que utilizan unicamente XML, adoptaría una estrategia híbrida que combine XML y Jetpack Compose para facilitar una migración progresiva.
    - Implementaría **paginación** para mejorar el rendimiento con listas grandes.
    - Mejoraría el soporte offline.


## Contacto
Alfonso - [LinkedIn](https://www.linkedin.com/in/alfonso-de-jesus-garces)  
Correo: alfonsodejesus47116@gmail.com

