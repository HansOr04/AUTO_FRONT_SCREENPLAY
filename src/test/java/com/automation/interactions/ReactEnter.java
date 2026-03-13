package com.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ReactEnter implements Interaction {

    private final String value;
    private final Target target;

    public ReactEnter(String value, Target target) {
        this.value = value;
        this.target = target;
    }

    public static ReactEnterBuilder theValue(String value) {
        return new ReactEnterBuilder(value);
    }

    @Override
    @Step("{0} sets React input #target to '#value'")
    public <T extends Actor> void performAs(T actor) {
        WebElement element = target.resolveFor(actor);
        JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        js.executeScript(
            "var setter = Object.getOwnPropertyDescriptor("
          + "window.HTMLInputElement.prototype, 'value').set;"
          + "setter.call(arguments[0], arguments[1]);"
          + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
          + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            element, value
        );
    }

    public static class ReactEnterBuilder {
        private final String value;

        ReactEnterBuilder(String value) {
            this.value = value;
        }

        public ReactEnter into(Target target) {
            return instrumented(ReactEnter.class, value, target);
        }
    }
}
