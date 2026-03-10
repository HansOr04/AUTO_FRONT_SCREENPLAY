package com.automation.tasks;

import com.automation.ui.BudgetManagementUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Register implements Task {

    private final String nombre;
    private final String email;
    private final String password;

    private Register(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public static Register withCredentials(String nombre, String email, String password) {
        return new Register(nombre, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url("http://localhost:3000/register")
        );

        actor.attemptsTo(
            WaitUntil.the(BudgetManagementUi.DISPLAY_NAME_INPUT, isVisible())
                     .forNoMoreThan(30).seconds(),
            Enter.theValue("Test User").into(BudgetManagementUi.DISPLAY_NAME_INPUT),
            Enter.theValue(email).into(BudgetManagementUi.EMAIL_INPUT),
            Enter.theValue(password).into(BudgetManagementUi.PASSWORD_INPUT),
            Enter.theValue(password).into(BudgetManagementUi.CONFIRM_PASSWORD_INPUT),
            WaitUntil.the(BudgetManagementUi.REGISTER_BUTTON, isVisible())
                     .forNoMoreThan(30).seconds(),
            Click.on(BudgetManagementUi.REGISTER_BUTTON)
        );
    }
}
