package com.automation.tasks;

import com.automation.interactions.ClearBrowserState;
import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class Authenticate implements Task {

    private final String email;
    private final String password;

    public Authenticate(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Authenticate withCredentials(String email, String password) {
        return instrumented(Authenticate.class, email, password);
    }

    @Override
    @Step("{0} attempts to login with credentials")
    public <T extends Actor> void performAs(T actor) {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = environmentVariables.getProperty("webdriver.base.url", "http://localhost:3000");

        actor.attemptsTo(
            ClearBrowserState.now(),
            Open.url(baseUrl + "/login"),
            WaitUntil.the(BudgetManagementUi.EMAIL_INPUT, isVisible())
                     .forNoMoreThan(30).seconds(),
            Enter.theValue(email).into(BudgetManagementUi.EMAIL_INPUT),
            Enter.theValue(password).into(BudgetManagementUi.PASSWORD_INPUT),
            WaitUntil.the(BudgetManagementUi.LOGIN_BUTTON, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.LOGIN_BUTTON)
        );
    }
}
