package com.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class CurrentUrl implements Question<String> {

    public static Question<String> ofThePage() {
        return new CurrentUrl();
    }

    public static Question<Boolean> containsPath(String path) {
        return actor -> BrowseTheWeb.as(actor).getDriver()
                            .getCurrentUrl().contains(path);
    }

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    }
}
