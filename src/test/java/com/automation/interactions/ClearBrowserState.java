package com.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ClearBrowserState implements Interaction {

    public static ClearBrowserState now() {
        return instrumented(ClearBrowserState.class);
    }

    @Override
    @Step("{0} clears browser cookies and storage")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript(
            "window.localStorage.clear(); window.sessionStorage.clear();");
    }
}
