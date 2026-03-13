package com.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class DashboardVisibility implements Question<Boolean> {

    public static DashboardVisibility isDisplayed() {
        return new DashboardVisibility();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver()
                   .getCurrentUrl().contains("/dashboard");
    }
}
