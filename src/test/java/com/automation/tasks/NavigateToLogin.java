package com.automation.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateToLogin implements Task {

    public static NavigateToLogin page() {
        return new NavigateToLogin();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url("http://localhost:3000")
        );
    }
}
