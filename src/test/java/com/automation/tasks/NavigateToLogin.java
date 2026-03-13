package com.automation.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class NavigateToLogin implements Task {

    public static NavigateToLogin page() {
        return instrumented(NavigateToLogin.class);
    }

    @Override
    @Step("{0} navigates to the login page")
    public <T extends Actor> void performAs(T actor) {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = environmentVariables.getProperty("webdriver.base.url", "http://localhost:3000");
        actor.attemptsTo(Open.url(baseUrl + "/login"));
    }
}
