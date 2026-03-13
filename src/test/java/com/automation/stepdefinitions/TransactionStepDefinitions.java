package com.automation.stepdefinitions;

import com.automation.interactions.ClearBrowserState;
import com.automation.questions.CurrentUrl;
import com.automation.questions.DashboardVisibility;
import com.automation.questions.TransactionVisibility;
import com.automation.tasks.Authenticate;
import com.automation.tasks.CreateTransaction;
import com.automation.tasks.Logout;
import com.automation.tasks.NavigateToLogin;
import com.automation.tasks.NavigateToReports;
import com.automation.tasks.NavigateToTransactions;
import com.automation.tasks.Register;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class TransactionStepDefinitions {

    private final EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();

    private static String testEmail;
    private static String testPassword;
    private static String testName;
    private static volatile boolean usuarioRegistrado = false;

    private Actor usuario() {
        return OnStage.theActorCalled("usuario");
    }

    @Before(order = 0)
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        if (testEmail == null) {
            testEmail = env.getProperty("test.credentials.email",
                            "testuser_" + System.currentTimeMillis() + "@example.com");
            testPassword = env.getProperty("test.credentials.password", "Password123!");
            testName = env.getProperty("test.credentials.name", "Test User");
        }
    }

    @Before(order = 1)
    public void registrarUsuario() {
        if (!usuarioRegistrado) {
            try {
                usuario().attemptsTo(
                    Register.withCredentials(testName, testEmail, testPassword)
                );
                usuario().attemptsTo(ClearBrowserState.now());
            } catch (Exception ignored) {
            }
            usuarioRegistrado = true;
        }
    }

    @Dado("el usuario se encuentra en la página de inicio de sesión")
    public void elUsuarioSeEncuentraEnLaPaginaDeInicioDeSesion() {
        usuario().attemptsTo(NavigateToLogin.page());
    }

    @Cuando("el usuario ingresa sus credenciales válidas")
    public void elUsuarioIngresaSusCredencialesValidas() {
        usuario().attemptsTo(Authenticate.withCredentials(testEmail, testPassword));
    }

    @Entonces("el usuario es redirigido al dashboard principal")
    public void elUsuarioEsRedirigidoAlDashboardPrincipal() {
        usuario().should(seeThat(DashboardVisibility.isDisplayed(), is(true)));
    }

    @Dado("el usuario ha iniciado sesión en la aplicación")
    public void elUsuarioHaIniciadoSesionEnLaAplicacion() {
        usuario().attemptsTo(Authenticate.withCredentials(testEmail, testPassword));
    }

    @Cuando("el usuario navega a la sección de transacciones")
    public void elUsuarioNavegaALaSeccionDeTransacciones() {
        usuario().attemptsTo(NavigateToTransactions.fromSidebar());
    }

    @Y("el usuario crea una transacción con descripción {string} y monto {string}")
    public void elUsuarioCreaUnaTransaccionConDescripcionYMonto(String descripcion, String monto) {
        usuario().attemptsTo(CreateTransaction.with(descripcion, monto));
    }

    @Entonces("la transacción {string} aparece en el listado")
    public void laTransaccionApareceEnElListado(String descripcion) {
        usuario().should(seeThat(TransactionVisibility.of(descripcion), is(true)));
    }

    @Cuando("el usuario navega a la sección de reportes")
    public void elUsuarioNavegaALaSeccionDeReportes() {
        usuario().attemptsTo(NavigateToReports.fromSidebar());
    }

    @Entonces("el sistema muestra el resumen financiero")
    public void elSistemaMuestraElResumenFinanciero() {
        usuario().should(seeThat(
            CurrentUrl.containsPath("/dashboard"), is(true)));
    }

    @Cuando("el usuario cierra su sesión desde el menú de usuario")
    public void elUsuarioCierraSuSesionDesdeElMenuDeUsuario() {
        usuario().attemptsTo(Logout.fromUserMenu());
    }

    @Entonces("el usuario es redirigido a la página de inicio de sesión")
    public void elUsuarioEsRedirigidoALaPaginaDeInicioDeSesion() {
        usuario().should(seeThat(
            CurrentUrl.containsPath("/login"), is(true)));
    }
}
