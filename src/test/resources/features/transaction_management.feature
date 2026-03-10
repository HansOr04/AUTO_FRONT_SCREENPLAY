# language: es
Característica: Gestión de transacciones financieras
  Como usuario autenticado en la aplicación financiera
  Quiero gestionar mis transacciones
  Para mantener control de mis finanzas personales

  Antecedentes:
    Dado el usuario se encuentra en la página de inicio de sesión

  Escenario: Inicio de sesión exitoso
    Cuando el usuario ingresa sus credenciales válidas
    Entonces el usuario es redirigido al dashboard principal

  Escenario: Crear una nueva transacción
    Dado el usuario ha iniciado sesión en la aplicación
    Cuando el usuario navega a la sección de transacciones
    Y el usuario crea una transacción con descripción "Compra supermercado" y monto "250"
    Entonces la transacción "Compra supermercado" aparece en el listado

  Escenario: Visualizar reportes financieros
    Dado el usuario ha iniciado sesión en la aplicación
    Cuando el usuario navega a la sección de reportes
    Entonces el sistema muestra el resumen financiero

  Escenario: Cerrar sesión correctamente
    Dado el usuario ha iniciado sesión en la aplicación
    Cuando el usuario cierra su sesión desde el menú de usuario
    Entonces el usuario es redirigido a la página de inicio de sesión
