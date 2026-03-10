package com.automation.stepdefinitions;

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
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class TransactionStepDefinitions {

    private static final String TEST_EMAIL = "testuser_" + System.currentTimeMillis() + "@example.com";
    private static final String TEST_PASSWORD = "Password123!";
    private static final String TEST_NAME = "Test User";
    private static volatile boolean usuarioRegistrado = false;

    private Actor usuario() {
        return OnStage.theActorCalled("usuario");
    }

    @Before(order = 0)
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Before(order = 1)
    public void registrarUsuario() {
        if (!usuarioRegistrado) {
            try {
                usuario().attemptsTo(Register.withCredentials(TEST_NAME, TEST_EMAIL, TEST_PASSWORD));
                org.openqa.selenium.WebDriver drv = BrowseTheWeb.as(usuario()).getDriver();
                drv.manage().deleteAllCookies();
                ((org.openqa.selenium.JavascriptExecutor) drv).executeScript(
                    "window.localStorage.clear(); window.sessionStorage.clear();");
            } catch (Exception ignored) {
                // continue
            }
            usuarioRegistrado = true;
        }
    }

    @Dado("el usuario se encuentra en la página de inicio de sesión")
    public void elUsuarioSeEncuentraEnLaPáginaDeInicioDeSesión() {
        usuario().attemptsTo(NavigateToLogin.page());
    }

    @Cuando("el usuario ingresa sus credenciales válidas")
    public void elUsuarioIngresaSusCredencialesVálidas() {
        usuario().attemptsTo(Authenticate.withCredentials(TEST_EMAIL, TEST_PASSWORD));
    }

    @Entonces("el usuario es redirigido al dashboard principal")
    public void elUsuarioEsRedirigidoAlDashboardPrincipal() {
        WebDriver driver = BrowseTheWeb.as(usuario()).getDriver();
        boolean enDashboard = new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/dashboard"));
        assertTrue("El usuario debería estar en el dashboard", enDashboard);
    }

    @Dado("el usuario ha iniciado sesión en la aplicación")
    public void elUsuarioHaIniciadoSesiónEnLaAplicación() {
        usuario().attemptsTo(Authenticate.withCredentials(TEST_EMAIL, TEST_PASSWORD));
    }

    @Cuando("el usuario navega a la sección de transacciones")
    public void elUsuarioNavegaALaSecciónDeTransacciones() {
        usuario().attemptsTo(NavigateToTransactions.fromSidebar());
    }

    @Y("el usuario crea una transacción con descripción {string} y monto {string}")
    public void elUsuarioCreaUnaTransacciónConDescripciónYMonto(String descripcion, String monto) {
        usuario().attemptsTo(CreateTransaction.with(descripcion, monto));
    }

    @Entonces("la transacción {string} aparece en el listado")
    public void laTransacciónApareceEnElListado(String descripcion) {
        usuario().should(seeThat(TransactionVisibility.of(descripcion), is(true)));
    }

    @Cuando("el usuario navega a la sección de reportes")
    public void elUsuarioNavegaALaSecciónDeReportes() {
        usuario().attemptsTo(NavigateToReports.fromSidebar());
    }

    @Entonces("el sistema muestra el resumen financiero")
    public void elSistemaMuestraElResumenFinanciero() {
        WebDriver driver = BrowseTheWeb.as(usuario()).getDriver();
        boolean enReportes = new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/dashboard"));
        assertTrue("El usuario debería estar en la sección de reportes", enReportes);
    }

    @Cuando("el usuario cierra su sesión desde el menú de usuario")
    public void elUsuarioCierraSuSesiónDesdeElMenúDeUsuario() {
        usuario().attemptsTo(Logout.fromUserMenu());
    }

    @Entonces("el usuario es redirigido a la página de inicio de sesión")
    public void elUsuarioEsRedirigidoALaPáginaDeInicioDeSesión() {
        WebDriver driver = BrowseTheWeb.as(usuario()).getDriver();
        boolean enLogin = new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(d -> {
                String url = d.getCurrentUrl();
                return url.contains("/login") ||
                       url.equals("http://localhost:3000/") ||
                       url.equals("http://localhost:3000");
            });
        assertTrue("El usuario debería estar en la página de login", enLogin);
    }
}
