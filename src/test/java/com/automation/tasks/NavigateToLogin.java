package com.automation.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class NavigateToLogin implements Task {

    public static NavigateToLogin page() {
        return instrumented(NavigateToLogin.class);
    }

    @Override
    @Step("{0} navigates to the login page")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url("/login"));
    }
}
