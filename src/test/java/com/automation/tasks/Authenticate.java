package com.automation.tasks;

import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Authenticate implements Task {

    private final String email;
    private final String password;

    private Authenticate(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Authenticate withCredentials(String email, String password) {
        return new Authenticate(email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript(
            "window.localStorage.clear(); window.sessionStorage.clear();");

        actor.attemptsTo(
            Open.url("http://localhost:3000/login")
        );

        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.EMAIL_INPUT, isVisible())
                     .forNoMoreThan(30).seconds()
        );

        actor.attemptsTo(
            Enter.theValue(email).into(BudgetManagementUi.EMAIL_INPUT),
            Enter.theValue(password).into(BudgetManagementUi.PASSWORD_INPUT),
            WaitUntil.the(BudgetManagementUi.LOGIN_BUTTON, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.LOGIN_BUTTON)
        );

        new WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.urlContains("/dashboard"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/dashboard"));
    }
}
