# AUTO_FRONT_SCREENPLAY

Proyecto de automatización de pruebas frontend para Budget Management App usando Serenity BDD + Cucumber con el patrón Screenplay (Actores, Tareas, Preguntas).

## Prerrequisitos

- Java 17
- Google Chrome (última versión estable)
- Docker Desktop
- Gradle 8+ (o usar el wrapper incluido)

## Levantar la aplicación

```bash
docker compose up --build
```

Servicios disponibles tras el arranque:

| Servicio         | URL                          |
|------------------|------------------------------|
| Frontend         | http://localhost:3000        |
| Auth API         | http://localhost:8083/api    |
| Transactions API | http://localhost:8081/api    |
| Reports API      | http://localhost:8082/api    |

## Ejecutar los tests

```powershell
.\gradlew clean test aggregate
```

Para ejecutar por tag:

```powershell
.\gradlew clean test aggregate -Dcucumber.filter.tags="@smoke"
```

## Ver el reporte

El reporte se genera automáticamente al finalizar. Ábrelo en:

```
target/site/serenity/index.html
```

## Estructura del proyecto

```
AUTO_FRONT_SCREENPLAY/
├── build.gradle                          # Dependencias y configuración de Gradle
├── serenity.conf                         # Configuración de Serenity y WebDriver
└── src/
    └── test/
        ├── java/
        │   └── com/automation/
        │       ├── ui/
        │       │   └── BudgetManagementUi.java        # Targets (selectores XPath)
        │       ├── interactions/
        │       │   ├── ReactEnter.java                # Entrada de texto compatible con React (JS nativeInputValueSetter)
        │       │   └── JavaScriptClick.java           # Click vía JavaScript (evita problemas de pointer-events)
        │       ├── tasks/
        │       │   ├── Register.java                  # Registra un usuario nuevo
        │       │   ├── Authenticate.java              # Limpia sesión (cookies + localStorage) e inicia sesión
        │       │   ├── NavigateToLogin.java           # Navega a la pantalla de login
        │       │   ├── NavigateToTransactions.java    # Navega a la sección de transacciones
        │       │   ├── CreateTransaction.java         # Crea una nueva transacción (usa ReactEnter para el monto)
        │       │   ├── NavigateToReports.java         # Navega a la sección de reportes
        │       │   └── Logout.java                    # Cierra sesión desde el menú de usuario
        │       ├── questions/
        │       │   ├── CurrentUrl.java                # Pregunta la URL actual del navegador
        │       │   └── TransactionVisibility.java     # Comprueba si una transacción es visible (WebDriverWait + XPath)
        │       ├── runner/
        │       │   └── CucumberTestRunner.java        # Runner JUnit 4 + CucumberWithSerenity
        │       └── stepdefinitions/
        │           └── TransactionStepDefinitions.java  # Glue entre Gherkin y Screenplay
        └── resources/
            └── features/
                └── transaction_management.feature     # Escenarios en Gherkin (español)
```

## Patrón Screenplay

El proyecto implementa el patrón Screenplay con los siguientes roles:

- **Actores**: se crean en los step definitions con `OnStage.theActorCalled("usuario")`
- **Tareas** (`tasks/`): encapsulan acciones de negocio de alto nivel
- **Interacciones** (`interactions/`): acciones de bajo nivel para compatibilidad con React (inputs controlados, clicks vía JS)
- **Preguntas** (`questions/`): consultan el estado de la UI para las aserciones
- **Targets** (`ui/`): centralizan todos los selectores XPath de la interfaz

## Escenarios de prueba

| # | Escenario                        | Descripción                                                    |
|---|----------------------------------|----------------------------------------------------------------|
| 1 | Inicio de sesión exitoso         | Verifica login con credenciales válidas y redirección a dashboard |
| 2 | Crear una nueva transacción      | Crea una transacción con descripción, monto y categoría        |
| 3 | Visualizar reportes financieros  | Navega a reportes y verifica el resumen financiero             |
| 4 | Cerrar sesión correctamente      | Cierra sesión y verifica redirección a la página de login      |

## Gestión de credenciales

El proyecto genera credenciales únicas en cada ejecución usando un timestamp (`testuser_<timestamp>@example.com`). Antes del primer escenario se registra automáticamente un usuario nuevo en la aplicación y esas mismas credenciales se reutilizan en todos los escenarios de la suite.

## Consideraciones técnicas

- **React controlled inputs**: el campo de monto usa `ReactEnter` (JavaScript `nativeInputValueSetter` + eventos `input`/`change`) para actualizar correctamente el estado interno de React.
- **Radix UI Select**: el dropdown de categoría usa el selector `//button[@role='combobox' and @data-placeholder='']` para distinguir el combobox sin selección del que ya tiene valor.
- **Autenticación por localStorage**: la app almacena tokens JWT en `localStorage`. Antes de cada login, `Authenticate` limpia cookies, localStorage y sessionStorage para garantizar una sesión limpia.
- **Estabilización post-login**: después de hacer login, se espera con `WebDriverWait` a que la URL contenga `/dashboard` y se da un margen de 3 segundos para que el reload de React termine.
