package com.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.annotations.Step;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class JavaScriptClick implements Interaction {

    private final Target target;

    public JavaScriptClick(Target target) {
        this.target = target;
    }

    public static JavaScriptClick on(Target target) {
        return instrumented(JavaScriptClick.class, target);
    }

    @Override
    @Step("{0} clicks on #target using JavaScript")
    public <T extends Actor> void performAs(T actor) {
        WebElement element = target.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }
}
